package libms.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import libms.model.entity.BorrowBook;
import libms.model.entity.Librarian;
import my_utility.Utilities;

public class LibrarianDatabaseService 
{

	public static boolean verifyUser(String email, String pass)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT id FROM tbl_librarians WHERE email = ? AND password = ?";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, email);
			pstm.setString(2, pass);
			
			ResultSet rs = pstm.executeQuery();
			
			return rs.next();
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static Librarian validUser(String email, String pass)
	{
		Librarian user = null;
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_librarians WHERE email = ? AND password = ?";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, email);
			pstm.setString(2, pass);
			
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				user = new Librarian();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setNrcno(rs.getString("nrcno"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
			}
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
	}

	
	public static boolean addLibrarian(Librarian librarian)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "INSERT INTO tbl_librarians(email, password, nrcno, phone, created_at) VALUES(?, ?, ?, ?, now())";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, librarian.getEmail());
			pstm.setString(2, librarian.getPassword());
			pstm.setString(3, librarian.getNrcno());
			pstm.setString(4, librarian.getPhone());
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static List<Librarian> getAllLibrarians()
	{
		List<Librarian> list = new ArrayList<Librarian>();
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_librarians";
			PreparedStatement pstm = con.prepareStatement(query);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				Librarian l = new Librarian();
				l.setId(rs.getInt("id"));
				l.setEmail(rs.getString("email"));
				l.setNrcno(rs.getString("nrcno"));
				l.setPassword(rs.getString("password"));
				l.setPhone(rs.getString("phone"));
				l.setCreated_at(LocalDate.parse(rs.getString("created_at")));
				
				list.add(l);
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static boolean emailExist(String email)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT email FROM tbl_librarians WHERE email = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, email);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				String e = rs.getString("email");
				System.out.println("Email exist : " + e);
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean nrcnoExist(String nrcno)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT nrcno FROM tbl_librarians WHERE nrcno = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, nrcno);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				String e = rs.getString("nrcno");
				System.out.println("Nrcno exist : " + e);
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean updateLibrarian(Librarian l)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "UPDATE tbl_librarians SET email = ?, password = ?, nrcno = ?, phone = ?, created_at = ? WHERE id = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, l.getEmail());
			pstm.setString(2, l.getPassword());
			pstm.setString(3, l.getNrcno());
			pstm.setString(4, l.getPhone());
			pstm.setDate(5, Date.valueOf(l.getCreated_at()));
			pstm.setInt(6, l.getId());
			
			int resultRows = pstm.executeUpdate();
			
			return resultRows > 0;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static List<Librarian> findLibrarian(Librarian l)
	{
		List<Librarian> list = new ArrayList<Librarian>();
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_librarians WHERE ";
			List<Object> params = new ArrayList<Object>();
			
			if(l.getId() != 0)
			{
				query += " id = ?";
				params.add(l.getId());
			}
			
			if(!l.getEmail().isEmpty())
			{
				query += params.size() > 0 ? " AND email = ?" : " email = ?";
				params.add(l.getEmail());
			}
			
			if(!l.getNrcno().isEmpty())
			{
				query += params.size() > 0 ? " AND nrcno = ?" : " nrcno = ?";
				params.add(l.getNrcno());
			}
			
			if(l.getCreated_at() != null)
			{
				query += params.size() > 0 ? " AND created_at = ?" : " created_at = ?";
				params.add(l.getCreated_at());
			}
			
			System.out.println(query);
			
			PreparedStatement pstm = con.prepareStatement(query);
			
			for(int i = 0; i < params.size(); i++)
			{
				pstm.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = pstm.executeQuery();
			while(rs.next())
			{
				Librarian li = new Librarian();
				li.setId(rs.getInt("id"));
				li.setEmail(rs.getString("email"));
				li.setPassword(rs.getString("password"));
				li.setNrcno(rs.getString("nrcno"));
				li.setPhone(rs.getString("phone"));
				li.setCreated_at(LocalDate.parse(rs.getString("created_at")));
				
				list.add(li);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static boolean vertifySimilarityWithOtherEmail(int id, String email)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_librarians WHERE email = ? AND id != ?";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, email);
			pstm.setInt(2, id);
			
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				String e = rs.getString("email");
				System.out.println("Similar email : " + e);
				return true;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean vertifySimilarityWithOtherNrcno(int id, String nrcno)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_librarians WHERE nrcno = ? AND id != ?";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setString(1, nrcno);
			pstm.setInt(2, id);
			
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				String e = rs.getString("nrcno");
				System.out.println("Similar nrcno : " + e);
				return true;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
