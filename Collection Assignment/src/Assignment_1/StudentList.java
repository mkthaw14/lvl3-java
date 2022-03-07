package Assignment_1;

import java.util.ArrayList;
import java.util.Collections;


public class StudentList {
	public static void main(String[] args) {
		ArrayList<String> studentNames = new ArrayList<>();
		studentNames.add("Mg Mg");
		studentNames.add("Kyaw Mg");
		studentNames.add("Sayar Kyaw");
		studentNames.add("U Mg");
		studentNames.add("Aung Aung");
		studentNames.add("Kyaw Win");
		
		System.out.println("Before Sorting : " + studentNames);
		
		Collections.sort(studentNames);
		System.out.println("After Sorting : " + studentNames);
		
		
		int position = Collections.binarySearch(studentNames, "U Mg");
		System.out.println("Index of U Mg : " + position + "\n");
		
		
		String name = "Aung Aung";
		
		if(!studentNames.contains(name))
			studentNames.add(name);
		
		
		
		System.out.println(studentNames);
		
		studentNames.forEach(s -> {
			if(s.startsWith("K") || s.startsWith("k"))
				System.out.println(s);
		});
		
		studentNames.removeIf(s -> s.endsWith("g") || s.endsWith("G"));
		
		System.out.println(studentNames);
		
		studentNames.clear();
		
		System.out.println(studentNames);
	}
}
