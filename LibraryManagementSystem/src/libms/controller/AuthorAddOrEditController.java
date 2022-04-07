package libms.controller;

import my_utility.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import libms.model.entity.Author;
import libms.model.service.AuthorDatabaseService;

public class AuthorAddOrEditController extends AuthorController implements Initializable
{    
	    private int currentSelectedID;    

	    @FXML
	    void btn_add_click(ActionEvent event) 
	    {
	    	String name = txt_name.getText();
	    	String country = txt_country.getText();
	    	
	    	if(name == null || name.isEmpty())
	    	{
	    		Utilities.showWarning("Please provide the name field", AlertType.WARNING);
	    		return;
	    	}
	    	
	    	if(country == null || country.isEmpty())
	    	{
	    		Utilities.showWarning("Please provide the country field", AlertType.WARNING);
	    		return;
	    	}
	    		
	    	if(AuthorDatabaseService.addAuthor(name, country))
	    	{
	    		Utilities.showWarning("A new author has been added", AlertType.INFORMATION);
	    		loadAuthors();
	    		resetUI();
	    	}
	    	else
	    	{
	    		Utilities.showWarning("Something went wrong", AlertType.ERROR);
	    	}
	    }

	    @FXML
	    void btn_edit_click(ActionEvent event) 
	    {
	    	int id = currentSelectedID;
	    	String name = txt_name.getText();
	    	String country = txt_country.getText();
	    	
	    	
	    	
	    	if(name == null || name.isEmpty())
	    	{
	    		Utilities.showWarning("Please provide the name field", AlertType.WARNING);
	    		return;
	    	}
	    	
	    	if(country == null || country.isEmpty())
	    	{
	    		Utilities.showWarning("Please provide the country field", AlertType.WARNING);
	    		return;
	    	}
	    	
	    	Author author = new Author();
	    	author.setId(id);
	    	author.setName(name);
	    	author.setCountry(country);
	    	
	    	if(AuthorDatabaseService.updateAuthor(author))
	    	{
	    		String message = "The info of the author ID : " + author.getId() + " has been updated";
	    		Utilities.showWarning(message, AlertType.INFORMATION);
	    		loadAuthors();
	    		resetUI();
	    	}
	    	else
	    	{
	    		Utilities.showWarning("Something went wrong", AlertType.ERROR);
	    	}
	    }
	    

	    @FXML
	    void btn_reset_click(ActionEvent event) 
	    {
	    	resetUI();
	    }

		private void resetUI()
		{
	    	txt_name.setText(null);
	    	txt_country.setText(null);
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1)
		{
			setUpAll();
		}


		@Override
		protected void setSelectedAuthor(Author newSelect)
		{
			super.setSelectedAuthor(newSelect);
			
			currentSelectedID = newSelect.getId();
			System.out.println("current ID : " + currentSelectedID);
		}
}
