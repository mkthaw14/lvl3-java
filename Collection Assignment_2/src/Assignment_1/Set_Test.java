package Assignment_1;

import java.util.TreeSet;

public class Set_Test {
	
	static TreeSet<Student> set;
	
	public static void main(String[] args) 
	{
		createSet();
		display();
		updateStudentData();
		searchByRollNo();
		deleteByRollNo();
		display();
	}
	
	static void createSet()
	{
		set = new TreeSet<>(new IRollNoComparer()); //All of the element will be sorted in ascending order by rollNo
		
		set.add(new Student(5, "Mg Mg", 34));
		set.add(new Student(9, "Kyaw Kyaw", 49));
		set.add(new Student(2, "Aye", 56));
		set.add(new Student(8, "U Kyaw", 45));
		
	}
	
	static void display()
	{
		System.out.println("\n*****Display*****");
		set.forEach( s -> {
			System.out.println(s.toString());
		});
		
		System.out.println();
	}
	
	static void updateStudentData()
	{
		System.out.println("******Update Data*****");
		int targetRollNo = 2;
		set.forEach(s -> {
			if(s.getRollNo() == targetRollNo)
				s.setMark(100);
		});
		
		System.out.println(set);
		System.out.println();
	}
	
	static void searchByRollNo()
	{
		System.out.println("*****Search by roll*****");
		int targetRoll = 9;
		
		set.forEach(s -> {
			if(s.getRollNo() == targetRoll)
			{
				System.out.println("RollNo : " + s.getRollNo());
				System.out.println("Name : " + s.getName());
				System.out.println("Mark : " + s.getMark());
			}
		});
		
		System.out.println();
	}
	
	static void deleteByRollNo()
	{
		System.out.println("*****Delete*****");
		int targetRoll = 8;
		
		boolean removed = set.removeIf(s -> s.getRollNo() == targetRoll);
		String msg = removed == true ? "RollNo " + targetRoll + " has been deleted": "RollNo not found";
		System.out.println(msg);
	}
}
