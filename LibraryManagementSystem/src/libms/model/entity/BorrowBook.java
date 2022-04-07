package libms.model.entity;

import java.time.LocalDate;

public class BorrowBook
{
	private int id;
	private int card_id;
	private int book_id;
	private int fee;

	private LocalDate borrow_date;
	private LocalDate due_date;
	private LocalDate return_date;
	private Librarian librarian;
	
	private String book_title;
	
	public BorrowBook()
	{
		
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getCard_id()
	{
		return card_id;
	}
	public void setCard_id(int card_id)
	{
		this.card_id = card_id;
	}
	public int getBook_id()
	{
		return book_id;
	}
	public void setBook_id(int book_id)
	{
		this.book_id = book_id;
	}
	public int getFee()
	{
		return fee;
	}
	public void setFee(int fee)
	{
		this.fee = fee;
	}
	public LocalDate getBorrow_date()
	{
		return borrow_date;
	}
	public void setBorrow_date(LocalDate borrow_date)
	{
		this.borrow_date = borrow_date;
	}
	public LocalDate getDue_date()
	{
		return due_date;
	}
	public void setDue_date(LocalDate due_date)
	{
		this.due_date = due_date;
	}
	public LocalDate getReturn_date()
	{
		return return_date;
	}
	public void setReturn_date(LocalDate return_date)
	{
		this.return_date = return_date;
	}

	public Librarian getLibrarian()
	{
		return librarian;
	}

	public void setLibrarian(Librarian librarian)
	{
		this.librarian = librarian;
	}

	public int getLibrarian_id()
	{
		return librarian.getId();
	}

	public void setLibrarian_id(int librarian_id)
	{
		if(this.librarian == null)
		{
			this.librarian = new Librarian();
		}
		
		this.librarian.setId(librarian_id);
	}

	public String getBook_title()
	{
		return book_title;
	}

	public void setBook_title(String book_title)
	{
		this.book_title = book_title;
	}
}
