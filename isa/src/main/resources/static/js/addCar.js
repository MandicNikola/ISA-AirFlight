
$(document).on('submit','.registracija',function(e){
			e.preventDefault();	
	
				var newcar={
								naziv : $('#ime').val(),
								marka : $('#marka').val(),
								model : $('#model').val(),
								godiste : $('#godiste').val(),
								sedista :  $('#sedista').val(),
								kategorija : $('#kategorija').val()				
				}
				
				sendcar= JSON.stringify(newcar);			
				console.log('auto je ' + sendcar);
				 
				$.ajax({
					type : 'POST',
					url : "/api/vozila/registrovanje",
					contentType : "application/json",
					data: sendcar,
					dataType : 'json',
					success : function(pov) {
						if( pov.verifikovan == "null"){	
							 alert("Naziv vozila mora biti jedinstveno.");
						}else if(pov.kategorija == 'marka'){
							alert('Unesite pravilno marku vozila');

						}else if(pov.kategorija == 'model'){
							alert('Unesite pravilno model vozila');

						}else if(pov.kategorija == 'naziv'){
							alert('Naziv vozila mora biti jedinstven.');

						}else{
							alert('Uspesno ste se registrovali');
							poveziCar(pov);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
	});

function poveziCar(data){
	
	console.log('usao u poveziCar');
	console.log('auto je ' + data);
	var sendcar= JSON.stringify(data);			

	console.log('ispis je '+sendcar);

	var pom=window.location.search.substring(1);
	var id= pom.split('=')[1];
	
	$.ajax({
		type : 'POST',
		url : "/api/rents/postavivozilo/"+id,
		contentType : "application/json",
		data: sendcar,
		dataType : 'json',
		success : function(ret) {
			if( ret == null){	
				 alert("Greska");
			}else{

				pozoviProfil(ret);

			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});	
	
	
}
function pozoviProfil(data){
	
	window.location="profileCar.html?id="+data.adresa;
}