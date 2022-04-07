package libms.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import libms.LMS_Main;
import libms.model.entity.Librarian;

public class MainController 
{
	public static Librarian currentUser;
	
    @FXML
    void author_clicked(MouseEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Author.fxml");
    }

    @FXML
    void category_clicked(MouseEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Category.fxml");
    }
    
    @FXML
    void book_clicked(MouseEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Book.fxml");
    }
    
    @FXML
    void member_clicked(MouseEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Member.fxml");
    }
    
    @FXML
    void book_borrow_clicked(MouseEvent event) throws IOException
    {
    	LMS_Main.changeScene("view/BorrowBook.fxml");
    }
    
    @FXML
    void return_book_clicked(MouseEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/ReturnBook.fxml");
    }
    

    @FXML
    void librarian_clicked(MouseEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Librarian.fxml");
    }
}
