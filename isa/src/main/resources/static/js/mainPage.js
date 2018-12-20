/**
 * 
 */

function onLoad(){
	
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();
}

function planeShow(){
	$("#pozadinaAvion").show();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();
	$("#ispisiTabelu").empty();
}
function hotelShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").show();
	$("#ispisiTabelu").empty();
		
	$.ajax({
		method:'GET',
		url: "/api/hoteli/all",
		success: function(lista){
			if(lista == null){
				console.log('Nema servise');
			}else{
				ispisiHotele(lista);
			}
		}
	});
}

function ispisiHotele(lista){
	console.log('usao u ispisi hotele u js');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> Promotional description</th><th>Grade</th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaHotel").append("<tr><td class=\"hoverName\" onclick=\"hotelProfil('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.opis+"</td><td > "+servis.ocena+"</td></tr>");
		});
	 $("#ispisiTabelu").append("</table>");
}
function carShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").show();
	$("#pozadinaHotel").hide();	
	 $("#ispisiTabelu").empty();
		
	
	$.ajax({
		method:'GET',
		url: "/api/rents/all",
		success: function(lista){
			if(lista == null){
				console.log('Nema servise')
			}else{
				ispisiAutoservise(lista);
				
			}
		}
	});
}
function ispisiAutoservise(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#ispisiTabelu").empty();
	 
	 $("#ispisiTabelu").append("<table class=\"table table-striped table-hover\" id=\"tabelaRent\" ><tr><th> Name </th><th> Promotional description</th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaRent").append("<tr><td class=\"hoverName\" onclick=\"visitCar('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.opis+"</td><td><button  class=\"btn btn-info\" onclick=\"izmeniRent('"+servis.id+"')\">Izmeni</button></td></tr>");
			
		});
	 $("#ispisiTabelu").append("</table>");
	 
	
}
function izmeniRent(id){
	window.location = "izmeniRent.html?id="+id;

}
$(document).on("mouseenter", ".hoverName",function(){
			
    $(this).css("color", "purple");
		
	
});
$(document).on("mouseleave", ".hoverName",function(){
	
    $(this).css("color", "black");
		
	
});
function visitCar(id){
	console.log('Usao u visitCar, dobio id: '+id);
	window.location="profileCar.html?id="+id;
}
function hotelProfil(id){
	console.log('usao u hotel profil');
	window.location = "profileHotel.html?id="+id;
}

function addPlane(){
	window.location = "newHotel.html";
}
function addHotel(){
	window.location = "newHotel.html";
	
	
}
function addCarHire(){
	window.location = "newRentACar.html";
	
	
}