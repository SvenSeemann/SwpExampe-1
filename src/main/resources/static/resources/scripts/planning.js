// globale Variablen

//objectValues(new Array(name, breite, hoehe))
var objectValues = new Array();

// Array with all datas for the db. ObjektArray(type,
// description, width, height, left, top)
var objectList = new Array();
// ...........................
var factor;
var areaWidth;
var areaHeight;

// ------------------------------------//
// get the values for of the object-types from the db
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

// ask the db for datas, if there are some object-datas, the script will build
// the objects on the area
$(document).ready(
		function() {
			event.preventDefault();
			$.ajax({
				url : "/isThereAnything",
				type : "POST",
				data : {
					request : "doYouHave?",
					festival : $("#festival-id").text()
				},
				success : function(data) {
					console.log(data);
					if (data.length >= 1) {
						// build the existing area
						buildArea(data[0].width, data[0].height);
						factor = data[0].factor;
						// build extisting objects
						for (i = 1; i < data.length; i++) {
							var newElem = document.createElement("div");
							var width = data[i].width;
							var height = data[i].height;
							newElem = $(newElem);
							newElem.appendTo('#area');
							newElem.attr("class", "objekt");
							newElem.attr("name", data[i].type);
							newElem
									.attr("oncontextmenu",
											("contextMenu(this)"));
							newElem.attr("onmouseup", "validateIt(this)");
							newElem.css({
								'width' : (data[i].width*factor),
								'height' : (data[i].height*factor),
								'left' : data[i].xPos,
								'top' : data[i].yPos
							});
							newElem.text(data[i].name);
							// script for draggable
							$(function() {
								$(".objekt").draggable({
									containment : "#area",
									snap : true,
									snapMode : "outer",
									snapTolerance : "8"
								});
							});
							// put all objects in the ojectList-Array
							objectList.push(new Array(newElem.attr('name'),
									newElem.text(), width, height, newElem.position().left,
									newElem.position().top));
						}
					}
				}
			});
		});

// toogle the submenu you click
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

// onLoad the Submenu with the choosable objects will be hidden
$(document).ready(function() {
	$(".objectMenu").hide();
});

function newArea() {
	document.getElementById('request').style.display = "block";
}

function areaRequest(event, form) {
	document.getElementById('request').style.display = "none";
	event.preventDefault();
	areaWidth = document.size.width.value;
	areaHeight = document.size.height.value;
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

function buildArea(width, height) {
	var parent = document.getElementById("substance");
	var area = document.createElement("div");
	areaWidth = width;
	areaHeight = height;
	area.setAttribute("id", "area");

	parent.appendChild(area);
	$('#area').text("Breite: " + width + "m,  Höhe: " + height + "m");
	factor = 835 / width;
	height = height * factor;

	$('#area').css({
		'width' : '835px',
		'height' : height
	});

}
// function to build camping-sites
function buildCamping() {
	// --- build the requestBox ---
	var frame = document.createElement("div");
	frame = $(frame);
	frame.attr("class", "requestAll");
	frame.append("<h3>Grö&szlig;e eingeben</h3>");
	var form = document.createElement("form");
	form = $(form);
	form.attr("name", "formul");
	form.attr("id", "formul");
	form.appendTo(frame);
	var table = document.createElement("table");
	table = $(table);
	table.appendTo(form);
	table
			.append("<tr><td>Weite:</td><td><input type='number' name='width' size='30' /> m</td></tr>");
	table
			.append("<tr><td>Höhe:</td><td><input type='number' name='height' size='30' /> m</td></tr>");
	table
			.append("<tr><td colspan='2'><input type='submit' value='submit'class='button'/><input type='reset' id='delete' value='cancel'class='button' /></td></tr>")
	frame.appendTo("#substance");
	// --------------------------
	// remove the requestBox by click the- canel-button
	$('#delete').on("click", function() {
		frame.remove();
	});
	// builds the campingsite
	$(form).submit(
			function(event) {
				event.preventDefault();
				var width = document.formul.width.value;
				var height = document.formul.height.value;
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
					newElem.attr("name", "CAMPING");
					newElem.attr("oncontextmenu", ("contextMenu(this)"));
					newElem.attr("onmouseup", "validateIt(this)");
					newElem.css({
						'width' : (document.formul.width.value * factor),
						'height' : (document.formul.height.value * factor)
					});
					newElem.text("Campingplatz");

					$(function() {
						$(".objekt").draggable({
							containment : "#area",
							snap : true,
							snapMode : "outer",
							snapTolerance : "8"
						});
					});
					objectList.push(new Array(newElem.attr('name'), newElem
							.text(), width, height, newElem.position().left,
							newElem.position().top));
					frame.remove();
				}
			});

}
// the same thing like the camping-site, only for the blocked Areals
function buildBlocked() {
	var frame = document.createElement("div");
	frame = $(frame);
	frame.attr("class", "requestAll");
	frame.append("<h3>Grö&szlig;e eingeben</h3>");
	var form = document.createElement("form");
	form = $(form);
	form.attr("name", "formul");
	form.attr("id", "formul");
	form.appendTo(frame);
	var table = document.createElement("table");
	table = $(table);
	table.appendTo(form);
	table
			.append("<tr><td>Weite:</td><td><input type='number' name='width' size='30' /> m</td></tr>");
	table
			.append("<tr><td>Höhe:</td><td><input type='number' name='height' size='30' /> m</td></tr>");
	table
			.append("<tr><td colspan='2'><input type='submit' value='submit'class='button'/><input type='reset' id='delete' value='reset'class='button' /></td></tr>")
	frame.appendTo("#substance");
	$('#delete').on("click", function() {
		frame.remove();
	});
	$(form).submit(
			function(event) {
				event.preventDefault();
				var width = document.formul.width.value;
				var height = document.formul.height.value;
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
					newElem.attr("name", "BLOCKED");
					newElem.attr("oncontextmenu", ("contextMenu(this)"));
					newElem.attr("onmouseup", "validateIt(this)");
					newElem.css({
						'width' : (width * factor),
						'height' : (height * factor)
					});
					newElem.text("Gesperrtes Areal");

					$(function() {
						$(".objekt").draggable({
							containment : "#area",
							snap : true,
							snapMode : "outer",
							snapTolerance : "8"
						});
					});
					objectList.push(new Array(newElem.attr('name'), newElem
							.text(), width, height, newElem.position().left,
							newElem.position().top));
					frame.remove();
				}
			});

}
// builds the Object
function buildObject(element, type, name) {
	// cases for the camp and the blocked areal
	if (type == "camp") {
		buildCamping();
	} else if (type == "block") {
		buildBlocked();
	} else {
		var title = objectValues[type][0];
		var width = objectValues[type][1];
		var height = objectValues[type][2];
		// checking if the item is to big for the areal
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
			// function for draggable
			$(function() {
				$(".objekt").draggable({
					containment : "#area",
					snap : true,
					snapMode : "outer",
					snapTolerance : "8"
				});
			});
			// put the object in the objectList-Array
			objectList.push(new Array(newElem.attr('name'), newElem.text(),
					width, height, newElem.position().left,
					newElem.position().top));
		}
	}
}
function validateIt(element) {
	var element = $(element);
	var index = element.parent().children().index(element);

	objectList[index][4] = element.position().left;
	objectList[index][5] = element.position().top;
}
// by clicking the save-button...
function saveIt() {
	$(document).ready(function() {
		// send the area-values at first to the db
		$.ajax({
			url : "/deleteExistingOnSave",
			type : "POST",
			data : {
				festival : $("#festival-id").text()
			},
			success : function() {
				// for each element in the objectList-Array, an ajax will be
				// sent
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
							top : objectList[i][5],
							faktor : factor,
							festival : $("#festival-id").text()
						},
						success : function(data) {
						}
					});
				}
			}
		});
	});
}
// function for turn the objects
function turnObject(element) {
	var parent = $(".contextButton").parents('.objekt');
	var a = $(parent);
	var my_index = a.parent().children().index(a);
	console.log(my_index);
	console.log(objectList[my_index]);
	width = objectList[my_index][2];
	height = objectList[my_index][3];

	if ($('#area').width() <= (width * factor)
			|| $('#area').height() <= (height * factor)) {
		alert("Drehen nicht möglich, Objekt geht über das Areal hinaus.");
		return false;
	} else {

		objectList[my_index][2] = height;
		objectList[my_index][3] = width;
		parent.css({
			'width' : objectList[my_index][2] * factor,
			'height' : objectList[my_index][3] * factor
		});
	}
}

