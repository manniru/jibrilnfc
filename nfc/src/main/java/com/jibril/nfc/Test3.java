package com.jibril.nfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test3 {

	public static void main(String[] args) {
		User user = getUser("6011230477373");
		
		System.out.println(user.getId());
		System.out.println(user.getAddress());

	}
	
	public static User getUser(String mobileno) {
		User user = new User();
		ResultSet rs = null;
		try {
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nfc", "root", "");
			
			rs = cn.createStatement().executeQuery("SELECT * FROM `field_data_field_mobile_no` WHERE field_mobile_no_value='"+mobileno+"'"); rs.next();
			int uid = Integer.parseInt(rs.getString("entity_id"));
			
			rs = cn.createStatement().executeQuery("SELECT field_first_name_value FROM `field_data_field_first_name` WHERE entity_id="+uid); rs.next();
			String firstname = rs.getString(1);
			rs = cn.createStatement().executeQuery("SELECT field_last_name_value FROM `field_data_field_last_name` WHERE entity_id="+uid); rs.next();
			String lastname = rs.getString(1);			
			rs = cn.createStatement().executeQuery("SELECT field_address_value FROM `field_data_field_address` WHERE entity_id="+uid); rs.next();
			String address = rs.getString(1);
			rs = cn.createStatement().executeQuery("SELECT field_amount_value FROM `field_data_field_amount` WHERE entity_id="+uid); rs.next();
			int amount = Integer.parseInt(rs.getString(1));
			rs = cn.createStatement().executeQuery("SELECT field_mobile_no_value FROM `field_data_field_mobile_no` WHERE entity_id="+uid); rs.next();
			String mobile = rs.getString(1);
			//rs = cn.createStatement().executeQuery("SELECT field_last_name_value FROM `field_data_field_image` WHERE entity_id="+uid); rs.next();
			
			rs = cn.createStatement().executeQuery("SELECT * FROM `users` WHERE uid="+uid); rs.next();
			String username = rs.getString("name");
			String email = rs.getString("mail");

			user.setId(uid);
			user.setFullname(firstname+" "+lastname);
			user.setUsername(username);
			user.setEmail(email);
			user.setMobileno(mobileno);
			user.setAmount(amount);
			user.setAddress(address);
			
			} catch(Exception e) { System.out.println(e); }
		
		return user;
	}

}
