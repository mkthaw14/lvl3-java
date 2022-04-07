package libms.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import libms.model.entity.Member;

public class MemberDatabaseService
{

	public static boolean cardIDExist(int card_id)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_members WHERE card_id = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			
			pstm.setInt(1, card_id);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				int id = rs.getInt("card_id");
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean rollNoExists(String rollNo)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_members WHERE roll_no = ?";
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, rollNo);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				String rNo = rs.getString("roll_no");
				System.out.println("rNo : " + rNo);
				return true;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static boolean addMember(Member member)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = """
						INSERT INTO tbl_members
						(card_id, name, roll_no, class_year, academic_year, created_at, expired_date)
						VALUES(?, ?, ?, ?, ?, ?, ?);
					""";
			
			PreparedStatement pstm = con.prepareStatement(query);
		    pstm.setInt(1, member.getCard_id());
		    System.out.println("Hello");
		    pstm.setString(2, member.getName());
		    System.out.println("Hello1");
		    pstm.setString(3, member.getRoll_no());
		    System.out.println("Hello2");
		    pstm.setString(4, member.getClass_year());
		    System.out.println("Hello3");
		    pstm.setInt(5, member.getAcademic_year().getValue());
		    System.out.println("Hello4" + member.getAcademic_year().getValue());
		    LocalDate date = LocalDate.now();
		    pstm.setDate(6, Date.valueOf(date));
		    System.out.println("Hello5");
		    pstm.setDate(7, Date.valueOf(date.plusDays(7)));
		    System.out.println("Hello6");
		    int resultRows = pstm.executeUpdate();
		    
		    return resultRows > 0;
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		return false;
	}

	public static List<Member> getAllMembers()
	{
		List<Member> list = new ArrayList<Member>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_members";
			
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next())
			{
				int card_id = rs.getInt("card_id");
				String name = rs.getString("name");
				String rollNo = rs.getString("roll_no");
				String class_year = rs.getString("class_year");
				String academic_y = rs.getString("academic_year");
				Year academic_year = Year.of(rs.getInt("academic_year"));
				LocalDate created_at = LocalDate.parse(rs.getString("created_at"));
				LocalDate expired_date = LocalDate.parse(rs.getString("expired_date"));
				
				
				Member member = new Member(card_id, rollNo, name, class_year, academic_year, created_at, expired_date);
				list.add(member);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}

	public static boolean updateMember(Member member)
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query =  """
							UPDATE tbl_members SET name = ?, roll_no = ?, class_year = ?,
							academic_year = ?, created_at = ?, expired_date = ?  
							WHERE card_id = ?
					""";
			
			if(member.getCard_id() == 0)
				throw new Exception("Zero card id");
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, member.getName());
			pstm.setString(2, member.getRoll_no());
			pstm.setString(3, member.getClass_year());
			pstm.setInt(4, member.getAcademic_year().getValue());
			pstm.setDate(5, Date.valueOf(member.getCreated_at()));
			
			LocalDate expiredDate = member.getCreated_at();
			pstm.setDate(6, Date.valueOf(expiredDate.plusDays(7)));
			pstm.setInt(7, member.getCard_id());
			
			System.out.println("Card ID " + member.getCard_id());
			int resultRows = pstm.executeUpdate();
			
			System.out.println("Rows " + resultRows);
			
			return resultRows > 0;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static List<Member> findMembers(Member member)
	{
		List<Member> list = new ArrayList<Member>();
		
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT * FROM tbl_members WHERE";
			
			List<Object> params = new ArrayList<Object>();
			
			int card_id = member.getCard_id();
			String name = member.getName();
			String rollNo = member.getRoll_no();
			String class_year = member.getClass_year();
			Year academic_year = member.getAcademic_year();
			LocalDate created_at = member.getCreated_at();
			LocalDate expired_at = member.getExpired_at();
			

			if(card_id != 0)
			{
				query += " card_id = ?";
				params.add(member.getCard_id());
			}

			
			System.out.println("Null " + name == null);
			
			if(!name.isEmpty())
			{
				query += params.size() > 0 ? " AND name LIKE ?" : " name LIKE ?";
				params.add("%" + member.getName() + "%");
			}
			
			if(!rollNo.isEmpty())
			{
				query += params.size() > 0 ? " AND roll_no LIKE ?" : " roll_no LIKE ?";
				params.add("%" + member.getRoll_no() + "%");
			}
			
			if(class_year != null)
			{
				query += params.size() > 0 ? " AND class_year = ?" : " class_year = ?";
				params.add(member.getClass_year());
			}
			
			if(academic_year != null)
			{
				query += params.size() > 0 ? " AND academic_year = ?" : " academic_year = ?";
				params.add(member.getAcademic_year().getValue());
			}

			if(created_at != null)
			{
				query += params.size() > 0 ? " AND created_at = ?" : " created_at = ?";
				params.add(member.getCreated_at());
			}
			
			if(expired_at != null)
			{
				query += params.size() > 0 ? " AND expired_date = ?" : " expired_date = ?";
				params.add(member.getExpired_at());
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
				System.out.println("Yes");
				Member retrievedMember = new Member();
				
				retrievedMember.setCard_id(rs.getInt("card_id"));
				retrievedMember.setName(rs.getString("name"));
				retrievedMember.setRoll_no(rs.getString("roll_no"));
				retrievedMember.setClass_year(rs.getString("class_year"));
				retrievedMember.setAcademic_year(Year.of(rs.getInt("academic_year")));
				
				System.out.println("Retrieved year " + retrievedMember.getAcademic_year());
				retrievedMember.setCreated_at(LocalDate.parse(rs.getString("created_at")));
				retrievedMember.setExpired_at(LocalDate.parse(rs.getString("expired_date")));
				
				
				list.add(retrievedMember);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}

	public static boolean validCardID(int card_id) // only check card_id exist and expired_date
	{
		try(Connection con = MyConnection.getConnection())
		{
			String query = "SELECT card_id FROM tbl_members WHERE card_id = ? AND expired_date > now()";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, card_id);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next())
			{
				int id = rs.getInt("card_id");
				System.out.println("Valid id :" + id);
				
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
