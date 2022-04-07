package libms.model.entity;

import java.time.LocalDate;
import java.time.Year;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member
{
    private final IntegerProperty card_id_property  = new SimpleIntegerProperty();
    private final SimpleObjectProperty<Year> academic_yearProperty = new SimpleObjectProperty<Year>();
    private final SimpleStringProperty rnoProperty = new SimpleStringProperty();
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();   
    private final SimpleStringProperty class_yearProperty = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> created_atProperty = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> expired_atProperty = new SimpleObjectProperty<LocalDate>();
    
	private int card_id;
	private String roll_no;
	private String name;
	private String class_year;
	private Year academic_year;
	private LocalDate created_at;
	private LocalDate expired_at;
	
	public Member() {}
	
	public Member(int card_id, String roll_no, String name, String class_year, Year academic_year)
	{
		super();
		this.card_id = card_id;
		this.roll_no = roll_no;
		this.name = name;
		this.class_year = class_year;
		this.academic_year = academic_year;
		
	}
	
	public Member(int card_id, String roll_no, String name, String class_year, Year academic_year, LocalDate created_at,
			LocalDate expired_at)
	{
		super();
		this.card_id = card_id;
		this.roll_no = roll_no;
		this.name = name;
		this.class_year = class_year;
		this.academic_year = academic_year;
		this.created_at = created_at;
		this.expired_at = expired_at;
	}
	


	public int getCard_id()
	{
		return card_id;
	}
	public void setCard_id(int card_id)
	{
		this.card_id = card_id;
	}
	public String getRoll_no()
	{
		return roll_no;
	}
	public void setRoll_no(String roll_no)
	{
		this.roll_no = roll_no;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getClass_year()
	{
		return class_year;
	}
	public void setClass_year(String class_year)
	{
		this.class_year = class_year;
	}
	public Year getAcademic_year()
	{
		return academic_year;
	}
	public void setAcademic_year(Year academic_year)
	{
		this.academic_year = academic_year;
	}
	public LocalDate getCreated_at()
	{
		return created_at;
	}
	public void setCreated_at(LocalDate created_at)
	{
		this.created_at = created_at;
	}
	public LocalDate getExpired_at()
	{
		return expired_at;
	}
	public void setExpired_at(LocalDate expired_at)
	{
		this.expired_at = expired_at;
	}

	public final IntegerProperty card_id_propertyProperty()
	{
		return this.card_id_property;
	}
	

	public final int getCard_id_property()
	{
		return this.card_id_propertyProperty().get();
	}
	

	public final void setCard_id_property(final int card_id_property)
	{
		this.card_id_propertyProperty().set(card_id_property);
	}
	

	@Override
	public String toString()
	{
		return "Member [card_id=" + card_id + ", roll_no=" + roll_no + ", name=" + name
				+ ", class_year=" + class_year + ", academic_year=" + academic_year + ", created_at=" + created_at
				+ ", expired_at=" + expired_at + "]";
	}

	public final SimpleStringProperty rnoPropertyProperty()
	{
		return this.rnoProperty;
	}
	

	public final String getRnoProperty()
	{
		return this.rnoPropertyProperty().get();
	}
	

	public final void setRnoProperty(final String rnoProperty)
	{
		this.rnoPropertyProperty().set(rnoProperty);
	}
	

	public final SimpleStringProperty namePropertyProperty()
	{
		return this.nameProperty;
	}
	

	public final String getNameProperty()
	{
		return this.namePropertyProperty().get();
	}
	

	public final void setNameProperty(final String nameProperty)
	{
		this.namePropertyProperty().set(nameProperty);
	}
	

	public final SimpleStringProperty class_yearPropertyProperty()
	{
		return this.class_yearProperty;
	}
	

	public final String getClass_yearProperty()
	{
		return this.class_yearPropertyProperty().get();
	}
	

	public final void setClass_yearProperty(final String class_yearProperty)
	{
		this.class_yearPropertyProperty().set(class_yearProperty);
	}
	

	public final SimpleObjectProperty<LocalDate> created_atPropertyProperty()
	{
		return this.created_atProperty;
	}
	

	public final LocalDate getCreated_atProperty()
	{
		return this.created_atPropertyProperty().get();
	}
	

	public final void setCreated_atProperty(final LocalDate created_atProperty)
	{
		this.created_atPropertyProperty().set(created_atProperty);
	}
	

	public final SimpleObjectProperty<LocalDate> expired_atPropertyProperty()
	{
		return this.expired_atProperty;
	}
	

	public final LocalDate getExpired_atProperty()
	{
		return this.expired_atPropertyProperty().get();
	}
	

	public final void setExpired_atProperty(final LocalDate expired_atProperty)
	{
		this.expired_atPropertyProperty().set(expired_atProperty);
	}

	public final SimpleObjectProperty<Year> academic_yearPropertyProperty()
	{
		return this.academic_yearProperty;
	}
	

	public final Year getAcademic_yearProperty()
	{
		return this.academic_yearPropertyProperty().get();
	}
	

	public final void setAcademic_yearProperty(final Year academic_yearProperty)
	{
		this.academic_yearPropertyProperty().set(academic_yearProperty);
	}
	
	

	
}
