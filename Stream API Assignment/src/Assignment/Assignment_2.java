package Assignment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Assignment_2 
{
	public static void main(String[] args) 
	{
		List<Employee> emplist = Arrays.asList(
				new Employee("Htet Htet", "Yangon", "Electronics", 900000, LocalDate.of(1991, 10, 16)),
				new Employee("Cherry", "Yangon", "Electronics", 820000, LocalDate.of(1994, 8, 14)),
				new Employee("Kyaw Kyaw", "Yangon", "Electronics", 780000, LocalDate.of(1988, 9, 02)),
				new Employee("Aung Aung", "Mandalay", "IT", 600000, LocalDate.of(1995, 1, 11)),
				new Employee("Jeon", "Mandalay", "IT", 600000, LocalDate.of(1997, 9, 1)),
				new Employee("Hsu Hus", "Pyi Oo Lwin", "IT", 920000, LocalDate.of(1994, 12, 10)),
				new Employee("Aye Aye", "Yangon", "HR", 500000, LocalDate.of(1992, 10, 10)),
				new Employee("Gay Gay", "Taung Gyi", "HR", 400000, LocalDate.of(1996, 9, 3)),
				new Employee("Phway Phway", "Monywa", "HR", 300000, LocalDate.of(1995, 9, 3)),
				new Employee("Ko Ko", "Monywa", "IT", 500000, LocalDate.of(1992, 11, 11))
				);
		
		
		minSalary(emplist);
		
		youngestEmp(emplist);	
		
		countEmpByBirthDate(emplist);
		
		totalSalaryOfAll(emplist);
		
		fetch_3_MaxSalary(emplist);
		
		averageSalaryOfHR(emplist);
		
		lowestSalaryOfEmp(emplist);
		
		maxSalaryInEachCity(emplist);
		
		sameSalaryOfEmployee(emplist);
		
		employeeInEachDepartment(emplist);
		
	}
	
	static void minSalary(List<Employee> emplist)
	{
		System.out.println("*****Min Salary****");
		int minimumSalary = emplist.stream()
				.mapToInt(e -> e.getSalary())
				.min()
				.getAsInt();
		
		System.out.println(minimumSalary);
	}
	
	static void youngestEmp(List<Employee> emplist)
	{
		System.out.println("\n*****Youngest Emp*****");
		
		Employee min = emplist.stream().min((e1, e2) -> e1.getBirthDate().compareTo(e2.getBirthDate())).get();
		System.out.println("Youngest Employee : " + min);
	}
	
	static void countEmpByBirthDate(List<Employee> emplist)
	{
		System.out.println("\n*****Numbers of emp who born in 1995-02-12 or later*****");
		LocalDate targetDate = LocalDate.of(1995, 2, 12);
		
		long emps = emplist.stream()
		.filter(e -> e.getBirthDate().equals(targetDate) || e.getBirthDate().isAfter(targetDate))
		.count();
		
		emplist.stream()
		.map(e -> e.getBirthDate())
		.filter(e -> e.equals(targetDate) || e.isAfter(targetDate))
		.forEach(e -> System.out.println(e));
		System.out.println("Numbers of emp who born in 1995-02-12 or later : " + emps);
	}
	
	static void totalSalaryOfAll(List<Employee> emplist)
	{
		System.out.println("\n*****Total Salary of all*****");
		int total = emplist.stream()
				.mapToInt(e -> e.getSalary())
				.sum();
		
		System.out.println("Total Salary : " + total);
	}
	
	static void fetch_3_MaxSalary(List<Employee> emplist)
	{
		System.out.println("\n*****3 Max Salary*****");
		List<Integer> max_salaries = emplist.stream()
				.sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
				.limit(3).map(e -> e.getSalary())
				.toList();
		
		max_salaries.forEach(s -> System.out.println(s));
		
	}
	
	static void averageSalaryOfHR(List<Employee> emplist)
	{
		System.out.println("\n******Average Salary of HR******");
		double avg_HR = emplist.stream()
				.filter(e -> e.getDepartment().equals("HR"))
				.mapToInt(e -> e.getSalary())
				.average()
				.getAsDouble();
		
		System.out.println("Avg_HR : " + avg_HR);
	}
	
	static void lowestSalaryOfEmp(List<Employee> emplist)
	{
		System.out.println("\n*******Emp with Lowest Salary*******");
		Employee lowest_emp = emplist.stream()
				.min(Comparator.comparingInt(e -> e.getSalary()))
				.get();
		
		System.out.println("Lowest Salary of employee : " + lowest_emp);
	}
	
	static void maxSalaryInEachCity(List<Employee> emplist)
	{
		System.out.println("\n********Max Salary of employee in each city********");
		
		Map<String, List<Employee>> groupByCities = emplist.stream()
				.collect( 
						Collectors.groupingBy(e -> e.getCity())
						);
				
		groupByCities.forEach((k, v) -> {
			System.out.println(k);
			
			int max_Emp = v.stream().mapToInt(e -> e.getSalary()).max().getAsInt();
			System.out.println(max_Emp);
			System.out.println("");
		});
	}
	
	static void sameSalaryOfEmployee(List<Employee> emplist)
	{
		System.out.println("\n********Same Salary of employees*******");

		Map<Integer, List<Employee>> groupBySalary = emplist.stream()
		.collect(Collectors.collectingAndThen(
				Collectors.groupingBy(e -> e.getSalary()),			
				tmp -> tmp.entrySet()
				.stream()
				.filter(e -> e.getValue().size() > 1)
				.collect(
						Collectors.toMap(k -> k.getKey(), v -> v.getValue()
						)
					)
				)
			);
		
		groupBySalary.forEach((k, v) -> {
			System.out.println(k);
			v.forEach(s -> System.out.println(s));
		});
	}
	
	static void employeeInEachDepartment(List<Employee> emplist)
	{
		System.out.println("\n********Employee in each department********");
		
		Map<String, List<String>> groupByDepartment = emplist.stream()
		.collect(Collectors.groupingBy(e -> e.getDepartment(), Collectors.mapping(e -> e.getName(), Collectors.toList())));
		
		groupByDepartment.forEach((k, v) -> {
			System.out.println("\nDepartment : " + k);
			v.forEach(a -> System.out.println(a));
		});
	}
	
	static void showEmpDate(List<Employee> emplist)
	{
		emplist.stream().map(e -> e.getBirthDate())
		.forEach(e -> System.out.println(e));
	}
}
