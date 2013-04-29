<%-- 
    Document   : index
    Created on : Apr 10, 2013, 11:52:57 AM
    Author     : David
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%
    Class.forName("com.mysql.jdbc.Driver");

    String userid = request.getParameter("ID");
    String password = request.getParameter("pwd");
    
    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/progin","progin","progin"); 
    Statement state = con.createStatement();
    ResultSet rs = state.executeQuery("select * from profil where Username='"+userid+"'");

    while (rs.next()){
        if(rs.getString(2).equals(password)){
            session.setAttribute("userid",userid);
            response.sendRedirect("Dashboard.jsp");
        }else {
            response.sendRedirect("index.html");
        }
    }
    
    con.close();
%>

