/*
*IZVRSITI IZMENE KOJE SU MI POTERBNE ZA RAD
 * 
 */


function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#name').val()		
	});
}

$(document).on('submit','.destinacija',function(e){
	e.preventDefault();
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	
	
	
	$.ajax({
		type : 'POST',
		url : "/api/kompanije/addDestination/"+idKompanija,
		contentType : 'application/json; charset=utf-8',
		data:formToJSON(),
		success : function(data) {
				if(data == null){
					alert('neuspesno dodavanje');
				}else{
					alert('Dodavanje uspesno!');
					window.location = "AirCompProfile.html?id="+data;
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
			   
		}
	});

});