/**
 * CreateAccount
 */
// open nav bar
function openNav() {
	document.getElementById("mySidenav").style.width = "100%";
}
// close nav bar
function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
}
var us = "";
var pass = "";
var email = "";
var token = "";
// send request for creating an account
function sendSignUp() {

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (this.readyState == 4 && (this.status == 204 || this.status == 200)) {

			document.getElementById("input_form").innerHTML = "<div id=\"formtext\"><i class=\"fa fa-question-circle-o icon\"></i><input type=\"text\" name =\"token\" id=\"token\" placeholder=\"token from email\"></div>"
					+ "<center><div id=\"buttonspace\"><input type=\"submit\" class=\"button buttonform\"  onclick=\"sendToken(); return false;\">"
					+ "</div><div id=\"wrong\"><div id=\"right\">"
					+ "Please check you email" + "</div></div></center>";
			// window.location.href = "login.html";

		} else if (this.status == 404) {
			document.getElementById("wrong").innerHTML = this.responseText;
		}
	}

	if ((document.getElementById("password").value).localeCompare(document
			.getElementById("passwordconfirm").value) == 0) {
		if (checkLength()) {
			us = DOMPurify.sanitize(document.getElementById("username").value);
			pass = DOMPurify
					.sanitize(document.getElementById("password").value);
			email = DOMPurify.sanitize(document.getElementById("email").value);
			sessionStorage.setItem("email", email);
			sessionStorage.setItem("username", us);
			sessionStorage.setItem("password", pass)

			

			xmlhttp
					.open(
							"POST",
							"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/reg?email="
									+ email, true);

			xmlhttp.setRequestHeader("Content-type", "application/json");
			xmlhttp.send();
			// window.location.href = "add location here";
		} else {
			document.getElementById("wrong").innerHTML = "Password must be more than 8 characters!";
		}

	} else {
		// Add an error if the passwords dont match
		document.getElementById("wrong").innerHTML = "The passwords don't match!";

	}

}
// send token to verify the account
function sendToken() {
	token = DOMPurify.sanitize(document.getElementById("token").value);

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 204 || this.status == 200)) {

			window.location.href = "login.html";

		} else if (this.status == 404) {
			document.getElementById("wrong").innerHTML = this.responseText;
		}
	}
	
	xmlhttp
			.open(
					"POST",
					"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/reg/"
							+ token
							+ "?username="
							+ us
							+ "&password="
							+ pass
							+ "&email=" + email, true);
	xmlhttp.setRequestHeader("Content-type", "application/json");
	xmlhttp.send();

}

function scorePassword(pass) {
	var score = 0;
	if (!pass)
		return score;

	// award every unique letter until 5 repetitions
	var letters = new Object();
	for (var i = 0; i < pass.length; i++) {
		letters[pass[i]] = (letters[pass[i]] || 0) + 1;
		score += 5.0 / letters[pass[i]];
	}

	// bonus points for mixing it up
	var variations = {
		digits : /\d/.test(pass),
		lower : /[a-z]/.test(pass),
		upper : /[A-Z]/.test(pass),
		nonWords : /\W/.test(pass),
	}

	variationCount = 0;
	for ( var check in variations) {
		variationCount += (variations[check] == true) ? 1 : 0;
	}
	score += (variationCount - 1) * 10;

	return parseInt(score);
}

function checkPassStrength(pass) {
	var score = scorePassword(pass);
	if(pass.length==0){
		document.getElementById("pp").style.background = "dodgerblue";
		document.getElementById("formt").style.marginBottom = "15px";
		document.getElementById("strength").innerHTML = "";
	}
	else if (score > 70) {

		document.getElementById("pp").style.background = "#00b524";
		document.getElementById("formt").style.marginBottom = "0px";
		document.getElementById("strength").innerHTML = "<font style=\"color:#00b524;\"><b>strong</b></font>";
	}
	else if (score > 40 && score <= 70) {

		document.getElementById("pp").style.background = "#f69227";
		document.getElementById("formt").style.marginBottom = "0px";
		document.getElementById("strength").innerHTML = "<font style=\"color:#f69227;\"><b>good</b></font>";
	}
	else if (score >= 0 && score <= 40) {

		document.getElementById("pp").style.background = "red";
		document.getElementById("formt").style.marginBottom = "0px";
		document.getElementById("strength").innerHTML = "<font style=\"color:red;\"><b>weak</b></font>";
	}

	return "";
}
function checkpass() {
	var p = DOMPurify.sanitize(document.getElementById("password").value);
	checkPassStrength(p);
}
function checkLength() {
	var p = DOMPurify.sanitize(document.getElementById("password").value);
	if (p.length <= 8) {

		return false;
	} else {
		return true;
	}
}