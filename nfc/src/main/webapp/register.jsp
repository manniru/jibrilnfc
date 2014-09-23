<%@include file="header.jsp" %>
<%@page import="java.awt.EventQueue,
java.awt.event.ActionListener,
java.awt.event.ActionEvent,
java.util.Date,
com.jibril.nfc.User, com.jibril.nfc.Dao"
%>

<%
if(request.getParameter("submit") != null) {
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String email = request.getParameter("email");
	String mobileno = request.getParameter("mobileno");
	String fullname = request.getParameter("fullname");
	
	User user = new User();
	user.setUsername(username);
	user.setPassword(password);
	user.setEmail(email);
	user.setMobileno(mobileno);
	user.setFullname(fullname);
	
	//Dao dao = new Dao("sqlite");
	dao.addUser(user);
	
	response.setStatus(response.SC_MOVED_TEMPORARILY); 
	response.setHeader("Location", "index.jsp");
	
}



%>

  <div id="main" class="clearfix">
    

    
    
    
    <div id="primary">
      <section id="content" class="grid_4" role="main">
        <div id="breadcrumbs"><nav class="breadcrumb"></nav></div>                        <div id="content-wrap">
                    <h1 class="page-title">User Registration</h1>                                                 <div class="region region-content">
  <div id="block-system-main" class="block block-system">

      
  <div class="content">

<form class="user-info-from-cookie" action="" method="post" id="user-register-form" accept-charset="UTF-8"><div><div id="edit-account" class="form-wrapper">
<div class="form-item form-type-textfield form-item-name">
  <label for="edit-name">Username <span class="form-required" title="This field is required.">*</span></label>
 <input class="username form-text" type="text" name="username" value="" size="60" maxlength="60">
</div>

<div class="form-item form-type-textfield form-item-name">
  <label for="edit-name">Password <span class="form-required" title="This field is required.">*</span></label>
 <input class="username form-text" type="password" name="password" value="" size="60" maxlength="60">
</div>


<div class="form-item form-type-textfield form-item-mail">
  <label for="edit-mail">E-mail address <span class="form-required" title="This field is required.">*</span></label>
 <input type="text" name="email" value="" size="60" maxlength="254" class="form-text required">
</div>


<div class="field-type-text field-name-field-mobile-no field-widget-text-textfield form-wrapper" id="edit-field-mobile-no"><div id="field-mobile-no-add-more-wrapper"><div class="form-item form-type-textfield form-item-field-mobile-no-und-0-value">
  <label for="edit-field-mobile-no-und-0-value">Mobile No </label>
 <input class="text-full form-text" type="text" name="mobileno" value="" size="60" maxlength="50">
</div>
</div></div>

<div class="field-type-text field-name-field-mobile-no field-widget-text-textfield form-wrapper" id="edit-field-mobile-no"><div id="field-mobile-no-add-more-wrapper"><div class="form-item form-type-textfield form-item-field-mobile-no-und-0-value">
  <label for="edit-field-mobile-no-und-0-value">Fullname</label>
 <input class="text-full form-text" type="text" name="fullname" value="" size="60" maxlength="50">
</div>
</div></div>


</div></div><div class="form-actions form-wrapper" id="edit-actions"><input type="submit" name="submit" value="Create new account" class="form-submit"></div></div></form>  </div>
  
</div> <!-- /.block -->
</div>
 <!-- /.region -->
        </div>
      </section> <!-- /#main -->
    </div>

          <aside id="sidebar" class="grid_2" role="complementary">

 <!-- /.region -->
      </aside> 
    

<%@include file="footer.jsp" %>