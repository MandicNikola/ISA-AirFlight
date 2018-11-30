/**
 * 
 */

function newHotel(forma){
	let naziv = $('#naziv').value;
	let adresa = $('#adr').value;
	let ispravno = true;
	
	 $("#greskaNaziv").html('');
	  $("#greskaAdresa").html('');
	 
	if(!naziv){
		$("#greskaNaziv").html('Naziv hotela je obavezan.').css('color':'red');
		ispravno = false;
	}
	if(!adresa){
		$("#greskaAdresa").html('Adresa hotela je obavena.').css('color':'red');
		ispravno = false;		
	}
	return ispravno;
}


$(document).on('submit','.hotel',function(e){
	e.preventDefault();	
	
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/newhotel",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data != null){
					console.log('uspjesno ste dodali hotel');					
					alert('dodavanje super');
				}else{
					alert('dodavanje nije super');
					
					
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
	});
}
