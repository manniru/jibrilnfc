<%@page import="javax.servlet.*,
java.awt.EventQueue,
java.awt.event.ActionListener,
java.awt.event.ActionEvent,
java.util.Date,
java.io.*,
java.net.URL,
java.net.HttpURLConnection,
java.io.BufferedReader,
java.util.Properties,
java.sql.*,
com.jibril.nfc.User,
com.jibril.nfc.Dao,
com.google.gson.Gson
"%>
<%! Dao dao = new Dao("mysql"); %>
<%! String uid="", message="", username="", password=""; %>
<%
if(request.getParameter("message") != null) {
	message = request.getParameter("message");
}

if(session.getAttribute("uid") != null) { uid = session.getAttribute("uid").toString(); }
if(session.getAttribute("username") != null) { username = session.getAttribute("username").toString(); }
if(session.getAttribute("password") != null) { password = session.getAttribute("password").toString(); }
%>

<!DOCTYPE html>
<!-- saved from url=(0028)http://localhost/nfc/?q=user -->
<html lang="en" dir="ltr" xmlns:content="http://purl.org/rss/1.0/modules/content/" xmlns:dc="http://purl.org/dc/terms/" xmlns:foaf="http://xmlns.com/foaf/0.1/" xmlns:og="http://ogp.me/ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:sioc="http://rdfs.org/sioc/ns#" xmlns:sioct="http://rdfs.org/sioc/types#" xmlns:skos="http://www.w3.org/2004/02/skos/core#" xmlns:xsd="http://www.w3.org/2001/XMLSchema#" class="js"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<link rel="shortcut icon" href="http://localhost/nfc/sites/all/themes/dark_elegant/favicon.ico" type="image/vnd.microsoft.icon">
<meta about="/nfc/?q=user/3" property="foaf:name" content="auwal">
<meta about="/nfc/?q=user/3#me" typeof="foaf:Person" rel="foaf:account" resource="/nfc/?q=user/3">
<meta name="Generator" content="Drupal 7 (http://drupal.org)">
<title>auwal | Mobile NFC In Transport and Ticketing Environment</title>
<style type="text/css" media="all">@import url("http://localhost/nfc/modules/system/system.base.css?nbd2x1");
@import url("http://localhost/nfc/modules/system/system.menus.css?nbd2x1");
@import url("http://localhost/nfc/modules/system/system.messages.css?nbd2x1");
@import url("http://localhost/nfc/modules/system/system.theme.css?nbd2x1");</style>
<style type="text/css" media="all">@import url("http://localhost/nfc/modules/comment/comment.css?nbd2x1");
@import url("http://localhost/nfc/modules/field/theme/field.css?nbd2x1");
@import url("http://localhost/nfc/modules/node/node.css?nbd2x1");
@import url("http://localhost/nfc/modules/search/search.css?nbd2x1");
@import url("http://localhost/nfc/modules/user/user.css?nbd2x1");</style>
<style type="text/css" media="all">@import url("http://localhost/nfc/sites/all/themes/dark_elegant/style.css?nbd2x1");</style>
<script type="text/javascript" src="./files/jquery.js"></script>
<script type="text/javascript" src="./files/jquery.once.js"></script>
<script type="text/javascript" src="./files/drupal.js"></script>
<script type="text/javascript" src="./files/superfish.js"></script>
<script type="text/javascript" src="./files/custom.js"></script>
<script type="text/javascript">
<!--//--><![CDATA[//><!--
jQuery.extend(Drupal.settings, {"basePath":"\/nfc\/","pathPrefix":"","ajaxPageState":{"theme":"dark_elegant","theme_token":"do05ZeXLqEACx3t0zqmZy1Q-xWZv1MvuvIUyXD369-A","js":{"misc\/jquery.js":1,"misc\/jquery.once.js":1,"misc\/drupal.js":1,"sites\/all\/themes\/dark_elegant\/js\/superfish.js":1,"sites\/all\/themes\/dark_elegant\/js\/custom.js":1},"css":{"modules\/system\/system.base.css":1,"modules\/system\/system.menus.css":1,"modules\/system\/system.messages.css":1,"modules\/system\/system.theme.css":1,"modules\/comment\/comment.css":1,"modules\/field\/theme\/field.css":1,"modules\/node\/node.css":1,"modules\/search\/search.css":1,"modules\/user\/user.css":1,"sites\/all\/themes\/dark_elegant\/style.css":1}}});
//--><!]]>
</script>
<!--[if lt IE 9]><script src="/nfc/sites/all/themes/dark_elegant/js/html5.js"></script><![endif]-->
<style type="text/css">.fancybox-margin{margin-right:17px;}</style></head>
<body class="html not-front logged-in one-sidebar sidebar-first page-user page-user- page-user-3">
    <div id="page" class="container_6">
  <header id="header" role="banner">
    <div class="top clearfix">
      <div class="site-logo"><a href="index.jsp" title="Home">
        <img src="./files/logo.png" alt="Home">
      </a></div>
      <hgroup class="site-name-wrap">
        <h1 class="site-name"><a href="index.jsp" title="Home">Mobile NFC In Transport and Ticketing Environment</a></h1>
              </hgroup>
      <div class="search-block-region">
              </div>
    </div>
    <nav id="navigation" role="navigation">
      <div id="main-menu">
        <ul class="menu sf-js-enabled sf-shadow">
        <li class="first last leaf"><a href="index.jsp">Home</a></li>
        <li class="first last leaf"><a href="register.jsp">Register</a></li>
        <li class="first last leaf"><a href="main.jsp">Main Account</a></li>
        <li class="first last leaf"><a href="admin.jsp">Admin</a></li>
        <li class="first last leaf"><a href="json.jsp?mobileno=601123064474&tagid=12345" target="new">JSON Test</a></li>
        <li class="first last leaf"><a href="index.jsp?action=logout">Logout</a></li>
</ul>      </div>
    </nav>
  </header>