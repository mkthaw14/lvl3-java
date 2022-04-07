package libms.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import libms.model.entity.BorrowBook;
import libms.model.entity.Librarian;

public class BorrowBookDatabaseService
{

	public static boolean vertifyPreviousBorrowedBookByCardID(int card_id)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_borrow_books WHERE card_id = ? AND borrow_date < Current_Date() AND return_date IS NULL";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, card_id);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				int id = rs.getInt("book_id");
				
				System.out.println("Borrowed book ID " + id);
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean vertifyBorrowedMoreThanThreeBooksInToday(int card_id)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT COUNT(*) FROM tbl_borrow_books WHERE card_id = ? AND borrow_date = Current_date() AND return_date IS NULL";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setInt(1, card_id);
			ResultSet rs = pstm.executeQuery();
			
			rs.next();
			
			int rowCount = rs.getInt("count(*)");
			
			if(rowCount >= 3 )
			{
				System.out.println("more than 3 books has borrowed today");
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean addBorrowBook(BorrowBook borrowBook) throws SQLException
	{
		Connection con = MyConnection.getConnection();
		try
		{
			String query = """
					INSERT INTO tbl_borrow_books(card_id, book_id, borrow_date, due_date, return_date, fees, librarian_id)
					VALUES(?, ?, now(), ?, null, 0, ?)
					""";
			
			con.setAutoCommit(false);
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, borrowBook.getCard_id());
			pstm.setInt(2, borrowBook.getBook_id());
			
			pstm.setDate(3, Date.valueOf(borrowBook.getDue_date()));
			pstm.setInt(4, borrowBook.getLibrarian().getId());
			
			int resultRows = pstm.executeUpdate();
			
			if(resultRows > 0)
			{
				//boolean success = BookDatabaseService.updateBookAvailable(borrowBook.getBook_id());
				query = "UPDATE tbl_books SET is_availabe = 0 WHERE code = ?";
				PreparedStatement pstm_book = con.prepareStatement(query);
				pstm_book.setInt(1, borrowBook.getBook_id());
				int book_resultRows = pstm_book.executeUpdate();
				
				System.out.println("Book Available " + book_resultRows);
				
				if(book_resultRows > 0)
				{
					con.commit();
					return true;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			con.rollback();
		}
		con.close();
		return false;
	}

	public static List<BorrowBook> getAllBorrowRecords()
	{
		List<BorrowBook> borrowBook = new ArrayList<BorrowBook>();
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT bb.*, l.id  FROM tbl_borrow_books bb, tbl_librarians l WHERE bb.librarian_id = l.id";
			
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				BorrowBook book = new BorrowBook();
				book.setId(rs.getInt("id"));
				book.setCard_id(rs.getInt("card_id"));
				book.setBook_id(rs.getInt("book_id"));
				book.setBorrow_date(LocalDate.parse(rs.getString("borrow_date")));
				book.setDue_date(LocalDate.parse(rs.getString("due_date")));
				
				String return_date_str = rs.getString("return_date");
				LocalDate return_date = return_date_str == null? null : LocalDate.parse(return_date_str);
				book.setReturn_date(return_date);
				
				Librarian l = new Librarian();
				l.setId(rs.getInt("l.id"));
				book.setLibrarian(l);
				
				borrowBook.add(book);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return borrowBook;
	}
	
	public static List<BorrowBook> findBorrowBooks(BorrowBook bb)
	{
		List<BorrowBook> list = new ArrayList<BorrowBook>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT bb.*, l.id FROM tbl_borrow_books bb, tbl_librarians l WHERE bb.librarian_id = l.id ";
			List<Object> params = new ArrayList<Object>();
			
			if(bb.getCard_id() != 0)
			{
				query += " AND card_id = ?";
				params.add(bb.getCard_id());
			}
			
			if(bb.getBorrow_date() != null)
			{
				query += " AND bb.borrow_date = ?";
				params.add(bb.getBorrow_date());
			}
			
			if(bb.getReturn_date() != null)
			{
				query += " AND bb.return_date = ?";
				params.add(bb.getReturn_date());
			}
			
			if(bb.getLibrarian_id() != 0)
			{
				query += " AND bb.librarian_id = ?";
				params.add(bb.getLibrarian_id());
			}
			
			
			PreparedStatement pstm = con.prepareStatement(query);
			
			for(int x = 0; x < params.size(); x++)
			{
				pstm.setObject(x + 1, params.get(x));
			}
			
			System.out.println(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				BorrowBook retrievedBB = new BorrowBook();
				retrievedBB.setId(rs.getInt("id"));
				retrievedBB.setCard_id(rs.getInt("card_id"));
				retrievedBB.setBook_id(rs.getInt("book_id"));
				retrievedBB.setBorrow_date(LocalDate.parse(rs.getString("borrow_date")));
				retrievedBB.setDue_date(LocalDate.parse(rs.getString("due_date")));
				
				String return_date_str = rs.getString("return_date");
				LocalDate return_date = return_date_str == null? null : LocalDate.parse(return_date_str);
				retrievedBB.setReturn_date(return_date);
				
				Librarian l = new Librarian();
				l.setId(rs.getInt("l.id"));
				retrievedBB.setLibrarian(l);
				
				System.out.println("BB ID  " + rs.getInt("id"));
				list.add(retrievedBB);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
}
