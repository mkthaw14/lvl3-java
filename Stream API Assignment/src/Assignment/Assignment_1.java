package Assignment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Assignment_1 
{
	public static void main(String[] args) 
	{
		List<SalePeople> salelist = Arrays.asList(
				new SalePeople("Peel", "London", 0.12f),
				new SalePeople("Seres", "San Jose", 0.13f),
				new SalePeople("Motika", "London", 0.11f),
				new SalePeople("Rifkin", "Barcelona", 0.15f),
				new SalePeople("Axelrod", "New York", 0.10f)
		);
		
		System.out.println("*****Name and cities of salePeople in London and comm > 0.1****");
		
		salelist.stream()
		.filter(s -> s.getCity().equals("London") && s.getComm() > 0.10f)
		.forEach(s -> {
			System.out.println(s.getName() + "\t" + s.getCity());
		});
		
		
		System.out.println("\n********Not Having Comm*******");
		Predicate<SalePeople> notHavingCommision = s ->  !s.equalCommision(0.1f) && !s.equalCommision(0.13f) && !s.equalCommision(0.15f);
		
		salelist.stream()
		.filter(notHavingCommision)
		.forEach(s -> {
			System.out.println(s.toString());
		});
		
		System.out.println("\n*****Different cities*****");
		
		salelist.stream()
		.map(s -> s.getCity())
		.distinct()
		.forEach(s -> System.out.println(s));
	
		System.out.println("\n*****Top 3*****");
		
		salelist.stream()
		.limit(3)
		.forEach(s -> System.out.println(s.toString()));
		
		System.out.println("\n*****Live in Rome****");
		
		boolean liveInRome = salelist.stream()
				.anyMatch(s -> s.getCity().equals("Rome"));
		
		salelist.stream()
		.filter(s -> s.getCity().equals("Rome"))
		.forEach(s -> System.out.println(s.toString()));
		
		System.out.println("Live in Rome : " + liveInRome);
		
		System.out.println("\n*****Num of people who lives in London*****");
		
		long count = salelist.stream()
		.filter(s -> s.getCity().equals("London"))
		.count();
		
		System.out.println("People live in London : " + count);
		
		System.out.println("\n******Descending Order By Commissions*****");
		salelist.stream()
		.sorted((s1, s2) -> Float.compare(s2.getComm(), s1.getComm()))
		.forEach(s -> System.out.println(s));
	}
}
