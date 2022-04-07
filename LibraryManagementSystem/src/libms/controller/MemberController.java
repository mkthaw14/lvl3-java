package libms.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import libms.LMS_Main;
import libms.model.entity.Member;
import libms.model.entity.MemberData;
import libms.model.service.MemberDatabaseService;
import my_utility.Utilities;

public class MemberController implements Initializable
{
	@FXML
    TableColumn<Member, Year> col_academic_year;

    @FXML
    TableColumn<Member, Integer> col_card_id;

    @FXML
    TableColumn<Member, String> col_class_year;

    @FXML
    TableColumn<Member, LocalDate> col_created_at;

    @FXML
    TableColumn<Member, LocalDate> col_expired_date;

    @FXML
    TableColumn<Member, String> col_name;

    @FXML
    TableColumn<Member, String> col_roll_no;

    @FXML
    TableView<Member> tbl_data;

    private MemberData data;
    
    @FXML
    protected ComboBox<String> cbo_academic_year;

    @FXML
    protected ComboBox<String> cbo_class_year;

    @FXML
    protected TextField txt_name;

    @FXML
    protected TextField txt_roll_no;
    
    @FXML
    protected Button btn_close;
    
    protected Member currentSelectedMemeber;
    private static List<Member> members = new ArrayList<Member>();
    
   
    @FXML
    private void btn_add_view_click(ActionEvent event) throws IOException 
    {
    	MemberAddController controller = LMS_Main.openNewWindow("view/MemberAdd.fxml", "Add Member");
    	controller.init(data);
    }

    @FXML
    private void btn_edit_view_click(ActionEvent event) throws IOException 
    {
    	if(this.currentSelectedMemeber == null)
    	{
    		Utilities.showWarning("Select one of the member row", AlertType.ERROR);
    		return;
    	}
    	
    	MemberEditController controller = LMS_Main.openNewWindow("view/MemberEdit.fxml", "Edit Member");
    	controller.init(this.currentSelectedMemeber);
    }
    
    @FXML
    private void btn_search_view_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.openNewWindow("view/MemberSearch.fxml", "Search Member");
    }
    
    @FXML
    private void btn_back_to_Main_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.changeScene("view/Main.fxml");
    }
    
    protected void setUpListeners()
    {
    	tbl_data.getSelectionModel().selectedItemProperty().addListener(
    			(obs, oldSelect, newSelect) -> {
    				if(newSelect != null)
    				{
    					setSelectedMember(newSelect);
    				}
    			}
    			);
    }
    
    protected void setSelectedMember(Member member)
	{
    	this.currentSelectedMemeber = member;
    	System.out.println("CurrentMember : " + this.currentSelectedMemeber);
	}
    

	@FXML
    protected void btn_cancel_click(ActionEvent event) throws IOException 
    {
    	LMS_Main.closeWindow(btn_close);
    	LMS_Main.changeScene("view/Member.fxml");
    }

    @FXML
    protected void btn_clear_click(ActionEvent event) 
    {

    }
   
    protected void loadMembers() throws SQLException
    {
    	System.out.println("load members " + col_class_year);

    	List<Member> list = MemberSearchController.memberList;
    	
    	if(list == null)
    	{
    		members = MemberDatabaseService.getAllMembers();
    	}
    	else
    	{
    		members = list;
    		MemberSearchController.memberList = null;
    	}
    	
    	if(this.data != null)
    	{
    		throw new IllegalStateException("Data can only be initialized once");
    	}
    	
    	this.data = new MemberData();
    	
    	col_card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
    	col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	col_academic_year.setCellValueFactory(new PropertyValueFactory<>("academic_year"));
    	col_roll_no.setCellValueFactory(new PropertyValueFactory<>("roll_no"));
    	col_class_year.setCellValueFactory(new PropertyValueFactory<>("class_year"));
    	col_created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    	col_expired_date.setCellValueFactory(new PropertyValueFactory<>("expired_at"));
    	
    	tbl_data.getItems().clear();
    	
    	//this.data.loadMembers();
    	System.out.println("Static members " + members);
    	
    	tbl_data.setItems(FXCollections.observableArrayList(members));
    }
	
	protected void setUpAcademicYears()
	{
		Year year = Year.of(2013);
	
		List<Year> years = new ArrayList<Year>();

		for(int x = 0; x < 5; x++)
		{
			year = year.plusYears(1);
			years.add(year);
		}
		
		List<String> yr = years.stream().map(y -> y.toString()).toList();
		cbo_academic_year.setItems(FXCollections.observableArrayList(yr));
	}
	
	protected void setUpClassYears()
	{
		List<String> years = List.of("1st year", "2nd years", "3rd years", "4th years");
		
		cbo_class_year.setItems(FXCollections.observableArrayList(years));
	}
	
	protected String getName()
	{
		String name = txt_name.getText();
		if(name.isEmpty())
			return null;
		
		return name;
	}

	protected Year getAcademicYear()
	{		
		int index = cbo_academic_year.getSelectionModel().getSelectedIndex();
		if(index == -1)
		{
			return null;
		}
		
		String yr = cbo_academic_year.getSelectionModel().getSelectedItem();
		Year year = Year.parse(yr);
		
		return year;
	}

	protected String getRollNo()
	{
		String rollNo = txt_roll_no.getText();
		
		if(rollNo.length() == 0)
		{
			Utilities.showWarning("Roll number required", AlertType.WARNING);
			return null;
		}
		
		String prefixLetters = rollNo.substring(0, 2);
		String lastNumbers = rollNo.substring(2, rollNo.length());
		
		
		try
		{
			if(isNumeric(prefixLetters))
			{
				Utilities.showWarning("The prefix letters must not be digits", AlertType.WARNING);
				throw new Exception();
			}
			
			if(!isNumeric(lastNumbers))
			{
				Utilities.showWarning("The last numbers must not be letters", AlertType.WARNING);
				throw new Exception();
			}
			
			if(MemberDatabaseService.rollNoExists(rollNo))
			{
				Utilities.showWarning("Roll Number already exist", AlertType.WARNING);
				throw new Exception();
			}
		} catch (Exception e)
		{
			System.err.println("Incorrect roll number format");
			return null;
		}
				
		return rollNo;
	}
	
	protected boolean isNumeric(String str)
	{
		try
		{
			Integer.parseInt(str);
		} catch (Exception e)
		{
			System.out.println("Not numbers");
			return false;
		}
		return true;
	}

    protected String getClassYear()
    {
    	int index = cbo_class_year.getSelectionModel().getSelectedIndex();
    	
    	if(index == -1)
    	{
    		return null;
    	}
    	
    	String year = cbo_class_year.getSelectionModel().getSelectedItem();
    	return year;
    }

    protected void clearUI()
    {
    	txt_name.setText(null);
    	txt_roll_no.setText(null);
    	cbo_academic_year.setValue(null);
    	cbo_class_year.setValue(null);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		System.out.println("HEllo");
		
		try
		{
			loadMembers();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setUpListeners();
	}

	public void init(MemberData data)
	{
		this.data = data;
	}
	
	public void init(Member currentSelectedMember)
	{
		this.currentSelectedMemeber = currentSelectedMember;
	}
	
	public static void setMemberList(List<Member> list)
	{
		members = list;
	}
}
