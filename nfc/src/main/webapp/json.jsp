<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.jibril.nfc.User, java.util.*,java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet" %>

<%! public Connection cn() {
	Connection con = null;
	try { Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nfcserver", "root", "");
		} catch(Exception e) { System.out.println(e); }
		return con;
}
%>

<%
try {
	ResultSet rs = cn().createStatement().executeQuery("SELECT * FROM `users` WHERE username='auwal'"); rs.next();
	String firstname = rs.getString("fullname");
	String mobileno = rs.getString("mobileno");
	System.out.println(mobileno);
} catch(Exception e) { System.out.println(e); }
%>

<%
if(request.getParameter("mobileno")!=null && request.getParameter("tagid")!=null) {
	String mobile = request.getParameter("mobileno");
	String tagid = request.getParameter("tagid");
	
	try {
		cn().createStatement().execute("INSERT INTO transact(`mobileno`, `tagid`) VALUES('"+mobile+"', '"+tagid+"')");
	} catch(Exception e3) { System.out.println(e3); }
	
	String usernamesList = null;
	
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(new Gson().toJson(usernamesList));
	
	//String username = request.getParameter("username");
	//boolean usernameAvailable = studentService.usernameAvailable(username);

	///Map<String, Object> data = new HashMap<String, Object>();
	///data.put("username", "auwa");
	
	
	
	
	
		User us = getUser("601123064474");
		int id = us.getId();
		String username = us.getUsername();
		String email = us.getEmail();
		String mobileno = us.getMobileno();
		String fullname = us.getFullname();
		String address = us.getAddress();
		int amount = us.getAmount();
		
	
	
			//int id = 1;
		/**
		String username = "auwal";
		
		String mobileno = "601123064474";
		String email = "auwal@gmail.com";
		String fullname = "Auwal Muhammad";
		
		*/
		String password = "auwal";
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
		
		//int amount = 150;
		int charge = price("601123064474");

		int balance = amount-charge;
		user.setAmount(amount);
		user.setLrt1("Bukit Jalil");
		user.setLrt2("Masjid Jamek");
		user.setCharge(charge);
		user.setBalance(balance);

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(new Gson().toJson(user));
	
	//System.out.println(charge);
}
%>

<%!
public int price(String mobile) {
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

%>
<%!
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
%>