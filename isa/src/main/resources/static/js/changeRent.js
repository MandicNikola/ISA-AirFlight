function loadInformacije(){
	
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	$.ajax({
		  url : "/api/rents/vratiRentId/"+id,
		 type: 'get',
		  success: function(rent) {
			  iscrtajStranicu(rent);
			}
	});


}
function iscrtajStranicu(rent){
	
	$("#naziv").val(rent.naziv);
	$("#adr").val(rent.adresa);
	$("#opis").val(rent.opis);
}

$(document).on('submit','.izmeni',function(e){
	console.log('Pritisnuo izmeni');
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var idRent= niz[1]; 
	
	
	var newRent={
			id: idRent,
			naziv: $("#naziv").val(),
			adresa : $("#adr").val(),
			opis : $("#opis").val()			
}

	sendRent= JSON.stringify(newRent);			

	
	$.ajax({
		type : 'POST',
		url : "/api/rents/izmena",
		contentType : "application/json",
		data: sendRent,
		dataType : 'json',
		success : function(pov) {
			if( pov == null){	
				alert('Vec postoji servis sa unetim nazivom');
			}else{
				console.log(pov);
				pozoviPromenu();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

});

function pozoviPromenu(){
	window.location="mainPage.html";

}