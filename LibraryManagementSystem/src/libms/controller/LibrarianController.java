package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import libms.LMS_Main;
import libms.model.entity.Librarian;
import libms.model.service.LibrarianDatabaseService;
import my_utility.Utilities;

public class LibrarianController implements Initializable
{
	@FXML
    private TableColumn<Librarian, LocalDate> col_created_at;

    @FXML
    private TableColumn<Librarian, String> col_email;

    @FXML
    private TableColumn<Librarian, Integer> col_id;

    @FXML
    private TableColumn<Librarian, String> col_nrcno;

    @FXML
    private TableColumn<Librarian, String> col_password;

    @FXML
    private TableColumn<Librarian, String> col_phone;

    @FXML
    private TableView<Librarian> tbl_data;
    
    public static Librarian currentSelectedLibrarian;

    @FXML
    void btn_add_view_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.openNewWindow("view/LibrarianAdd.fxml", "Add Librarian");
    }

    @FXML
    void btn_back_to_Main_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }

    @FXML
    void btn_edit_view_click(ActionEvent event) throws IOException 
    {
    	if(currentSelectedLibrarian == null)
    	{
    		Utilities.showWarning("Select at least one librarian", AlertType.WARNING);
    		return;
    	}
    	LMS_Main.openNewWindow("view/LibrarianEdit.fxml", "Edit Librarian");
    }

    @FXML
    void btn_search_view_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.openNewWindow("view/LibrarianSearch.fxml", "Search Librarian");
    }

    private void setUpTable()
    {
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_email.setCellValueFactory(new PropertyValueFactory<>("email"));		
		col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
		col_nrcno.setCellValueFactory(new PropertyValueFactory<>("nrcno"));
		col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		col_created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    }
    
    private void updateTableData()
    {
    	List<Librarian> list = LibrarianSearchController.librarians;
    	
    	if(list == null)
    	{
    		list = LibrarianDatabaseService.getAllLibrarians();
    	}
    	else
    	{
    		LibrarianSearchController.librarians = null;
    	}
    	
    	tbl_data.setItems(FXCollections.observableArrayList(list));
    }
    
    private void addListeners()
    {
    	tbl_data.getSelectionModel().selectedItemProperty().addListener(
    			(obs, oldSelect, newSelect) -> {
    				if(newSelect != null)
    				{
    					currentSelectedLibrarian = newSelect;
    					System.out.println(currentSelectedLibrarian);
    				}
    			}
    			);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpTable();
		updateTableData();
		addListeners();
	}
}
