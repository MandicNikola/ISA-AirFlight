/**
 * 
 */

function onLoad(){
	
	$("#sobe").hide();
	$("#cijene").hide();
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
	
}

function ispisiSobe(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#sobe").empty();
	 $("#sobe").show();
	 $("#sobe").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Beds</th><th>Price per night</th><th></th><th></th></tr>");
		
		$.each(pom, function(index, data) {
			$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.kreveti+"</td><td>"+data.cijena+"</td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
			
		});
		
	 $("#sobe").append("</table>");
	 
}

function changeRoom(sobaID){
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	
	window.location = "changeRoom.html?"+sobaID+"-"+id;
	
	
}
function deleteRoom(sobaID){
	
	$.ajax({
		type : 'POST',
		url : "/api/rooms/obrisiSobu/"+sobaID,
		success : function(data) {
				console.log('obrisana soba');
				listaSoba();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	
	
}

function changePrice(roomID,roomCijena){
	console.log("dosao u room id : "+roomID);
	 $("#tabovi").hide();
	 $("#sobe").hide();
	 $("#izmjena").empty();
	 $("#izmjena").show();
			 
	 $("#izmjena").append("<div class=\"container\"><h3>New price:</h3>");
		$("#izmjena").append("<input class=\"form-control\" type = \"number\"  id=\"newPrice\" value=\""+roomCijena+"\">"); 	
		$("#izmjena").append("<button id=\"buttonID\" class=\"btn btn-light\" onclick=\"changeR("+roomID+")\"  >Change</button>");
	$("#izmjena").append("</div>");
}
function changeR(roomID){
		
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	console.log(id);
	var kat =$('#newPrice').val();
	var pom = roomID+"-"+kat+"-"+id;

	$.ajax({
		type : 'GET',
		url : "/api/hoteli/changePrice/"+pom,
		success : function(data) {
					alert('usao ovdje');
					window.location = "profileHotel.html?id="+id;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
		}
	});
	
}
function prikaziProfil(id){
	console.log(id);
	window.location = "profileHotel.html?id="+id;

}
function ispisiSobeBezAdima(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#sobe").empty();
	 $("#sobe").show();
	 $("#sobe").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Beds</th><th>Price for night</th></tr>");
		
		$.each(pom, function(index, data) {
			$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.kreveti+"</td><td>"+data.cijena+"</td></tr>");
			
		});
		
	 $("#sobe").append("</table>");
	
	
}

function addRoom(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "NewRoom.html?id="+id;
		
}
function addConfiguration(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	$("#tabovi").hide();
	$("#sobe").hide();
	$("#informacije").hide();
	$("#cijene").hide();
	
	$("#ispisiTabelu").show();
	$("#ispisiTabelu").empty();
	$("#ispisiTabelu").append("<div class=\"container\"><h3>New configuration</h3><form method=\"post\" class=\"konfiguracija\"  id = \"formaKat\" >");
		$("#formaKat").append("<div class=\"form-group\">");
		$("#formaKat").append("<input  type = \"text\" class=\"form-control\" id=\"katNaziv\" placeholder=\"Configuration name\">"); 	
		$("#formaKat").append("</div><button type=\"submit\" class=\"btn btn-default\">Add</button></form>");
	$("#ispisiTabelu").append("</div>");
}

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
					resetProfil();
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
		}
	});
	
	
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
function resetProfil(){
	$("#tabovi").show();
	$("#informacije").show();
	
	$("#ispisiTabelu").hide();
	
}
function ucitajPocetnu(){
	window.location = "mainPage.html";
}
$(document).ready(function(){
	$("#sobe").hide();
	$("#cijene").hide();
 	
    $("#rooms").click(function(){
    	listaSoba();
		$("#informacije").hide();
		$("#ispisiTabelu").hide();
		$("#cijene").hide();
	 	
    });
    $("#info").click(function(){
    	$("#informacije").show();
		$("#ispisiTabelu").hide();
		$("#sobe").hide();
		$("#cijene").hide();
	 	
    });
    
    $("#price").click(function(){
    	console.log('dosao u price');
    	showPrices();
    	$("#informacije").hide();
    	
		$("#ispisiTabelu").hide();
		$("#sobe").hide();
		 
		$("#cijene").show();
	 	
    });
});

function showPrices(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getUsluge/"+id,
		success: function(lista){
			if(lista==null){
				$("#cijeneDodatne").empty();
				console.log('Nema usluga');
			}else if(lista.length == 0){
				$("#cijeneDodatne").empty();
				console.log('Prazne usluga');
			}else{
				console.log('Ima usluga ');
				ispisiDodatne(lista);
			}
		}
	});
	
}

function ispisiDodatne(data){
	
		console.log('usao u ispisiCenovnik dodatnih usluga');
		
		$("#cijeneDodatne").empty();
		var lista = data == null ? [] : (data instanceof Array ? data : [ data ]);
			
		$("#cijeneDodatne").append("<table class=\"table table-hover\" id=\"tblDodatne\" ><tr><th>Service</th><th>Price</th><th></th><th>Discount</th><th></th></tr>");
		console.log(lista.length);
		
		$.each(lista, function(index, usluga) {
			
				console.log(usluga.konfiguracija);
			if(usluga.konfiguracija == 'da'){
				console.log('dosao u dodavanje za uslugu');
				$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td ><input class=\"form-control\" type = \"number\"  id=\""+usluga.id+"\" value=\""+usluga.cena+"\"></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"promjeniDodatnu("+usluga.id+")\">Change</button></td><td ><input class=\"form-control\" type = \"number\"  id=\"pop"+usluga.id+"\" value=\""+usluga.popust+"\"></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"promjeniPopust("+usluga.id+")\">Change</button></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"izbrisiDodatnu("+usluga.id+")\">Delete</button></td>");

			}else{
				$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td ><input class=\"form-control\" type = \"number\"  id=\""+usluga.id+"\" value=\""+usluga.cena+"\"></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"promjeniDodatnu("+usluga.id+")\">Change</button></td><td></td><td></td></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"izbrisiDodatnu("+usluga.id+")\">Delete</button></td>");
					
			}
			$("#tblDodatne").append("</tr>");
		});
	    $("#cijeneDodatne").append("</table>");
}

