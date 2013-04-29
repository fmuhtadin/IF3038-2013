<%-- 
    Document   : tambah
    Created on : Apr 12, 2013, 4:36:11 PM
    Author     : user
--%>
<%-- 
    Document   : tambah
    Created on : Apr 11, 2013, 11:31:23 AM
    Author     : user
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Tambah Tugas</title>

<script language="JavaScript" type="text/JavaScript" src="js/tambah.js"></script>
<script language="JavaScript" type="text/JavaScript">
counter = 0;
function action(){
	counterNext = counter + 1;
	document.getElementById("input1"+counter).innerHTML="<p><input type='file' name='attachment[]'></p><div id=\"input1"+counterNext+"\"></div>";
	//document.getElementById("input1"+counter).innerHTML = "<p>Lampiran : <input type='file' name='dokumen[]'></p><div id=\"input1"+counterNext+"\"></div>";
	counter++;
}
function addTask() {
var a = document.getElementById("task").value;
//alert(a);
tambahTask(a);
}
function tambahTask(chk)
{
validasi();
//alert(chk);
//var b = document.getElementById("").value;
/*if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function(){
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    //console.log("response get");
    //console.log(xmlhttp.responseText);
	//alert(chk);
	alert(xmlhttp.responseText);
    //document.getElementById("containercoba").innerHTML=xmlhttp.responseText;
    }
	}
xmlhttp.open("GET","tambah.php?task=" + chk,true);
xmlhttp.send();*/
}
</script>

<link href="css/utama.css" rel="stylesheet" type="text/css" />
<link href="css/dashboard.css" rel="stylesheet" type="text/css" />
<link href="css/search.css" rel="stylesheet" type="text/css" />
<link href="css/mediaqueries.css" rel="stylesheet" type ="text/css" />
<link href='http://fonts.googleapis.com/css?family=Merienda' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Skranji' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="css/datepickr.css" />

	</head>
	<body>
<div class="header">
    <a href="Dashboard.jsp"><img align="left" src="images/logo.png" width="150" height="50" />
	<h6>Dashboard</a> <p>|</p> <a href="profile.jsp">Profile</a> <p>|</p> <a href="logout.jsp">Logout</a>
        <form id="searchform" onsubmit="return searchByFilter()" method="post" >
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
  
  <div id="containercoba">
  </div>
	<div class="container"><br>
			<div class="tambah">
			<form onsubmit="return addNewTask()" name="myform">	
			<h1 class="judul">Tambah Tugas</h1>
			Nama Task: 
			<input type="text" id="namatask" name="namatask" id="task"/><br>
			Deadline  : 
			<input type="text" id="datepick2" name="deadline" size="20" /> <br>
			Assignee : 
			<input type="text" id="assignee" name="assignee"/> <br>
			      <tr>
			Tag : 
			<input type="text" id="tag" name="tag"/> <br>
			<td><div align="left"><strong>Attachment</strong></div>
				<strong>
				</th>
				</strong>
				<td><div id="attach" align="left">
				<input type="file" name="attachment[]"/>
				<div id="input10"></div>
				<a href="javascript:action();">Tambah</a>
				</div></td>
			</tr>
			<br>

			<input type="submit" value="Tambah Tugas">
			</form><br>




</div>
</div>
<script>
function validasi(){
var namatask=/^[A-Za-z0-9_]{1,25}$/;  
var assignee=/^\S+$/;
var file=/.*\.(gif)|(jpeg)|(jpg)|(pdf)|(png)|(ogg)|(flv)|(doc)|(docx) $/;
var tag=/^([A-Za-z0-9]\s?)+([,]\s?([A-Za-z0-9]\s?)+)*$/;
if (document.myform.namatask.value.search(namatask)==-1) 
	alert("Nama tugas kosong atau tidak valid (<26 karakter)")
else{
	if (document.myform.assignee.value.search(assignee)==-1) 
		alert("Assignee kosong");
	else
	{
		if (document.myform.tag.value.search(tag)==-1) 
			alert("Tag kosong atau tidak valid");
		else
		{
			form = document.getElementByID("attach");
			for(var i = 0; i < form.elements.length; i++) 
				if (document.myform.attachment[i].value.search(file)==-1) 
					alert("Tipe file tidak valid");
				else
				{
					document.write('Penambahan Tugas Sukses<br>');
					alert("succes");
				}
			}
		}
	}
}



</script>

<script type="text/javascript" src="js/datepickr.min.js"></script>
		<script type="text/javascript">			
			new datepickr('datepick2', {
				'dateFormat': 'y/m/d'
			});
</script>

	</body>
</html>