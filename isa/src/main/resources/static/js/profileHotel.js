/**
 * 
 */

function onLoad(){
	$("#konfig").hide();
	$("#sobe").hide();
	$("#cijene").hide();
	$("#divPopust").hide();
	$("#rezervacije").hide();
	$("#adminStrana").hide();
	$("#promjenaLozinke").hide();
	$("#dodajPopust").hide();
 	
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	$.ajax({
		method:'GET',
		url: "/api/hoteli/findById/"+id,
		success: function(hotel){
			if(hotel == null){
				console.log('Nema servise');
			}else{
				ispisiProfilHotela(hotel);
				
			}
		}
	});
}
function ispisiProfilHotela(hotel){
	console.log("id "+hotel.id);
	var adr = hotel.adresa;
	$("#naziv").text('Welcome to '+hotel.naziv);
	$("#opis").text(hotel.opis);
	$("#adresa").append(hotel.adresa);
	var adresa=	adr.replace(" ", "%20");
    
	$("#adresa").append("<div class=\"mapouter\"><div class=\"gmap_canvas\"><iframe width=\"600\" height=\"500\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q="+adresa+"&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe><a href=\"https://www.embedgooglemap.net\">embedgooglemap.net</a></div><style>.mapouter{text-align:right;height:500px;width:600px;}.gmap_canvas {overflow:hidden;background:none!important;height:500px;width:600px;}</style></div>")
	
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	$.ajax({
		method:'GET',
		url: "/api/hoteli/vratiTipoveSoba/"+id,
		success: function(data){
			if(data == null){
				console.log('Nema soba');
			}else{
				ispisiTipove(data);
				
			}
		}
	});
}

