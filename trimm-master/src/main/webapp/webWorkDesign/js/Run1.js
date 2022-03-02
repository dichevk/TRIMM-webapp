//stores the full server response
var result;
var pietop = 58;
var piebottom = 25;
var pieleft = 0;
var pieright = 0;

// store the values for each variable; used in the first and second graph
var resultImpact = [];
var resultBrakingForce = [];
var resultPushOff = [];
var resultPace = [];
var resultTibialImpactL = [];
var resultTibialImpactR = [];
var resultSacralImpactL = [];
var resultSacralImpactR = [];
var resultBrakingForceL = [];
var resultBrakingForceR = [];
var resultTibialAccelerationR = [];
var resultTibialAccelerationL = [];
var resultSacralAccelerationR = [];
var resultSacralAccelerationL = [];
var resultAngleR = [];
var resultAngleL = [];
var resultSteps = [];

// counter for all the surfaces, used in third graph
var surface0 = 0;
var surface1 = 0;
var surface2 = 0;
var surface3 = 0;
var surface4 = 0;
var surface5 = 0;
var surface6 = 0;

// stores the total steps; used for the x-axis of the graph
var stepsTotal;

// first chart, created in createChart1();
var chart1;

// second chart, created in createChart2();
var chart2;

// third chard, created in createChart3();
var chart3;

// session token for the session
var sessiontoken = sessionStorage.getItem('sessiontoken');

// selected run
var run = localStorage.getItem('run');

// opens menu
function openNav() {
	document.getElementById("mySidenav").style.width = "100%";
}

// closes menu
function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
}

// saves the server's response with all the data for the selected run in the
// variable result
// starts filling the graph with the number of steps (x-axis)
window.onload = function getData() {
	checkIfloggedIn();
	detectMobile();
	document.title = "Run " + run + " | TRIMM Dashboard";
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			result = JSON.parse(this.responseText);
			stepsTotal = result.totalSteps;
			stepsArray(stepsTotal);
			getOverview();
		}
	}
	xhr
			.open(
					"GET",
					"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/run?sessiontoken="
							+ sessiontoken + "&run=" + run, true);
	xhr.send();
}

/*
 * fills the boxes above the graph with data about the distance, time,
 * description, date and average pace of the selected run
 */
function getOverview() {
	document.getElementById("overview1").innerHTML = "<p id = \"distance\">"
			+ result.meta.distance + " km</p>";
	document.getElementById("overview2").innerHTML = "<p id = \"time\">"
			+ result.meta.time + "</p>";
	document.getElementById("overview3").innerHTML = "<p id = \"description\">"
			+ result.meta.description + "</p>"
	document.getElementById("overview4").innerHTML = "<p id = \"date\">"
			+ result.meta.date + "</p>"
	document.getElementById("overview5").innerHTML = "<p id = \"pace\">"
			+ result.averagePace + " steps/min</p>"
	document.getElementById("overview6").innerHTML = "<p id = \"shoes\">"
			+ result.meta.shoe.brand + "</p>"

	getImpact();
}

// fills the first graph with data about the average tibial impact
function getImpact() {
	var impact = result.variable_avg[2];
	resultArray(impact, resultImpact);
	createChart1();
	getBrakingForce();
}

// fills the first graph with data about the average braking force
function getBrakingForce() {
	var brakingForce = result.variable_avg[0];
	resultArray(brakingForce, resultBrakingForce);
	chart1.update();
	getPushOffPower();
}

// fills the first graph with data about the average push-off power
function getPushOffPower() {
	var pushOff = result.variable_avg[1];
	resultArray(pushOff, resultPushOff);
	chart1.update();
	getPace();
}

// fills the first graph with data about the pace
function getPace() {
	var pace = result.pace;
	resultArray(pace, resultPace);
	chart1.update();
	getTibialImpactL();
}

// fills the second graph with data about the tibial impact of the left leg
function getTibialImpactL() {
	resultTibialImpactL = getDataFromArray("tibimpact_left");
	createChart2();
	getTibialImpactR();
}

// fills the second graph with data about the tibial impact of the right leg
function getTibialImpactR() {
	resultTibialImpactR = getDataFromArray("tibimpact_right");
	chart2.data.datasets[1].data = resultTibialImpactR;
	chart2.update();
	getSacralImpactL();
}

// fills the second graph with data about the sacral impact of the left leg
function getSacralImpactL() {
	resultSacralImpactL = getDataFromArray("sacimpact_left");
	chart2.data.datasets[2].data = resultSacralImpactL;
	chart2.update();
	getSacralImpactR();
}

