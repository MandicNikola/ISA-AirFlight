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
				console.log('Nema servise')
				
			}else{
				ispisiHotele(lista);
				
			}
		}
	});
}

function ispisiHotele(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> Promotional description</th><th>Grade</th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaHotel").append("<tr><td >"+servis.naziv+"</td><td > "+servis.opis+"</td><td > "+servis.ocena+"</td></tr>");
			
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
	 $("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaRent\" ><tr><th> Name </th><th> Promotional description</th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaRent").append("<tr><td >"+servis.naziv+"</td><td > "+servis.opis+"</td></tr>");
			
		});
	 $("#ispisiTabelu").append("</table>");
}