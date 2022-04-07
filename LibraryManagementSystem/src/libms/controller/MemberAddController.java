package libms.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Year;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import libms.LMS_Main;
import libms.model.entity.Member;
import libms.model.entity.MemberData;
import libms.model.service.MemberDatabaseService;
import my_utility.Utilities;
import myexception.IDAlreadyExistException;

public class MemberAddController extends MemberController
{
	private MemberData data;
	
    @FXML
    private TextField txt_card_id;

    @FXML
    private void btn_save_click(ActionEvent event) throws IOException, SQLException 
    {
    	int card_id = getCard_ID();   	
    	if(card_id == 0)
    	{
    		Utilities.showWarning("Card ID required", AlertType.WARNING);
    		return;
    	}
    	
    	String name = getName();
    	if(name == null)
    	{
    		Utilities.showWarning("Name required", AlertType.WARNING);
    		return;
    	}
    	
    	String roll_no = getRollNo();
    	if(roll_no == null)
    	{
    		return;
    	}
    	
    	String class_year = getClassYear();
    	if(class_year == null)
    	{
    		Utilities.showWarning("Class year required", AlertType.WARNING);
    		return;
    	}
    	
    	Year academic_year = getAcademicYear();
    	if(academic_year == null)
    	{
    		Utilities.showWarning("Academic year required", AlertType.WARNING);
    		return;
    	}
    	
    	Member member = new Member(card_id, roll_no, name, class_year, academic_year);
    			
    	if(MemberDatabaseService.addMember(member))
    	{
    		Utilities.showWarning("A new member has been added", AlertType.INFORMATION);
        	//MemberController controller = LMS_Main.getController("view/Member.fxml");
        	//controller.loadMembers();
    	}
    	else
    	{
    		Utilities.showWarning("Something went wrong", AlertType.ERROR);
    	}
    	
    	LMS_Main.changeScene("view/Member.fxml");
    }
 
	private int getCard_ID()
	{
		int card_id = 0;
		
		try
		{
			card_id = Integer.parseInt(txt_card_id.getText());
			
			if(MemberDatabaseService.cardIDExist(card_id))
			{
				throw new IDAlreadyExistException("Id already exists");
			}
		} catch (NumberFormatException e)
		{
			e.getMessage();
			
		}
		catch(IDAlreadyExistException e)
		{
			System.err.println(e.getMessage());
		}
		return card_id;
	}
	
	@Override
	protected void setSelectedMember(Member member)
	{
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		setUpAcademicYears();
		setUpClassYears();
	}
	

}
