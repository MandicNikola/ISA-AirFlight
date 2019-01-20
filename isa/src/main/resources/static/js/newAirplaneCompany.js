/**
 * 
 */


$(document).on('submit','.airplane',function(e){
	e.preventDefault();	
	
	let naziv = $('#naziv').val();
	let adresa = $('#adr').val();
	let ispravno = true;
	
	 $("#greskaNaziv").html('');
	  $("#greskaAdresa").html('');
	 
	if(!naziv){
		$("#greskaNaziv").html('Naziv servisa je obavezan.').css('color','red');
		ispravno = false;
	}
	if(!adresa){
		console.log('usao u neispravnu adresu');
		$("#greskaAdresa").html('Adresa servisa je obavezna.').css('color','red');
		ispravno = false;		
	}
	
	if(ispravno == true){
	$.ajax({
		type : 'POST',
		url : "/api/kompanije/novaAvioKompanija",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.naziv != null){
					alert('dodavanje super');
					ucitajPocetnu();
				}else{
					alert('dodavanje nije super');	
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove aviokompanije");
			   
		}
	});
	}
});
function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#naziv').val(),
		"adresa" : $('#adr').val(),
		"opis" : $('#opis').val(),			
	});
}
function ucitajPocetnu(){
	window.location = "mainPage.html";
}