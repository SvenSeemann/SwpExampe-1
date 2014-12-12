function newArea() {
	document.getElementById('request').style.display = "block";
}

function build() {
	document.getElementsByClass('request').style.display = "none";
	var width = document.size.width.value;
	var height = document.size.height.value;
	document.getElementById('area').style.width = width;
	document.getElementById('area').style.height = height;

}

function areaRequest(event,form) {
	build();
	event.preventDefault();
	alert("blabliblub");
	form = $(form);
	var width = document.size.width.value;
	var height = document.size.heihgt.value;
	var action = form.attr("action");
	var method = form.attr("method");

	$.ajax({
		url : action,
		type : method,
		data : {
			width : width,
			height : height
		},
		success : function(data) {
			console.log(data);
		}
	});
}
$(document).ready(function() {
	$('#size').submit(function(event) {
		areaRequest(event,this);
	});
});

function newObject(thing) {
	document.getElementById('requestAll').style.display = "block";
	switch (thing) {

	case 1:
		document.objects.action = "/newStage";
		break;
	case 2:
		document.objects.action = "/newCatering";
		break;
	case 3:
		document.objects.action = "/newToilet";
		break;
	case 4:
		document.objects.action = "/newCamping";
		break;
	}
}

function build2() {
	document.getElementById('requestAll').style.display = "none";
}