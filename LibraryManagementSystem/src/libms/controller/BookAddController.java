package libms.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import libms.model.entity.Author;
import libms.model.entity.Book;
import libms.model.entity.Category;
import libms.model.service.BookDatabaseService;
import my_utility.Utilities;

public class BookAddController extends BookController implements Initializable
{
    
    @FXML
    private void btn_add_click(ActionEvent event) 
    {
    	int code = getCode(); 	
    	if(code == 0)
    		return;
    	
    	String title = getTitle();  	
    	if(title == null)
    		return;
    	
    	Author author = getAuthor();   	   	
    	if(author == null)
    		return;
    	
    	Category category = getCategory();   	   	
    	if(category == null)
    		return;
    	
    	int is_availiable = 1;
    	
    	Book book = new Book(code, is_availiable, author, category, title);
    	
    	if(BookDatabaseService.addBook(book))
    	{
    		Utilities.showWarning("A new book has been added", AlertType.INFORMATION);
    		loadBooks();
    	}
    	else
    	{
    		Utilities.showWarning("Some wrong, come back later", AlertType.ERROR);
    	}

    }
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpAll();	
	}
	
	@Override
	protected void btn_reset_click(ActionEvent event)
	{
		super.btn_reset_click(event);
		txt_code.setText(null);
	}
}
