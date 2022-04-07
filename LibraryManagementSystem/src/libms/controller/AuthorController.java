package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import libms.LMS_Main;
import libms.model.entity.Author;
import libms.model.service.AuthorDatabaseService;

public class AuthorController
{
    @FXML
    protected TableColumn<Author, String> col_country;

    @FXML
    protected TableColumn<Author, Integer> col_id;

    @FXML
    protected TableColumn<Author, String> col_name;

    @FXML
    protected TableView<Author> tbl_data;

    @FXML
    protected TextField txt_country;

    @FXML
	protected TextField txt_name;
    
    @FXML
    void btn_add_edit_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/AuthorAddOrEdit.fxml");
    }

    @FXML
    void btn_search_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/AuthorSearch.fxml");
    }
    
    @FXML
    void btn_back_to_Main_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }

    @FXML
    protected void btn_back_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Author.fxml");
    }
    
    
	protected void loadAuthors()
	{
		List<Author> authors = AuthorDatabaseService.getAllAuthors();
		
		updateTableData(authors);
	}
	
	protected void setUpListeners()
	{
		tbl_data.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldSelect, newSelect) ->
				{
					if(newSelect != null)
					{
						setSelectedAuthor(newSelect);
					}
				}
				);
	}
	
	protected void setSelectedAuthor(Author newSelect)
	{
		txt_name.setText(newSelect.getName());
		txt_country.setText(newSelect.getCountry());
	}
	
	protected void setUpAll() 
	{
		setUpListeners();
		loadAuthors();	
	}
	
	protected void updateTableData(List<Author> authorList)
	{
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_country.setCellValueFactory(new PropertyValueFactory<>("country"));
		
		tbl_data.setItems(FXCollections.observableArrayList(authorList));
	}
}
