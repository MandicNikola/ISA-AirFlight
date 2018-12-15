$(document).on('submit','.registracija',function(e){
			e.preventDefault();	
	
				var newoffice={
								grad : $('#grad').val(),
								ulica : $('#ulica').val()			
				}
				
				sendoffice= JSON.stringify(newoffice);			
				console.log('auto je ' + sendoffice);
				 
				$.ajax({
					type : 'POST',
					url : "/api/filijale/registrovanje",
					contentType : "application/json",
					data: sendoffice,
					dataType : 'json',
					success : function(pov) {
						if( pov == null){	
						}else{
							alert('Uspesno ste dodali filijalu');
							poveziFilijalu(pov);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
	});

function poveziFilijalu(data){
	
	console.log('usao u poveziFilijalu');
	console.log('filijala je ' + data);
	var sendfil= JSON.stringify(data);			

	console.log('ispis je '+sendfil);

	var pom=window.location.search.substring(1);
	var id= pom.split('=')[1];
	
	$.ajax({
		type : 'POST',
		url : "/api/rents/postavifilijalu/"+id,
		contentType : "application/json",
		data: sendfil,
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