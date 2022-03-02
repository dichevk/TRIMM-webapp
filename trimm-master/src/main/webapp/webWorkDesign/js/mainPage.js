function openNav() {
	document.getElementById("mySidenav").style.width = "100%";
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
}
function logout() {
	console.log("I enter logout");
	var sessiontoken = sessionStorage.getItem('sessiontoken');
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState + " " + this.status);
		if (this.readyState == 4 && (this.status == 200 || this.status == 204)) {
			sessionStorage.setItem("sessiontoken", null);
			window.location.href = "../index.html";
		}

	}
	console.log("I send the request");
	xhr
	.open(
			"DELETE",
			"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/logout/"
					+ sessiontoken, true);
	
	xhr.send();
}
function showGreeting(){
	var username = sessionStorage.getItem('user');
	document.getElementById("txt").innerHTML = "<p>Hi "+username+"! </p><br>"+
	                                                      "<p>Through the menu you can see</p>"+
                                                       "<p>your latest runs, compare them or manage your account</p>";
	}