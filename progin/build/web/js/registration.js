    //regex
    var ck_username = /^[A-Za-z0-9_]{5,20}$/;
    var ck_password = /^[A-Za-z0-9!@#$%^&*()_]{8,20}$/;
    var ck_cpassword = /^[A-Za-z0-9!@#$%^&*()_]{8,20}$/;
    var ck_name = /^[A-Za-z0-9 ]{5,20}$/;
    var ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var file = /.*\.(jpeg)|(jpg)$/;
    var abis_koma = /^/
    
function appendRegistration(P)
{
    var regForm = document.createElement("form"); 		
    regForm.name = "registrasi";
    P.appendChild(regForm);
    P.disabled = true;
    
    var ID = document.createElement("p");
    var TID = document.createTextNode("ID: ");
    var Password = document.createElement("p");
    var TP = document.createTextNode("Password: ");
    var CPassword = document.createElement("p");
    var TCPassword = document.createTextNode("Confirm Password: ");
    var Name = document.createElement("p");
    var TName = document.createTextNode("Name: ");
    var TTL = document.createElement("p");
    var TTTL = document.createTextNode("Tanggal Lahir: ");
    var Mail = document.createElement("p");
    var TMail = document.createTextNode("Alamat e-Mail: ")
    var Avatar = document.createElement("p");
    var TAvatar = document.createTextNode("Avatar")
     
        ID.appendChild(TID);
        Password.appendChild(TP);
        CPassword.appendChild(TCPassword);
        Name.appendChild(TName);
        TTL.appendChild(TTTL);
        Mail.appendChild(TMail);
        Avatar.appendChild(TAvatar);
    
    var IID = document.createElement("INPUT");
    IID.type = "text";
    IID.name = "DID";
    IID.id = "DID";
    IID.autocomplete = "off";
    var IP = document.createElement("INPUT");
    IP.type = "password";
    IP.name = "DP";
    IP.id = "DP";
    IP.autocomplete = "off";  
    var ICP = document.createElement("INPUT");
    ICP.type = "password";
    ICP.name = "DCP";
    ICP.id = "DCP";
    ICP.autocomplete = "off";
    var IName = document.createElement("INPUT");
    IName.type = "text";
    IName.name = "DName";
    IName.id = "DName";
    IName.autocomplete = "off";
    
    //dropdown TTL
    var tanggal = document.createElement("select");
    var bulan = document.createElement("select");
    var tahun = document.createElement("select");
    //element option untuk dropdown
    //tanggal
    var t01 = document.createElement("option");
    t01.text = "01";
    t01.value = "01";
    tanggal.add(t01, null);
    var t02 = document.createElement("option");
    t02.text = "02";
    t02.value = "02";
    tanggal.add(t02, null);
    //bulan
    var b01 = document.createElement("option");
    b01.text = "01";
    b01.value = "01";
    bulan.add(b01,null);
    var b02 = document.createElement("option");
    b02.text = "02";
    b02.value = "02";
    bulan.add(b02,null);
    //tahun
    var th01 = document.createElement("option");
    th01.text = "1956";
    th01.value = "1956";
    tahun.add(th01,null);
    var th02 = document.createElement("option");
    th02.text = "1957";
    th02.value = "1957";
    tahun.add(th02,null);
    
    //email belum pake validasi
    var IMail = document.createElement("INPUT");
    IMail.type = "text";
    IMail.name = "DMail";
    IMail.id = "DMail";
    IMail.autocomplete = "off";
    
    var IAvatar = document.createElement("INPUT");
    IAvatar.type = "file";
    IMail.name = "DAvatar";
    IMail.id = "DAvatar";
    
    var SB = document.createElement("INPUT");
    SB.type = "submit";
    var TSB = document.createTextNode("sign up");
    SB.value = "signup"; 
	SB.setAttribute("onClick","validateFormRegistrasi()");
    
        ID.appendChild(IID);
        Password.appendChild(IP);
        CPassword.appendChild(ICP);
        Name.appendChild(IName);
        TTL.appendChild(tanggal);
        TTL.appendChild(bulan);
        TTL.appendChild(tahun);
        Mail.appendChild(IMail);
        Avatar.appendChild(IAvatar);
        SB.appendChild(TSB);
        
        regForm.appendChild(ID);
        regForm.appendChild(Password);
        regForm.appendChild(CPassword);
        regForm.appendChild(Name);
        regForm.appendChild(TTL);
        regForm.appendChild(Mail);
        regForm.appendChild(Avatar);
        regForm.appendChild(SB);   
                  
}

function validateFormLogin()
{
	if (document.login.ID.value.search(ck_username)==-1) 
	alert("Please enter a valid username")
	else 
	{
	if (document.login.userPassword.value.search(ck_password)==-1) 
	alert("Please enter a valid password")
	else 
			{ 
				alert("Login sukses!")
				document.write('<a href="dashboard.html">Lihat dashboard</a>');
			}
	}
	
}

function validateFormRegistrasi()
{
	alert("Registrasi sukses");
}
function formValidation(oEvent) { 
oEvent = oEvent || window.event;
var txtField = oEvent.target || oEvent.srcElement; 
var ck_username = /^[A-Za-z0-9_]{5,20}$/;
var ck_password = /^[A-Za-z0-9!@#$%^&*()_]{8,20}$/;
var ck_cpassword = /^[A-Za-z0-9!@#$%^&*()_]{8,20}$/;
var ck_name = /^[A-Za-z0-9 ]{5,20}$/;
var file = /.*\.(jpeg)|(jpg)$/;
var ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
var check=true;
var msg=" ";
if(document.getElementById("DID").value.search(ck_username)==-1 )
{ check=false; }
if(document.getElementById("DP").value.search(ck_password)==-1 )
{ check=false }
if (document.registrasi.DCP.value != document.registrasi.DP.value)
{ check=false }
if(document.getElementById("DName").value.search(ck_name)==-1 )
{ check=false }
if(document.getElementById("DMail").value.search(ck_email)==-1 )
{ check=false }
if(check){document.getElementById("signup").disabled = false; }
else{document.getElementById("signup").disabled = true; }
} 

function resetForm(){
document.getElementById("signup").disabled = true; 
var frmMain = document.forms[0]; 
frmMain.reset();
}

window.onload = function () { 

var signup = document.getElementById("signup"); 

var DID = document.getElementById("DID"); 
var DP = document.getElementById("DP"); 
var DCP = document.getElementById("DCP"); 
var DName = document.getElementById("DName");
var DMail = document.getElementById("DMail");  

var check=false;
document.getElementById("signup").disabled = true;
DID.onkeyup = formValidation; 
DP.onkeyup = formValidation; 
DCP.onkeyup = formValidation; 
DName.onkeyup = formValidation; 
DMail.onkeyup = formValidation; 
}

function reportErrors(errors)
{
    var msg = "Please Enter Valide Data...\n";
    for (var i = 0; i<errors.length; i++)
    {
    var numError = i + 1;
     msg += "\n" + numError + ". " + errors[i];
    }
    alert(msg);
}
