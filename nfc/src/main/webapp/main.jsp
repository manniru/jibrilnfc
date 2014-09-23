<%@include file="header.jsp" %>
<%! String userid="", email="", mobileno="", fullname="";%>
<%
//Dao dao = new Dao("sqlite");
User user = dao.userLogin(username, password);
System.out.println(new Gson().toJson(user));

userid = user.getId()+"";
password = user.getPassword();
email = user.getEmail();
mobileno = user.getMobileno();
fullname = user.getFullname();

%>

  <div id="main" class="clearfix">
    

    
    
    
    <div id="primary">


<section id="content" class="grid_4" role="main">
        <div id="breadcrumbs"><h2 class="element-invisible">You are here</h2><nav class="breadcrumb"></nav></div>                        <div id="content-wrap">
                    <h1 class="page-title">Welcome <%=username %></h1><div class="tabs-wrapper clearfix"><h2 class="element-invisible">Primary tabs</h2>

<ul class="tabs primary clearfix">
<li class="active"><a href="#" class="active">View</a></li>
<li><a href="#">Edit</a></li>
<li><a href="?action=buy">Buy Reload</a></li>
</ul></div>

<div class="region region-content">
  <div id="block-system-main" class="block block-system">

      
  <div class="content">
<div class="profile" typeof="sioc:UserAccount" about="#">

<div>
<%
if(request.getParameter("action")!=null && request.getParameter("action").equals("buy")) {
	out.println("<form id='form1' name='form1' method='post'>"+
			  "<label for='select'>Select:</label><select name='amount'>"+
			   "<option value='20'>20</option><option value='30'>30</option><option value='50'>50</option></select>"+
			 " <input type='submit' name='buynow' value='Submit'></form>");
}

if(request.getParameter("buynow")!=null) {
	String amount = request.getParameter("amount");
	System.out.println(uid+"=="+amount);
	int uid2 = Integer.parseInt(uid);
	int amt = Integer.parseInt(amount);
	//Dao dao2 = new Dao("sqlite");
	dao.addAccount(uid2, "CR", "BUY RELOAD", amt);
	response.setStatus(response.SC_MOVED_TEMPORARILY); 
	response.setHeader("Location", "main.jsp");
	//dao.addTransact(mobileno, tagid);
}

%>
</div>
    
<div class="field field-label-inline clearfix"><div class="field-label">User ID:</div><%= uid %></div>
<div class="field field-label-inline clearfix"><div class="field-label">Username:</div><%= username %></div>
<div class="field field-label-inline clearfix"><div class="field-label">Password:</div><div class="field-items"><%= password %></div></div>
<div class="field field-label-inline clearfix"><div class="field-label">Email:</div><div class="field-items"><%= email %></div></div>
<div class="field field-label-inline clearfix"><div class="field-label">Mobile No:</div><div class="field-items"><%= mobileno %></div></div>
<div class="field field-label-inline clearfix"><div class="field-label">Fullname:</div><div class="field-items"><%= fullname %></div></div>

<h3>Account History</h3>

<table width='500' border='1' cellspacing='0' cellpadding='1'>
  <tr>
    <td width='45'><strong>ID</strong></td>
    <td width='145'><strong>UID</strong></td>
    <td width='95'><strong>TYPE</strong></td>
    <td width='95'><strong>DESCRIPTION</strong></td>
    <td width='98'><strong>AMOUNT</strong></td>
  </tr>
  
  <%
  Object[][] oo = dao.getData("accounts", uid);
  int tt=0;
  try {
  for(int i=0; i<oo.length;i++) {
		System.out.println(oo[i][0]+"=="+oo[i][1]);
		
		out.println("<tr><td>"+oo[i][0]+"</td><td>"+oo[i][1]+"</td><td>"+oo[i][2]+"</td><td>"+oo[i][3]+"</td><td>"+oo[i][4]+"</td></tr>");
		try { tt = tt + Integer.parseInt(oo[i][4].toString()); } catch(Exception e) { }
	}
  } catch(Exception e) { out.println("Empty Records!"); }
  %>
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>Total Credits:</td>
    <td><strong><%= tt %></strong></td>
  </tr>
</table>


</div>
  </div>
  
</div> <!-- /.block -->
</div>
 <!-- /.region -->
        </div>
      </section> <!-- /#main -->
    </div>

          <aside id="sidebar" class="grid_2" role="complementary">
       <div class="region region-sidebar-first">
 
<div id="block-system-user-menu" class="block block-system block-menu">

        <h2>User menu</h2>
    
  <div class="content">
    <ul class="menu"><li class="first leaf"><a href="main.jsp">My account</a></li>
<li class="last leaf"><a href="index.jsp?action=logout">Log out</a></li>
</ul>  </div>
  
</div> <!-- /.block -->

</div>
 <!-- /.region -->
      </aside> 
    
      </div>


  
<%@include file="footer.jsp" %>