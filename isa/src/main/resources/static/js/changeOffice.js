function loadInformacije(){
	
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	$.ajax({
		  url : "/api/filijale/vratiFilijalu/"+id,
		  type: 'get',
		  success: function(filijala) {
			  iscrtajStranicu(filijala);
			}
		});


}
function iscrtajStranicu(filijala){
	console.log(filijala);
	$("#grad").val(filijala.grad);
	$("#ulica").val(filijala.ulica);
}

$(document).on('submit','.izmeni',function(e){
	e.preventDefault();
	console.log('Pritisnuo izmeni');
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var idFilijale= niz[1]; 
	
	
	var newoffice={
			id: idFilijale,
			grad : $('#grad').val(),
			ulica : $('#ulica').val()			
}

	sendoffice= JSON.stringify(newoffice);			

	
	$.ajax({
		type : 'POST',
		url : "/api/filijale/izmena",
		contentType : "application/json",
		data: sendoffice,
		dataType : 'json',
		success : function(pov) {
			if( pov.ulica == 'ne'){	
				alert('Vec postoji filijala sa datom adresom');
			}else{
				//alert('Uspesno ste dodali filijalu');
				console.log('Nije null');
				console.log(pov);
				pozoviProfil(pov.ulica);			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

});


function pozoviProfil(data){
	
	window.location="profileCar.html?id="+data;
}