function izbrisiDodatnu(idUsluga){
	
	$.ajax({
		type : 'POST',
		url : "/api/usluge/izbrisidodatnu/"+idUsluga,
		success : function(data) {
					showPrices();
					alert('izbrisao');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri brisanju dodatne usluge");
			   
		}
	});

}

function promjeniDodatnu(idUsluga){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	var vr =$("#"+idUsluga).val();
	var pom = idUsluga+"-"+vr+"-"+id;

	$.ajax({
		type : 'POST',
		url : "/api/hoteli/promjenidodatnu/"+pom,
		success : function(data) {
					alert('usao ovdje');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
		}
	});

}

function promjeniPopust(idUsluga){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	var vr =$("#pop"+idUsluga).val();
	var pom = idUsluga+"-"+vr+"-"+id;

	$.ajax({
		type : 'POST',
		url : "/api/hoteli/promjeniPopust/"+pom,
		success : function(data) {
					alert('izmjenio Popust');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri izmjeni popusta");
			   
		}
	});

}
function ispisiDodatneNijeAdmin(data){
	console.log('usao u ispisiCenovnik dodatnih usluga');
	
	$("#cijeneDodatne").empty();
	var lista = data == null ? [] : (data instanceof Array ? data : [ data ]);
		
	$("#cijeneDodatne").append("<table class=\"table table-hover\" id=\"tblDodatne\" ><tr><th>Service</th><th>Price</th></tr>");
	console.log(lista.length);
	
	$.each(lista, function(index, usluga) {
		
		$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td > "+usluga.cena+"</td></tr>");
	});
    $("#cijeneDodatne").append("</table>");

	
}
function listaSoba(){
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
function addPopust(){
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getDodatneUsluge/"+id,
		success: function(lista){
			if(lista.length ==0){
				console.log('Nema dodatnih usluga');
				$("#ispisiTabelu").empty();
			}else{
				
				console.log('postoje dodatne usluge');
				formaPopusti(lista);
			}
		}
	});
	
	
}
function formaPopusti(lista){
	 var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 
		$("#sobe").hide();
		$("#informacije").hide();
		$("#cijene").hide();
		
		$("#ispisiTabelu").show();
		$("#ispisiTabelu").empty();
		$("#ispisiTabelu").append("<div class=\"container\"><h3>Add discount</h3><select class=\"form-control\" id=\"dodatne\">");
			
	 $.each(pom, function(index, data) {
		 	
		 $("#dodatne").append("<option  value=\""+data.id+"\">"+data.naziv+"</option>");	 
		 
	 });
		$("#ispisiTabelu").append("</select><div class=\"container\"><input  type = \"number\"  min=\"1\" max=\"99\" class=\"form-control\" id=\"popust\" placeholder=\"discount in %\"></div><div><button id=\"dugmePopust\"  class=\"btn btn-info\" onclick=\"popustFunkcija()\"  >Change</button></div></div>");

}
function popustFunkcija(){
	
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	var vr =$('#popust').val();
	var idUsluga =$('#dodatne').val();
	console.log("izbor je "+idUsluga);
	
	var pom = idUsluga+"-"+vr+"-"+id;

	$.ajax({
		type : 'POST',
		url : "/api/hoteli/dodajPopust/"+pom,
		success : function(data) {
					alert('usao ovdje');
					resetProfil();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog hotela");
			   
		}
	});

	
}
function addUsluga(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	$("#tabovi").hide();
	$("#sobe").hide();
	$("#informacije").hide();
	$("#cijene").hide();
	
	$("#ispisiTabelu").show();
	$("#ispisiTabelu").empty();
	$("#ispisiTabelu").append("<div class=\"container\"><h3>New additional services</h3><form method=\"post\" class=\"dodatnausluga\"  id = \"formaUsluga\" >");
		$("#formaUsluga").append("<div class=\"form-group\">");
		$("#formaUsluga").append("<input  type = \"text\" class=\"form-control\" id=\"uslugaNaziv\" placeholder=\"Additional service name\">"); 	
		$("#formaUsluga").append("<input  type = \"number\" class=\"form-control\" id=\"uslugaCijena\" placeholder=\"Additional service price\">"); 	
		$("#formaUsluga").append("</div><button type=\"submit\" class=\"btn btn-default\">Add</button></form>");
	$("#ispisiTabelu").append("</div>");
}
$(document).on('submit','.dodatnausluga',function(e){
	e.preventDefault();	
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/dodatnausluga/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:dodUsluga(),
		success : function(data) {
				if(data.opis == "Usluga"){
					alert('Postoji izabrana dodatna usluga');					
					resetProfil();
				}else{
					resetProfil();
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove kat");
			   
		}
	});
	
});

function dodUsluga() {
	
	var kat = JSON.stringify({
		"naziv":$('#uslugaNaziv').val(),
		"cena":$('#uslugaCijena').val()
	});
	return kat;
}