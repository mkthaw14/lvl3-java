package libms.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import libms.model.entity.Author;

public class AuthorDatabaseService
{

	public static boolean addAuthor(String name, String country)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "INSERT INTO tbl_authors(name, country)VALUES(?,?)";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, name);
			pstm.setString(2, country);
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static List<Author> getAllAuthors()
	{
		List<Author> list = new ArrayList<Author>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_authors";
			
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Author auth = new Author();
				auth.setId(rs.getInt("author_id"));
				auth.setName(rs.getString("name"));
				auth.setCountry(rs.getString("country"));
				
				list.add(auth);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}

	public static boolean updateAuthor(Author author)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "UPDATE tbl_authors SET name = ?, country = ? WHERE author_id = ? ";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, author.getName());
			pstm.setString(2, author.getCountry());
			pstm.setInt(3, author.getId());
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static List<Author> findAuthors(Author author)
	{
		List<Author> list = new ArrayList<Author>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_authors WHERE ";
			List<Object> params = new ArrayList<>();
			
			int id = author.getId();
			String name = author.getName();
			String country  = author.getCountry();
			
			if(id != 0)
			{
				query += "author_id = ?";
				params.add(id);
			}
			
			if(!name.isEmpty())
			{
				query += params.size() > 0 ? " AND name LIKE ?" : " name LIKE ?";
				params.add("%" + name + "%");
			}
			
			if(!country.isEmpty())
			{
				query += params.size() > 0 ? " AND country = ?" : " country = ?";
				params.add(country);
			}
			
			System.out.println(query);
			PreparedStatement pstm = con.prepareStatement(query);
		
			for(int x = 0; x < params.size(); x++)
			{
				int index = x;
				pstm.setObject(++index, params.get(x));
			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Author retrievedAuthor = new Author();
				retrievedAuthor.setId(rs.getInt("author_id"));
				retrievedAuthor.setName(rs.getString("name"));
				retrievedAuthor.setCountry(rs.getString("country"));
				
				list.add(retrievedAuthor);
			}
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
}
