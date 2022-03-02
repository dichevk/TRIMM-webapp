/**
 * helpMain
 */
//log out
function logout() {
				
				var sessiontoken = sessionStorage.getItem('sessiontoken');
				var xhr = new XMLHttpRequest();
				xhr.onreadystatechange = function() {
					
					if (this.readyState == 4
							&& (this.status == 200 || this.status == 204)) {
						sessionStorage.setItem("sessiontoken", null);
						window.location.href = "../index.html";
					}

				}
				
				xhr
						.open(
								"DELETE",
								"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/logout/"
										+ sessiontoken, true);
				//xhr.setRequestHeader("Access-Control-Allow-Origin", "http://localhost:8080/TRIMM_visualizing_runners_data_42");
				xhr.send();
			}