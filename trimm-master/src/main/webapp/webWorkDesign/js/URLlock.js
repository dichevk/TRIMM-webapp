/**
 * 
 */
function checkIfloggedIn(){
	
	if(sessionStorage.getItem("sessiontoken")=="null"){
		
		window.location.href="login.html";
	}
}