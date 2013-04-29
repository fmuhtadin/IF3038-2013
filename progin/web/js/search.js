//Tubes 2
var xmlhttp;
function loadXMLDocPost(url,parameters,cfunc)
{
    if (window.XMLHttpRequest)
      {// code for IE7+, Firefox, Chrome, Opera, Safari
      xmlhttp=new XMLHttpRequest();
      }
    else
      {// code for IE6, IE5
      xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    xmlhttp.onreadystatechange=cfunc;
    xmlhttp.open("POST",url,true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(parameters);
}

function loadSearchResult(page)
{
    var filtertype=encodeURIComponent(document.getElementById("filtertype").value);
    var searchquery=encodeURIComponent(document.getElementById("searchquery").value);
    var parameters = "filtertype="+filtertype+"&searchquery="+searchquery+"&page="+page;
    loadXMLDocPost('Search',parameters,function()
      { 
      if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
        console.log("response get");
        console.log(xmlhttp.responseText);
        document.getElementById("contentdashboard").innerHTML=xmlhttp.responseText;
        }

      });
    return false;
}
