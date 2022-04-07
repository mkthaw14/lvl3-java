package libms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import libms.LMS_Main;
import libms.model.entity.BorrowBook;
import libms.model.service.BorrowBookDatabaseService;
import my_utility.Utilities;

public class SearchBorrowBookController
{
	@FXML
    private Button btn_close;

    @FXML
    private DatePicker dp_borrow_date;

    @FXML
    private DatePicker dp_return_date;

    @FXML
    private TextField txt_card_id;

    @FXML
    private TextField txt_librarian_id;

    public static List<BorrowBook> borrowedBooks = null;
    
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
    void btn_search_click(ActionEvent event) throws IOException 
    {
    	int card_id = Utilities.stringToInt(txt_card_id.getText());
    	
    	LocalDate borrow_date = dp_borrow_date.getValue();
    	LocalDate return_date = dp_return_date.getValue();
    	
    	int librarian_id = Utilities.stringToInt(txt_librarian_id.getText());
    	
    	System.out.println("card_id " + card_id);
    	System.out.println("borrow_date " + borrow_date);
    	System.out.println("return_date " + return_date);
    	System.out.println("librarian id " + librarian_id);
    	
    	BorrowBook bb = new BorrowBook();
    	bb.setCard_id(card_id);
    	bb.setBorrow_date(borrow_date);
    	bb.setReturn_date(return_date);
    	bb.setLibrarian_id(librarian_id);
    	
    	borrowedBooks = BorrowBookDatabaseService.findBorrowBooks(bb);

    	LMS_Main.changeScene("view/BorrowBook.fxml");
    	LMS_Main.closeWindow(btn_close);
    }
}