// fills the second graph with data about the sacral impact of the right leg
function getSacralImpactR() {
	resultSacralImpactR = getDataFromArray("sacimpact_right");
	chart2.data.datasets[3].data = resultSacralImpactR;
	chart2.update();
	getBrakingForceL();
}

// fills the second graph with data about the braking force of the left leg
function getBrakingForceL() {
	resultBrakingForceL = getDataFromArray("brakingforce_left");
	chart2.data.datasets[4].data = resultBrakingForceL;
	chart2.update();
	getBrakingForceR();
}

// fills the second graph with data about the braking force of the right leg
function getBrakingForceR() {
	resultBrakingForceR = getDataFromArray("brakingforce_right");
	chart2.data.datasets[5].data = resultBrakingForceR;
	chart2.update();
	getTibialAccelerationL();
}

// fills the second graph with data about the tibial acceleration of the left
// leg
// dataset is hidden by default so the graph can be readable/not have too much
// info
function getTibialAccelerationL() {
	resultTibialAccelerationL = getDataFromArray("axtibacc_left");
	chart2.data.datasets[6].data = resultTibialAccelerationL;
	chart2.update();
	chart2.data.datasets[6].hidden = true;
	chart2.update();
	getTibialAccelerationR();
}

// fills the second graph with data about the tibial acceleration of the right
// leg
// dataset is hidden by default so the graph can be readable/not have too much
// info
function getTibialAccelerationR() {
	resultTibialAccelerationR = getDataFromArray("axtibacc_right");
	chart2.data.datasets[7].data = resultTibialAccelerationR;
	chart2.update();
	chart2.data.datasets[7].hidden = true;
	chart2.update();
	getSacralAccelerationL();
}

// fills the second graph with data about the sacral acceleration of the left
// leg
// dataset is hidden by default so the graph can be readable/not have too much
// info

function getSacralAccelerationL() {
	resultSacralAccelerationL = getDataFromArray("axsacacc_left");
	chart2.data.datasets[8].data = resultSacralAccelerationL;
	chart2.update();
	chart2.data.datasets[8].hidden = true;
	chart2.update();
	getSacralAccelerationR();
}

// fills the second graph with data about the sacral acceleration of the right
// leg
// dataset is hidden by default so the graph can be readable/not have too much
// info

function getSacralAccelerationR() {
	resultSacralAccelerationR = getDataFromArray("axsacacc_right");
	chart2.data.datasets[9].data = resultSacralAccelerationR;
	chart2.update();
	chart2.data.datasets[9].hidden = true;
	chart2.update();
	getAngleL();
}

// fills the second graph with data about the vertical lower leg angle of the
// left leg
// dataset is hidden by default so the graph can be readable/not have too much
// info

function getAngleL() {
	resultAngleL = getDataFromArray("vll_left");
	chart2.data.datasets[10].data = resultAngleL;
	chart2.update();
	chart2.data.datasets[10].hidden = true;
	chart2.update();
	getAngleR();
}

// fills the second graph with data about the vertical lower leg angle of the
// right leg
// dataset is hidden by default so the graph can be readable/not have too much
// info

function getAngleR() {
	resultAngleR = getDataFromArray("vll_right");
	chart2.data.datasets[11].data = resultAngleR;
	chart2.update();
	chart2.data.datasets[11].hidden = true;
	chart2.update();
	getSurfaces();
}

// goes through all the surfaces during the run and adds them to the respective
// surface counter
function getSurfaces() {
	var surface;
	var s0 = 0;
	var s1 = 0;
	var s2 = 0;
	var s3 = 0;
	var s4 = 0;
	var s5 = 0;
	var s6 = 0;

	for (var i = 0; i < stepsTotal; i++) {
		surface = result.stepData[i].surface;
		if (surface != null) {
			switch (surface) {
			case 1:
				s1++;
				break;
			case 2:
				s2++;
				break;
			case 3:
				s3++;
				break;
			case 4:
				s4++;
				break;
			case 5:
				s5++;
				break;
			case 6:
				s6++;
				break;
			default:
				s0++;
			}
		}
	}
	surface0 = (s0 / stepsTotal) * 100;
	surface0 = surface0.toFixed(2);
	surface1 = (s1 / stepsTotal) * 100;
	surface1 = surface1.toFixed(2);
	surface2 = (s2 / stepsTotal) * 100;
	surface2 = surface2.toFixed(2);
	surface3 = (s3 / stepsTotal) * 100;
	surface3 = surface3.toFixed(2);
	surface4 = (s4 / stepsTotal) * 100;
	surface4 = surface4.toFixed(2);
	surface5 = (s5 / stepsTotal) * 100;
	surface5 = surface5.toFixed(2);
	surface6 = (s6 / stepsTotal) * 100;
	surface6 = surface6.toFixed(2);
	createChart3();
	getShoes();
}

