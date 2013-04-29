<!--<?

$iduser = $_SESSION['login'];
ChromePhp::log($iduser);
$idtask = $_POST['idtask'];
ChromePhp::log($idtask);

$con = mysqli_connect(localhost,"progin","progin","progin") or die ('Cannot connect to database : ' . mysql_error());
    ChromePhp::log("userid: ".$userid);
    
$result = mysqli_query($con, "SELECT * FROM task WHERE ID='".$idtask."'");
$row = mysqli_fetch_array($result);
$resultassignee = mysqli_query($con, "SELECT * FROM task INNER JOIN assignee ON task.ID=assignee.IDTask WHERE task.ID='".$idtask."'");

$resulttag = mysqli_query($con, "SELECT * FROM tags INNER JOIN task ON task.ID=tags.IDTask WHERE task.ID='".$idtask."'");


?>-->
<%
if (session != null) {
    String username = (String)session.getAttribute("userid");
}
if (session.getAttribute("taskid") == null || session.getAttribute("taskid").equals("") || (
        request.getParameter("idtask") != null && !request.getParameter("idtask").equals(""))) {
    session.setAttribute("taskid", request.getParameter("idtask"));
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Rincian Tugas | DTD</title>
<link href="css/rincian.css" rel="stylesheet" type="text/css" />
<link href="css/search.css" rel="stylesheet" type="text/css" />
<link href="css/mediaqueries.css" rel="stylesheet" type ="text/css" />
<link href='http://fonts.googleapis.com/css?family=Merienda' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Skranji' rel='stylesheet' type='text/css'>
    <!--<script type="text/javascript" language="javascript" src="js/pagination.js"></script>-->
    <script type="text/javascript" language="javascript" src="js/rincian.js"></script>
    
	
</head>
    <!--loadComment(1,'<?php  echo $idtask; ?>')-->
    <body onload="onload()">
    
    
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
	<div id ="contentdashboard" class="container"><br>
			<div class="rincian" id="rincian">
                            <p>Curr ID Task: </p> <%= request.getParameter("idtask") %>
<!--				<form name="rincian">
				<h1 class="judul">Rincian Tugas</h1>
                                <?php
                                echo "Nama Task : <input type='text' id='namatask' value='".$row['Nama']."' border='0' /> <br>";
                                ?>
				Attachment : <br> 
				<video width="320" height="240" controls="controls">
  				<source src="assets/mov_bbb.ogg" type="video/ogg">
				Your browser does not support the video tag.
				</video>
				<br>
                                <?php
                                echo "Deadline : <input  type='text' id='deadline' value='".$row['Deadline']."' border='0' /><br>";
                                ?>
				<?php 
                                $listassignee = "";
                                while ($rowassignee = mysqli_fetch_array($resultassignee)) {
                                    $listassignee.=$rowassignee['IDUser'].",";
                                }
                                $listassignee1 = substr($listassignee, 0, strlen($listassignee)-1);
                                echo "Assignee : <input type='text' id='listassignee' value='".$listassignee1."' border='0' />";
                                
                                $listag = "";
                                while ($rowtag = mysqli_fetch_array($resulttag)) {
                                    $listag.=$rowtag['Tag'].",";
                                }
                                $listag1 = substr($listag, 0, strlen($listag)-1);
                                
                                ?>
				
				<?Php echo "Tag : <input  type='text' id='listtag' value='".$listag1."' border='0' /> <br> "?>
				<img src="images/edit.png" width="150" id="edit-button" value="EDIT" onClick="edit()" />
				<img src="images/save.png" width="150" id="save-button" value="SAVE" onClick="saveTaskDetails()" /><br><br>-->
				
			</div>

			<div class="comment">
				<h1 class="judul">Komentar</h1>
                                <textarea name="commentarea" id="commentarea" rows="4" cols="45"></textarea> <br> 
                                <form id="formkomentar"  > 
				
                                    <input type="button" onclick="saveComment()" value="SUBMIT" /> 
                                    <br><br>
                                </form>
                                                <div id="commentpage">
<!--                                <?php
                                $resultcomment = mysqli_query($con, "SELECT * FROM komentar INNER JOIN task ON task.ID=komentar.IDTask");
                                ChromePhp::log($con);
                                ChromePhp::log($resultcomment);
                                while ($rowcomment = mysqli_fetch_array($resultcomment)) {
                                    echo " ".$rowcomment['IDUser']." ".$rowcomment['Waktu']." ".$rowcomment['Isi']." <br>";
                                }
                                ?>-->
				
                                        
                                        </div>
			</div>
		</div>
<script>
function edit(){
	document.getElementById("tag").readOnly=false;
	document.getElementById("deadline").readOnly=false;
	document.getElementById("assignee").readOnly=false;
}
function save()
{
	document.getElementById("tag").readOnly=true;
	document.getElementById("deadline").readOnly=true;
	document.getElementById("assignee").readOnly=true;
}
</script>
<script type="text/javascript" language="javascript" src="js/autocomplete.js"></script>
<script type="text/javascript" src="js/datepickr.min.js"></script>
<script type="text/javascript">			
			new datepickr('datepick2', {
				'dateFormat': 'm/d/y'
			});
</script>
<!--<script type="text/javascript" language="javascript" src="js/pagination.js"></script>-->
</body>
</html>