// delete the chosed object
function deleteObject(index) {
	var parent = $(".contextButton").parents('.objekt');
	var a = $(parent);
	var my_index = a.parent().children().index(a); // get the index-value to
													// find the right position
													// in the objectList-Array
	objectList.splice(my_index, 1); // delete the entry in the array
	parent.remove();
}
// click right and get the context-menu
function contextMenu(element) {
	// blanks the original context-menu
	$(document).bind("contextmenu", function(e) {
		return false;
	});
	var element = $(element);
	var a = element.parent().children().index(element);
	var menu = document.createElement("div");
	var list = document.createElement("ul");
	// .........
	var name = document.createElement("li");
	$(name).text("Name: " + objectList[a][1]);
	$(name).attr("id", "use_a_line");
	$(name).appendTo(list);
	// .........
	var weite = document.createElement("li");
	$(weite).text("Breite: " + objectList[a][2] + "m");
	$(weite).appendTo(list);
	// ........
	var hoehe = document.createElement("li");
	$(hoehe).text("Höhe: " + objectList[a][3] + "m");
	$(hoehe).attr("id", "use_a_line");
	$(hoehe).appendTo(list);
	// ........
	var turnCCW = document.createElement("li");
	var turnACW = document.createElement("li");
	var loeschen = document.createElement("li");
	// ........
	loeschen = $(loeschen);
	turnCCW = $(turnCCW);
	turnACW = $(turnACW);
	loeschen.attr("class", "contextButton");
	turnCCW.attr("class", "contextButton");
	turnACW.attr("class", "contextButton");
	loeschen.text("Objekt löschen");
	turnCCW.text("90° im Uhrzeigersinn");
	turnACW.text("90° gegen Uhrzeigersinn");
	loeschen.attr("onClick", "deleteObject(this)");
	turnCCW.attr("onClick", "turnObject(this)");
	turnACW.attr("onClick", "turnObject(this)");
	turnCCW.appendTo(list);
	turnACW.appendTo(list);
	loeschen.appendTo(list);
	menu = $(menu);
	$(list).appendTo(menu);
	menu.attr("id", "context");
	menu.appendTo(element);
	// remove the context-menu by click anywhere
	$(document).click(function() {
		menu.remove();
	});
}