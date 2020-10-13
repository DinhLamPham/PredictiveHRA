/* global document */
let globalVal1=0, globalVal2=0, globalSum=0;

function getInputTrace(){
	logid = document.getElementById("logid").value;
	url = document.getElementById("url").value;
	traceid = document.getElementById("txtTraceId").value;

	let e = document.getElementById("graph_type");
    let visual_type = e.options[e.selectedIndex].text;


	location = url + '?log_id='+logid+'&trace_id='+traceid+'&visual_type='+visual_type;
}

function getHistory() {
	return document.getElementById("history-value").innerText;
}

function printHistory(num) {
	document.getElementById("history-value").innerText = num;
}
function getOutput() {
	return document.getElementById("output-value").innerText;
}
function printOutput(num) {
	if(num==""){
		document.getElementById("output-value").innerText = num;
	}
	else{
		document.getElementById("output-value").innerText=getFormattedNumber(num);
	}

}
function getFormattedNumber(num) {
	let n = Number(num);
	let value = n.toLocaleString("en");
	return value;
}
function reverseNumberFormat(num) {
	return Number(num.replace(/,/g,''));
}

// let operator = document.getElementsByClassName("operator");
// for(let i = 0; i < operator.length; i++) {
// 	operator[i].addEventListener('click', function(){
// 		switch (this.id) {
// 			case "view_detail":
// 				// getInputTrace(); break;
// 		}
// 	})
// }



var txtTraceId = document.getElementById('txtTraceId');
if (txtTraceId !== null)
{
	txtTraceId.addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
  		getInputTrace();
    }
});
}

