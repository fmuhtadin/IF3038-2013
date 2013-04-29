//Tubes 2
var xmlhttp;
function loadXMLDocPostT(url,parameters,cfunc)
{
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
alert(url);
xmlhttp.onreadystatechange=cfunc;
xmlhttp.open("POST",url,true);
xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
xmlhttp.send(parameters);
}

function addNewTask() {
    var param = "namatask=" + encodeURIComponent(document.getElementById("namatask").value) +
                //"&attachment=" + document.getElementById("attachment").value +
                "&deadline=" + encodeURIComponent(document.getElementById("datepick2").value) +
                "&assignee=" + encodeURIComponent(document.getElementById("assignee").value) +
                "&tag=" + encodeURIComponent(document.getElementById("tag").value);
    alert(param);
    loadXMLDocPostT('AddTask',param,function() { 
        console.log(xmlhttp.readyState);
        console.log(xmlhttp.status);
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
            alert("Task Succesfully Added");
            console.log("response get");
            console.log(xmlhttp.responseText);
            }
    });
    return false;
}
