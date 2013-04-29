<%-- 
    Document   : editprofile
    Created on : Apr 12, 2013, 3:22:56 PM
    Author     : Asus
--%>
<%
    if (session.getAttribute("userid")==null){
        response.sendRedirect("index.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
    .user{
        width : 400px;
        height : 100px;
        margin-top : -55px;
        margin-left :770px;
        padding-top : 30px;
        padding-left: 80px;
    }
</style>
<script>
function updateprofil() {

var user = document.getElementById("DID").value;
var password = document.getElementById("DP").value;
var nama = document.getElementById("DName").value;
var tgl = document.getElementById("Day").value;
var bln = document.getElementById("DMonth").value;
var thn = document.getElementById("DYear").value;
var email = document.getElementById("DMail").value;

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    alert(xmlhttp.responseText);
    }
  }
xmlhttp.open("GET","../progin/editprofile?newname="+user+"&newpass="+password+"&newnama="+nama+"&newtanggal="+tgl+"&newbulan="+bln+"&newtahun="+thn+"&newemail="+ email,true);
xmlhttp.send();

}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0"/>
<title>Profile | So La Si Do</title>
<link href="css/profile.css" rel="stylesheet" type="text/css" />
<link href="css/mediaqueries.css" rel="stylesheet" type ="text/css" />
<link href='http://fonts.googleapis.com/css?family=Skranji' rel='stylesheet' type='text/css'/>
</head>

<body>
<div class="header">
	<a href="Dashboard.jsp"><img align="left" src="images/logo.png" width="150" height="50" />
	<h6>Dashboard</a> | <a href="profile.jsp">Profile</a> | <a href="logout.jsp">Logout</a>
	
   | Search: <input type="search">
  <input type="submit" value="GO">
      <div class="user">Welcome, 
      <%
      String login = (String) session.getAttribute("userid");
      out.print(login);
      %>
  </div>
	</div>
	<div class="container">
<div class="data">
<center>
<h1>Profile</h1>
<form action="../progin/editprofile" name="update">
		<p>Username: <input  id="DID" name="DID" type="text"></input></p>
		<p>Password: <input id="DP" name="DP" type="password"></input></p>
		<p>Confirm Password: <input  id="DCP" name="DCP" type="password"></input></p>
		<p>Name: <input  id="DName" name="DName" type="text"></input></p>
		<p>Tanggal Lahir: <select id="Day" name="Day"><option value="01">01</option><option value="02">02</option></select><select id="DMonth" name="Month"><option value="01">01</option><option value="02">02</option></select><select id="DYear" name="Year"><option value="1992">1992</option><option value="1993">1993</option></select></p>
		<p>Alamat email: <input  id="DMail" name="DMail" type="text"></input></p>
		<input id="signup" value="save" type="submit" ></input>
	</form>
</center>  
</div>
<div class="assignment">
        <div class="col3">
            <img src="images/cek.png" width="35" height="35" /><br/>
			<img src="images/cek.png" width="35" height="35" /><br/>
			<br/>
			<br/>
        </div> 
    </div> 
  </div>
</div>
</div>
</body>
</html>
