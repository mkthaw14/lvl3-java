package Assignment_2;

import java.time.LocalDate;

public class Book { 
	private int code;
	private String title;
	private String category;
	private LocalDate publishDate;
	private Author author;
	
	public Book(int code, String title, String category, LocalDate publishDate, Author author) {
		super();
		this.code = code;
		this.title = title;
		this.category = category;
		this.publishDate = publishDate;
		this.author = author;
	}
	

	@Override
	public String toString() {
		return "Book [code=" + code + ", title=" + title + ", category=" + category + ", publishDate=" + publishDate
				+ ", author=" + author + "]";
	}


	public Book(int code, String title, LocalDate publishDate) {
		this.code = code;
		this.title = title;
		this.publishDate = publishDate;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public LocalDate getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}

}
