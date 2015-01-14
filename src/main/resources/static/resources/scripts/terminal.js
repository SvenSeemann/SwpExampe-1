//global variables
var factor;

$(document).ready(function() {
	terminalRequest(event);
});

// interrogate the datas
function terminalRequest(event) {
	// prohibit to reload the page
	event.preventDefault();
	$.ajax({
		url : "/terminal/show/area/" + $("#festival-id").text(),
		type : "post",
		data : {
			request : "true"
		},
		datatype : "json",
		success : function(data) {
			// check if there are any datas
			if (data.length >= 1) {
				factor = data[0].factor; // set the factor
				buildArea(data[0]);
				buildObjects(data);
			}
		}
	});

}
// build the area
function buildArea(obj) {
	var area = document.createElement("div");
	var area = $(area);
	area.appendTo("#substance");
	area.attr("id", "area");
	area.width(obj.width * factor);
	area.height(obj.height * factor);
	//conform the '#substance'-div to the area-height
	$('#substance').css({
		'width' : '835px',
		'height' : 'auto',
		'padding' : '0',
	});
}
// build all the objects which are in the db
function buildObjects(obj) {
	for (i = 1; i < obj.length; i++) {
		var newObj = document.createElement("div");
		var newObj = $(newObj);
		newObj.attr("class", "objekt");
		newObj.css({
			'width' : obj[i].width * factor,
			'height' : obj[i].height * factor,
			'left' : obj[i].xPos,
			'top' : obj[i].yPos
		});
		newObj.text(obj[i].name);
		newObj.appendTo('#area')
	}
}