function getShoes() {
	document.getElementById("shoes_brand").innerHTML = document
			.getElementById("shoes_brand").innerHTML
			+ result.meta.shoe.brand;
	document.getElementById("shoes_model").innerHTML = document
			.getElementById("shoes_model").innerHTML
			+ result.meta.shoe.name;
	document.getElementById("shoes_heel").innerHTML = document
			.getElementById("shoes_heel").innerHTML
			+ result.meta.shoe.heelmm+" mm";
	document.getElementById("shoes_forefoot").innerHTML = document
			.getElementById("shoes_forefoot").innerHTML
			+ result.meta.shoe.forefootmm+" mm";
	document.getElementById("shoes_drop").innerHTML = document
			.getElementById("shoes_drop").innerHTML
			+ result.meta.shoe.dropmm+" mm";
	document.getElementById("shoes_weight").innerHTML = document
			.getElementById("shoes_weight").innerHTML
			+ result.meta.shoe.weightgr+" gr";
}

// hides/shows the given dataset of the given graph when the respective button
// is pressed
function hide(i, chart) {
	if (!chart.data.datasets[i].hidden) {
		chart.data.datasets[i].hidden = true;
		chart.update();
	} else {
		chart.data.datasets[i].hidden = false;
		chart.update();
	}
}

/*
 * takes an array with lots of data and fills the other given array with the
 * data from the first one but only for every 100 steps to avoid having too much
 * data on the graph
 */
// this is only used for the variables of the first graph
function resultArray(d, toFill) {
	var count = 99;
	var length = d.length / 100;
	toFill[0] = d[0];
	for (var i = 1; i < length; i++) {
		toFill[i] = d[count];
		count = count + 100;
	}
}

// returns an array with data only for the given variable (every 100 steps)
// this is only used for the variables of the second graph (left/right)
function getDataFromArray(variable) {
	var res = [];
	for (i = 0; i < resultSteps.length; i++) {
		count = resultSteps[i];
		res[i] = result.stepData[count - 1][variable];
	}
	return res;
}

// fills the array corresponding to the x-axis with the number of steps (every
// 100 steps)
function stepsArray(steps) {
	var count = 100;
	var length = steps / 100;
	resultSteps[0] = 1;
	for (var i = 1; i < length; i++) {
		resultSteps[i] = count;
		count = count + 100;
	}
}

// downloads the chosen graph
function download(whichGraph) {
	var graph = whichGraph.toString();
	var download = document.getElementById("download" + graph);
	var image = document.getElementById("chart" + graph).toDataURL("image/png")
			.replace("image/png", "image/octet-stream");
	download.setAttribute("href", image);

}

// creates the first graph
function createChart1() {
	Chart.defaults.global.defaultFontFamily = 'Gotham Book';
	var ctx = document.getElementById('chart1').getContext('2d');
	chart1 = new Chart(ctx, {
		type : 'line',
		data : {
			labels : resultSteps,
			datasets : [ {
				label : 'Tibial impact',
				data : resultImpact,
				backgroundColor : 'rgba(241, 181, 181, 0)',
				borderColor : 'rgba(28, 158, 188, 1)',
				pointBackgroundColor : 'rgba(28, 158, 188, 1)',
				pointBorderColor : 'rgba(18, 127, 152, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Braking force',
				data : resultBrakingForce,
				backgroundColor : 'rgba(181, 241, 211, 0)',
				borderColor : 'rgba(13, 112, 154, 1)',
				pointBackgroundColor : 'rgba(13, 112, 154, 1)',
				pointBorderColor : 'rgba(11, 94, 129, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Push-off power',
				data : resultPushOff,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(8, 87, 130, 1)',
				pointBackgroundColor : 'rgba(8, 87, 130, 1)',
				pointBorderColor : 'rgba(10, 74, 109, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Pace',
				data : resultPace,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(165, 226, 255, 1)',
				pointBackgroundColor : 'rgba(165, 226, 255, 1)',
				pointBorderColor : 'rgba(146, 200, 226, 1)',
				borderWidth : 1,
				lineTension : 0
			} ]
		},
		options : {
			responsive : true,
			maintainAspectRatio : false,
			legend : {
				onClick : function(e) {
					e.stopPropagation();
				}
			},
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			},
			title : {
				display : true,
				text : 'Variables per step (average)',
				position : 'top',
				fontFamily : 'Gotham Medium',
				fontSize : 13

			},
			tooltips : {
				callbacks : {
					label : function(t, d) {
						if (t.datasetIndex == 0 || t.datasetIndex == 1) {
							return t.yLabel + ' m/s^2';
						} else if (t.datasetIndex == 2) {
							return t.yLabel + ' m/s';
						} else {
							return t.yLabel + ' steps/min';
						}
					},
					title : function(tooltipItem, data) {
						return 'Step '
								+ data['labels'][tooltipItem[0]['index']];
					}
				}
			}
		}
	});
}

