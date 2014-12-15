function newArea() {
	document.getElementById('request').style.display = "block";
}

function build() {
	document.getElementById('request').style.display = "none";
}

function areaRequest(event, form) {
	console.log("blabliblub");
	build();
	event.preventDefault();
	form = $(form);
	var width = document.size.width.value;
	var height = document.size.height.value;
	var action = form.attr("action");
	var method = form.attr("method");

	$.ajax({
		url : action,
		type : "GET",
		data : {
			width : width,
			height : height
		},
		complete : function(data) {
			console.log(data);
		}
	});
}
$(document).ready(function() {
	$('#size').submit(function(event) {
		areaRequest(event, this);
	});
});

function reset(){
	alert("iofgjdf");
	document.getElementById('request').style.display = "none";
}

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

function build2() {
	document.getElementById('requestAll').style.display = "none";
}

/*//------Drag-and-Drop-Code----------
var dragobject = null;
var dragx = 0;
var dragy = 0;

var mousex = 0;
var mousey = 0;
$(document).ready(function() {
	console.log("i am loaded");
	document.onmousemove = drag;
	document.onmouseup = dragstop;
});

function dragstart(element) {
   //Wird aufgerufen, wenn ein Objekt bewegt werden soll.
  dragobjekt = element;
  dragx = mousex - dragobjekt.offsetLeft;
  dragy = mousey - dragobjekt.offsetTop;
}

function dragstop() {
  //Wird aufgerufen, wenn ein Objekt nicht mehr bewegt werden soll.

  dragobjekt = null;
}

function drag(ereignis) {
  //Wird aufgerufen, wenn die Maus bewegt wird und bewegt bei Bedarf das Objekt.

  posx = document.all ? window.event.clientX : ereignis.pageX;
  posy = document.all ? window.event.clientY : ereignis.pageY;
  if(dragobjekt != null) {
    dragobjekt.style.left = (mousex - dragx) + "px";
    dragobjekt.style.top = (mousey - dragy) + "px";
  }
}
//----------------------------------*/