function ispisiSobe(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#sobe").empty();
	 $("#sobe").show();
		
	 $("#sobe").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Price per night</th><th></th><th></th><th></th></tr>");
		
		$.each(pom, function(index, data) {
			if(data.brojRezervacija == 0){
				$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
			}else{
				console.log('dosao ovdje');
				$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" disabled = \"disabled\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" disabled = \"disabled\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
				
			}
			
		});
		
	 $("#sobe").append("</table>");
	 
}
//dodaje u select tipove soba
function ispisiTipove(list){
	
	console.log('Usao u popuni select');
	
	var lista = list == null ? [] : (list instanceof Array ? list : [ list ]);
	 $.each(lista, function(index, data) {
		 
		 $("#typeRoom").append("<option  value=\""+data+"\">"+data+"</option>");
		 
	 });
	
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
	 $("#konfig").hide();
     $("#promjenaLozinke").hide();
	
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

function ispisiAdmine(){
	 $.ajax({
			method:'GET',
			url: "/api/korisnici/getUsersForSistem",
			success: function(lista){
				if(lista == null){
					console.log('Nema admina')
				}else if(lista.length==0){
					console.log('Nema admina')
				}else{
					izborAdmina(lista);
					
				}
			}
		});

	
}

function izborAdmina(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	console.log('dosao u izbor admina')
	$("#adminSelect").empty();
	 $.each(pom, function(index, data) {
		 	
		 $("#adminSelect").append("<option value=\""+data.id+"\" >"+data.ime+" "+ data.prezime+"</option>");	 
		 
	 });
	adminiHotela();
}

function izmjeniAdmineHotela(){
	var idUser =$('#adminSelect').val();
	console.log('dosao u izmjeni adminaHotela '+idUser);
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	var pomocna = idUser + "-" + id;
	 $.ajax({
			method:'POST',
			url: "/api/korisnici/newAdminHotel/"+pomocna,
			success: function(lista){
				console.log('izmjenio');
				ispisiAdmine();
				//adminSistem();
			}
		});
}
function adminiHotela(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	 $.ajax({
			method:'GET',
			url: "/api/korisnici/getAdminsHotel/"+id,
			success: function(lista){
				if(lista == null){
					nemaAdmina();
					console.log('Nema admina');
				}else if(lista.length==0){
					nemaAdmina();
					console.log('Nema admina');
				}else{
					ispisiAdmineHotela(lista);
					
				}
			}
		});
	
}
function nemaAdmina(){
	$("#adminDiv").empty();
	 $("#adminDiv").append("<div><h3 id = \"h2Ad\">No registered administrators</h3></div>");
	
}
function ispisiAdmineHotela(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	console.log('dosao u ispisi adminaHotela')

	$("#adminDiv").empty();
	$("#adminDiv").append("<table class=\"table table-striped\" id=\"tabelaAdmini\" ><tr><th> Name </th><th> Surname</th><th></th></tr>");
		$.each(pom, function(index, servis) {
			$("#tabelaAdmini").append("<tr><td>"+servis.ime+"</td><td>"+servis.prezime+"</td><td><button  class=\"btn btn-light\" onclick=\"removeAdmin('"+servis.id+"')\">Remove</button></td><td></tr>");
		});
	 $("#adminDiv").append("</table>");

}
function removeAdmin(id){
	 $.ajax({
			method:'POST',
			url: "/api/korisnici/removeAdminHotel/"+id,
			success: function(lista){
				console.log('obrisao');
				ispisiAdmine();
				
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
	 $("#sobe").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Price for night</th></tr>");
		
		$.each(pom, function(index, data) {
			$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td></tr>");			
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
	$("#divPopust").hide();
	$("#konfig").hide();
	$("#rezervacije").hide();
	$("#promjenaLozinke").hide();

	
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
function resetujGreske(){
	$("#errorKraj").text("");
	$("#errorPocetak").text("");
	$("#errorSobe").text("");
	$("#errorLjudi").text("");	
	$("#checkin").val("");	
	$("#checkout").val("");	
	$("#brojSoba").val("");	
	$("#brojLjudi").val("");	
}
$(document).ready(function(){
	$("#sobe").hide();
	$("#cijene").hide();
	$("#divPopust").hide();
	$("#konfig").hide();
	$("#rezervacije").hide();
	$("#adminStrana").hide();
	$("#promjenaLozinke").hide();
	$("#dodajPopust").hide();
 	


    $("#rooms").click(function(){
    	listaSoba();
    	$("#informacije").hide();
		$("#ispisiTabelu").hide();
		$("#cijene").hide();
		$("#divPopust").hide();		
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#sobe").show();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
	 	
			
    });
    $("#info").click(function(){
    	$("#informacije").show();
		$("#ispisiTabelu").hide();
		$("#sobe").hide();
		$("#cijene").hide();
		$("#divPopust").hide();
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
	 	
		
    });
    
    $("#reservation").click(function(){
    	resetujGreske();
		
    	$("#informacije").hide();
		$("#ispisiTabelu").hide();
		$("#sobe").hide();
		$("#cijene").hide();
		$("#divPopust").hide();
		$("#konfig").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
	 	
		$("#rezervacije").show();
		$("#korak").empty();
		$("#korakDodatne").empty();
		$("#reserveHotel").show();
		$("#adminStrana").hide();
		
    });
    $("#config").click(function(){
    	ispisiKonfiguracije();
    	$("#informacije").hide();
		$("#ispisiTabelu").hide();
		$("#sobe").hide();
		$("#promjenaLozinke").hide();
		$("#divPopust").hide();
		$("#cijene").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#dodajPopust").hide();
	 	
    });
    
    $("#admini").click(function(){
    	ispisiAdmine();
		$("#informacije").hide();
		$("#ispisiTabelu").hide();
		$("#cijene").hide();
		$("#divPopust").hide();
		$("#konfig").hide();
		$("#sobe").hide();
		$("#rezervacije").hide();
		$("#adminStrana").show();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
	 	
    });  	
    $("#price").click(function(){
    	console.log('dosao u price');
    	showPrices();
    	$("#informacije").hide();
		$("#ispisiTabelu").hide();
		$("#sobe").hide(); 
		$("#cijene").show();
		$("#divPopust").hide();
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
	 	
    });
    $("#sistemPopust").click(function(){
    	console.log('dosao u popust');
    	showRoomsForDiscounts();
    	$("#informacije").hide();
    	$("#divPopust").show();
		$("#ispisiTabelu").hide();
		$("#sobe").hide(); 
		$("#cijene").hide();
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
	 	
    });

});
function ispisiKonfiguracije(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getKonfiguracije/"+id,
		success: function(lista){
			if(lista.length == 0){
				 $("#konfig").empty();
				console.log('nema usluga');
			}else{
				console.log('Ima usluga ');
				ispisiKonf(lista);
			}
		}
	});
	
}
function ispisiKonf(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#konfig").empty();
	 $("#konfig").show();
	 $("#konfig").append("<table class=\"table table-hover\" id=\"tabelaKonfig\" ><tr><th>Name </th><th></th></tr>");
		
		$.each(pom, function(index, data) {
			console.log(data.id);
			$("#tabelaKonfig").append("<tr><td class=\"hoverName\">"+data.naziv+"</td><td><button type=\"button\" onclick=\"deleteKonf("+data.id+")\" class=\"btn btn-light\">Delete</button></td></tr>");
			
		});
		
	 $("#konfig").append("</table>");
	
}
function deleteKonf(id){
	console.log(id);
	$.ajax({
		type : 'POST',
		url : "/api/kategorije/obrisiKat/"+id,
		success : function(data) {
				console.log('obrisana konf');
				ispisiKonfiguracije();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	
}
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
	    console.log("Zavrsio sa tabelom dodatnih");
	    dodajDatum();
	    
}
function dodajDatum(){
	console.log("Dodaj datum");
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	$.ajax({
		type : 'GET',
		url : "/api/hoteli/findCijenovnik/"+id,
		success : function(data) {
			
			if(data==null){
				console.log('Nema usluga');
			}else if(data.length == 0){
				console.log('Nema usluga');
			}else{
				
					ispisiDatum(data);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri preuzimanju datuma");
			   
		}
	});	
	
}
function ispisiDatum(data){
	var pom=JSON.stringify(data);
	console.log(pom);
	console.log('datum je  '+data.datum_primene);
	$("#cijene").append("<p><i class=\"glyphicon glyphicon-calendar\"> </i> Effective date : "+data.datum_primene+"</p>");

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
		$("#konfig").hide();
		$("#divPopust").hide();
		
		
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
	$("#konfig").hide();
	$("#divPopust").hide();
	
	$("#promjenaLozinke").hide();

	
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
//ispisuje ponudu za pretrazene dane
function izlistajPonudu(){
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	var ispravno = true;
	var pocetak=$('#checkin').val();
	var kraj=$('#checkout').val();
	var osobe=$('#brojLjudi').val();
	var sobe=$('#brojSoba').val();
	var today = new Date().toISOString().split('T')[0];
	console.log(today);
	console.log(pocetak);
	
	$("#errorKraj").text("");
	$("#errorPocetak").text("");
	$("#errorSobe").text("");
	$("#errorLjudi").text("");
	
	if(pocetak == ""){
		ispravno = false;
		$("#errorPocetak").text(" Fill out this field").css('color', 'red');
	}else if(pocetak < today){
		$("#errorPocetak").text("You can not select the date that passed").css('color', 'red');
		ispravno=false;
		
	}
	if(kraj == ""){
		ispravno=false;
		$("#errorKraj").text(" Fill out this field").css('color', 'red');
	}
	var date1 = Date.parse(pocetak);
	
	var date2 = Date.parse(kraj);
	
	if (date1 > date2) {
		$("#errorKraj").text("Check out date must be greater than check in date").css('color', 'red');
		ispravno=false;
	}
	
	if(osobe == ""){
		ispravno = false;
		$("#errorLjudi").text(" Fill out this field").css('color', 'red');
	}
	if(sobe ==""){
		ispravno = false;
		$("#errorSobe").text(" Fill out this field").css('color', 'red');
		
	}
	if(ispravno){
	
	
	$("#reserveHotel").hide();
	$("#korak").empty();
	$("#korak").append("<p><h2>Offers </h2></p>");
	$("#korak").append("<p>FROM <span id=\"pocetak\">"+pocetak+"</span>TO <span id=\"kraj\">"+kraj+"</span></p>");
	$("#korak").append("<p>FOR <span id=\"osobe\">"+osobe+"</span>  passengers</p>");
	
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/vratiPonude/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:preuzmiPodatke(),
		success : function(data) {
				
				if(data == null){
					nemaPonuda();
				}else if(data.length  == 0){
		
					nemaPonuda();
				}else{
					ispisiPonude(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove kat");
			   
		}
	});
	}
}

function preuzmiPodatke() {
	
	var kat = JSON.stringify({
		"checkIn":$('#checkin').val(),
		"checkOut":$('#checkout').val(),
		"brojSoba":$('#brojSoba').val(),
		"brojLjudi":$('#brojLjudi').val(),
		"brojKreveta":$('#typeRoom').val()
	});
	return kat;
}

function nemaPonuda(){
	$("#reserveHotel").hide(); 
	$("#korak").empty();
	$("#korak").append("<p><h2>Unfortunately we don't have rigth offer for you.</h2></p>");
}

function ispisiPonude(lista){
	console.log('ima ponuda');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	
	 $("#korak").show();

	 $("#korak").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Floor</th><th>Balkony</th><th>Total price</th><th>Select</th></tr>");
		

		$.each(pom, function(index, data) {
			if(data.balkon == 'da'){
				$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" ></td><td>"+data.cijena+"</td><td><input type=\"checkbox\" id=\""+data.id+"\"  class=\"cekiraj\" name= \"cekirani\"  value=\""+data.id+"\"></td></tr>");	
			}else{
				$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td><input type=\"checkbox\"  disabled=\"disabled\" ></td><td>"+data.cijena+"</td><td><input type=\"checkbox\" name= \"cekirani\"  class=\"cekiraj\" id=\""+data.id+"\" value=\""+data.id+"\"></td></tr>");	
			}
		});

		
	 $("#korak").append("</table>");
	 $("#korak").append("<p><button type=\"button\" onclick = \"povratakPretraga()\" class=\"btn btn-outline-secondary\">Back</button><button onclick = \"korak2get()\" id=\"predjiNaDodatne\" type=\"button\" class=\"btn btn-success\" disabled=\"disabled\">Next</button></p>")

	   $(".cekiraj").change(function (event) {  
           event.preventDefault();
           
           var sList="";
     	  
     	  $('input[name = "cekirani"]').each(function () {
     		  console.log('usao ovdje');
     		  if(this.checked){
     			  sList += (sList=="" ? $(this).val() : "," + $(this).val());	  
     		    }
     		   
     		});
     	  if(sList==""){
     		  $("#predjiNaDodatne").prop('disabled', true);
     	  }else{
     		  $("#predjiNaDodatne").prop('disabled', false);
	     	    
     	  }
     });
	
}
function povratakPretraga(){
	 $("#korak").hide();
	 $("#korakDodatne").hide();
	 $("#korak").empty();
	 console.log('pritisnuo back'); 
	 $("#reserveHotel").show();	
	
}


function korak2get(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	//ovdje uzimam sobe koje sam selektovala da imam u narednom koraku
	  var sList="";
	  
	  $('input[name = "cekirani"]').each(function () {
		  console.log('usao ovdje');
		  if(this.checked){
			  sList += (sList=="" ? $(this).val() : "." + $(this).val());
				  
		    }
		   
		});
	   console.log('niz je' +sList);
	  //dodatne informacije za rezervaciju	
	  	var pocetak=$('#checkin').val();
		var kraj=$('#checkout').val();
		var osobe=$('#brojLjudi').val();
		$("#korakDodatne").empty();
		$("#reserveHotel").hide();
		
		$("#korakDodatne").append("<p><h2>Offers </h2></p>");
		$("#korakDodatne").append("<p>FROM <span id=\"pocetak\">"+pocetak+"</span>TO <span id=\"kraj\">"+kraj+"</span></p>");
		$("#korakDodatne").append("<p>FOR <span id=\"osobe\">"+osobe+"</span>  passengers</p>");

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getUsluge/"+id,
		success: function(lista){
			if(lista==null){
				korak4bezUsluga(sList);
				console.log('Nema usluga');
			}else if(lista.length == 0){
				korak4bezUsluga(sList);
				console.log('Prazne usluga');
			}else{
				console.log('Ima usluga ');
				korak4ispis(lista,sList);
			}
		}
	});
	
}
function korak4bezUsluga(niz){
		console.log('dosao ovdje u korak 4 bez usluga'+niz);
	    $("#revervacija").show();
		$("#korakDodatne").show();
		$("#korak").hide();
	    $("#reserveHotel").hide();
	    $("#korakDodatne").append("<p id = \"korak4css\">Unfortunately, we don't have any additional services rigth now.</p>");			                                                                                                    
	    $("#korakDodatne").append("<p><button type=\"button\" onclick = \"povratakSobe()\" class=\"btn btn-outline-secondary\">Back</button><button onclick = \"zavrsiRezBezUsluga("+niz+")\" type=\"button\" class=\"btn btn-success\">Finish</button></p>")
}
//nudi se mogucnost korisniku da izabere dodatne usluge
function korak4ispis(data,niz){
	   $("#korak").hide();	
	 
	   console.log('sobe u suluga '+niz);
		
	   $("#korakDodatne").show();	
	   $("#reserveHotel").hide();
	   var lista = data == null ? [] : (data instanceof Array ? data : [ data ]);
			
		$("#korakDodatne").append("<table class=\"table table-hover\" id=\"tblDodatne\" ><tr><th>Service name </th><th>Rate</th><th data-toggle=\"tooltip\" data-placement=\"top\" title=\"We will count the biggest discount for you\">Discount(%)</th><th>Charge</th><th></th></tr>");
		$.each(lista, function(index, usluga) {
			if(usluga.konfiguracija == 'da'){
				$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td>"+usluga.cena+"</td><td>"+usluga.popust+"</td><td> per person/day </td><td><input type=\"checkbox\" name= \"cekiraneUsluge\" id=\""+usluga.id+"\" value=\""+usluga.id+"\"></td></tr>");
			}else{
				$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td>"+usluga.cena+"</td><td></td><td> per person/day </td><td><input type=\"checkbox\" name= \"cekiraneUsluge\" id=\""+usluga.id+"\" value=\""+usluga.id+"\"></td></tr>");
				
			}
		});
	    $("#korakDodatne").append("</table>");                                                                                                     
	    $("#korakDodatne").append("<p><button type=\"button\" onclick = \"povratakSobe()\" class=\"btn btn-outline-secondary\">Back</button><button onclick = \"zavrsiRez("+niz+")\" type=\"button\" class=\"btn btn-success\">Finish</button></p>")
	   
	   
}
function povratakSobe(){
	 //praznim cekirano ne treba da cuva stanje
	 $("#korak").show();
	 $("#korakDodatne").hide();
	 $("#reserveHotel").hide();		
	 console.log('pritisnuo back'); 
	
}
function zavrsiRezBezUsluga(nizSoba){
	var pocetak=$('#checkin').val();
	var kraj=$('#checkout').val();
	var osobe=$('#brojLjudi').val();
	var info = pocetak+"*" + kraj+"*"+osobe;	  
	console.log('zavrsi bez rez'+nizSoba);
	//var pom = nizSoba.split(',');
	var listaUsl="nema";
	var adresa = window.location.search.substring(1);
    var id = adresa.split('=')[1];
		//ovo treba da obradim da vrati podatke
	  
	  $.ajax({
			type : 'POST',
			url : "/api/hoteli/rezervisi/"+info+"/sobe/"+nizSoba+"/nizUsluga/"+listaUsl+"/idHotela/"+id,
			success : function(povratna) {
						if(povratna.length==0){
							console.log('neuspjesno');
						}else if(povratna == 0){
							console.log('neuspjesno');
						}else{
							ispisiUspjesno(povratna);		
							console.log('uspjesno');
						}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
			});
	  
}
function zavrsiRez(nizSoba){
	var pocetak=$('#checkin').val();
	var kraj=$('#checkout').val();
	var osobe=$('#brojLjudi').val();
	var info = pocetak+"*" + kraj+"*"+osobe;	  
	console.log(info);
	console.log(nizSoba);
	
	var listaUsl="";
	  
	
	  $('input[name = "cekiraneUsluge"]').each(function () {
		  console.log('usao ovdje');
		  if(this.checked){
			  listaUsl += (listaUsl=="" ? $(this).val() : "." + $(this).val());
				  
		    }
		   
		});
	  if(listaUsl == ""){
		  listaUsl = "nema";
	  }
	 
	  var adresa = window.location.search.substring(1);
		console.log('adesa je '+adresa);
		var id = adresa.split('=')[1];
	  
	  $.ajax({
			type : 'POST',
			url : "/api/hoteli/rezervisi/"+info+"/sobe/"+nizSoba+"/nizUsluga/"+listaUsl+"/idHotela/"+id,
			success : function(povratna) {
					dodajUseruRez(povratna);
					},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
			});
	  
}
function dodajUseruRez(data){
	 console.log('dosao je da doda rez korisniku');
	 var rezervacija= JSON.stringify(data);
	  $.ajax({
			type : 'POST',
			url : "/api/korisnici/addRezSobe",
			contentType : "application/json",
			data: rezervacija,
			dataType : 'json',		
			success : function() {
				console.log('usao u uspesno');
				 ispisiUspjesno(data);
					
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log('usao u gresku');
				alert('greska');
			}
			});
	
}
function ispisiUspjesno(data){
	 
	$("#korakDodatne").empty();
	$("#korakDodatne").append("<div id= \"obavj\"><p>You have successfully made a reservation.</p><p>Total price:"+data.cijena+"</p><p>We are looking forward to have you as our guests</p></div>");
	
}
function changePass(){
	 $("#tabovi").hide();
	 $("#sobe").hide();
	 $("#konfig").hide();
	 $("#cijene").hide();
	 $("#divPopust").hide();		
     $("#rezervacije").hide();
     $("#adminStrana").hide();
     $("#informacije").hide();

 	 $('#lozinkaOld').html('');
 	 $('#lozinka1').html('');
 	 $('#lozinka2').html('');	
 		prikaziPromjenu();
	
}
function prikaziPromjenu(){
    $("#promjenaLozinke").show();

}

$(document).on('submit','.changePassword',function(e){
	e.preventDefault();	
	
	var oldLoz =  $('#lozinkaOld').val();
	var loz1 = $('#lozinka1').val();
	var loz2 = $('#lozinka2').val();
			 
				$.ajax({
					type : 'GET',
					url : "/api/korisnici/changePass?oldPass="+oldLoz+ "&lozinka1="+loz1+"&lozinka2="+loz2,
					success : function(pov) {
						if( pov.verifikovan == "stara"){	
							 alert("Old password is not valid");
								
						}else if(pov.verifikovan == "ponavljanje"){
							 alert("Passwords do not match.");										
						}else {
							alert("Izmjenio .");
							ispisiHotel();		
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		
	});
//prikaze profil hotela posle promjene lozinke admina
function ispisiHotel(){
	$("#informacije").show();
	 $("#promjenaLozinke").hide();
	 $("#tabovi").show();
		
 	
}
function changeData(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	window.location = "changePersonalData.html?id="+id;
	
}
//ispusje sobe za admina sistema da definise popuste za bodove korisnika
function showRoomsForDiscounts(){
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getRoomsForDiscount/"+id,
		success: function(lista){
			if(lista == null){
				console.log('Nema soba')
			}else{
				writeRoomsForDiscounts(lista);
				
			}
		}
	});
	
	
}
function pomocnaFA(){
	showRoomsForDiscounts();
	
}
function writeRoomsForDiscounts(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#sobePopusti").empty();
	 $("#sobePopusti").show();
	//public RoomDTO(Long id, String tip, int kapacitet, int sprat,boolean imapopust) 
			
	 $("#sobePopusti").append("<table class=\"table table-hover\" id=\"tabelaSoba1\" ><tr><th>Room type </th><th>Capacity</th><th>Price per night</th><th></th><th></th><th></th></tr>");
		
		$.each(pom, function(index, data) {
			if(data.imapopust){
				console.log('ima popust');
				
				$("#tabelaSoba1").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td><button type=\"button\" onclick=\"addDiscountForRooms("+data.id+")\" class=\"btn btn-light\">Add discount</button></td><td><button type=\"button\" onclick=\"listOfDiscount("+data.id+")\" class=\"btn btn-light\">Discounts</button></td></tr>");
			}else{
				$("#tabelaSoba1").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td><button type=\"button\" onclick=\"addDiscountForRooms("+data.id+")\" class=\"btn btn-light\">Add discount</button></td><td></td></tr>");
				
			}
			
		});
		
	 $("#sobePopusti").append("</table>");

	
}
function addDiscountForRooms(idRoom){
	$("#sobePopusti").hide();

	$("#dugmePopust").empty();
	$("#dugmePopust").append("<button type=\"button\"  class=\"btn btn-lg\" onclick = \"dodajPopustSistem("+idRoom+")\">Add</button></div>");				
	$("#dodajPopust").show();
}
function dodajPopustSistem(idRoom){
	console.log(idRoom);
	$("#dodajPopust").hide();
	var pocetak=$('#sincewhen').val();
	var kraj=$('#untilwhen').val();
	var bodovi=$('#brojBodova').val();
	var procenat=$('#procenat').val();
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	$('#sincewhen').val('');
	$('#untilwhen').val('');
	$('#brojBodova').val("");
	$('#procenat').val("");
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/definisiPopust/"+id+"/soba/"+idRoom+"/pocetak/"+pocetak+"/kraj/"+kraj+"/bodovi/"+bodovi+"/procenat/"+procenat,
		success : function(povratna) {
						console.log('uspjesno');
						pomocnaFA();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
		});

	
}
function listOfDiscount(idRoom){
	$("#sobePopusti").hide();
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getRoomDiscount/"+id+"/idRoom/"+idRoom,
		success: function(lista){
				writeDiscountsOfRoom(lista);
			
		}
	});
}
function writeDiscountsOfRoom(lista){
	 var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#postojeciPopusti").empty();
	 $("#postojeciPopusti").show();
	//public RoomDTO(Long id, String tip, int kapacitet, int sprat,boolean imapopust) 
			
	 $("#postojeciPopusti").append("<table class=\"table table-hover\" id=\"popustiTab\" ><tr><th>Since when </th><th>Until when</th><th>Number of user pointst</th><th>Discount percentage</th><th></th></tr>");
		
		$.each(pom, function(index, data) {
				$("#popustiTab").append("<tr><td class=\"hoverName\">"+data.datumod+"</td><td> "+data.datumdo+"</td><td>"+data.bodovi+"</td><td>"+data.vrijednost+"</td><td><button type=\"button\" onclick=\"removeDisc("+data.id+")\" class=\"btn btn-light\">Remove</button></td></tr>");
			
			
		});
		
	 $("#postojeciPopusti").append("</table>");

}

function removeDisc(idPopust){
	console.log(idPopust);
}

