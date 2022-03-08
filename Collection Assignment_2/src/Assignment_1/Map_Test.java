package Assignment_1;

import java.util.Map;
import java.util.TreeMap;

public class Map_Test 
{
	static Map<Integer, Student> map = new TreeMap<>();
	static Student[] stds = new Student[] {new Student(16, "Aung", 100),
											new Student(7, "Kyaw", 39),
											new Student(9, "Oliva Charllote", 37),
											new Student(12, "Boris Johnson", 36),
											new Student(2, "John Travolta", 29)};
	
	public static void main(String[] args) 
	{
		createMap();
		display();
		updateStudentData();
		searchStudentData();
		deleteStudent();
		display();
	}
	
	static void createMap()
	{
		System.out.println("*****Create*****");
		for(int x = 0; x < stds.length; x++)
		{
			map.put(stds[x].getRollNo(), stds[x]); // This will automatically be sorted by rollNo
		}
	}
	
	static void display()
	{
		System.out.println("\n*****Display*****");
		map.forEach((k, v) -> {
			System.out.println(k + " " + v);
		});
	}
	
	static void updateStudentData() 
	{
		System.out.println("\n*****Update*****");
		map.forEach((k, v) -> {
			if(v.getMark() > 36)
				v.setMark(40);
		});
	}
	
	static void searchStudentData()
	{
		System.out.println("\n****Search****");
		int targetRollNo = 9;
		Student std = map.get(targetRollNo);
		
		System.out.println("Roll NO : " + std.getRollNo());
		System.out.println("Name : " + std.getName());
		System.out.println("Mark : " + std.getMark());
	}
	
	static void deleteStudent() 
	{
		System.out.println("\n*****Delete****");
		int targetRollNo = 7;
		map.remove(targetRollNo);
		System.out.println("RollNo : " + targetRollNo + " has been deleted");
	}
}
