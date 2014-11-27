 function newArea() {
                document.getElementById('request').style.display = "block";
            }
            function build() {
                document.getElementById('request').style.display = "none";
                var width = document.size.width.value;
                var height = document.size.height.value;
            	alert(width + "," + height);
                document.getElementById('area').style.width = width;
                document.getElementById('area').style.height = height;
            	
            }