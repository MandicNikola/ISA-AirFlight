function loadInformacije(){
	
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	$.ajax({
		  url : "/api/vozila/vratiVozilo/"+id,
		  type: 'get',
		  success: function(vozilo) {
			  iscrtajStranicu(vozilo);
			}
		});

	
	
}

function iscrtajStranicu(vozilo){
	
	$("#marka").val(vozilo.marka);
	$("#model").val(vozilo.model);
	$("#godiste").val(vozilo.godiste);
	$("#sedista").val(vozilo.sedista);
	$("#kategorija").val(vozilo.kategorija);
}


$(document).on('submit','.izmeni',function(e){
	e.preventDefault();	
	
	console.log('Pritisnuo izmeni');
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var idVozilo= niz[1]; 
	
	
	var newVehicle={
			id: idVozilo,
			marka : $('#marka').val(),
			model : $('#model').val(),
			godiste : $('#godiste').val(),
			sedista : $('#sedista').val(),
			kategorija : $('#kategorija').val()
}

	sendVehicle= JSON.stringify(newVehicle);			

	
	$.ajax({
		type : 'POST',
		url : "/api/vozila/izmeniAuto",
		contentType : "application/json",
		data: sendVehicle,
		dataType : 'json',
		success : function(pov) {
			if( pov == null){	
				alert('Naziv auta mora biti jedinstven');
			}else{
				//alert('Uspesno ste dodali filijalu');
				console.log('Nije null');
				pozoviProfil(pov.model);			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

});


function pozoviProfil(data){
	
	window.location="profileCar.html?id="+data;
}