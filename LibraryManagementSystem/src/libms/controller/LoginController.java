package libms.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import libms.LMS_Main;
import libms.model.service.LibrarianDatabaseService;

public class LoginController
{
    @FXML
    private Label lbl_status;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_password;

    @FXML
    void btn_login_click(ActionEvent event) throws IOException 
    {
    	String email = txt_email.getText();
    	String pass = txt_password.getText();
    	

    	if(LibrarianDatabaseService.verifyUser(email, pass))
    	{
    		lbl_status.setText("Login success");
    		MainController.currentUser = LibrarianDatabaseService.validUser(email, pass);
    		LMS_Main.changeScene("view/Main.fxml");
    	}
    	else
    	{
    		lbl_status.setText("Login fail");
    	}
    }
}
