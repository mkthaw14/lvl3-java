package libms.model.entity;



public class Book
{
	private int code;
	private int is_available;
	
	private Author author;
	private Category category;
	private String title;
	
	public Book() { }
	
	public Book(int code, int is_available, Author author, Category category, String title)
	{
		super();
		this.code = code;
		this.is_available = is_available;
		this.author = author;
		this.category = category;
		this.title = title;
	}
	
	public int getCode()
	{
		return code;
	}
		
	public void setCode(int code)
	{
		this.code = code;
	}

	public Author getAuthor()
	{
		return author;
	}
	public void setAuthor(Author author)
	{
		this.author = author;
	}
	public Category getCategory()
	{
		return category;
	}
	public void setCategory(Category category)
	{
		this.category = category;
	}
	public int getIs_available()
	{
		return is_available;
	}
	public void setIs_available(int is_available)
	{
		this.is_available = is_available;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getAuthorName()
	{
		return author.getName();
	}
	
	public String getCategoryName()
	{
		return category.getName();
	}
	
}


