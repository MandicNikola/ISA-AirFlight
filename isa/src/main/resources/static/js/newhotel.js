/**
 * 
 */



$(document).on('submit','.hotel',function(e){
	e.preventDefault();	
	
	let naziv = $('#naziv').val();
	let adresa = $('#adr').val();
	let grad = $('#grad').val();
	
	let ispravno = true;
	
	 $("#greskaNaziv").html('');
	  $("#greskaAdresa").html('');
	 
	if(!naziv){
		console.log('usao u neispravan naziv'+naziv);
		
		$("#greskaNaziv").html('Field name is required.').css('color','red');
		ispravno = false;
	}
	if(!adresa){
		console.log('usao u neispravnu adresu');
		$("#greskaAdresa").html('Field address is required.').css('color','red');
		ispravno = false;		
	}
	
	if(!grad){
		console.log('usao u neispravan grad');
		$("#greskaGrad").html('Field city is required.').css('color','red');
		ispravno = false;		
	}
	if(ispravno == true){
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/newhotel",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.naziv != null){
					console.log('uspjesno ste dodali hotel');					
					alert('dodavanje super');
					ucitajPocetnu();
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
		"grad":$('#grad').val()
	});
}
function ucitajPocetnu(){
	window.location = "mainPage.html";
}