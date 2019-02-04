function dodajFilijale(){
	console.log('usao u dodaj filijale')
	var pom=window.location.search.substring(1);
	var idRent= pom.split('=')[1];
	
	$.ajax({
		method:'GET',
		url: "/api/rents/getFilijale/"+idRent,
		success: function(data){
			if(data == null){
				console.log('Nema filijala');
				upozorenje();
			}else if(data.length==0){
				console.log('Nema filijala');
				upozorenje();
			}else{
			    ispisiFilijale(data);
			}
		}
	});

	
}
function upozorenje(){
	$('#polje').hide();
	
	$('#upozorenje').empty();
	
	$('#upozorenje').append("<p>You have to add branch office of the rent-a-car first.</p><p><button type=\"button\" onclick=\"vratiSe()\" class=\"btn btn-link\">Go to add</button></p>");

	
}
function vratiSe(){
	var adresa = window.location.search.substring(1);
	console.log('adresa je '+adresa);
	var id = adresa.split('=')[1];
	window.location = "profileCar.html?id="+id;
	
}
function ispisiFilijale(skup){
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);

	$("#office").empty();
	$.each(lista, function(index, fil) {
		 $("#office").append("<option  value=\""+fil.id+"\">"+fil.grad+","+fil.ulica+"</option>");
	});
}
$(document).on('submit','.registracija',function(e){
			e.preventDefault();	
	
				var newcar={
								marka : $('#marka').val(),
								model : $('#model').val(),
								godiste : $('#godiste').val(),
								sedista :  $('#sedista').val(),
								kategorija : $('#kategorija').val()				
				}
				
				sendcar= JSON.stringify(newcar);			
				console.log('auto je ' + sendcar);
				 idFilijala=$('#office').val();
	
				 var pom=window.location.search.substring(1);
				var id= pom.split('=')[1];
				
				var salji=id+"="+idFilijala;
				
				$.ajax({
					type : 'POST',
					url : "/api/rents/poveziFilijalu/"+salji,
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
	});

function pozoviProfil(data){
	
	window.location="profileCar.html?id="+data.adresa;
}