package com.jibril.nfc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	Db db = new Db("javadb","jdbc:derby:","3306","NfcDB","admin","admin");
	Connection cn = db.cn();
	
	public User userTest(int id) {
		String username = "auwal";
		String password = "auwal";
		String mobileno = "601123064474";
		String email = "auwal@gmail.com";
		String fullname = "Auwal Muhammad";
		String androidid = "667AF647";
		String simid = "Sri Petaling";
		String imei = "354745959506903";
		String datereg = "2014-09-05";
		
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setMobileno(mobileno);
		user.setEmail(email);
		user.setFullname(fullname);
		user.setAndroidid(androidid);
		user.setSimid(simid);
		user.setImei(imei);
		user.setDatereg(datereg);
		
		return user;
	}
	
	public String toString(User user){
        StringBuilder sb = new StringBuilder();
        sb.append("******* User Details *******\n");
        sb.append("ID="+user.getId()+"\n");
        sb.append("Username="+user.getUsername()+"\n");
        sb.append("Password="+user.getPassword()+"\n");
        sb.append("Mobile="+user.getMobileno()+"\n");
        sb.append("Email="+user.getEmail()+"\n");
        sb.append("Fullname="+user.getFullname()+"\n");
        sb.append("AndroidID="+user.getAndroidid()+"\n");
        sb.append("SimID="+user.getSimid()+"\n");
        sb.append("IMEI="+user.getImei()+"\n");
        sb.append("DateReg="+user.getDatereg()+"\n");
        //sb.append("Phone Numbers="+Arrays.toString(getPhoneNumbers())+"\n");
        //sb.append("Cities="+Arrays.toString(getCities().toArray())+"\n");
        sb.append("*****************************");
         
        return sb.toString();
    }

	public void addUser(User user) {
		try {
			PreparedStatement ps = cn.prepareStatement("INSERT INTO ADMIN.USERS(USERNAME, PASSWORD, MOBILENO, EMAIL, FULLNAME, "
					+ "ANDROIDID, SIMID, IMEI, DATEREG) VALUES(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getMobileno());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getFullname());
			ps.setString(6, user.getAndroidid());
			ps.setString(7, user.getSimid());
			ps.setString(8, user.getImei());
			ps.setString(9, user.getDatereg());
			ps.executeUpdate();
			System.out.println("Record Saved!");
		} catch (SQLException e) { e.printStackTrace(); }
	}

	public User getUser(int id) {
		User user = new User();
		try {
			ResultSet rs = cn.createStatement().executeQuery("SELECT * FROM ADMIN.USERS WHERE ID="+id); rs.next();
			int id2 = Integer.parseInt(rs.getString("id"));
			String username = rs.getString("username");
			String password = rs.getString("password");
			String mobileno = rs.getString("mobileno");
			String email = rs.getString("email");
			String fullname = rs.getString("fullname");
			String androidid = rs.getString("androidid");
			String simid = rs.getString("simid");
			String imei = rs.getString("imei");
			String datereg = rs.getString("datereg");
			
			user.setId(id2);
			user.setUsername(username);
			user.setPassword(password);
			user.setMobileno(mobileno);
			user.setEmail(email);
			user.setFullname(fullname);
			user.setAndroidid(androidid);
			user.setSimid(simid);
			user.setImei(imei);
			user.setDatereg(datereg);
			System.out.println("User Fetched!");
		} catch(Exception e) { System.out.println(e); }
		return user;
	}

	public void editUser(User user) {
		try {
			String sql = "UPDATE ADMIN.USERS SET "
					+ "USERNAME=?, "
					+ "PASSWORD=?, "
					+ "MOBILENO=?, "
					+ "EMAIL=?, "
					+ "FULLNAME=?, "
					+ "ANDROIDID=?, "
					+ "SIMID=?, "
					+ "IMEI=?, "
					+ "DATEREG=? WHERE ID = ?";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getMobileno());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getFullname());
			ps.setString(6, user.getAndroidid());
			ps.setString(7, user.getSimid());
			ps.setString(8, user.getImei());
			ps.setString(9, user.getDatereg());
			ps.setInt(10, user.getId());
			ps.executeUpdate();
			System.out.println("Record Updated!");
		} catch(Exception e) { System.out.println(e); }
	}
	
	public List lisUsers() {
		List<User> users = new ArrayList();		
		try {
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM ADMIN.USERS");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	User user = new User();
        	int id2 = Integer.parseInt(rs.getString("id"));
			String username = rs.getString("username");
			String password = rs.getString("password");
			String mobileno = rs.getString("mobileno");
			String email = rs.getString("email");
			String fullname = rs.getString("fullname");
			String androidid = rs.getString("androidid");
			String simid = rs.getString("simid");
			String imei = rs.getString("imei");
			String datereg = rs.getString("datereg");
			
			user.setId(id2);
			user.setUsername(username);
			user.setPassword(password);
			user.setMobileno(mobileno);
			user.setEmail(email);
			user.setFullname(fullname);
			user.setAndroidid(androidid);
			user.setSimid(simid);
			user.setImei(imei);
			user.setDatereg(datereg);                            
            users.add(user);
        }
    } catch (SQLException e) { e.printStackTrace(); }		
		return users;
	}

	public void deleteUser(int id) {
		try { PreparedStatement ps = cn.prepareStatement("DELETE FROM ADMIN.USERS WHERE ID=?");
            ps.setInt(1, id);
            System.out.println(ps.executeUpdate());
        } catch (SQLException e) { e.printStackTrace(); }
      }
	
	public User getUserD7(int id) {
		User user = new User();
		try {
			ResultSet rs = cn.createStatement().executeQuery("SELECT * FROM ADMIN.USERS WHERE ID="+id); rs.next();
			int id2 = Integer.parseInt(rs.getString("id"));
			String username = rs.getString("username");
			String password = rs.getString("password");
			String mobileno = rs.getString("mobileno");
			String email = rs.getString("email");
			String fullname = rs.getString("fullname");
			String androidid = rs.getString("androidid");
			String simid = rs.getString("simid");
			String imei = rs.getString("imei");
			String datereg = rs.getString("datereg");
			
			user.setId(id2);
			user.setUsername(username);
			user.setPassword(password);
			user.setMobileno(mobileno);
			user.setEmail(email);
			user.setFullname(fullname);
			user.setAndroidid(androidid);
			user.setSimid(simid);
			user.setImei(imei);
			user.setDatereg(datereg);
			System.out.println("User Fetched!");
		} catch(Exception e) { System.out.println(e); }
		return user;
	}
}
