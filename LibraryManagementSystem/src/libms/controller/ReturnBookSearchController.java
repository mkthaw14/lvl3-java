package libms.controller;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import libms.LMS_Main;
import libms.model.entity.BorrowBook;
import libms.model.service.ReturnBookDatabaseService;
import my_utility.Utilities;

public class ReturnBookSearchController
{

	public static List<BorrowBook> borrowedBooks = null;
	
    @FXML
    private Button btn_close;

    @FXML
    private TextField txt_card_id;

    @FXML
    void btn_cancel_click(ActionEvent event) 
    {
    	LMS_Main.closeWindow(btn_close);
    }


    @FXML
    void btn_search_click(ActionEvent event) throws IOException 
    {
    	int card_id = Utilities.stringToInt(txt_card_id.getText());
    	
    	borrowedBooks = ReturnBookDatabaseService.findByCardID(card_id);
    	
    	LMS_Main.closeWindow(btn_close);
    	LMS_Main.changeScene("view/ReturnBook.fxml");
    }
}
