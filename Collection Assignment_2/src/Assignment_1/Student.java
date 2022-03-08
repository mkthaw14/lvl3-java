package Assignment_1;

import java.util.Comparator;
import java.util.Objects;

public class Student {
	private int rollNo;
	private String name;
	private int mark;
	
	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + ", mark=" + mark + "]";
	}

	public Student(int rollNo, String name, int mark) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.mark = mark;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

}


//I had no option to sort the Set. So I found this on google and had to use this
class IRollNoComparer implements Comparator<Student>{ 

	@Override
	public int compare(Student o1, Student o2) {
		// TODO Auto-generated method stub
		int result = o1.getRollNo() < o2.getRollNo() ? -1 : 1; // -1 = o1.rollNo is lesser , 1 = o1.rollNo is greater
		return result;
	}
}
