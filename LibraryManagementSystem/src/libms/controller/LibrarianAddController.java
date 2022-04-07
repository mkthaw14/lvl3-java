package libms.controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import libms.LMS_Main;
import libms.model.entity.Librarian;
import libms.model.service.LibrarianDatabaseService;
import my_utility.Utilities;

public class LibrarianAddController
{
	@FXML
    private Button btn_close;

    @FXML
    protected TextField txt_email;

    @FXML
    protected TextField txt_nrcno;

    @FXML
    protected TextField txt_password;

    @FXML
    protected TextField txt_phone;

    @FXML
    protected void btn_cancel_click(ActionEvent event) 
    {
    	LMS_Main.closeWindow(btn_close);
    }

    @FXML
    protected void btn_clear_click(ActionEvent event) 
    {
    	txt_email.setText(null);
    	txt_password.setText(null);
    	txt_nrcno.setText(null);
    	txt_phone.setText(null);
    }

    @FXML
    void btn_save_click(ActionEvent event) throws IOException 
    {
    	String email = getEmail();
    	if(email == null)
    	{
    		Utilities.showWarning("Please fill the email field", AlertType.WARNING);
    		return;
    	}
 
    	String password = getPassword();
    	if(password == null)
    	{
    		Utilities.showWarning("Please fill the password field", AlertType.WARNING);
    		return;
    	}
    	
    	String nrcno = getNrcno();
    	if(nrcno == null)
    	{
    		Utilities.showWarning("Please fill the nrcno field", AlertType.WARNING);
    		return;
    	}
    	
    	String phone = getText(txt_phone.getText());
    	if(phone == null)
    	{
    		Utilities.showWarning("Please fill the phone field", AlertType.WARNING);
    		return;
    	}
    	Librarian l = new Librarian();
    	l.setEmail(email);
    	l.setPassword(password);
    	l.setNrcno(nrcno);
    	l.setPhone(phone);
    	
    	if(LibrarianDatabaseService.addLibrarian(l))
    	{
    		Utilities.showWarning("A new user has been added", AlertType.INFORMATION);
    		LMS_Main.changeScene("view/Librarian.fxml");
    	}
    	else
    	{
    		Utilities.showWarning("Something wrong", AlertType.ERROR);
    	}
    }
    
    protected String checkEmailFormat()
    {
    	String email = txt_email.getText();
    	if(email.isEmpty())
    	{
    		return null;
    	}
    	
    	if(email.contains("admin"))
    	{
    		Utilities.showWarning("the email should not include 'admin' ", AlertType.ERROR);
    		return null;
    	}
    	
    	if(email.contains("@"))
    	{
    		int index = email.indexOf("@");
    		
    		if(index == 0)
    		{
            	Utilities.showWarning("Incorrect email format", AlertType.ERROR);
    			return null;
    		}
    			
    		boolean emailFormat = email.substring(index, email.length()).equals("@gmail.com");
    		if(emailFormat)
    		{
        		System.out.println("@gmail.com");
        		return email;
    		} 
    		else
    		{
            	Utilities.showWarning("Incorrect email format", AlertType.ERROR);
    			return null;
    		}
    	}
    	else
    	{
        	Utilities.showWarning("Incorrect email format", AlertType.ERROR);
    	}
    	
    	return null;
    }
    
    private String getEmail()
    {
    	String email = checkEmailFormat();
    	
    	if(email == null)
    		return null;

    	if(LibrarianDatabaseService.emailExist(email))
    	{
    		Utilities.showWarning("Email Exist", AlertType.ERROR);
    		return null;
    	}
    	
    	return email;
    }
    
    /*private String getEmail()
    {
    	String email = txt_email.getText();
    	if(email.isEmpty())
    	{
    		return null;
    	}
    	
    	if(LibrarianDatabaseService.emailExist(email))
    	{
    		Utilities.showWarning("Email Exist", AlertType.ERROR);
    		return null;
    	}
    	
    	if(email.contains("admin"))
    	{
    		Utilities.showWarning("the email should not include 'admin' ", AlertType.ERROR);
    		return null;
    	}
    	
    	if(email.contains("@"))
    	{
    		int index = email.indexOf("@");
    		boolean emailFormat = email.substring(index, email.length()).equals("@gmail.com");
    		if(emailFormat)
    		{
        		System.out.println("@gmail.com");
        		return email;
    		} 		
    	}
    	else
    	{
        	Utilities.showWarning("Incorrect email format", AlertType.ERROR);
    	}
    	 	
    	return null;
    }*/
    
    protected String getPassword()
    {
    	String pass = txt_password.getText();
    	if(pass.isEmpty())
    	{
    		return null;
    	}
    	
    	if(pass.length() < 6)
    	{
    		Utilities.showWarning("Password length should be greater than 6", AlertType.ERROR);
    		return null;
    	}
    	
    	return pass;
    }
    
    private String getNrcno()
    {
    	String nrcno = txt_nrcno.getText();
    	if(nrcno.isEmpty())
    	{
    		return null;
    	}
    	
    	if(LibrarianDatabaseService.nrcnoExist(nrcno))
    	{
    		Utilities.showWarning("This Nrcno already exist", AlertType.ERROR);
    		return null;
    	}
    	
    	return nrcno;
    }
    
    protected String getText(String txt)
    {
    	if(txt.isEmpty())
    	{
    		return null;
    	}
    	
    	return txt;
    }
}
