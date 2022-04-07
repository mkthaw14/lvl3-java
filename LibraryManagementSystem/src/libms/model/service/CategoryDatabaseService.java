package libms.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import libms.model.entity.Category;

public class CategoryDatabaseService 
{

	public static boolean addCategory(String name)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "INSERT INTO tbl_categories(name) VALUES(?)";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, name);
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static List<Category> getAllCategory()
	{
		List<Category> list = new ArrayList<Category>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_categories";
			
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Category cat = new Category();
				cat.setId(rs.getInt("category_id"));
				cat.setName(rs.getString("name"));
				
				list.add(cat);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public static boolean updateCategory(int selectedCategoryID, String name)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "UPDATE tbl_categories SET name = ? WHERE category_id = ?";
			
		    PreparedStatement pstm = con.prepareStatement(query);
		    pstm.setString(1, name);
		    pstm.setInt(2, selectedCategoryID);
		    
		    int resultRows = pstm.executeUpdate();
		    
		    return resultRows > 0;
		    
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static List<Category> findCategories(int id, String name)
	{
		List<Category> list = new ArrayList<Category>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_categories WHERE ";
			
			List<Object> params = new ArrayList<>();
			
			
			if(id != 0)
			{
				query += "category_id = ?";
				params.add(id);
			}
			
			if(!name.isEmpty())
			{
				query += params.size() > 0 ? " AND name LIKE ?": " name LIKE ?";
				params.add("%" + name + "%");
			}
			
			System.out.println(query);
			PreparedStatement pstm = con.prepareStatement(query);
			
			for(int x = 0; x < params.size(); x++)
			{
				pstm.setObject(x + 1, params.get(x));
			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Category cat = new Category();
				cat.setId(rs.getInt("category_id"));
				cat.setName(rs.getString("name"));
				
				list.add(cat);
			}
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
}
