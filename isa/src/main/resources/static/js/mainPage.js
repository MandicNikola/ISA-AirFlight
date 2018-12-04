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
	
	$.ajax({
		method:'GET',
		url: "/api/hotels/all",
		success: function(lista){
			if(lista == null){
				nemaPor();
				
			}else{
				
				
			}
		}
	});
}
function carShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").show();
	$("#pozadinaHotel").hide();
	
	
	
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