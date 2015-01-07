// globale Variablen
//..........................
// 0 = small stage
// 1 = medium stage
// 2 = big stage
// 3 = standard-toilet
// 4 = behindertenklo
// 5 = Badcontainer
// 6 = Standard-catering

//objectValues(new Array(name, breite, hoehe))
var objectValues = new Array();

// Array mit allen Objekten, die in die DB geschickt werden. ObjektArray(typ,
// bezeichnung, weite, hoehe, left, top)
var objectList = new Array();
// ...........................
var factor;
var areaWidth;
var areaHeight;

$(document).ready(
		function() {
			event.preventDefault();
			$.ajax({
				url : "/getValues",
				type : "POST",
				data : {
					request : "true"
				},
				success : function(data) {
					for (i = 0; i < data.length; i++) {
						objectValues.push(new Array(data[i].name,
								data[i].width, data[i].height));
					}
				}
			});

		});

$(document).ready(
		function() {
			event.preventDefault();
			$.ajax({
				url : "/isThereAnything",
				type : "POST",
				data : {
					request : "doYouHave?"
				},
				success : function(data) {
					console.log(data);
					if (data.length >= 1) {
						buildArea(data[0].width, data[0].height);
						factor = data[0].factor;

						for (i = 1; i < data.length; i++) {
							var newElem = document.createElement("div");
							newElem = $(newElem);
							newElem.appendTo('#area');
							newElem.attr("class", "objekt");
							newElem.attr("name", data[i].type);
							newElem
									.attr("oncontextmenu",
											("contextMenu(this)"));
							newElem.attr("onmouseup", "validateIt(this)");
							newElem.css({
								'width' : (data[i].width * factor),
								'height' : (data[i].height * factor),
								'left' : data[i].xPos,
								'top' : data[i].yPos
							});
							newElem.text(data[i].name);

							$(function() {
								$(".objekt").draggable({
									containment : "#area",
									snap : true,
									snapMode : "outer",
									snapTolerance : "8"
								});
							});
							objectList.push(new Array(newElem.attr('name'),
									newElem.text(), newElem.width(), newElem
											.height(), newElem.position().left,
									newElem.position().top));
						}
					}
				}
			});
		});

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
	areaWidth = document.size.width.value;
	areaHeight = document.size.height.value;
	alert(areaWidth + "," + areaHeight);
	buildArea(areaWidth, areaHeight);
}
$(document).ready(function() {
	$('#size').submit(function(event) {
		areaRequest(event, this);
	});
});

function breakIt() {
	document.getElementById('request').style.display = "none";
}
// ------------------------------------------
function buildArea(width, height) {
	var parent = document.getElementById("substance");
	var area = document.createElement("div");
	area.setAttribute("id", "area");

	parent.appendChild(area);
	$('#area').text("weite: " + width + "m <br> Höhe: " + height + "m");
	factor = 835 / width;
	height = height * factor;

	$('#area').css({
		'width' : '835px',
		'height' : height
	});

}

