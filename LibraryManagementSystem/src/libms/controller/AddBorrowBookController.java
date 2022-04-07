package libms.controller;

import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import libms.LMS_Main;
import libms.model.entity.BorrowBook;
import libms.model.entity.Librarian;
import libms.model.service.BookDatabaseService;
import libms.model.service.BorrowBookDatabaseService;
import libms.model.service.LibrarianDatabaseService;
import libms.model.service.MemberDatabaseService;
import my_utility.Utilities;

public class AddBorrowBookController implements Initializable
{
	 	@FXML
	    private Button btn_close;

	    @FXML
	    private TextField txt_card_id;

	    @FXML
	    private TextField txt_book_id;

	    @FXML
	    private TextField txt_librarian_email;

	    @FXML
	    private TextField txt_librarian_password;

	 

	    @FXML
	    void btn_cancel_click(ActionEvent event) 
	    {
	    	LMS_Main.closeWindow(btn_close);
	    }

	    @FXML
	    void btn_clear_click(ActionEvent event) 
	    {

	    }

	    @FXML
	    void btn_save_click(ActionEvent event) throws IOException, SQLException 
	    {
	    	BorrowBook borrowBook = new BorrowBook();
	    	
	    	if(!validCardID(borrowBook))
	    		return;
	    	
	    	if(!validBookID(borrowBook))
	    		return;
	    	
	    	if(!validLibrarianAccount(borrowBook))
	    		return;
	    	
	    	LocalDate dueDate = LocalDate.now().plusDays(3);
	    	borrowBook.setDue_date(dueDate);
	    	
	    	if(BorrowBookDatabaseService.addBorrowBook(borrowBook))
	    	{
	    		String msg = "A new borrow record has been added \n This book ID : " + borrowBook.getBook_id() + " must be returned at " + borrowBook.getDue_date();
	    		Utilities.showWarning(msg, AlertType.INFORMATION);
	    		LMS_Main.changeScene("view/BorrowBook.fxml");
	    	}
	    	else
	    	{
	    		Utilities.showWarning("Some wrong, go away", AlertType.ERROR);
	    	}
	    }

		private boolean validLibrarianAccount(BorrowBook borrowBook) throws IOException
		{
			String email = txt_librarian_email.getText();
			String password = txt_librarian_password.getText();
			
			Librarian user = LibrarianDatabaseService.validUser(email, password);
			
			if(user != null)
			{
				System.out.println("Email " + email);
				System.out.println("password " + password);
				if(user.getId() == MainController.currentUser.getId())
				{
					borrowBook.setLibrarian(user);
					return true;
				}
				else
				{
					Utilities.showWarning("Current Login User ID and provided User ID doesn't match", AlertType.ERROR);
					LMS_Main.closeWindow(btn_close);
					LMS_Main.changeScene("view/Login.fxml");
				}
			}
			else
			{
				Utilities.showWarning("Enter current librarian info correctly", AlertType.ERROR);
			}

			return false;
		}

		private boolean validBookID(BorrowBook borrowBook)
		{
			int book_id = Utilities.stringToInt(txt_book_id.getText());
			
			if(book_id == 0)
			{
				Utilities.showWarning("Enter only numbers", AlertType.WARNING);
				return false;
			}
			
			if(!BookDatabaseService.vertifyBookIDIsAvaiable(book_id))
			{
				Utilities.showWarning("This book id " + book_id + " is not avaiable", AlertType.WARNING);
				return false;
			}
			
			borrowBook.setBook_id(book_id);
			return true;
		}

		private boolean validCardID(BorrowBook borrowBook)
		{
			int card_id = Utilities.stringToInt(txt_card_id.getText());
			
			if(card_id == 0)
			{
				Utilities.showWarning("Enter only numbers", AlertType.WARNING);
				return false;
			}
			
			if(!MemberDatabaseService.validCardID(card_id))
			{
				Utilities.showWarning("Membership period expired or card_id doesn't exist", AlertType.WARNING);
				return false;
			}
			
			if(BorrowBookDatabaseService.vertifyPreviousBorrowedBookByCardID(card_id))
			{
				Utilities.showWarning("The card holder hasn't return all books", AlertType.WARNING);
				return false;
			}
			
			if(BorrowBookDatabaseService.vertifyBorrowedMoreThanThreeBooksInToday(card_id))
			{
				Utilities.showWarning("This guy has borrowed too many books", AlertType.WARNING);
				return false;
			}
			
			System.out.println("Card_ID : " + card_id);
			
			borrowBook.setCard_id(card_id);
			
			return true;
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1)
		{
		}
}
