/**
 * 
 */
$(document).on('submit','.rentacar',function(e){
	e.preventDefault();	
	
	let naziv = $('#naziv').val();
	let adresa = $('#adr').val();
	let ispravno = true;
	
	 $("#greskaNaziv").html('');
	  $("#greskaAdresa").html('');
	 
	if(!naziv){
		console.log('usao u neispravan naziv'+naziv);
		
		$("#greskaNaziv").html('Naziv rent a car servisa je obavezan.').css('color','red');
		ispravno = false;
	}
	if(!adresa){
		console.log('usao u neispravnu adresu');
		$("#greskaAdresa").html('Adresa rent a car servisa je obavezna.').css('color','red');
		ispravno = false;		
	}
	
	if(ispravno == true){
	$.ajax({
		type : 'POST',
		url : "/api/rents/newrentacar",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.naziv != null){
					console.log('uspjesno ste dodali rent a car servis');					
					alert('dodavanje super');
				}else{
					alert('dodavanje nije super');	
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
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
