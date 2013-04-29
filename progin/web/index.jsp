<%-- 
    Document   : index
    Created on : Apr 11, 2013, 11:03:48 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("userid")!=null){
        response.sendRedirect("Dashboard.jsp");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0"/>
<title>So La Si Do</title>
<link href="css/utama.css" rel="stylesheet" type="text/css" ></link>
<link href="css/mediaqueries.css" rel="stylesheet" type ="text/css" ></link>
<link href='http://fonts.googleapis.com/css?family=Skranji' rel='stylesheet' type='text/css'></link>
<link href='http://fonts.googleapis.com/css?family=Merienda' rel='stylesheet' type='text/css'></link>

</head>
<body>
	<div class="header">
	So La Si Do<br/>
	Beat Your Task!
	</div>
	<img alt="" src="images/fitur.png" width="403" height="403" class="fitur"></img>
	<div class="login">
	<h4 class="judul">Login</h4>
	<form action="../progin/dbconn" name="login" method="post" >
        <p>Username: <input type="text" name="ID" id="user" ></input></p>
        <p>Password: <input type="password" name="pwd" id="password" ></input> </p>
        <input type="submit" name="submitHome" value="login"></input> <br/>
    </form>
    </div>
	<div class="register">
	<h4 class="judul">Register</h4>
		
	<form action="../progin/register" name="registrasi" method="post">
                <p>Avatar: <input type="file" id="file" name="file"></input></p>
		<p>Username: <input  id="DID" name="DID" type="text"></input></p>
		<p>Password: <input id="DP" name="DP" type="password"></input></p>
		<p>Confirm Password: <input  id="DCP" name="DCP" type="password"></input></p>
		<p>Name: <input  id="DName" name="DName" type="text"></input></p>
		<p>Tanggal Lahir: <select id="Day" ><option value="01">01</option><option value="02">02</option></select><select id="DMonth"><option value="01">01</option><option value="02">02</option></select><select id="DYear"><option value="1992">1992</option><option value="1993">1993</option></select></p>
		<p>Alamat email: <input  id="DMail" name="DMail" type="text"></input></p>
		<input id="signup" value="signup" type="submit" ></input>
	</form>
    </div>
</body>   

</html>



