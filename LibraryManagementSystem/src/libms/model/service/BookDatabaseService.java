package libms.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import libms.model.entity.Author;
import libms.model.entity.Book;
import libms.model.entity.Category;

public class BookDatabaseService
{

	public static List<Book> getAllBooks()
	{
		List<Book> books = new ArrayList<Book>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = """		
					    SELECT a.*, b.*, c.* FROM tbl_books b, tbl_authors a, tbl_categories c
						WHERE b.author_id = a.author_id AND b.category_id = c.category_id 
						ORDER BY b.code
					""";
			
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Book book = new Book();
				book.setCode(rs.getInt("code"));
				book.setTitle(rs.getString("title"));
				
				Author author = new Author();
				author.setId(rs.getInt("b.author_id"));
				author.setName(rs.getString("a.name"));
				author.setCountry(rs.getString("a.country"));
				book.setAuthor(author);
				

				Category cat = new Category();
				cat.setId(rs.getInt("b.category_id"));
				cat.setName(rs.getString("c.name"));
				book.setCategory(cat);
				
				book.setIs_available(rs.getInt("b.is_availabe"));
				
				books.add(book);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return books;
	}

	public static boolean addBook(Book book)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = """
							INSERT INTO tbl_books(code, title, author_id, category_id, is_availabe)
							VALUES(?, ?, ?, ?, ?); 
						""";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, book.getCode());
			pstm.setString(2, book.getTitle());
			pstm.setInt(3, book.getAuthor().getId());
			pstm.setInt(4, book.getCategory().getId());
			pstm.setInt(5, book.getIs_available());
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyBookID(int code)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_books WHERE code = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, code);
			
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				int returned_ID = rs.getInt("code");
				System.out.println("Book ID exists : " + returned_ID);
				return true;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static boolean updateBook(Book book)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = """ 					
					UPDATE tbl_books SET title = ?, author_id = ?, category_id = ?, is_availabe = ? 
					WHERE code = ?
					""";
			
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, book.getTitle());
			pstm.setInt(2, book.getAuthor().getId());
			pstm.setInt(3, book.getCategory().getId());
			pstm.setInt(4, book.getIs_available());
			pstm.setInt(5, book.getCode());
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}


	public static List<Book> findBooks(int code, String title, String authorName, String categoryName, int available)
	{
		List<Book> list = new ArrayList<Book>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = """
						SELECT a.*, b.*, c.* FROM tbl_books b, tbl_authors a, tbl_categories c
						WHERE b.author_id = a.author_id AND b.category_id = c.category_id
					""";
			
			List<Object> params = new ArrayList<Object>();
			
			System.out.println("Code " + code);
			System.out.println("a name " + authorName);
			System.out.println("c name " + categoryName);
			System.out.println("available " + available);
			if(code != 0)
			{
				query += " AND code = ?";
				params.add(code);
			}
			
			if(title != null && !title.isEmpty())
			{
				query += " AND title LIKE ?";
				params.add("%" + title + "%");
			}
			
			if(authorName != null && !authorName.isEmpty())
			{
				query += " AND a.name = ?";
				params.add(authorName);
			}
			
			if(categoryName != null && !categoryName.isEmpty())
			{
				query += " AND c.name = ?";
				params.add(categoryName);
			}
			
			if(available != -1)
			{
				query += " AND b.is_availabe = ?";
				params.add(available);
			}
			
			query += " ORDER BY code";
			
			PreparedStatement pstm = con.prepareStatement(query);
			
			for(int i = 0; i < params.size(); i++)
			{
				pstm.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Book b = new Book();
				b.setCode(rs.getInt("b.code"));
				b.setTitle(rs.getString("b.title"));
				b.setIs_available(rs.getInt("b.is_availabe"));
				
				Author a = new Author();
				a.setId(rs.getInt("b.author_id"));
				a.setName(rs.getString("a.name"));
				a.setCountry(rs.getString("a.country"));
				
				b.setAuthor(a);
				
				Category c = new Category();
				c.setId(rs.getInt("b.category_id"));
				c.setName(rs.getString("c.name"));
				
				b.setCategory(c);
				
				list.add(b);
			}
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}

	public static boolean vertifyBookIDIsAvaiable(int book_id)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_books WHERE code = ? AND is_availabe = 1";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, book_id);
			
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				int id = rs.getInt("code");
				System.out.println("Available book code : " + id);
				return true;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean updateBookAvailable(int bookCode)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "UPDATE tbl_books SET is_availabe = 0 WHERE code = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, bookCode);
			int resultRows = pstm.executeUpdate();
			
			System.out.println("Book Available " + resultRows);
			
			return resultRows > 0;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}


	
}
