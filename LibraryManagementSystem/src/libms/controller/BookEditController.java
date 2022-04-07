package libms.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import libms.model.entity.Author;
import libms.model.entity.Book;
import libms.model.entity.Category;
import libms.model.service.BookDatabaseService;
import my_utility.Utilities;

public class BookEditController extends BookController implements Initializable
{  
    private int selectedCode;
    
    @FXML
    void btn_edit_click(ActionEvent event) 
    {
    	String title = getTitle();
    	
    	if(title == null)
    		return;
    	
    	Author author = getAuthor();
    	
    	if(author == null)
    		return;
    	
    	Category category = getCategory();
    	
    	if(category == null)
    		return;
    	
    	int available = chk_available.isSelected() ? 1 : 0;

    	
    	Book book = new Book(selectedCode, available, author, category, title);
    	
    	if(BookDatabaseService.updateBook(book))
    	{
    		String message = "The info of the book code : " + book.getCode() + " has been updated";
    		Utilities.showWarning(message, AlertType.INFORMATION);
    		loadBooks();
    	}
    	else
    	{
    		Utilities.showWarning("Sorry come back later", AlertType.ERROR);
    	}
    }

    private void setUpListeners()
    {
    	tbl_data.getSelectionModel().selectedItemProperty().addListener(
    			(obs, oldSelect, newSelect) -> {
    				if(newSelect != null)
    				{
    					selectedCode = newSelect.getCode();
    					txt_title.setText(newSelect.getTitle());
    					cbo_author.getSelectionModel().select(newSelect.getAuthorName());
    					cbo_category.getSelectionModel().select(newSelect.getCategoryName());
    					
    					boolean available = newSelect.getIs_available() == 1 ? true: false;
    					chk_available.setSelected(available);
    				}
    			}
    			);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpAll();
	}
	
	@Override
	protected void setUpAll()
	{
		super.setUpAll();
		setUpListeners();
	}
	
	@Override
	protected void btn_reset_click(ActionEvent event)
	{
		// TODO Auto-generated method stub
		super.btn_reset_click(event);
		chk_available.setSelected(false);
	}
}
