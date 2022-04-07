package libms.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import libms.model.entity.Author;
import libms.model.entity.Book;
import libms.model.entity.Category;
import libms.model.service.BookDatabaseService;
import my_utility.Utilities;

public class BookSearchController extends BookController implements Initializable
{
    @FXML
    private ComboBox<String> cbo_availability;
    
	List<String> available_Txt = List.of("None", "Yes", "No");
	
    @FXML
    void btn_search_click(ActionEvent event) 
    {
    	int code = Utilities.stringToInt(txt_code.getText());
    	
    	String title = txt_title.getText();
    	
    	String authorName = cbo_author.getSelectionModel().getSelectedItem();
    	
    	String categoryName = cbo_category.getSelectionModel().getSelectedItem();
    	
    	if(code == 0 && title == null && authorName == null && categoryName == null)
    	{
    		Utilities.showWarning("Fill at least one of these fields", AlertType.WARNING);
    		return;
    	}
    	
    	String availability = cbo_availability.getSelectionModel().getSelectedItem();
    	System.out.println(availability);
    	int available = getAvailability(availability);
    	
    	System.out.println("av " + available);
    	
    	List<Book> books = BookDatabaseService.findBooks(code, title, authorName, categoryName, available);
    	
    	updateTableData(books);
    }
    

    private int getAvailability(String val)
    {
    	if(val.equals(available_Txt.get(1))) // Yes
    		return 1;
    	else if(val.equals(available_Txt.get(2))) // No
    		return 0;
    		
    	
    	return -1; // None
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub
		setUpAll();
	}
	
	@Override
	protected void setUpAll()
	{
		super.setUpAll();
		loadAvailability();
	}
	
	@Override
	protected void btn_reset_click(ActionEvent event)
	{
		super.btn_reset_click(event);
		cbo_availability.getSelectionModel().select(available_Txt.get(0));
	}
	
	private void loadAvailability()
	{
		cbo_availability.setItems(FXCollections.observableArrayList(available_Txt));
		cbo_availability.getSelectionModel().select(available_Txt.get(0));
	}
}
