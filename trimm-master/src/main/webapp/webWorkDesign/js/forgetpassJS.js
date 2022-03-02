/**
 * forgetpass
 */
//open nav bar
function openNav() {
		document.getElementById("mySidenav").style.width = "100%";
	}
// close nav bar
	function closeNav() {
		document.getElementById("mySidenav").style.width = "0";
	}
	var email="";
	var token="";
	var pass1="";
	var pass2="";
	//send email
	function sendEmail(){
	email=document.getElementById("email").value;
	var	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
        		
        	    if (this.readyState == 4 && (this.status == 204 || this.status==200)) {
        	    	document.getElementById("msg").innerHTML="<div id=\"right\">"+this.responseText+"</div>";
				document.getElementById("buttonspace").innerHTML="<input type=\"submit\" class=\"button buttonform\"  onclick=\"sendNewPass(); return false;\">";
        
        	    } else if(this.status==404){
        	    	
					document.getElementById("msg").innerHTML="<div id=\"wrong\">"+this.responseText+"</div>";
				}
        	}
			///http://localhost:8080/TRIMM_visualizing_runners_data_42/rest/authentication/forgetpassword
			xmlhttp.open("POST","http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/forgetpassword", true);
			xmlhttp.setRequestHeader("Content-type","application/json");
			xmlhttp.send(email);
			
	}
	//send new password
	function sendNewPass(){
		pass1=document.getElementById("password").value;
		pass2=document.getElementById("passwordconfirm").value;
		token=document.getElementById("token").value;
		
	if(pass1.localeCompare(pass2)==0){
		if(checkLength()){
		var	xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			 if (this.readyState == 4 && (this.status == 204 || this.status==200)) {
				 
				 window.location.replace("login.html");
			 } else if(this.status==404){
				 
				 document.getElementById("msg").innerHTML="<div id=\"wrong\">"+this.responseText+"</div>";
			 }
		}
		
		xmlhttp.open("POST","http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/forgetpassword/"+token+"?email="+email+"&password="+pass1, true);
		xmlhttp.setRequestHeader("Content-type","application/json");
		xmlhttp.send();
		}else{
			document.getElementById("wrong").innerHTML = "Password must be more than 8 characters!";
		}
		
	}else {
		
		document.getElementById("msg").innerHTML="<div id=\"wrong\">"+"passwords are not the same"+"</div>";
	}
	return false;
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