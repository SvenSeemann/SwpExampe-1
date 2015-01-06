//global variables

var factor;

function terminalRequest(event) {
	event.preventDefault();
	$.ajax({
		url : "/terminal",
		type : "post",
		data : {
			request : "true"
		},
		datatype: "json",
		success : function(d
				ata) {
			factor = data[0].factor;
			buildArea(data[0]);
			buildObjects(data);
		}
	});

}
$(document).ready(function() {
		terminalRequest(event);
});

function buildArea(obj){
	var area = document.createElement("div");
	var area = $(area);
	area.appendTo("#substance");
	area.attr("id","area");
	area.width(obj.width * factor);
	area.height(obj.height * factor);
	$('#substance').css({
		'width' : '835px',
		'height' : 'auto',
		'padding' : '0',
	});
}

function buildObjects(obj){
	for (i = 1; i < obj.length; i++){
		var newObj = document.createElement("div");
		var newObj = $(newObj);
		newObj.attr("class", "objekt");
		newObj.css({
			'width' : obj[i].width * factor,
			'height': obj[i].height * factor,
			'left' : obj[i].xPos,
			'top' : obj[i].yPos
		});
		newObj.text(obj[i].name);
		newObj.appendTo('#area')
	}
}