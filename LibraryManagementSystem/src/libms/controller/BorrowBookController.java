package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import libms.LMS_Main;
import libms.model.entity.BorrowBook;
import libms.model.service.BorrowBookDatabaseService;

public class BorrowBookController implements Initializable
{
	@FXML
    private TableColumn<BorrowBook, Integer> col_book_id;

    @FXML
    private TableColumn<BorrowBook, LocalDate> col_borrow_date;

    @FXML
    private TableColumn<BorrowBook, Integer> col_card_id;

    @FXML
    private TableColumn<BorrowBook, LocalDate> col_due_date;

    @FXML
    private TableColumn<BorrowBook, Integer> col_fees;

    @FXML
    private TableColumn<BorrowBook, Integer> col_id;

    @FXML
    private TableColumn<BorrowBook, LocalDate> col_return_date;
    
    @FXML
    private TableColumn<BorrowBook, Integer> col_librarian_id;

    @FXML
    private TableView<BorrowBook> tbl_data;


    
    @FXML
    void btn_add_view_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.openNewWindow("view/AddBorrowBook.fxml", "Add Borrow Record");
    }

    @FXML
    void btn_back_to_Main_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }

    @FXML
    void btn_edit_view_click(ActionEvent event) 
    {

    }

    @FXML
    void btn_search_view_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.openNewWindow("view/SearchBorrowBook.fxml", "Search Borrow Record");
    }

    void setUpTable()
    {
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
		col_card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
		col_borrow_date.setCellValueFactory(new PropertyValueFactory<>("borrow_date"));
		col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));
		col_fees.setCellValueFactory(new PropertyValueFactory<>("fee"));
		col_return_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
		col_librarian_id.setCellValueFactory(new PropertyValueFactory<>("librarian_id"));
		
		List<BorrowBook> books = SearchBorrowBookController.borrowedBooks;
		
		System.out.println("Bbooks " + books);
		if(books == null)
		{
			books = BorrowBookDatabaseService.getAllBorrowRecords();
		}
		else
		{
			books = SearchBorrowBookController.borrowedBooks;
			SearchBorrowBookController.borrowedBooks = null;
		}
		
		tbl_data.setItems(FXCollections.observableArrayList(books));
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpTable();
		System.out.println("Current Librarian : " + MainController.currentUser.toString());
	}
	
	
    
    

}
