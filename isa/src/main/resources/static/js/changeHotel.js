/**
 * 
 */
function onLoad(){
	var adresa = window.location.search.substring(1);
	console.log('adresa je '+adresa);
	
		
	var id = adresa.split('=')[1];
	$.ajax({
		method:'GET',
		url: "/api/hoteli/findById/"+id,
		success: function(hotel){
			 popuniPolja(hotel);
		}
	});
	
}
function popuniPolja(hotel){
	$("#naziv").val(hotel.naziv);
	$("#adr").val(hotel.adresa);
	$("#opis").val(hotel.opis);	
	$("#grad").val(hotel.grad);	
	
}


$(document).on('submit','.hotel',function(e){
	e.preventDefault();	
	
		var adresa = window.location.search.substring(1);
		console.log('adresa je '+adresa);
		var id = adresa.split('=')[1];
		
		
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/changehotel/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.opis == 'naziv'){
					console.log('');					
				
					$("#greskaNaziv").html('A hotel with the same name exists').css('color','red');
					
				}else{
					ucitajPocetnu();
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
		}
	});
	
});
function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#naziv').val(),
		"adresa" : $('#adr').val(),
		"opis" : $('#opis').val(),	
		"grad": $('#grad').val()
	});
}
function ucitajPocetnu(){
	window.location = "mainPage.html#services";
}

