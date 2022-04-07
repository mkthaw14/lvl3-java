package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import libms.LMS_Main;
import libms.model.entity.Librarian;
import libms.model.service.LibrarianDatabaseService;
import javafx.scene.control.Alert.AlertType;
import my_utility.Utilities;

public class LibrarianEditController extends LibrarianAddController implements Initializable
{
	@FXML
    private Button btn_close;

    @FXML
    private DatePicker dp_created_at;

    @FXML
    private Label lbl_id;

    private int id = 0;

    @FXML
    void btn_update_click(ActionEvent event) throws IOException 
    {
    	id = LibrarianController.currentSelectedLibrarian.getId();
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
    	
    	LocalDate created_at = getCreated_date();
    	if(created_at == null)
    	{
    		Utilities.showWarning("Please fill the created date field", AlertType.WARNING);
    		return;
    	}
    	
    	Librarian l = new Librarian();
    	l.setId(id);
    	l.setEmail(email);
    	l.setPassword(password);
    	l.setNrcno(nrcno);
    	l.setPhone(phone);
    	l.setCreated_at(created_at);
    	
    	if(LibrarianDatabaseService.updateLibrarian(l))
    	{
    		String msg = "Librarina ID : " + l.getId() + " has been updated";
    		Utilities.showWarning(msg, AlertType.INFORMATION);
    		LMS_Main.changeScene("view/Librarian.fxml");
    	}
    	else
    	{
    		Utilities.showWarning("Something went wrong", AlertType.ERROR);
    	}
    }



	private LocalDate getCreated_date()
	{
		LocalDate date = dp_created_at.getValue();
		if(date == null)
		{
			return null;
		}

		return date;
	}


	private void autoFillUI()
	{
		lbl_id.setText(LibrarianController.currentSelectedLibrarian.getId() + "");	
		txt_email.setText(LibrarianController.currentSelectedLibrarian.getEmail());
		txt_password.setText(LibrarianController.currentSelectedLibrarian.getPassword());
		txt_nrcno.setText(LibrarianController.currentSelectedLibrarian.getNrcno());
		txt_phone.setText(LibrarianController.currentSelectedLibrarian.getPhone());
	}

	private String getEmail()
	{
		String email = txt_email.getText();
    	if(email.isEmpty())
    		return null;
    	
    	if(!vertifyEmailFormat())
    		return null;
		
    	if(LibrarianDatabaseService.vertifySimilarityWithOtherEmail(id, email))
    	{
    		Utilities.showWarning("email should not be similar with others", AlertType.ERROR);
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
    	
    	if(LibrarianDatabaseService.vertifySimilarityWithOtherEmail(id, email))
    	{
    		Utilities.showWarning("email should not be similar with others", AlertType.ERROR);
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
	
	private String getNrcno()
	{
    	String nrcno = txt_nrcno.getText();
    	if(nrcno.isEmpty())
    	{
    		return null;
    	}
    	
    	if(LibrarianDatabaseService.vertifySimilarityWithOtherNrcno(id, nrcno))
    	{
    		Utilities.showWarning("Nrcno should not be same with others", AlertType.ERROR);
    		return null;
    	}
    	
		return nrcno;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		autoFillUI();
	}
}
