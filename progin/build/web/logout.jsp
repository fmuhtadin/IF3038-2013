<%-- 
    Document   : logout
    Created on : Apr 13, 2013, 12:50:08 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
session.removeAttribute("userid");
response.sendRedirect("index.jsp");
%>