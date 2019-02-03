/**
 * 
 */

function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#nazivAvion').val(),
		"konfiguracija" : $('#konfiguracijaAvion').val(),
		"ekonomska" : $('#brEkonomska').val(),
		"prvaKlasa" : $('#brPrva').val(),
		"biznis" : $('#brBiznis').val(),
		
	});
}

$(document).on('submit','.airplane',function(e){
	e.preventDefault();
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	
	
	$.ajax({
		method : 'POST',
		url : "/api/avioni/addNewPlane/"+idKompanija,
		contentType : 'application/json',
		data:formToJSON(),
		success : function(data) {
				if(data == null){
					alert('dodavanje neuspesno');
				}else{
					alert('Dodavanje uspesno!');
					window.location = "AirCompProfile.html?id="+data;
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove aviokompanije");
			   
		}
	});

});
