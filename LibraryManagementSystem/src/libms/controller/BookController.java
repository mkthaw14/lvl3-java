package libms.controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import libms.LMS_Main;
import libms.model.entity.Author;
import libms.model.entity.Book;
import libms.model.entity.Category;
import libms.model.service.AuthorDatabaseService;
import libms.model.service.BookDatabaseService;
import libms.model.service.CategoryDatabaseService;
import my_utility.Utilities;

public class BookController
{
    @FXML
    protected TableColumn<Book, String> col_author;

    @FXML
    protected TableColumn<Book, String> col_available;

    @FXML
    protected TableColumn<Book, String> col_category;

    @FXML
    protected TableColumn<Book, Integer> col_code;

    @FXML
    protected TableColumn<Book, String> col_title;

    @FXML
    protected TableView<Book> tbl_data;

    @FXML
    protected ComboBox<String> cbo_author;

    @FXML
    protected ComboBox<String> cbo_category;

    @FXML
    protected CheckBox chk_available;
    
    @FXML
    protected TextField txt_title;

    @FXML
    protected TextField txt_code;
    
    protected List<Author> authors;
    protected List<Category> categories;

    @FXML
    void btn_back_to_Main_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }

    @FXML
    void btn_view_add_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/BookAdd.fxml");
    }

    @FXML
    void btn_view_edit_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/BookEdit.fxml");
    }

    @FXML
    void btn_view_seach_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/BookSearch.fxml");
    }
    
    @FXML
    protected void btn_back_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Book.fxml");
    }

    @FXML
    protected void btn_reset_click(ActionEvent event) 
    {
		txt_title.setText(null);
		cbo_author.setValue(null);
		cbo_category.setValue(null);
    }
    
    protected int getCode()
    {
    	int code = 0;
    	
    	try
		{
    		code = Integer.parseInt(txt_code.getText());
    		
			if(BookDatabaseService.verifyBookID(code))
			{
				throw new myexception.IDAlreadyExistException("Book already exist");
			}
		} 
    	catch (NumberFormatException e)
		{
			System.err.println(e.getMessage());
			Utilities.showWarning("Fill the code field and only numbers are accepted", AlertType.ERROR);
			return 0;
		}
    	catch (myexception.IDAlreadyExistException e) 
    	{
			// TODO: handle exception
    		System.out.println(e.getMessage());
    		Utilities.showWarning("This code is already exist. Please enter a different code", AlertType.ERROR);
    		return 0;
		}
    	
    	return code;
    }
    
    protected Category getCategory()
	{
    	int categoryIndex = cbo_category.getSelectionModel().getSelectedIndex();
    	
    	if(categoryIndex == -1)
    	{
    		Utilities.showWarning("Select a category name", AlertType.ERROR);
    		return null;
    	}
    	
    	Category category = categories.get(categoryIndex);
		return category;
	}

	protected Author getAuthor()
	{	
    	int authorIndex = cbo_author.getSelectionModel().getSelectedIndex();
    	
    	if(authorIndex == -1)
    	{
    		Utilities.showWarning("Select an author name", AlertType.ERROR);
    		return null;
    	}
    	
    	Author author = authors.get(authorIndex);
    	
		return author;
	}
	
	protected String getTitle()
	{
		String title = txt_title.getText();
		
		if(title.isEmpty())
		{
    		Utilities.showWarning("Fill the title field", AlertType.ERROR);
    		return null;
		}
		
		return title;
	}
	
    protected void setUpAll()
    {
    	loadAuthors();
    	loadCategories();
    	loadBooks();
    }
    
    protected void loadCategories()
	{
    	categories = CategoryDatabaseService.getAllCategory();
    	List<String> names = categories.stream().map(c -> c.getName()).toList();
    	
    	cbo_category.setItems(FXCollections.observableArrayList(names));
    	
	}

	protected void loadAuthors()
    {
    	authors = AuthorDatabaseService.getAllAuthors();
    	
    	List<String> names = authors.stream().map(a -> a.getName()).toList();
    	cbo_author.setItems(FXCollections.observableArrayList(names));
    }
	
	protected void loadBooks()
	{
		List<Book> books = BookDatabaseService.getAllBooks();
            
		updateTableData(books);
	}
	
	protected void updateTableData(List<Book> books)
	{
		col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
		col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
		col_author.setCellValueFactory(new PropertyValueFactory<>("authorName"));
		col_category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		col_available.setCellValueFactory(new PropertyValueFactory<>("is_available"));
		
		tbl_data.setItems(FXCollections.observableArrayList(books));
	}
}

