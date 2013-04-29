//Tubes 2

var xmlhttp = new Array();
function loadXMLDocPostR(index,url,parameters,cfunc)
{
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp[index]=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp[index]=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp[index].onreadystatechange=cfunc;
xmlhttp[index].open("POST",url,true);
xmlhttp[index].setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
xmlhttp[index].send(parameters);
}

function loadRincian() {
    alert($idtask);
}

function onload() {
    loadTaskDetails();
    loadComment(1);
    return false;
}

function loadTaskDetails()
{
    var parameters = "requesttype=load&tabletype=taskdetails";
    loadXMLDocPostR(1,'Rincian',parameters,
        function()
        { 
            if (xmlhttp[1].readyState==4 && xmlhttp[1].status==200)
            {
            console.log("response get");
            console.log(xmlhttp[1].responseText);
            document.getElementById("rincian").innerHTML=xmlhttp[1].responseText;
            }
        });
}

function loadComment(currpage)
{
    
var parameters = "requesttype=load&tabletype=komentar&page="+currpage;    
loadXMLDocPostR(2,'Rincian',parameters,function()
  { 
  if (xmlhttp[2].readyState==4 && xmlhttp[2].status==200)
    {
    console.log("response get");
    console.log(xmlhttp[2].responseText);
    document.getElementById("commentpage").innerHTML=xmlhttp[2].responseText;
    }
    
  });
  return false;
}

function saveComment() {
    var parameters = "requesttype=save"+
                    "&tabletype=komentar"+
                    "&commentarea=" + encodeURIComponent(document.getElementById("commentarea").value);
loadXMLDocPostR(3,'Rincian',parameters,function() { 
    console.log(xmlhttp[3].readyState);
    console.log(xmlhttp[3].status);
        if (xmlhttp[3].readyState==4 && xmlhttp[3].status==200)
        {
        alert("db get");
        console.log("response get");
        console.log(xmlhttp[3].responseText);
        }

});
return false;
}

function saveTaskDetails() {
    var param = "requesttype=save" +
                "&tabletype=" + "taskdetails"  +
                "&namatask=" + encodeURIComponent(document.getElementById("namatask").value) +
                //"&attachment=" + document.getElementById("attachment").value +
                "&deadline=" + encodeURIComponent(document.getElementById("deadline").value) +
                "&listassignee=" + encodeURIComponent(document.getElementById("listassignee").value) +
                "&listtag=" + encodeURIComponent(document.getElementById("listtag").value);
loadXMLDocPostR(4,'Rincian',param,function() { 
    console.log(xmlhttp[4].readyState);
    console.log(xmlhttp[4].status);
        if (xmlhttp[4].readyState==4 && xmlhttp[4].status==200)
        {
        alert("db get");
        console.log("response get");
        console.log(xmlhttp[4].responseText);
        }
});
return false;
}

function deleteComment(commentid) {
    alert("delete");
    var parameters = "requesttype=delete&commentid="+commentid;
    loadXMLDocPostR(5,'Rincian',parameters,function() { 
        console.log(xmlhttp[5].readyState);
        console.log(xmlhttp[5].status);
            if (xmlhttp[5].readyState==4 && xmlhttp[5].status==200)
            {
            alert("comment deleted");
            console.log("response get");
            console.log(xmlhttp[5].responseText);
            }
    });
    return loadComment(1);
}

