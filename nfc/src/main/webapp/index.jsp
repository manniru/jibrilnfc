<%@include file="header.jsp" %>

<%
if(request.getParameter("login") != null) {
	String user1="", pass1="", user2="", pass2="", role="", role2="";
	user1 = request.getParameter("username");
	pass1 = request.getParameter("password");
	
	//Dao dao = new Dao("sqlite");
	User user = dao.userLogin(user1, pass1);
	user2 = user.getUsername();
	pass2 = user.getPassword();
	
	if(user1.equals(user2) && pass1.equals(pass2)) {
		session.setAttribute("uid", user.getId()+"");
		session.setAttribute("username", user1);
		session.setAttribute("password", pass1);
		//session.setAttribute("role", "user");
		
		response.setStatus(response.SC_MOVED_TEMPORARILY); 
		response.setHeader("Location", "main.jsp");
	}
	
	else {
		response.setStatus(response.SC_MOVED_TEMPORARILY); 
		response.setHeader("Location", "index.jsp?message=Invalid Username or Password!");
	}
	
	
	
	
	
}

if(request.getParameter("action") != null && request.getParameter("action").equals("logout")) {
	session.setAttribute("username", null);
	session.setAttribute("password", null);
	session.setAttribute("role", null);
	response.setStatus(response.SC_MOVED_TEMPORARILY);
	response.setHeader("Location", "index.jsp"); 
}
%>

  <div id="main" class="clearfix">
                    <div id="slider">
        <div class="flexslider">
          <ul class="slides">
            <li style="width: 100%; float: left; margin-right: -100%; display: none;"><a href="#"><img class="slide-image" src="./files/slide-image-1.jpg"></a>
              <div class="flex-caption">
                <h3>Mobile NFC</h3><p>NFC...</p>
              </div>
            </li>
            <li style="width: 100%; float: left; margin-right: -100%; display: list-item;"><a href="http://localhost/nfc/?q=node/2"><img class="slide-image" src="./files/slide-image-2.jpg"></a>
              <div class="flex-caption">
                <h3>Technology for the Future</h3><p>Technology for the Future...</p>
              </div>
            </li>
            <li style="width: 100%; float: left; margin-right: -100%; display: none;"><a href="http://localhost/nfc/?q=node/3"><img class="slide-image" src="./files/slide-image-3.jpg"></a>
              <div class="flex-caption">
                <h3>Contact us</h3><p>Mobile NFC...</p>
              </div>
            </li>
          </ul>
        <ol class="flex-control-nav"><li><a class="">1</a></li><li><a class="active">2</a></li><li><a class="">3</a></li></ol></div>  
      </div>
      <div class="intro"><div class="intro-text"><p>Mobile NFC Technology <a href="#">NFC</a>.</p>
</div></div>        

    
    
    
    <div id="primary">
      <section id="content" class="grid_4" role="main">
                                <div id="content-wrap">
                    <h1 class="page-title"><%= message %></h1>                                                  <div class="region region-content">
  <div id="block-system-main" class="block block-system">

      
  <div class="content">
    <div id="first-time"><p>Welcome please login or register to use the system</p></div>  </div>
  
</div> <!-- /.block -->
</div>
 <!-- /.region -->
        </div>
      </section> <!-- /#main -->
    </div>

          <aside id="sidebar" class="grid_2" role="complementary">
       <div class="region region-sidebar-first">

<div id="block-user-login" class="block block-user">

        <h2>User login</h2>
    
  <div class="content">
    <form action="" method="post" id="user-login-form" accept-charset="UTF-8"><div><div class="form-item form-type-textfield form-item-name">
  <label for="edit-name">Username <span class="form-required" title="This field is required.">*</span></label>
 <input type="text" name="username" value="" size="15" maxlength="60" class="form-text required">
</div>
<div class="form-item form-type-password form-item-pass">
  <label for="edit-pass">Password <span class="form-required" title="This field is required.">*</span></label>
 <input type="password"  name="password" size="15" maxlength="128" class="form-text required">
</div>
<div class="item-list"><ul><li class="first"><a href="register.jsp" title="Create a new user account.">Create new account</a></li>
</ul></div>
<input type="hidden" name="form_id" value="user_login_block">
<div class="form-actions form-wrapper" id="edit-actions"><input type="submit" name="login" value="Log in" class="form-submit"></div></div></form>  </div>
  
</div> <!-- /.block -->

</div>
 <!-- /.region -->
      </aside> 
    
      </div>

<%@include file="footer.jsp" %>