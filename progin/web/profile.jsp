
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%
    if (session.getAttribute("userid")==null){
        response.sendRedirect("index.jsp");
    }
%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0"/>
<title>Profile | So La Si Do</title>
<link href="css/profile.css" rel="stylesheet" type="text/css" />
<link href="css/mediaqueries.css" rel="stylesheet" type ="text/css" />
<link href='http://fonts.googleapis.com/css?family=Skranji' rel='stylesheet' type='text/css'/>
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
</head>

<body>
<div class="header">
	<a href="Dashboard.jsp"><img align="left" src="images/logo.png" width="150" height="50" />
	<h6>Dashboard</a> | <a href="profile.jsp">Profile</a> | <a href="logout.jsp">Logout</a>
	
   | Search: <input type="search">
  <input type="submit" value="GO"></input>
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
<%
    Class.forName("com.mysql.jdbc.Driver");
    
    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/progin","progin","progin"); 
    Statement state = con.createStatement();
    
    Object getobj = request.getSession().getAttribute("userid");
    String user = String.valueOf(getobj);
    response.setContentType("text/html;charset=UTF-8");

   
    
    ResultSet rs = state.executeQuery("select * from profil where Username='"+user+"'");
    
    while(rs.next()){
        %>
        <img src="upload/<%=rs.getString(4)%>" width="200" height="150" hspace="15" vspace="15" />
        <p>User : <%=rs.getString(1)%></p>
        <p>Nama Lengkap : <%=rs.getString(3)%></p>
        <p>Tanggal Lahir : <%=rs.getString(5)%></p>
        <p>Email : <%=rs.getString(6)%></p>
       <%
    }
    state.close();
    con.close();
%>
  <form method="link" action="editprofile.jsp">
	<input type="submit" Value="edit" name="image" onclick="" ></input>
  </form>
</center>  
</div>
<div class="assignment">
  <p>Daftar Tugas :</p>
<%
    Class.forName("com.mysql.jdbc.Driver");
    
    java.sql.Connection ncon = DriverManager.getConnection("jdbc:mysql://localhost:3306/progin","progin","progin"); 
    Statement nstate = ncon.createStatement();
    
    Object ngetobj = request.getSession().getAttribute("userid");
    String nuser = String.valueOf(ngetobj);
    response.setContentType("text/html;charset=UTF-8");
    
    ResultSet nrs = nstate.executeQuery("select * from task where IDCreator='"+nuser+"'");
%>
<div class="colmid">
<div class="colleft">
<div class="col1">
<%
    while(nrs.next()) {
%>
<%=nrs.getString(3)%>
<br/>
<%
    }
%>
</div>
<div class="col2">
<%
    while(nrs.next()) {
%>
<%=nrs.getString(7)%>
<br/>
<%
               }
%>
</div>
<%
    nstate.close();
    ncon.close();

%>
        <div class="col3">
            <img src="images/cek.png" width="35" height="35" /><br/>
			<img src="images/cek.png" width="35" height="35" /><br/>
			<br/>
			<br/>
        </div> 
    </div> 
  </div>
</body>
</html>

