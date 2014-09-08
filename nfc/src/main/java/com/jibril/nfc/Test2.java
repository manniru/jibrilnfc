package com.jibril.nfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test2 {

	public static void main(String[] args) {
		int charge = price("601123064474");
		System.out.println(charge);
		

	}
	
	public static Connection cn() {
		Connection con = null;
		try { Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nfcserver", "root", "13131");
			} catch(Exception e) { System.out.println(e); }
			return con;
	}
	
	public static int price(String mobile) {
		int price = 0;
		String l1 = "";
		String l2 = "";
		List<String> ls = new ArrayList<String>();
		
		try {
			ResultSet rs = cn().createStatement().executeQuery("SELECT * FROM `transact` WHERE mobileno='"+mobile+"' ORDER BY id DESC LIMIT 2");
			while(rs.next()){
				System.out.println(rs.getString("tagid"));
				ls.add(rs.getString("tagid")); }
			
			ResultSet rs2 = cn().createStatement().executeQuery("SELECT price FROM `prices` WHERE `from`='"+ls.get(1)+"' AND `to`='"+ls.get(0)+"'"); rs2.next();
			price= Integer.parseInt(rs2.getString("price"));
			ls.add(price+"");
			
			//System.out.println(ls.get(0));
			//System.out.println(ls.get(1));
			//System.out.println(price);
			
		} catch(Exception e) { System.out.println(e); }
		
		
		return price;
	}

}
