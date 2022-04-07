package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import libms.LMS_Main;
import libms.model.entity.BorrowBook;
import libms.model.service.BorrowBookDatabaseService;
import libms.model.service.ReturnBookDatabaseService;
import my_utility.Utilities;

public class ReturnBookController implements Initializable
{
	@FXML
    private TableColumn<BorrowBook, LocalDate> col_borrow_date;

    @FXML
    private TableColumn<BorrowBook, Integer> col_card_id;

    @FXML
    private TableColumn<BorrowBook, Integer> col_code;

    @FXML
    private TableColumn<BorrowBook, LocalDate> col_due_date;

    @FXML
    private TableColumn<BorrowBook, Integer> col_fees;

    @FXML
    private TableView<BorrowBook> tbl_data;

    private List<BorrowBook> borrowedBooks; 
    private BorrowBook currentSelectRecord;
    private int fineFeePerDay = 150;
    
    @FXML
    void btn_back_to_Main_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }

    @FXML
    void btn_confirm_return_click(ActionEvent event) throws SQLException 
    {
    	if(currentSelectRecord == null)
    	{
    		Utilities.showWarning("Select at least one record", AlertType.ERROR);
    		return;
    	}
    	
    	Alert alert = Utilities.showConfirmation("Please confirm the action to return the book", AlertType.CONFIRMATION);
    	
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get() == ButtonType.OK)
		{
			if(ReturnBookDatabaseService.confirmToReturn(currentSelectRecord))
			{
				String msg = "Book ID " + currentSelectRecord.getBook_id() + " has returned";
				Utilities.showWarning(msg, AlertType.INFORMATION);
				updateTableData();
			}
			else
			{
				Utilities.showWarning("Some went wrong", AlertType.INFORMATION);
			}
		}
    }

    @FXML
    void btn_search_view_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.openNewWindow("view/ReturnBookSearch.fxml", "Search Borrow History");
    }

    void setUpTable()
    {
    	col_code.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_card_id.setCellValueFactory(new PropertyValueFactory<>("book_title"));
		col_borrow_date.setCellValueFactory(new PropertyValueFactory<>("borrow_date"));
		col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));
		col_fees.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }
    
    private void updateTableData()
    {
		List<BorrowBook> books = ReturnBookSearchController.borrowedBooks;
		
		System.out.println("bb " + books);
		if(books == null)
		{
			borrowedBooks = ReturnBookDatabaseService.getUnReturnedBook();
		}
		else
		{
			borrowedBooks = ReturnBookSearchController.borrowedBooks;
			ReturnBookSearchController.borrowedBooks = null;
		}
		

		tbl_data.setItems(FXCollections.observableArrayList(borrowedBooks));
    }
    
    
    private void addListeners()
    {
    	tbl_data.getSelectionModel().selectedItemProperty().addListener(
    			(obs, oldSelect, newSelect) -> {
    				if(newSelect != null)
    				{
    					currentSelectRecord = newSelect;
    				}
    			}
    			);
    	
    }
    
    private void setFineFee()
    {
    	List<BorrowBook> list = ReturnBookDatabaseService.getUnReturnedBook();
    	
    	LocalDate currentDate = LocalDate.now();
    	list.stream().forEach(b -> {
    		if(b.getDue_date().isBefore(currentDate))
    		{
    			long diff = ChronoUnit.DAYS.between(b.getDue_date(), currentDate);
    			int fine = (int) diff * fineFeePerDay;
    			b.setFee(fine);
    			
    			System.out.println("Fine " + b.getFee());
    			if(ReturnBookDatabaseService.updateFee(b.getId(), b.getFee()))
    			{
    				System.out.println("Fine fee updated");
    			}
    			else
    			{
    				System.out.println("fee not updated");
    			}
    		}
    	});
    }
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpTable();
		setFineFee();
		updateTableData();
		addListeners();
	}
}
