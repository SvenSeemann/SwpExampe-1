// globale Variablen
//..........................
// 0 = small stage
// 1 = medium stage
// 2 = big stage
// 3 = standard-toilet
// 4 = behindertenklo
// 5 = Badcontainer
// 6 = Standard-catering
var objectValues = new Array(new Array('10', '5'), new Array('20', '10'),
		new Array('40', '20'), new Array('3', '3'), new Array('6', '6'),
		new Array('10', '3'), new Array('8', '3'), new Array('', ''));
// ...........................
var factor;

function newArea() {
	document.getElementById('request').style.display = "block";
}
// ---------------------------------------------------
function openSubMenu(thing) {
	switch (thing) {
	case "stage":
		$("#stages").slideToggle('fast');
		break;
	case "toilet":
		$("#toilets").slideToggle('fast');
		break;
	case "catering":
		$("#catering").slideToggle('fast');
		break;
	case "camping":
		$("#camping").slideToggle('fast');
		break;
	}
}
$(document).ready(function() {
	$(".objectMenu").hide();
});
// --------------------------------------------------
function areaRequest(event, form) {
	document.getElementById('request').style.display = "none";
	event.preventDefault();
	form = $(form);
	var width = document.size.width.value;
	var height = document.size.height.value;
	var action = form.attr("action");
	var method = form.attr("method");
	buildArea(width, height);

	$.ajax({
		url : action,
		type : method,
		data : {
			width : width,
			height : height,
			faktor : factor
		},
		success : function(data) {
			console.log(data);
		}
	});

}
$(document).ready(function() {
	$('#size').submit(function(event) {
		areaRequest(event, this);
	});
});

function reset() {
	document.getElementById('request').style.display = "none";
}
// ------------------------------------------
function buildArea(width, height) {
	var parent = document.getElementById("substance");
	var area = document.createElement("div");
	area.setAttribute("id", "area");

	parent.appendChild(area);

	factor = 835 / width;
	height = height * factor;

	$('#area').css({
		'width' : '835px',
		'height' : height
	});
}
// ------Drag-and-Drop-Code----------
// ----------------------------------------------------------
function cloneIt(element, type) {
	var newElem = $(element).clone().appendTo("#area");
	newElem.removeAttr('onClick');
	//newElem.attr('onmousedown', 'dragDrop(this)');
	switch (type) {
	case 0:
		var width = objectValues[0][0];
		var height = objectValues[0][1];
		break;
	case 1:
		var width = objectValues[1][0];
		var height = objectValues[1][1];
		break;
	case 2:
		var width = objectValues[2][0];
		var height = objectValues[2][1];
		break;
	case 3:
		var width = objectValues[3][0];
		var height = objectValues[3][1];
		break;
	case 4:
		var width = objectValues[4][0];
		var height = objectValues[4][1];
		break;
	case 5:
		var width = objectValues[5][0];
		var height = objectValues[5][1];
		break;
	case 6:
		var width = objectValues[6][0];
		var height = objectValues[6][1];
		break;
	}
	newElem.attr("class", "objekt");
	newElem.css({
		'width' : (width * factor),
		'height' : (height * factor)
	});
	$(function(){
	    $( ".objekt" ).draggable({containment : "#area" });
	   });

}

// ----------------------------------

/* hier das zeug zum validieren fuers gelanede- theoretisch	var areaBorderLeft = $('#area').offset().left;
var areaBorderRight = $('#area').width() + $('#area').offset().left;
var areaBorderTop = $('#area').offset().top;
var areaBorderBottom = $('#area').offset().top + $('#area').height();

var objectBorderLeft = element.offsetLeft;
var objectBorderRight = element.offsetLeft
		+ parseInt(element.style.width, 10);
var objectBorderTop = element.offsetTop;
var objectBorderBottom = element.offsetTop
		+ parseInt(element.style.height, 10);

if (objectBorderLeft >= areaBorderLeft
			&& objectBorderRight <= areaBorderRight
			&& objectBorderTop >= areaBorderTop
			&& objectBorderBottom <= areaBorderBottom) {
	console.log("funzt");
	}
*/