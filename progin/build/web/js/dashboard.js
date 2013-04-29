function showCatPrompt()
{   
	var cat=prompt("Daftar Pengguna","");   
	var cat=prompt("Nama Kategori","");   
    
    if (cat!=null)  {addCategory(cat);}
    else if (cat == "") {alert("enter a category");} 
}

function addCategory(x)
{
    var toAppend = document.getElementById("cats");
    var newCat = document.createElement("p");
    newCat.id = x;
    var catTask = document.createElement("p");
    catTask.id = x + "t";
    
    var tNewCat = document.createTextNode(x);

    newCat.appendChild(tNewCat);
    newCat.appendChild(catTask);
    toAppend.appendChild(newCat);   
    
}

function showCatTask(p)
{
    var x = document.getElementById(p);
    x.style.backgroundColor="#D94A38";
    x.innerHTML = "let's bang!";
}

function nShowCatTask(p)
{
    var x = document.getElementById(p);
    x.innerHTML = "don't bang!";
}

var xmlhttp;
function loadXMLDocPostD(url,parameters,cfunc)
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

function deleteTask(id) {
    var param = "idtask=" + id;
    loadXMLDocPostD('DeleteTask',param,function() { 
        console.log(xmlhttp.readyState);
        console.log(xmlhttp.status);
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
            alert("Task Succesfully Deleted");
            console.log("response get");
            console.log(xmlhttp.responseText);
            }
    });
    return false;
}