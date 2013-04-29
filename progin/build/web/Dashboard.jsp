<%
    if (session.getAttribute("userid")==null){
        response.sendRedirect("index.jsp");
    }
%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    
<head>
    <script>
var getkat="";

function hapuskategori(){
if(getkat == ""){
	alert("Pilih kategori terlebih dahulu !!!");
}else {
	var conf = confirm("Anda yakin menghapus kategori ini ?\nSeluruh task akan dihapus");
	//alert(getkat);
	if(conf == true){
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
			location.reload();
			}
		  }
	xmlhttp.open("GET","../progin/HapusCategory?kategori="+getkat ,true);
	xmlhttp.send();
	}else{
		alert("check");
	}
}
}

function taketask(ktg)
{
getkat = ktg;

if (ktg=="")
  {
  document.getElementById("div1").innerHTML="";
  return;
  } 
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
    document.getElementById("div1").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","../progin/daftartask?tkategori="+ktg,true);
xmlhttp.send();
}
function addkategori(){
var inputkategori = prompt("Masukkan nama kategori","");

	if(inputkategori=="") {
	alert("kolom harus diisi !");
	}else {
		callkategori(inputkategori);
	}
}

function callkategori(ckt){
if (ckt=="")
  {
  return;
  } 
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
	location.reload();
    }
  }
xmlhttp.open("GET","../progin/newcategory?newkategori="+ckt ,true);
xmlhttp.send();

}
</script>
    <title>Dashboard | So La Si Do</title>
	
    <link href="css/dashboard.css" rel="stylesheet" type="text/css" />
    <link href="css/search.css" rel="stylesheet" type="text/css" />
    <link href="css/mediaqueries.css" rel="stylesheet" type ="text/css" />
    <script type="text/javascript" language="javascript" src="js/dashboard.js"></script>
    <script type="text/javascript" language="javascript" src="js/search.js"></script>
    <!--<link href='http://fonts.googleapis.com/css?family=Merienda' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Skranji' rel='stylesheet' type='text/css'>-->
    <style>
    .user{
        width : 300px;
        height : 100px;
        margin-top : -55px;
        margin-left :770px;
        padding-top : 30px;
        padding-left: 80px;
        float:left;
    }
    </style>

</head>

<body>
    
<div class="header">
    <a href="Dashboard.jsp"><img align="left" src="images/logo.png" width="150" height="50" />
	<h6>Dashboard</h6></a> <p>|</p> <a href="profile.jsp">Profile</a> <p>|</p> <a href="logout.jsp">Logout</a>
        <form id="searchform" onsubmit="return loadSearchResult(1)" method="post" >
   | Search:<input type="search" id="searchquery" name="searchquery" /> 
   Filter: <select id="filtertype" name="filtertype">
        <option value="All" selected>All</option>
        <option value="Username">Username</option>
        <option value="Category">Category</option>
        <option value="Task">Task</option>
   </select>
   <input type="submit" value="GO" />
        </form>
        <div class="user">Welcome, 
            <%
            String login = (String) session.getAttribute("userid");
            out.print(login);
            %>
        </div>
	</div>
    <br>
    <div id="contentdashboard">
    <center>Pilih Kategori	:
		<form>
		<select name="users" onChange="taketask(this.value)">
                    <option></option>
                    <%
                    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/progin","progin","progin"); 
                    Statement state = con.createStatement();
                    ResultSet rs = state.executeQuery("select * from category");
                    while(rs.next()){
                       %>
                       <option value="<%=rs.getString(1)%>"><%=rs.getString(1)%></option>
                    <%   
                    }
                    state.close();
                    con.close();
                    %>
		</select>
		</form>
                <button onclick="addkategori()">Tambah Kategori</button>
		<button onclick="hapuskategori()">Hapus Kategori</button>
	</center>
	<div class="kategori">
	<center><h2 class="judul">Daftar Tugas</h2>
	<table>
	<tr><td><div class="judulkat"
        onmouseover="document.getElementById('div1').style.display = 'block'; document.getElementById('addtask').style.display = 'block';"
		onmouseout="document.getElementById('div1').style.display = 'block'; document.getElementById('addtask').style.display = 'block';">
		
   <div id="div1" style="display: block;">
	
	</div>
	</div>
   </td>
   
   <td><div class="judulkat"
        onmouseover="document.getElementById('div1').style.display = 'none'; document.getElementById('addtask3').style.display = 'block';"
		onmouseout="document.getElementById('div1').style.display = 'block'; document.getElementById('addtask3').style.display = 'none';"><p id="cats"></p>
		<div id="addtask3" style="display: none;">
		<a href="tambah.jsp"><img src="images/newtask.png"></a>
	</div>
       </div>
   </td>
   
   </tr></table>  
                    
 
	
</center>
            </div>
                    </div>
</body>
</html>