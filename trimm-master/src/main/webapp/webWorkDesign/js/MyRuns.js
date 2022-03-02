	var sessiontoken = sessionStorage.getItem('sessiontoken');
	function saveRun(no) {
		localStorage.setItem("run", no);
	}
	function createRunBoxes() {
		var myObj;
		var result = document.getElementById("body");
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				myObj = JSON.parse(this.responseText);
			
				
				for(var i=0;i<myObj.length;i++){
					
					result.innerHTML = result.innerHTML + "<div class=\"box\">"+
		                                                   "<div class=\"txt\">Run "+(i+1)+"</div>"+
		                                                   "<a href=\"Run1.html\""
		                       							+ "onclick="
		                       							+ "\"saveRun("
		                       							+ (i+1)
		                       							+ ")\""
		                       							+ ">"+
		                                                    "<div class=\"overlay\">"+
                                                             "<div class=\"txtover\">"+myObj[i]+"</div>"+
                                                             "</a>"+
                                                             " </div>";
					
					
				}
			}
		}
		xhr
		.open(
				"GET",
				"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/dates?sessiontoken="
						+ sessiontoken, true);
		
		xhr.send();
	}

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
	function checkIfloggedIn(){
		if(sessionStorage.getItem("sessiontoken")=="null"){
			window.location.href="login.html";
		}
	}