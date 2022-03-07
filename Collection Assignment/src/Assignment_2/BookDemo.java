package Assignment_2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BookDemo {
	static List<String> categoryList = new ArrayList<>();
	static List<Author> authorList = new ArrayList<Author>();
	static List<Book> bookList = new ArrayList<Book>();
	
	static boolean running = true;
	static int userInput = 0;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		initialize();
			
		while(running)
		{						
			switch(userInput)
			{
			case 0:
				mainOptions();
				break;
			case 1:
				viewAuthor();
				proceed();
				break;
			case 2:
				viewBook();
				break;
			case 3:
				viewCategory();
				proceed();
				break;
			case 4:
				addBook();
				proceed();
				break;
			default:
				running = false;
				break;
			}
		}

		sc.close();
		System.out.println("Program terminated");
	}
	
	static void initialize() {
		initializeAuthor();
		initializeCategory();
		initializeBook();
	}
	
	static void initializeAuthor() {
		authorList.add(new Author("Kyaw", "Burma"));
		authorList.add(new Author("Aye", "Burma"));
		authorList.add(new Author("Mg Mg", "Burma"));
		
		//System.out.println(authorList);
	}
	
	static void initializeCategory() {
		categoryList.add("Programming");
		categoryList.add("Art");
		categoryList.add("Something");
		categoryList.add("Another thing");
		
		//System.out.println(categoryList);
	}
	
	static void initializeBook() {
		bookList.add(new Book(101, "Title1", "Art", LocalDate.of(2014, 3, 7), new Author("Kyaw", "Burma")));
		bookList.add(new Book(109, "Title2", "Something", LocalDate.of(2016, 1, 9), new Author("Zaw", "Burma")));
		bookList.add(new Book(121, "Title3", "Programming", LocalDate.of(2012, 2, 7), new Author("Kyaw", "Burma")));
		
		//System.out.println(bookList);
	}
	
	
	static void viewAuthor() {
		System.out.println("*************Author Name************");
		authorList.forEach(
				author -> System.out.println(author.getName())
				);
	}
	
	static void viewBook() {
		System.out.println("*************View Book**************");
			
		String[] options = new String[] {
				"viewAllBook", "viewByCategory", "viewByAuthor"};
		
		showOptions(options);		
		getUserInput();
		
		switch(userInput)
		{
		case 1:
			viewAllBook();
			proceed();
			break;
		case 2:
			viewByCategory();
			proceed();
			break;
		case 3:
			viewByAuthor();
			proceed();
			break;
		default:
			running = false;
			break;
		}
	}
	
	static void viewAllBook() {
		System.out.println("*************All Book Names**************");
		bookList.forEach(
				book -> System.out.println(book.getTitle())
				);
	}
	
	static void viewCategory() {
		System.out.println("************Category Name*************");
		categoryList.forEach(
				category -> System.out.println(category)
				);
	}
	
	
	static void viewByCategory() {
		System.out.println("************Books by Category*************");
		String input = sc.nextLine();
		
		
		boolean categoryFound  = false;
	
		for(var category: categoryList) {
			if(category.equalsIgnoreCase(input)) {
				categoryFound = true;
				break;
			}		
		}
		
		if(categoryFound) 
		{
			boolean bookFound = false;
			
			for(var book: bookList)
			{
				if(book.getCategory().equalsIgnoreCase(input))
				{
					bookFound = true;
					
					System.out.println("\nBook ID : " + book.getCode());
					System.out.println("Book Name : " + book.getTitle());
					System.out.println("Book Author : " + book.getAuthor().getName());
					System.out.println("Book Published Date : " + book.getPublishDate());
					System.out.println();
				}
			}
			
			if(!bookFound)
				System.out.println("There is no book for this author");
		}
		else 
		{
			System.out.println("Category not found");
		}
	}
	
	static void viewByAuthor() {
		System.out.println("************Books by Author*************");
		
		String input = sc.nextLine();
		
		
		boolean authorFound  = false;
	
		for(var author: authorList) {
			if(author.getName().equalsIgnoreCase(input)) {
				authorFound = true;
				break;
			}		
		}
		
		if(authorFound) 
		{
			boolean bookFound = false;
			
			for(var book: bookList)
			{
				if(book.getAuthor().getName().equalsIgnoreCase(input))
				{
					bookFound = true;
					
					System.out.println("\nBook ID : " + book.getCode());
					System.out.println("Book Name : " + book.getTitle());
					System.out.println("Book Author : " + book.getAuthor().getName());
					System.out.println("Book Published Date : " + book.getPublishDate());
					System.out.println();
				}
			}
			
			if(!bookFound)
				System.out.println("There is no book for this author");
		}
		else 
		{
			System.out.println("Author not found");
		}
		
	}
	
	static void addBook() {
		System.out.println("Book ID: ");
		int code = bookList.size() - 1;

		try {
			code = Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			System.err.println(e);
		}
		
		System.out.println("Book Title: ");
		String title = sc.nextLine();
		
		System.out.println("Book Category: ");
		String category = sc.nextLine();
		
		System.out.println("Book AuthorName: ");
		String authorName = sc.nextLine();
		
		int[] publishDate = new int[3];
		String[] dates = new String[] {"Year", "Month", "Day"};
		
		for(int i = 0; i < publishDate.length; i++)
		{
			System.out.println("Enter " + dates[i] + " : ");
			publishDate[i] = Integer.parseInt(sc.nextLine());
		}
		
		int categoryIndex = Collections.binarySearch(categoryList, category); 
		//System.out.println("Category Index " + categoryIndex);
		
		if(categoryIndex < 0)
		{
			categoryList.add(category);
		}
		
		Author existingAuthor = null;
		
		for(var author: authorList) {
			if(author.getName().equalsIgnoreCase(authorName))
			{
				existingAuthor = author;
				break;
			}
		}
		

		if(existingAuthor == null)
		{
			System.out.println("This is a new author. Please enter author's country name");
			String countryName = sc.nextLine();
			
			Author newAuthor = new Author();
			newAuthor.setName(authorName);
			newAuthor.setCountry(countryName);
			
			authorList.add(newAuthor);
			
			bookList.add(new Book(code, title, category, 
					LocalDate.of(publishDate[0], publishDate[1], publishDate[2]), newAuthor));
		}
		else
		{
			bookList.add(new Book(code, title, category, 
					LocalDate.of(publishDate[0], publishDate[1], publishDate[2]), existingAuthor));
		}

	}
	
	static void mainOptions() {
				
		System.out.println("\n***********BookDemo************\n");

		String[] options = new String[] {
				"viewAuthor", "viewBook", "viewCategory", 
				 "addBook"};
		
		showOptions(options);
		
		System.out.println("Enter nothing for terminating the program");
		
		getUserInput();
	}
	
	static void showOptions(String[] options) {
		for(int x = 0; x < options.length; x++)
		{
			System.out.println("Enter " + (x + 1) + " for " + options[x] + " : ");
		}
	}
	
	static void getUserInput(){
		try 
		{
			userInput = Integer.parseInt(sc.nextLine());
		} catch (Exception e) 
		{
			// TODO: handle exception
			running = false;
			System.err.println(e);
		}
	}
	
	static void proceed() {
		System.out.println("\nEnter any character to continue");
		String s = sc.nextLine();
		
		userInput = 0;
	}
	
}
