/**
 * 
 */


function formToJSON() {
	return JSON.stringify({
		"naziv" : $('#name').val()		
	});
}

$(document).on('submit','.destinacija',function(e){
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	
	
	
	$.ajax({
		type : 'POST',
		url : "/api/kompanije/addDestination/"+idKompanija,
		contentType : 'application/json; charset=utf-8',
		data:formToJSON(),
		success : function(data) {
				if(data == "uspesno"){
					alert('dodavanje super');
					window.location="AirCompProfile?id="+idKompanija;
				}else{
					alert('dodavanje nije super');	
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
			   
		}
	});

});