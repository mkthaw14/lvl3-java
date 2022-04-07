package libms.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import libms.model.entity.BorrowBook;

public class ReturnBookDatabaseService
{

	public static List<BorrowBook> getUnReturnedBook()
	{
		List<BorrowBook> list = new ArrayList<BorrowBook>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT bb.*, b.title FROM tbl_borrow_books bb, tbl_books b WHERE return_date IS NULL AND bb.book_id = b.code";
			
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				BorrowBook bb = new BorrowBook();
				bb.setId(rs.getInt("id"));
				bb.setBook_id(rs.getInt("book_id"));
				bb.setBook_title(rs.getString("b.title"));
				bb.setBorrow_date(LocalDate.parse(rs.getString("borrow_date")));
				bb.setDue_date(LocalDate.parse(rs.getString("due_date")));
				bb.setFee(rs.getInt("fees"));
				
				list.add(bb);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static List<BorrowBook> findByCardID(int card_id)
	{
		List<BorrowBook> list = new ArrayList<BorrowBook>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = """
					     SELECT bb.*, b.title FROM tbl_borrow_books bb, tbl_books b WHERE card_id = ? 
					     AND bb.book_id = b.code AND return_date IS NULL
					""";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, card_id);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				BorrowBook bb = new BorrowBook();
				bb.setId(rs.getInt("id"));
				bb.setBook_title(rs.getString("b.title"));
				bb.setBorrow_date(LocalDate.parse(rs.getString("borrow_date")));
				bb.setDue_date(LocalDate.parse(rs.getString("due_date")));
				bb.setFee(rs.getInt("fees"));
				
				list.add(bb);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}

	public static boolean confirmToReturn(BorrowBook record) throws SQLException
	{
		String query = """
						UPDATE tbl_borrow_books SET return_date = now() WHERE id = ?
				  """;
		
		Connection con = MyConnection.getConnection();
		
		try
		{
			con.setAutoCommit(false);
			
			PreparedStatement pstm_borrow = con.prepareStatement(query);
			
			pstm_borrow.setInt(1, record.getId());
			
			int resultRows = pstm_borrow.executeUpdate();
			
			System.out.println("Borrow Rows " + resultRows);
			
			System.out.println("Book ID " + record.getBook_id());
			if(resultRows > 0)
			{
				query = "UPDATE tbl_books SET is_availabe = 1 WHERE code = ?";
				PreparedStatement pstm_book = con.prepareStatement(query);
				pstm_book.setInt(1, record.getBook_id());
				
				int resultRows_book = pstm_book.executeUpdate();
				
				System.out.println("book row : " + resultRows_book);
				
				if(resultRows_book > 0)
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

	public static boolean updateFee(int id, int fee)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "UPDATE tbl_borrow_books SET fees = ? WHERE id = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setInt(1, fee);
			pstm.setInt(2, id);
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
}
