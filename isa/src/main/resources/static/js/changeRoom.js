/**
 * 
 */
function onLoad(){
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var idRoom = adresa.split('-')[0];
	var idHotel = adresa.split('-')[1];
	
	$.ajax({
		  url : "/api/rooms/vratiSobu/"+idRoom,
		  type: 'get',
		  success: function(soba) {
			  popuniPolja(soba);
			}
		});


}
function popuniPolja(soba){
	$("#kreveti").val(soba.kapacitet);
	$("#sprat").val(soba.sprat);
	if(soba.balkon == 'da'){
		$("#balkon").prop('checked', true);
	}
	
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var idRoom = adresa.split('-')[0];
	var idHotel = adresa.split('-')[1];
	
	$.ajax({
		  url : "/api/hoteli/getKonf/"+adresa,
		  type: 'get',
		  success: function(pom) {
				  popuni(pom);
			  
			}
		});
}
function popuni(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	$.each(pom, function(index, data) {
		 $("#tip").append("<option  value=\""+data+"\">"+data+"</option>");	 	 
	 });
}

$(document).on('submit','.soba',function(e){
	e.preventDefault();	
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var idRoom = adresa.split('-')[0];
	var idHotel = adresa.split('-')[1];
	
	
	$.ajax({
		type : 'POST',
		url : "/api/rooms/izmjeniSobu/"+idRoom,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data == null){
					alert('greska pri izmejni sobe u hotelu');		
				}else{
						prikaziHotel(idHotel);
				}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri prikazu hotela");
			   
		}
	});

	
});

function formToJSON() {
	var rez = 'ne';
	if ($('#balkon').is(":checked")){
		rez = 'da';
	}
	return JSON.stringify({
		"tip" : $('#tip').val(),
		"sprat" : $('#sprat').val(),
		"kapacitet": $('#kreveti').val(),
		"balkon" : rez			
	});
}
function prikaziHotel(data){
	window.location = "profileHotel.html?id="+data;
}