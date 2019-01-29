/**
 * 
 */

function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#nazivAvion').val()		
	});
}

$(document).on('submit','.airplane',function(e){
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	
	
	$.ajax({
		method : 'POST',
		url : "/api/avioni/addNewPlane/"+idKompanija,
		contentType : 'application/json',
		data:formToJSON(),
		success : function(data) {
				if(data == "uspesno"){
					alert('dodavanje super');
				}else{
					alert('dodavanje nije super');	
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove aviokompanije");
			   
		}
	});

});
