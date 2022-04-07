package libms.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import libms.LMS_Main;
import libms.model.entity.Librarian;
import libms.model.service.LibrarianDatabaseService;
import my_utility.Utilities;

public class LibrarianSearchController
{
	    @FXML
	    private Button btn_close;

	    @FXML
	    private DatePicker dp_created_at;

	    @FXML
	    private TextField txt_email;

	    @FXML
	    private TextField txt_id;

	    @FXML
	    private TextField txt_nrcno;
	    
	    public static List<Librarian> librarians;

	    @FXML
	    void btn_cancel_click(ActionEvent event) 
	    {
	    	LMS_Main.closeWindow(btn_close);
	    }

	    @FXML
	    void btn_clear_click(ActionEvent event) 
	    {
	    	txt_email.setText(null);
	    	txt_id.setText(null);
	    	txt_nrcno.setText(null);
	    }

	    @FXML
	    void btn_search_click(ActionEvent event) throws IOException 
	    {
	    	int id = Utilities.stringToInt(txt_id.getText());
	    	String email = txt_email.getText();
	    	String nrcno = txt_nrcno.getText();
	    	LocalDate created_at = dp_created_at.getValue();
	    	
	    	System.out.println("id " + id);
	    	System.out.println("email " + email);
	    	System.out.println("nrcno " + nrcno);
	    	System.out.println("date " + created_at);
	    	if(id == 0 && email.isEmpty() && nrcno.isEmpty() && created_at == null)
	    	{
	    		Utilities.showWarning("Fill at least one field", AlertType.WARNING);
	    		return;
	    	}
	    	
	    	Librarian l = new Librarian();
	    	l.setId(id);
	    	l.setEmail(email);
	    	l.setNrcno(nrcno);
	    	l.setCreated_at(created_at);
	    	
	    	librarians =  LibrarianDatabaseService.findLibrarian(l);
    		LMS_Main.closeWindow(btn_close);
    		LMS_Main.changeScene("view/Librarian.fxml");
	    }
}
