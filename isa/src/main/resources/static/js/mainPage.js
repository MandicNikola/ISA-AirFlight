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
		url: "../restorani/vratiDostava",
		success: function(lista){
			if(lista == null){
				nemaPor();
				
			}else{
				ispisiDostavu(lista);
				
				
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
		url: "/api/rents/",
		success: function(lista){
			if(lista == null){
				console.log('Nema auta')
			}else{
				ispisiAuta(lista);
				
				
			}
		}
	});
}
function ispisiAuta(lista){
	
	
}