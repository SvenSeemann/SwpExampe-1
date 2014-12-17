// globale Variablen
//..........................
// 0 = small stage
// 1 = medium stage
// 2 = big stage
// 3 = standard-toilet
// 4 = behindertenklo
// 5 = Badcontainer
// 6 = Standard-catering
var objectValues = new Array(new Array('10','5'),new Array('20','10'),new Array('40','20'),new Array('3','3'),new Array('6','6'),new Array('10','3'),new Array('8','3'),new Array('',''));
//...........................
var factor;

function newArea() {
	document.getElementById('request').style.display = "block";
}
//---------------------------------------------------
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
//--------------------------------------------------
function areaRequest(event, form) {
	document.getElementById('request').style.display = "none";
	event.preventDefault();
	form = $(form);
	var width = document.size.width.value;
	var height = document.size.height.value;
	console.log(width + "," + height);
	var action = form.attr("action");
	var method = form.attr("method");
	buildArea(width, height);
	/*$.ajax({
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
	});*/
}
$(document).ready(function() {
	$('#size').submit(function(event) {
		areaRequest(event, this);
	});
});

function reset(){
	document.getElementById('request').style.display = "none";
}
//------------------------------------------
function buildArea(width, height){
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
		console.log(area.style.width + "," + area.style.height + "," + factor);
}
//------Drag-and-Drop-Code----------
    var dragobjekt = null;

// Position, an der das Objekt angeklickt wurde.
var dragx = 0;
var dragy = 0;

// Mausposition
var posx = 0;
var posy = 0;


function draginit() {
 // Initialisierung der Überwachung der Events

  document.onmousemove = drag;
  document.onmouseup = dragstop;
}


function dragstart(element) {
   //Wird aufgerufen, wenn ein Objekt bewegt werden soll.

  dragobjekt = element;
  dragx = posx - dragobjekt.offsetLeft;
  dragy = posy - dragobjekt.offsetTop;
}


function dragstop() {
  //Wird aufgerufen, wenn ein Objekt nicht mehr bewegt werden soll.

  dragobjekt=null;
}


function drag(ereignis) {
  //Wird aufgerufen, wenn die Maus bewegt wird und bewegt bei Bedarf das Objekt.
  //checkTheFrame();
  posx = document.all ? window.event.clientX : ereignis.pageX;
  posy = document.all ? window.event.clientY : ereignis.pageY;
  if(dragobjekt != null) {
    dragobjekt.style.left = (posx - dragx) + "px";
    dragobjekt.style.top = (posy - dragy) + "px";
  }
}
function cloneIt(element,type){
	var newElem = $(element).clone().appendTo("#area");
	newElem.removeAttr('onClick');
	newElem.attr('onmousedown','dragstart(this)');
	switch(type){
		case 0:
			newElem.attr("class", "object");
			var width = objectValues[0][0];
			var height = objectValues[0][1];
			break;
		case 1:
			newElem.attr("class", "object");
			var width = objectValues[1][0];
			var height = objectValues[1][1];
			break;
		case 2:
			newElem.attr("class", "object");
			var width = objectValues[2][0];
			var height = objectValues[2][1];
			break;
		case 3:
			newElem.attr("class", "object");
			var width = objectValues[3][0];
			var height = objectValues[3][1];
			break;
		case 4:
			newElem.attr("class", "object");
			var width = objectValues[4][0];
			var height = objectValues[4][1];
			break;
		case 5:
			newElem.attr("class", "object");
			var width = objectValues[5][0];
			var height = objectValues[5][1];
			break;
		case 6:
			newElem.attr("class", "object");
			var width = objectValues[6][0];
			var height = objectValues[6][1];
			break;
	}
	console.log(width + "," + height);
	newElem.css({
		'width' : (width * factor),
		'height' : (height * factor)
		});
	contextMenu(element);
}

function contextMenu(element){
var menu = document.createElement('menu');
	menu.setAttribute('type','context');
	menu.setAttribute('id','contextMenu');
	
var menuItem1 = document.createElement('menuitem');
	menuItem1.setAttribute('label' , 'Objekt drehen');	
	
var menuItem2 = document.createElement('menuitem');
	menuItem2.setAttribute('label' , 'Objekt l&ouml;schen');	
	element.appendChild(menu);
	menu.appendChild(menuItem1);
	menu.appendChild(menuItem2);
}
//----------------------------------