// creates the second graph
function createChart2() {
	var ctx = document.getElementById('chart2').getContext('2d');
	chart2 = new Chart(ctx, {
		type : 'line',
		data : {
			labels : resultSteps,
			datasets : [ {
				label : 'Tibial impact(L)',
				data : resultTibialImpactL,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(215, 39, 39, 1)',
				pointBackgroundColor : 'rgba(215, 39, 39, 1)',
				pointBorderColor : 'rgba(181, 33, 33, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Tibial impact(R)',
				data : resultTibialImpactR,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(248, 137, 34, 1)',
				pointBackgroundColor : 'rgba(248, 137, 34, 1)',
				pointBorderColor : 'rgba(220, 125, 36, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Sacral impact(L)',
				data : resultSacralImpactL,
				backgroundColor : 'rgba(241, 181, 181, 0)',
				borderColor : 'rgba(245, 207, 57, 1)',
				pointBackgroundColor : 'rgba(245, 207, 57, 1)',
				pointBorderColor : 'rgba(220, 183, 35, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Sacral impact(R)',
				data : resultSacralImpactR,
				backgroundColor : 'rgba(181, 241, 211, 0)',
				borderColor : 'rgba(159, 220, 35, 1)',
				pointBackgroundColor : 'rgba(159, 220, 35, 1)',
				pointBorderColor : 'rgba(143, 197, 34, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Braking force(L)',
				data : resultBrakingForceL,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(49, 224, 183, 1)',
				pointBackgroundColor : 'rgba(49, 224, 183, 1)',
				pointBorderColor : 'rgba(38, 186, 151, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Braking force(R)',
				data : resultBrakingForceR,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(48, 134, 228, 1)',
				pointBackgroundColor : 'rgba(48, 134, 228, 1)',
				pointBorderColor : 'rgba(37, 107, 184, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Axial tibial acceleration(L)',
				data : resultTibialAccelerationL,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(149, 110, 232, 1)',
				pointBackgroundColor : 'rgba(149, 110, 232, 1)',
				pointBorderColor : 'rgba(103, 65, 184, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Axial tibial acceleration(R)',
				data : resultTibialAccelerationR,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(234, 102, 184, 1)',
				pointBackgroundColor : 'rgba(234, 102, 184, 1)',
				pointBorderColor : 'rgba(191, 64, 143, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Axial sacral acceleration(L)',
				data : resultSacralAccelerationL,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(20, 156, 28, 1)',
				pointBackgroundColor : 'rgba(20, 156, 28, 1)',
				pointBorderColor : 'rgba(16, 125, 22, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Axial sacral acceleration(R)',
				data : resultSacralAccelerationR,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(16, 34, 125, 1)',
				pointBackgroundColor : 'rgba(16, 34, 125, 1)',
				pointBorderColor : 'rgba(11, 26, 105, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Vertical lower leg angle(L)',
				data : resultAngleL,
				backgroundColor : 'rgba(83, 203, 243, 0)',
				borderColor : 'rgba(83, 203, 243, 1)',
				pointBackgroundColor : 'rgba(83, 203, 243, 1)',
				pointBorderColor : 'rgba(72, 177, 211, 1)',
				borderWidth : 1,
				lineTension : 0
			}, {
				label : 'Vertical lower leg angle(R)',
				data : resultAngleR,
				backgroundColor : 'rgba(75, 158, 240, 0)',
				borderColor : 'rgba(144, 46, 236, 1)',
				pointBackgroundColor : 'rgba(144, 46, 236, 1)',
				pointBorderColor : 'rgba(113, 31, 188, 1)',
				borderWidth : 1,
				lineTension : 0
			} ]
		},
		options : {
			title : {
				display : true,
				text : 'Variables per step (left/right)',
				position : 'top',
				fontFamily : 'Gotham Medium',
				fontSize : 13

			},
			responsive : true,
			maintainAspectRatio : false,
			legend : {
				onClick : function(e) {
					e.stopPropagation();
				}
			},
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			},
			tooltips : {
				callbacks : {
					label : function(t, d) {
						if (t.datasetIndex == 10 || t.datasetIndex == 11) {
							return t.yLabel + '°';
						} else {
							return t.yLabel + ' m/s^2';
						}
					},
					title : function(tooltipItem, data) {
						return 'Step '
								+ data['labels'][tooltipItem[0]['index']];
					}
				}
			}
		}
	});
}

