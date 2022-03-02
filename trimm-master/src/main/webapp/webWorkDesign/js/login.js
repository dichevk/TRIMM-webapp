function openNav() {
	document.getElementById("mySidenav").style.width = "100%";
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
}
window.onload = function toOpen() {
	var isok = sessionStorage.getItem("ok");
	if (isok == "no") {
		document.getElementById("wrong").innerHTML = "Wrong username or password!";
	}
}
function sendLogIn() {
	var user = DOMPurify.sanitize(document.getElementById("username").value);
	var pass = DOMPurify.sanitize(document.getElementById("password").value);
	localStorage.setItem("username", user);
	localStorage.setItem("password", pass);
	window.location.href = "loadingPage.html";

}