function loadPodatke(){
	
	var podatak = window.location.search.substring(1);
	console.log("Usao u loadPodatke, dobio je "+ podatak);
	var niz= podatak.split("=");
	var id= niz[1];
	console.log("id servisa je "+id);

	$.ajax({
			  url : "/api/rents/vratiRentId/"+id,
			  type: 'get',
			  success: function(pom) {
				  iscrtajStranicu(pom);
				}
			});
}

function iscrtajStranicu(servis){
	console.log('usao u iscrtaj stranicu dobio je '+ servis);
	
    $("#naslov").text(servis.naziv);
    $("#adresa").append(servis.adresa);
	
}
$(document).ready(function(){
	var pom=window.location.search.substring(1);
	var id= pom.split('=')[1];
	
    $("p#vozilo").click(function(){
		window.location="addCar.html?id="+id;


   });
    $("p#filijala").click(function(){
    	
		window.location="addOffice.html?id="+id;

   });
});
