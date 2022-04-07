package libms.model.entity;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.SQLException;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libms.model.service.MemberDatabaseService;

public class MemberData
{
	
    public ObservableList<Member> getMemberList() {
        return memberList;
    }
    
	public ObservableList<Member> memberList = FXCollections.observableArrayList(m -> 
	new Observable[] {m.card_id_propertyProperty(), m.namePropertyProperty(), m.rnoPropertyProperty(),
			m.class_yearPropertyProperty(), m.academic_yearPropertyProperty(),
			m.created_atPropertyProperty(), m.expired_atPropertyProperty()});
	
	private SimpleObjectProperty<Member> currentMembers = new SimpleObjectProperty<Member>();
	
	
    public ObservableList<Member> loadMembers() throws SQLException {
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList(MemberDatabaseService.getAllMembers());
        this.memberList = memberObservableList;
        return memberObservableList;
    }
    
    public void refreshMember() throws SQLException {
        this.memberList.clear();
        this.memberList.addAll(FXCollections.observableArrayList(MemberDatabaseService.getAllMembers()));
    }
    
    public boolean addMember(Member member) throws SQLException {
        boolean result = MemberDatabaseService.addMember(member);
        
        if(result)
            refreshMember();
        
        return result;
    }
}
