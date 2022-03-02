/**
 * comparepage
 */
 var sessiontoken = sessionStorage.getItem('sessiontoken');
	//variables for the second run
	var colorpicker = ['rgb(193, 66, 66)','rgb(191, 63, 191)','rgb(210, 8, 8)','rgb(97, 199, 19)','rgba(19, 187, 199, 0.5)','rgba(199, 19, 187, 0.5)',
		'rgba(192, 151, 148, 0.5)','rgba(217, 200, 48, 0.5)','rgba(105, 96, 16, 0.5)','rgba(55, 148, 130, 0.5)','rgba(151, 246, 227, 0.5)',
		'rgba(229, 86, 229, 0.5)'];
	var dataforgraph = [];
	var chart;
	var st=[];
	var saverun;
	var variable="";
	var ch=0;
	//the variables end here
	//variables for first run
    var numrun = " ";
    var countruns = 0;
    var runsselected=[];
    var dataToCompare=[];
    var toCompare="";
    var ascdesc=2;
    var myChart;
    //first run variables end here
    //the furst graph funcrionality starts here
    //create the run buttons for the bar graph
       window.onload= function createButtons(){
    	   checkIfloggedIn();
    	   detectMobile();
    	   
    	var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			//console.log(this.readyState + " " + this.status);
			if (this.readyState == 4 && this.status == 200) {
				//--produe=ce buttons
				var count = parseInt(JSON.parse(this.responseText));
				countruns = count;
				var produceButtons = "";
				
				for(var i=1;i<=count;i++){
					produceButtons = produceButtons+"<button id="+"\"run"+i+"\""+"; class="+"\"buttonAdd\""+";  onclick =\"saveRunNumber("+i+"); return false;\">Run "+i+"</button>";
				}
				document.getElementById("firstpagetoadd").innerHTML = document.getElementById("firstpagetoadd").innerHTML+produceButtons;
				createSecondPartButtons();
				barGraph();
				createChart();
			}
		}
		xhr
		.open(
				"GET",
				"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/count?sessiontoken="+sessiontoken,
				true);
	
		xhr.send();
    }
       //save the desired run number
    function saveRunNumber(num){
    	if(toCompare==""){
    		alert("Please select on what you want to compare first!");
    	}else{
    	
    	runsselected.push("run"+""+num);
    	
    	retreiveOneDataToCompare(num,toCompare);
    	moveToRemove(num);
    	}
    	
    
    }
    //on what the user wants to compare
    function onWhatToCompare(onwhat){
    	
    	toCompare=onwhat;
    	myChart.data.datasets[0].label=onwhat;
    	myChart.update();
    	
    }
    //does the user want ascending or descending graph sort
    function ascOrDsc(onwhat){
    	
    	ascdesc=onwhat;
    	sort(runsselected,dataToCompare);
    	
    	updateGraph(dataToCompare,runsselected);
    }
    
   // retreive the data for the specific run
    function retreiveOneDataToCompare(run,onwhat){
    	var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			
			if (this.readyState == 4 && this.status == 200) {
				
				var d = parseInt(this.responseText);
				dataToCompare.push(d);
				
				sort(runsselected,dataToCompare);
		    	
		    	updateGraph(dataToCompare,runsselected);
		    	
		    	
				
			}
		}
		xhr
		.open(
				"GET",
				"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/"+onwhat+"?sessiontoken="+sessiontoken+"&run="+run,
				true);
	
		xhr.send();
    	
    }
    //sort the graph
    function sort(runsar,dataar){
    	
    	if(ascdesc==1){
    	for(var i=0;i<dataar.length;i++){
    		for(var j=0;j<dataar.length;j++){
    		if(dataar[j]>dataar[j+1]){
    			var temp = dataar[j];
    			dataar[j]=dataar[j+1];
    			dataar[j+1]=temp;
    			var temp1 = runsar[j];
    			runsar[j]=runsar[j+1];
    			runsar[j+1]=temp1;
    		}
    	}
    }
    }else if(ascdesc==0){
    	for(var i=0;i<dataar.length;i++){
    		for(var j=0;j<dataar.length;j++){
    		if(dataar[j]<dataar[j+1]){
    			var temp = dataar[j];
    			dataar[j]=dataar[j+1];
    			dataar[j+1]=temp;
    			var temp1 = runsar[j];
    			runsar[j]=runsar[j+1];
    			runsar[j+1]=temp1;
    		}
    	}
    }
    }
    	
    }
    //moving a button to the remove dropdown
    function moveToRemove(id){
    	removeElement("run"+""+id);
    	var removeButton = "<button id="+"\"run"+id+"\""+"; class="+"\"buttonRemove\""+";  onclick =\"moveToAdd("+id+"); return false;\">Run "+id+"</button>";
    	document.getElementById("firstpagetoremove").innerHTML = document.getElementById("firstpagetoremove").innerHTML+removeButton;
    	
    	
    }
    //move a button to the add run dropdown
    function moveToAdd(id){
    	
    	var prodButtons =" <button id="+"\"run"+id+"\""+"; class="+"\"buttonAdd\""+";  onclick =\"saveRunNumber("+id+"); return false;\">Run "+id+"</button>";
    	var saveind;
    	for(var i=0;i<runsselected.length;i++){
    		if(runsselected[i]=="run"+""+id){
    			saveind = i;
    			
    			
    		}
    	}
    	
    	runsselected.splice(saveind,1);
		dataToCompare.splice(saveind,1);
		updateGraph(dataToCompare,runsselected);
		removeElement("run"+""+id);
		document.getElementById("firstpagetoadd").innerHTML = document.getElementById("firstpagetoadd").innerHTML+prodButtons;
		
		
    	
    }
    //removing an element from a div box used to delete html elements from the dropdowns while moving
    function removeElement(id) {
    	var elem = document.getElementById(id);
    	 elem.parentElement.removeChild(elem);
    }
    //update the first graph with the new datasets
    function updateGraph(data,label){
    	myChart.data.datasets.data = data;
        myChart.data.labels = label;
  	   myChart.update();
    }
    //remove all the runs from the graph. just presses all the buttons
    function removeFirstAll(){
    	
   	 var x = document.getElementById("firstpagetoremove").querySelectorAll(".buttonRemove");
   	
   	 for(var i=1;i<x.length;i++){
   		 
   	  x[i].click();
   	 }
   }

    //first chart
    function barGraph(){
    	
    	var ctx = document.getElementById('myChart').getContext('2d');

		//the chart starts here
		 myChart = new Chart(ctx, {
			type : 'bar',
			data : {
				labels : runsselected,
				datasets : [ {
					label : 'Compare runs by Average pace, Duration or Steps',
					data : dataToCompare,
					backgroundColor: [
						 'rgba(28, 158, 188, 0.4)',
			             'rgba(215, 28, 76, 0.4)',
			             'rgba(246, 146, 39, 0.4)',
			             'rgba(185, 234, 255, 0.4)',
			             'rgba(247, 206, 43, 0.4)',
			             'rgba(190, 205, 0, 0.4)',
			             'rgba(13, 112, 154, 0.4)',
			             'rgba(201, 18, 55, 0.4)'
					],
					borderColor: [
						'rgba(28, 158, 188, 0.6)',
			             'rgba(215, 28, 76, 0.6)',
			             'rgba(246, 146, 39, 0.6)',
			             'rgba(185, 234, 255, 0.6)',
			             'rgba(247, 206, 43, 0.6)',
			             'rgba(190, 205, 0, 0.6)',
			             'rgba(13, 112, 154, 0.6)',
			             'rgba(201, 18, 55, 0.6)'
					],
					borderWidth : 1
				} ]
			},
			options : {
				responsive : true,
				maintainAspectRatio : false,
				scales : {
					yAxes : [ {
						ticks : {
							beginAtZero : true
						}
					} ]
				}
			}
		});
    }
    //the furst graph functionality ends here
    //the second graph functionality starts here
    //create the buttons for the second graph
    function createSecondPartButtons() {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				//console.log(this.readyState + " " + this.status);
				if (this.readyState == 4 && this.status == 200) {
					//--produe=ce buttons
					var count = parseInt(JSON.parse(this.responseText));
					countruns = count;
					var produceButtons = "";
					for (var i = 1; i <= count; i++) {
						produceButtons = produceButtons + "<button id="+"\"run"+i+""+i+"\""+"; class="+"\"buttonAdd\""+";  onclick =\"saveSecondRunNumber("+i+"); return false;\">Run "+i+"</button>";
					}
					document.getElementById("secondpagetoadd").innerHTML = document.getElementById("secondpagetoadd").innerHTML+produceButtons;
					createChart();
				}
			}
			xhr
			.open(
					"GET",
					"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/count?sessiontoken="
							+ sessiontoken, true);
		
			xhr.send();
		}
    //update the datasets in the second graph. dynamically adding new datasets
    function updateSecondGraph(data,l,color) {
		
		chart.data.datasets.push({
			label : l,
			data : data,
			backgroundColor : "rgba(255, 33, 89, 0)",
			borderColor : colorpicker[color],
			pointBackgroundColor : colorpicker[color],
			pointBorderColor : colorpicker[color],
			borderWidth : 1,
			lineTension : 0
		});
		chart.update();
	}
    //save the run number for the second graph
    function saveSecondRunNumber(num) {
    	if(variable==""){
    		alert("Select a variable first");
    	}else{
		
		saverun=num;
		getSteps(num);
		 moveToSecondRemove(num);
    	}

	}
    //on what variable the user wants to compare
    function setVariable(v){
		
		variable=v;
		chart.options.title.text = v+" by steps";
		chart.update();
	}
