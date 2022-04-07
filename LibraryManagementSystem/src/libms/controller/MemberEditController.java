package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.AclEntry;
import java.time.LocalDate;
import java.time.Year;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import libms.LMS_Main;
import libms.model.entity.Member;
import libms.model.service.MemberDatabaseService;
import my_utility.Utilities;

public class MemberEditController extends MemberController
{
    @FXML
    private DatePicker dp_date;
    
    @FXML
    private Label lbl_card_id;
    
    @FXML
    private void btn_update_click(ActionEvent event) throws IOException 
    {
    	String name = getName();
    	if(name == null)
    		return;
    	
    	String rollNo = getRollNo();
    	if(rollNo == null)
    		return;
    	
    	String class_year = getClassYear();
    	if(class_year == null)
    		return;
    	
    	Year academic_year = getAcademicYear();
    	if(academic_year == null)
    		return;
    	
    	LocalDate created_at = getCreatedDate();
    	if(created_at == null)
    		return;
    	
    	Member member = new Member();
    	
    	member.setCard_id(this.currentSelectedMemeber.getCard_id());
    	member.setName(name);
    	member.setRoll_no(rollNo);
    	member.setClass_year(class_year);
    	member.setAcademic_year(academic_year);
    	member.setCreated_at(created_at);
    	
    	if(MemberDatabaseService.updateMember(member))
    	{
    		String message = "Member ID " + member.getCard_id() + " has been update";
    		Utilities.showWarning(message, AlertType.INFORMATION);
    	}
    	else
    	{
    		Utilities.showWarning("Something went wrong", AlertType.ERROR);
    	}
    	
    	LMS_Main.closeWindow(btn_close);
    	LMS_Main.changeScene("view/Member.fxml");
    }

	private LocalDate getCreatedDate()
	{
		LocalDate date = null;
		
		date = dp_date.getValue();
		
		if(dp_date == null)
		{
			Utilities.showWarning("Enter created date", AlertType.WARNING);
		}
		
		return date;
	}
	
	private void autoFillUIs(Member member)
	{
		lbl_card_id.setText(member.getCard_id() + "");
		txt_name.setText(member.getName());
		txt_roll_no.setText(member.getRoll_no());
		cbo_class_year.setValue(member.getClass_year());
		cbo_academic_year.setValue(member.getAcademic_year().toString());
		dp_date.setValue(member.getCreated_at());
	}
	
	@Override
	protected void clearUI()
	{
		super.clearUI();
		dp_date.setValue(null);
	}
	
	@Override
	protected void setSelectedMember(Member member)
	{
		super.setSelectedMember(member);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpAcademicYears();
		setUpClassYears();
	}
	
	@Override
	public void init(Member currentSelectedMember)
	{
		super.init(currentSelectedMember);
		System.out.println(this + " " + this.currentSelectedMemeber.toString());
		autoFillUIs(this.currentSelectedMemeber);
	}
}
