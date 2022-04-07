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
import javafx.scene.control.Alert.AlertType;
import libms.LMS_Main;
import libms.model.entity.Category;
import libms.model.service.CategoryDatabaseService;
import my_utility.Utilities;

public class CategoryController implements Initializable
{
    @FXML
    private TableColumn<Category, Integer> col_id;

    @FXML
    private TableColumn<Category, String> col_name;

    @FXML
    private TableView<Category> tbl_data;


    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_search_id;

    @FXML
    private TextField txt_search_name;

    
    private int selectedCategoryID;
    
    @FXML
    void btn_add_click(ActionEvent event) 
    {
    	String name = txt_name.getText();
    	
    	if(name.isEmpty())
    	{
    		Utilities.showWarning("Don't be lazy fill the name", AlertType.ERROR);
    		return;
    	}
    	
    	if(CategoryDatabaseService.addCategory(name))
    	{
    		Utilities.showWarning("A new category has been added", AlertType.INFORMATION);
    		loadCategories();
    		resetUI();
    	}
    	else
    	{
    		Utilities.showWarning("Sorry come back later", AlertType.ERROR);
    	}
    }

    private void loadCategories()
	{
    	List<Category> catList = CategoryDatabaseService.getAllCategory();
    	updateTableData(catList);
    	
	}
    
    private void updateTableData(List<Category> catlist)
    {
    	col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	tbl_data.setItems(FXCollections.observableArrayList(catlist));
    }

	@FXML
    void btn_back_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }

    @FXML
    void btn_edit_click(ActionEvent event) 
    {
    	String name = txt_name.getText();
    	
    	if(name.isEmpty())
    	{
    		Utilities.showWarning("Please fill the name field", AlertType.ERROR);
    		return;
    	}
    	
    	if(CategoryDatabaseService.updateCategory(selectedCategoryID, name))
    	{
    		String message = "The info of category ID : " + selectedCategoryID + " has been updated";
    		Utilities.showWarning(message, AlertType.INFORMATION);
    		loadCategories();
    		resetUI();
    	}
    	else
    	{
    		Utilities.showWarning("Sorry come back later", AlertType.ERROR);
    	}
    }

    @FXML
    void btn_search_click(ActionEvent event) 
    {
    	int id = 0;
    	
    	try
		{
    		if(!txt_search_id.getText().isEmpty())
    		{
            	id = Integer.parseInt(txt_search_id.getText());
    		}

		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			return;
		}
    	
    	String name = txt_search_name.getText();
    	
    	if(id == 0 && name.isEmpty())
    	{
    		Utilities.showWarning("Please fill at least one of these fields", AlertType.ERROR);
    		return;
    	}
    	
    	List<Category> list = CategoryDatabaseService.findCategories(id, name);
    	

		updateTableData(list);
    }
    
    void addListeners()
    {
    	tbl_data.getSelectionModel().selectedItemProperty().addListener(
    			
    			(obs, oldSelect, newSelect) -> {
    				if(newSelect != null)
    				{
    					selectedCategoryID = newSelect.getId();
    					txt_name.setText(newSelect.getName());
    				}
    			}
    			);
    }
    
    void resetUI()
    {
    	txt_name.setText(null);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub
		loadCategories();
		addListeners();
	}

}
