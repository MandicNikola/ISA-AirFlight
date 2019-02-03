/**
 * 
 */

var destinacije;



function load()
{
	
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	$('#povratak').hide();
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
							success : function(data1)
							{
								if(data1 == null || data.length1 == 0)
								{
									alert('neuspesno!');
									window.location = "AirCompProfile.html?id="+idKompanija;
								}
								else
								{
									destinacije = data1;
									var text = "";
									$.each(data1,function(index,value)
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
/*
 * za presedanja koristim saljem niz presedanja ukoliko postoje
 * jos mi ostaje da odradim za datum povratka
 */
function formToJSON(id,presedanja,datPovratka)
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
		"lokacijaSletanja" : $('#selectSletanje').val(),
		"tip" : $('#selectTip').val(),
		"presedanja" : presedanja,
		"datumPovratka" : datPovratka
		
	});
}
	

$(document).ready(function (){
	function check()
	{
		alert($('#vremePoletanja').val());
	
	}
	
	$("#brojPresedanja").on("change keyup", function() {
	    
		var valueNum = $(this).val();
		$('#presedanjaIzbor').empty();
		if(valueNum < 0)
			valueNum = 0;
		
		if(valueNum > 0)
		{
			var text = "";
			var i;
			for(i = 0; i < valueNum; i++)
			{
				text += '<div class="form-group">';		
				text +=	 '<i class="glyphicon glyphicon-bed"></i> <label for="selectPresedanje'+i+'">Presedanje :</label>';
				text += '<select class="form-control" id="selectPresedanje'+i+'">';
				$.each(destinacije,function(index,value)
						{
							text+='<option value="'+value.id+'">'+value.naziv+'</option>';					
						});
				text +=	 '</select><div id="greskaDatumP"></div>';
				text += '</div>';
			}
			
			$('#presedanjaIzbor').append(text);			
		}		
	});
	
	$("#selectTip").on("change", function() {
	    
		var valueNum = $(this).val();
		$('#presedanjaIzbor').empty();
		if(valueNum == "round-trip")
		{
			$('#povratak').show();
		}
		else
		{
			$('#povratak').hide();
		}
			
	});
	
		

});

$(document).on('submit','.flight',function(e){
	e.preventDefault();
	var adresa = window.location.search.substring(1);
	var idKompanija = adresa.split('=')[1];
	
	var valueNum = $("#brojPresedanja").val();
	
	var presedanja = [];
	if(valueNum > 0)
	{
		var i;
		for(i = 0; i < valueNum; i++)
		{
			presedanja.push($('#selectPresedanje'+i).val());
		}
	}
	
	var tipSletanja = $('#selectTip').val();
	var datumPovratka;
	if(tipSletanja == "multi-city")
	{
		datumPovratka = $('#datumPovratka').val();
	}
	else
	{
		datumPovratka = "nema";
	}
	alert(formToJSON(idKompanija,presedanja,datumPovratka));
	$.ajax({
		type : 'POST',
		url : "api/kompanije/addFlight",
		contentType : "application/json",
		data : formToJSON(idKompanija,presedanja,datumPovratka),
		success : function(data)
		{
			alert("uspesno dodavanje !");
			window.location = "AirCompProfile.html?id="+data;
		}
		
	});
	
	
	
	
});

