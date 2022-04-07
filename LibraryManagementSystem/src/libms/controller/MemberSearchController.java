package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import libms.LMS_Main;
import libms.model.entity.Member;
import libms.model.service.MemberDatabaseService;
import my_utility.Utilities;

public class MemberSearchController extends MemberController
{
	@FXML
	private TextField txt_card_id;
    @FXML
    private ComboBox<String> cbo_created_at;

    @FXML
    private ComboBox<String> cbo_expired_at;
    
    private List<Member> members;
    
    public static List<Member> memberList = null;
    
    @FXML
    void btn_search_click(ActionEvent event) throws IOException 
    {
    	System.out.println("Search ???");
    	int card_id = getCard_ID();
    	String name = txt_name.getText();
    	String rollNo = txt_roll_no.getText();
    	String class_year = getClassYear();
    	Year academic_year = getAcademicYear();
    	LocalDate created_at = getCreatedDate();
    	LocalDate expired_at = getExpiredDate();
    	

    	Member member = new Member(card_id, rollNo, name, class_year, academic_year, created_at, expired_at);
    	System.out.println("Search >> id " + member.getCard_id());
    	System.out.println("Name : " + member.getName());
    	System.out.println("RollNo : " + member.getRoll_no());
    	System.out.println("Class Year : " + member.getClass_year());
    	System.out.println("Academic_Year : " + member.getAcademic_year());
    	System.out.println("Created at : " + member.getCreated_at());
    	System.out.println("Expired at : " + member.getExpired_at());
    	
    	if(card_id == 0 && name == null && rollNo == null && class_year == null && academic_year == null && created_at == null && expired_at == null)
    	{
    		Utilities.showWarning("Fill at least one of these fields", AlertType.ERROR);
    		return;
    	}
    	
    	memberList = MemberDatabaseService.findMembers(member);
    	
    	System.out.println("list size " + memberList.size());
    	MemberController.setMemberList(memberList);
    	LMS_Main.changeScene("view/Member.fxml");
    	
    }

    private LocalDate getExpiredDate()
	{
		LocalDate date = null;
		
		int index = cbo_expired_at.getSelectionModel().getSelectedIndex();
		if(index == -1)
		{
			return null;
		}
		
		String dateString = cbo_expired_at.getSelectionModel().getSelectedItem();
		date = LocalDate.parse(dateString);
		return date;
	}

	private LocalDate getCreatedDate()
	{
		LocalDate date = null;
		
		int index = cbo_created_at.getSelectionModel().getSelectedIndex();
		if(index == -1)
		{
			return null;
		}
		
		String dateString = cbo_created_at.getSelectionModel().getSelectedItem();
		date = LocalDate.parse(dateString);
		return date;
	}

	private int getCard_ID()
    {
    	int id = 0;
    	
    	try
		{
			id = Integer.parseInt(txt_card_id.getText());
		} catch (Exception e)
		{
			System.out.println("Not numbers");
		}
    	
    	return id;
    }
	
	private void loadClassYears()
	{
		List<String> class_years = members.stream().map(c -> c.getClass_year()).distinct().toList();
		
		class_years = class_years.stream().sorted().toList();
		cbo_class_year.setItems(FXCollections.observableArrayList(class_years));
	}
	
	private void loadAcademicYears()
	{
		List<String> academic_years = members.stream().map(c -> c.getAcademic_year().toString()).distinct().toList();
		
		academic_years = academic_years.stream().sorted().toList();
		cbo_academic_year.setItems(FXCollections.observableArrayList(academic_years));
		
	}
	
	private void loadCreatedDates()
	{
		List<String> createdDates = members.stream().map(c -> c.getCreated_at().toString()).distinct().toList();
		
		createdDates = createdDates.stream().sorted().toList();
		cbo_created_at.setItems(FXCollections.observableArrayList(createdDates));
	}
	
	private void loadExpiredDates()
	{
		List<String> expiredDates = members.stream().map(c -> c.getExpired_at().toString()).distinct().toList();
		
		expiredDates = expiredDates.stream().sorted().toList();
		cbo_expired_at.setItems(FXCollections.observableArrayList(expiredDates));
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		members = MemberDatabaseService.getAllMembers(); // loading comboBox UIs
		loadClassYears();
		loadAcademicYears();
		loadCreatedDates();
		loadExpiredDates();
	}
}
