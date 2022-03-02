/**
 * AccountJS
 */
var myObj;
// get the data for the account
function getAcc(){
	
	var sessiontoken=sessionStorage.getItem('sessiontoken');
	
	var xhrr = new XMLHttpRequest();
	xhrr.onreadystatechange = function(){
	if(this.status ==200 && this.readyState == 4){
		myObj = JSON.parse(this.responseText);
		document.getElementById("username").innerHTML= document.getElementById("username").innerHTML+"<b>"+myObj.username+"</b>";
		document.getElementById("distance").innerHTML= document.getElementById("distance").innerHTML+myObj.distanceran +" km";
		document.getElementById("steps").innerHTML= document.getElementById("steps").innerHTML+myObj.steps +" steps";
		document.getElementById("firstname").innerHTML= document.getElementById("firstname").innerHTML+myObj.firstname;
		document.getElementById("lastname").innerHTML= document.getElementById("lastname").innerHTML+myObj.lastname;
		document.getElementById("height").innerHTML= document.getElementById("height").innerHTML+myObj.height+" cm";
		document.getElementById("weight").innerHTML= document.getElementById("weight").innerHTML+myObj.weight+" kg";
		
	}
	}
	xhrr.open("GET", "http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/user/"+sessiontoken, true);
	
	xhrr.send();
	
}
// edit the new user information
function edit(){
	document.getElementById("firstname").innerHTML="First name: <input id=\"input1\" value="+"\""+myObj.firstname+"\""+">";
	document.getElementById("lastname").innerHTML="Last name: <input id=\"input2\" value="+"\""+myObj.lastname+"\""+">";
	document.getElementById("height").innerHTML="Height: <input id=\"input3\" value="+"\""+myObj.height+"\""+">"+" cm";
	document.getElementById("weight").innerHTML="Weight: <input id=\"input4\" value="+"\""+myObj.weight+"\""+">"+" kg";
	document.getElementById("editsave").innerHTML="<button id=\"save\" onclick=\"save(); return false;\">Save information</button>";
}
// save the edited information
function save(){
myObj.firstname=DOMPurify.sanitize(document.getElementById("input1").value);
myObj.lastname=DOMPurify.sanitize(document.getElementById("input2").value);
myObj.height=DOMPurify.sanitize(document.getElementById("input3").value);
myObj.weight=DOMPurify.sanitize(document.getElementById("input4").value);
var sessiontoken=sessionStorage.getItem('sessiontoken');
var username=myObj.username;
var firstname=DOMPurify.sanitize(document.getElementById("input1").value);

var lastname =DOMPurify.sanitize(document.getElementById("input2").value);

var height=DOMPurify.sanitize(document.getElementById("input3").value);
var weight=DOMPurify.sanitize(document.getElementById("input4").value);
var jsobj={username,firstname,lastname,height,weight};

var xhrr = new XMLHttpRequest();
xhrr.onreadystatechange = function(){
	if(this.status ==204 && this.readyState == 4){
		document.getElementById("username").innerHTML= "<b>"+myObj.username+"</b>";
		document.getElementById("distance").innerHTML= "Total distance: "+myObj.distanceran +" km";
		document.getElementById("steps").innerHTML= "Total steps: "+myObj.steps +" steps";
		document.getElementById("firstname").innerHTML= "First name:"+myObj.firstname;
		document.getElementById("lastname").innerHTML= "Last name: "+myObj.lastname;
		document.getElementById("height").innerHTML= "Height: "+myObj.height+" cm";
		document.getElementById("weight").innerHTML= "Weight: "+myObj.weight+" kg";
		document.getElementById("editsave").innerHTML="<button onclick=\"edit(); return false;\">Edit account information</button>";
		
	}
	}
xhrr.open("PUT", "http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/user/"+sessiontoken, true);
	
	xhrr.setRequestHeader("Content-type","application/json");
	
	xhrr.send(JSON.stringify(jsobj));
	

}
function openNav() {
  document.getElementById("mySidenav").style.width = "100%";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}
// logout
function logout() {
	
	var sessiontoken = sessionStorage.getItem('sessiontoken');
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		
		if (this.readyState == 4
				&& (this.status == 200 || this.status == 204)) {
			sessionStorage.setItem("sessiontoken",null);
			window.location.href = "../index.html";
		}
		
	}
	xhr
	.open(
			"DELETE",
			"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/logout/"
					+ sessiontoken, true);
	
	xhr.send();
}
// Get the modal
var modal;
var span;

// Get the button that opens the modal

var btn;
function openWind(){
btn = document.getElementById("delete");
modal = document.getElementById("myModal");

// Get the <span> element that closes the modal

// When the user clicks on the button, open the modal
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
function closeWind(){
	modal.style.display = "none";
}


// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
// send delete of account request to the server
function sendDel(){
	var sessiontoken = sessionStorage.getItem('sessiontoken');
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		
		if (this.readyState == 4
				&& (this.status == 200 || this.status == 204)) {
			sessionStorage.setItem("sessiontoken",null);
			window.location.href = "../index.html";
		}
		}
	xhr
	.open(
			"DELETE",
			"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/user/"
					+ sessiontoken, true);
	
	xhr.send();
}
// function change(tag,event){
// if(event.keyCode == 13){
// console.log(tag);
// document.getElementById(tag).defaultValue=
// }
// }
