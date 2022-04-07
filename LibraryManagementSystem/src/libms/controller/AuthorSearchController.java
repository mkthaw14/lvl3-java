package libms.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import libms.model.entity.Author;
import libms.model.service.AuthorDatabaseService;
import javafx.scene.control.Alert.AlertType;
import my_utility.Utilities;

public class AuthorSearchController extends AuthorController implements Initializable
{

	@FXML
	private TextField txt_id;
	
    @FXML
    void btn_search_click(ActionEvent event) 
    {
    	int id = 0;
    	
    	try
		{
    		if(!txt_id.getText().isEmpty())
    		{
    			id = Integer.parseInt(txt_id.getText());
    		}
			
		} catch (NumberFormatException e)
		{
			System.err.println(e.getMessage());
			Utilities.showWarning("Only numbers are accepted", AlertType.ERROR);
			return;
		}
    	
    	System.out.println("ID : " + id);
    	
    	String name = txt_name.getText();
    	String country = txt_country.getText();
    	
    	if(id == 0 && name.isEmpty() && country.isEmpty())
    	{
    		Utilities.showWarning("Please fill at least one of these fields", AlertType.WARNING);
    		return;
    	}
    	
    	Author author = new Author();
    	author.setId(id);
    	author.setCountry(country);
    	author.setName(name);
    	
    	System.out.println(author.getCountry());
    	System.out.println(author.getName());
    	 	
    	List<Author> authorList = AuthorDatabaseService.findAuthors(author);
    	updateTableData(authorList);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpAll();
	}
}