// creates the third chart
function createChart3() {
	var ctx = document.getElementById('chart3').getContext('2d');
	chart3 = new Chart(
			ctx,
			{
				type : 'pie',
				data : {
					datasets : [ {
						data : [ surface0, surface1, surface2, surface3,
								surface4, surface5, surface6 ],
						backgroundColor : [ 'rgb(216, 118, 242)',
								'rgb(128, 179, 255)', 'rgb(106, 224, 195)',
								'rgb(139, 242, 118)', 'rgb(243, 181, 107)',
								'rgb(243, 128, 107)', 'rgb(242, 118, 191)', ]
					} ],
					labels : [ 'Unknown', 'Asphalt', 'Grass', 'Sand',
							'Shell path', 'Brick', 'Gravel' ]
				},
				options : {
					layout : {
						padding : {
							top : pietop,
							bottom : piebottom,
							left : pieleft,
							right : pieright
						}
					},
					plugins : {
						legend : false,
						outlabels : {
							text : '%l %p',
							borderRadius : 10,
							borderWidth : 1,
							borderColor : [ 'rgb(187, 22, 233)',
									'rgb(0, 102, 255)', 'rgb(43, 212, 170)',
									'rgb(57, 233, 22)', 'rgb(236, 138, 19)',
									'rgb(236, 52, 19)', 'rgb(233, 22, 145)' ],
							color : 'white',
							stretch : 20,
							font : {
								resizable : true,
								minSize : 12,
								maxSize : 18
							}
						}
					},
					tooltips : {
						callbacks : {
							label : function(tooltipItem, data) {
								return data['labels'][tooltipItem['index']]
										+ ': '
										+ data['datasets'][0]['data'][tooltipItem['index']]
										+ '%';
							}
						}
					},
					title : {
						display : true,
						text : 'Surfaces during run',
						position : 'bottom',
						fontFamily : 'Gotham Medium',
						fontSize : 13
					},
					responsive : true,
					maintainAspectRatio : false,
					legend : {
						labels : {
							fontStyle : 'bold',
							fontColor : 'black',
						},
						display : false
					}
				}
			});
	detectPieMob();
}

// logout
function logout() {

	var sessiontoken = sessionStorage.getItem('sessiontoken');
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 204)) {
			sessionStorage.setItem("sessiontoken",null);
			window.location.href = "../index.html";
		}

	}

	xhr
			.open(
					"DELETE",
					"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/logout/"
							+ sessiontoken, true);
	// xhr.setRequestHeader("Access-Control-Allow-Origin",
	// "http://localhost:8080/TRIMM_visualizing_runners_data_42");
	xhr.send();
}

// recognise mobile device
function detectMobile() {
	var isMobile = {
		Android : function() {
			return navigator.userAgent.match(/Android/i);
		},
		BlackBerry : function() {
			return navigator.userAgent.match(/BlackBerry/i);
		},
		iOS : function() {
			return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		},
		Opera : function() {
			return navigator.userAgent.match(/Opera Mini/i);
		},
		Windows : function() {
			return navigator.userAgent.match(/IEMobile/i)
					|| navigator.userAgent.match(/WPDesktop/i);
		},
		any : function() {
			return (isMobile.Android() || isMobile.BlackBerry()
					|| isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
		}
	};
	if (isMobile.any()) {
		alert("For better experience rotate your screen horizontally!");
		pietop = 0;
		piebottm = 0;
		pieleft = 40;
		pieright = 40;

	}
}
function detectPieMob() {
	chart3.options.layout.padding.top = pietop;
	chart3.options.layout.padding.bottom = piebottom;
	chart3.options.layout.padding.left = pieleft;
	chart3.options.layout.padding.right = pieright;
	chart3.update();
}
function checkIfloggedIn(){
	
	if(sessionStorage.getItem("sessiontoken")=="null"){
		
		window.location.href="login.html";
	}
}