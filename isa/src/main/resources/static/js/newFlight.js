/**
 * 
 */

function check()
{
	alert($('#vremePoletanja').val());

}

function load()
{
	
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	$.ajax(
			{
				type : 'GET',
				url: 'api/kompanije/airplanes/'+idKompanija,
				dataType : 'json',
				success : function(data)
				{
					if(data == null || data.length == 0)
					{
						alert('neuspesno!');
					}
					else
					{
						$.each(data,function(index,value)
								{
									$('#selectAirplane').append('<option value="'+value.id+'>'+value.naziv+'</option>');
								});
					}
					
				}
				
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
					alert('neuspesno dodavanje')
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



