<%@page import="javax.servlet.*,java.util.*,
com.google.gson.Gson,
com.jibril.nfc.User,
com.jibril.nfc.Dao
"%>

<%
/**
try {
	ResultSet rs = cn().createStatement().executeQuery("SELECT * FROM `users` WHERE username='auwal'"); rs.next();
	String firstname = rs.getString("fullname");
	String mobileno = rs.getString("mobileno");
	System.out.println(mobileno);
} catch(Exception e) { System.out.println(e); }
*/
%>

<%
if(request.getParameter("mobileno")!=null && request.getParameter("tagid")!=null) {
	String mobileno = request.getParameter("mobileno");
	String tagid = request.getParameter("tagid");
	
	Dao dao = new Dao("mysql");
	dao.addTransact(mobileno, tagid);
	User user = dao.getUser(mobileno);
	System.out.println(new Gson().toJson(user));
	
	//System.out.println("mobile="+mobileno+", tagid="+tagid);
	
	
	//dao.addTransact(mobileno, tagid);
	
	//User user = dao.getUser(1);
	
	
	//String usernamesList = null;
	/**
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(new Gson().toJson(usernamesList));
*/
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(new Gson().toJson(user));
	
	//System.out.println(charge);
}
%>

<%!
/**
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
*/
%>