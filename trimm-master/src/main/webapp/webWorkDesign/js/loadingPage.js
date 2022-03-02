window.onload = function sendLogIn() {
	var user = localStorage.getItem('username');
	var pass = localStorage.getItem('password');
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var myObj = this.responseText;
			if (myObj.slice(0, 7) == "success") {
				sessionStorage.setItem("user", user);
				localStorage.setItem("username", "");
				localStorage.setItem("password", "");
				
				sessionStorage.setItem("sessiontoken", myObj.slice(8));
				sessionStorage.setItem("ok", "yes");
				window.location.href = "MyRuns.html";
			} else {
				localStorage.setItem("username", "");
				localStorage.setItem("password", "");
				sessionStorage.setItem("ok", "no");
				window.location.href = "login.html";
			}
		}
	}
	xhr
	.open(
			"GET",
			"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/login?username="
					+ user + "&password=" + pass, true);
	
	xhr.send();
}