/**
 * 
 */

function check()
{
	alert($('#vremePoletanja').val());

}
function formToJSON(id)
{
	
	return JSON.stringify({
		"idAviona" : $('#selectAirplane').val(),
		"idKompanije" : id,
		"datumPoletanja" : $('#datumPoletanja').val(),
		"datumSletanja" : $('#datumSletanja').val(),
		"vremePoletanja" : $('#vremePoletanja').val(),
		"vremeSletanja" : $('#vremeSletanja').val(),
		"duzina" : $('#duzinaPutovanja').val(),
		"cena" : $('#cenaPutovanja').val(),
		"lokacijaPoletanja" : $('#selectPoletanje').val(),
		"lokacijaSletanja" : $('#selectSletanje').val()
		
	});
}





var destinacije;

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
						window.location = "AirCompProfile.html?id="+idKompanija;
					}
					else
					{
						alert(data);
						var text = "";
						$.each(data,function(index,value)
								{
									text += '<option value="'+value.id+'">'+value.naziv+'</option>';
								});
						$('#selectAirplane').html(text);
						
						$.ajax
						({
							type : 'GET',
							url: 'api/kompanije/getDestinations/'+idKompanija,
							dataType : 'json',
							success : function(data)
							{
								if(data == null || data.length == 0)
								{
									alert('neuspesno!');
									window.location = "AirCompProfile.html?id="+idKompanija;
								}
								else
								{
									destinacije = data;
									var text = "";
									$.each(data,function(index,value)
											{
												text += '<option value="'+value.id+'">'+value.naziv+'</option>';
												alert(text);
											});
									$('#selectPoletanje').html(text);
									$('#selectSletanje').html(text);				
								}
							}
						});
					}
				}
			});


}

$(document).on('submit','.flight',function(e){
	e.preventDefault();
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	

});



