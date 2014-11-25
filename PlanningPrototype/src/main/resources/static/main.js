 function newArea() {
                document.getElementById('request').style.display = "block";
            }
            function build() {
                document.getElementById('request').style.display = "none";
                var width = document.size.width.value;
                var height = document.size.height.value;
            	alert(width + "," + height);
                document.getElementById('area').style.width = "500px";
                document.getElementById('area').style.height = "500px";
                //areal.style.width = width;
            	//areal.style.height = height;
            	
            }