function buildObject(element, type, name) {
	switch (type) {
	case 0:
		var title = objectValues[0][0];
		var width = objectValues[0][1];
		var height = objectValues[0][2];
		break;
	case 1:
		var title = objectValues[1][0];
		var width = objectValues[1][1];
		var height = objectValues[1][2];
		break;
	case 2:
		var title = objectValues[2][0];
		var width = objectValues[2][1];
		var height = objectValues[2][2];
		break;
	case 3:
		var title = objectValues[3][0];
		var width = objectValues[3][1];
		var height = objectValues[3][2];
		break;
	case 4:
		var title = objectValues[4][0];
		var width = objectValues[4][1];
		var height = objectValues[4][2];
		break;
	case 5:
		var title = objectValues[5][0];
		var width = objectValues[5][1];
		var height = objectValues[5][2];
		break;
	case 6:
		var title = objectValues[6][0];
		var width = objectValues[6][1];
		var height = objectValues[6][2];
		break;
	case 7:
		var title = objectValues[7][0];
		var width = objectValues[7][1];
		var width = objectValues[7][2];
		break;
	case 8:
		var title = objectValues[8][0];
		var width = objectValues[8][1];
		var width = objectValues[8][2];
		break;

	}
	if ($('#area').width() <= (width * factor)
			|| $('#area').height() <= (height * factor)) {
		alert("Objekt zu gross. Bitte nehmen sie ein anderes.");
		return false;
	} else {
		var newElem = document.createElement("div");
		newElem = $(newElem);
		newElem.appendTo('#area');
		newElem.removeAttr('onClick');
		newElem.attr("class", "objekt");
		newElem.attr("name", name);
		newElem.attr("oncontextmenu", ("contextMenu(this)"));
		newElem.attr("onmouseup", "validateIt(this)");
		newElem.css({
			'width' : (width * factor),
			'height' : (height * factor)
		});
		newElem.text(title);

		$(function() {
			$(".objekt").draggable({
				containment : "#area",
				snap : true,
				snapMode : "outer",
				snapTolerance : "8"
			});
		});
		objectList.push(new Array(newElem.attr('name'), newElem.text(), width,
				height, newElem.position().left, newElem.position().top));
	}
}

function validateIt(element) {
	var element = $(element);
	var index = element.parent().children().index(element);

	objectList[index][4] = element.position().left;
	objectList[index][5] = element.position().top;

	for (i = 0; i < objectList.length; i++) {
		console.log(objectList[i][0] + ", " + objectList[i][1] + ", "
				+ objectList[i][2] + ", " + objectList[i][3] + ", "
				+ objectList[i][4] + ", " + objectList[i][5]);
	}
}

function saveIt() {
	$(document).ready(function() {
		$.ajax({
			url : "/newArea",
			type : "POST",
			data : {
				width : areaWidth,
				height : areaHeight,
				faktor : factor
			},
			success : function() {
				for (i = 0; i < objectList.length; i++) {
					$.ajax({
						url : "/newObject",
						type : "post",
						data : {
							typ : objectList[i][0],
							name : objectList[i][1],
							width : objectList[i][2],
							height : objectList[i][3],
							left : objectList[i][4],
							top : objectList[i][5]
						},
						success : function(data) {
							console.log(data);
						}
					});
				}

			}
		});
	});
}

function deleteObject(index) {
	var parent = $(".contextButton").parents('.objekt');
	var a = $(parent);
	var my_index = a.parent().children().index(a);
	objectList.splice(my_index, 1);
	parent.remove();
}

function contextMenu(element) {
	$(document).bind("contextmenu", function(e) {
		return false;
	});
	var element = $(element);
	var a = element.parent().children().index(element);
	console.log(a);
	console.log(objectList[a][1]);

	var menu = document.createElement("div");
	var list = document.createElement("ul");

	var name = document.createElement("li");
	$(name).text("Name: " + objectList[a][1]);
	$(name).appendTo(list);
	var weite = document.createElement("li");
	$(weite).text("Breite: " + objectList[a][2]);
	$(weite).appendTo(list);
	var hoehe = document.createElement("li");
	$(hoehe).text("Höhe: " + objectList[a][3]);
	$(hoehe).appendTo(list);
	var top = document.createElement("li");
	$(top).text("Y-Achse: " + objectList[a][5]);
	$(top).appendTo(list);
	var left = document.createElement("li");
	$(left).text("x-Achse: " + objectList[a][4]);
	$(left).appendTo(list);
	// var turnCCW = document.createElement("li");
	// var leftACW = document.createElement("li");
	var loeschen = document.createElement("li");
	loeschen = $(loeschen);
	loeschen.attr("class", "contextButton");
	loeschen.attr("id", "loeschen");
	loeschen.text("Objekt löschen");
	loeschen.attr("onClick", "deleteObject(this)");
	loeschen.appendTo(list);
	menu = $(menu);
	$(list).appendTo(menu);
	menu.attr("id", "context");
	menu.appendTo(element);
	$(document).click(function() {
		menu.remove();
	});
}