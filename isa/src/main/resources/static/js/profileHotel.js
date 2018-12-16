/**
 * 
 */

function onLoad(){
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	$.ajax({
		method:'GET',
		url: "/api/hoteli/findById/"+id,
		success: function(hotel){
			if(hotel == null){
				console.log('Nema servise')
			}else{
				ispisiProfilHotela(hotel);
				
			}
		}
	});
}
function ispisiProfilHotela(hotel){
	console.log("id "+hotel.id);
	$("#naziv").text('Welcome to '+hotel.naziv);
	preuzmiSobe(hotel);
}
function preuzmiSobe(hotel){
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getRooms/"+id,
		success: function(lista){
			if(lista == null){
				console.log('Nema soba')
			}else{
				ispisiSobe(lista);
				
			}
		}
	});
	
}
function ispisiSobe(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#ispisiTabelu").empty();
	 
	 $("#ispisiTabelu").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Beds</th><th>Price for night</th></tr>");
		
		$.each(pom, function(index, data) {
			$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.kreveti+"</td><td>"+data.cijena+"</td></tr>");
			
		});
	 $("#ispisiTabelu").append("</table>");
}

function addRoom(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "NewRoom.html?id="+id;
		
}
function addConfiguration(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	$("#ispisiTabelu").empty();
	 
	 $("#ispisiTabelu").append("<div class=\"container\"><h3>New configuration</h3><form method=\"post\" class=\"konfiguracija\" id = \"formaKat\" >");
	 $("#formaKat").append("<div class=\"form-group\">");
	 $("#formaKat").append("<input  type = \"text\" class=\"form-control\" id=\"katNaziv\" placeholder=\"Configuration name\">"); 	
	 $("#formaKat").append("</div><button type=\"submit\" class=\"btn btn-default\">Add</button></form>");
	 $("#ispisiTabelu").append("</div>");
}

$(document).on('submit','.konfiguracija',function(e){
	e.preventDefault();	
	$.ajax({
		type : 'POST',
		url : "/api/kategorije/kat",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.naziv != "null"){
					alert('Postoji izabrana kategorija');
					
				}else{
					alert('dodavanje super');
					dodajHoteluKategoriju(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove kat");
			   
		}
	});
	
});
function formToJSON() {
	console.log('dosao u form to JSON');
	var kat = JSON.stringify({
		"naziv":$('#katNaziv').val()
	});
	console.log(kat);
	return kat;
}
function dodajHoteluKategoriju(data){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/sacuvajKat/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.naziv == null){
					alert('Greska pri dodavanju kategorije hotelu');
					
				}else{
					alert('dodavanje super');
					formaZaCijene(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
		}
	});
	
	
}
function formaZaCijene(){
	
}
function ucitajPocetnu(){
	window.location = "mainPage.html";
}