// get the variable data for the specific run
	function getVariable() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			
			if (this.readyState == 4 && this.status == 200) {
				var result = JSON.parse(this.responseText);
				var toadd = resultArray(result);
				// update graph
				updateSecondGraph(toadd,"run"+""+saverun,ch);
				ch++;

			}
		}
		xhr
		.open(
				"GET",
				"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/overview_variable?sessiontoken="
						+ sessiontoken
						+ "&run="
						+ saverun
						+ "&variable1="
						+ variable
						+ "_right&variable2="
						+ variable
						+ "_left", true);
	
		xhr.send();
	}
// generate the result array for the y-axis
	function resultArray(d) {
		var toFill = [];
		var count = 99;
		var length = d.length / 100;
		toFill[0] = d[0];
		for (var i = 1; i < length; i++) {
			toFill[i] = d[count];
			count = count + 100;
		}
		return toFill;
	}
	//get the steps to be generated for the x-axis
	function getSteps(run) {
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				
				if (this.readyState == 4 && this.status == 200) {
					var steps = parseInt(JSON.parse(this.responseText));
					if(st.length==0){
						compareSteps(st,convStepsArray(steps));
					}else{
						compareSteps(st,convStepsArray(steps));
					}
					getVariable();
				}
			}
			xhr
			.open(
					"GET",
					"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/data/steps?sessiontoken="
							+ sessiontoken + "&run=" + run, true);
	
			xhr.send();
		
	}
	//compare the steps and save the biggest array
	function compareSteps(st1,st2){
		if(st1.length>st2.length){
			st=st1;
			chart.data.labels =st;
		  	chart.update();
		}else{
			st=st2;
			chart.data.labels =st;
		  	chart.update();
		}
	}
	//generate the rediced steps array
	function stepsArray(steps) {
		var count = 100;
		var length = steps / 100;
		st[0] = 1;
		for (var i = 1; i < length; i++) {
			st[i] = count;
			count = count + 100;
		}
	}
	//directly converting the steps to reduced array
	function convStepsArray(steps) {
		var si=[];
		var count = 100;
		var length = steps / 100;
		si[0] = 1;
		for (var i = 1; i < length; i++) {
			si[i] = count;
			count = count + 100;
		}
		return si
	}
	//move the run to the remoce dropdown
	function moveToSecondRemove(id){
    	removeSecondElement("run"+""+id+""+id);
    	var removeButton = "<button id="+"\"run"+id+""+id+"\""+"; class="+"\"buttonRemove\""+";  onclick =\"moveToSecondAdd("+id+"); return false;\">Run "+id+"</button>";
    	document.getElementById("secondpagetoremove").innerHTML = document.getElementById("secondpagetoremove").innerHTML+removeButton;
    	
    	
    }
	//delete a html element
	function removeSecondElement(id) {
    	var elem = document.getElementById(id);
    	 elem.parentElement.removeChild(elem);
    }
	//move a button to the second part add dropdown
    function moveToSecondAdd(id){
    	
    	var prodButton =" <button id="+"\"run"+id+""+id+"\""+"; class="+"\"buttonAdd\""+";  onclick =\"saveSecondRunNumber("+id+"); return false;\">Run "+id+"</button>";
    	var saveind;
    	for(var i=0;i<runsselected.length;i++){
    		if(runsselected[i]=="run"+""+id){
    			saveind = i;
    			
    			
    		}
    	}
    	//console.log("removing dataset run"+id);
    	removeDataset("run"+""+id);
		removeElement("run"+""+id+""+id);
		document.getElementById("secondpagetoadd").innerHTML = document.getElementById("secondpagetoadd").innerHTML+prodButton;
		ch--;
		
		
    	
    }
    //delete a dataset from the chart
    function removeDataset(ind) {
    	var index;
    	
    	for(var i=0;i<chart.data.datasets.length;i++){
    		
    		if(chart.data.datasets[i].label ==ind){
    			
    			index=i
    			
    		}
    	}
    	chart.data.label.splice(index, 1);
    	chart.data.datasets.splice(index, 1);
    	chart.update();
    	}
    //clock all the buttons in the remove deopdown for Remove all
    function removeAll(){
    	 var x = document.getElementById("secondpagetoremove").querySelectorAll(".buttonRemove");
    	 for(var i=1;i<x.length;i++){
    	  x[i].click();
    	 }
    }
    //create the second chart
    function createChart() {
		var ctx = document.getElementById('mysecondChart').getContext('2d');
		chart = new Chart(ctx, {
			type : 'line',
			data : {
				label:st

			},
			options : {
				title: {
		            display: true,
		            text: 'Compare runs by variable'
		        },
				responsive : true,
				maintainAspectRatio : false,
				scales : {
					yAxes : [ {
						ticks : {
							beginAtZero : true
						}
					} ]
				}
			}
		});
	}
    
    
    //the second graph functionality ends here
    //log out
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
			console.log("I send the request");
			xhr
			.open(
					"DELETE",
					"http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/rest/authentication/logout/"
							+ sessiontoken, true);
		
			xhr.send();
		}
    //detect mobile device
    function detectMobile(){
    	var isMobile = {
    		    Android: function() {
    		        return navigator.userAgent.match(/Android/i);
    		    },
    		    BlackBerry: function() {
    		        return navigator.userAgent.match(/BlackBerry/i);
    		    },
    		    iOS: function() {
    		        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    		    },
    		    Opera: function() {
    		        return navigator.userAgent.match(/Opera Mini/i);
    		    },
    		    Windows: function() {
    		        return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
    		    },
    		    any: function() {
    		        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    		    }
    		};
    	if( isMobile.any() ) {alert("For better experience rotate your screen horizontally!");}
    }