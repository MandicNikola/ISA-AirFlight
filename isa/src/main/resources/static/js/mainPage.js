

var filterData = [];
var imaKorisnika = "nema";


function onLoad(){
	
	$("#ispisiSelect").hide();
	$("#divBodPopust").hide();
	$("#zaAdminaSistema").hide();
	$("#history").hide();
	$("#friendsList").hide();
	
	var user = sessionStorage.getItem("ulogovan");
	console.log(user);
	console.log('provera logovanja');
	
	if(user!=null && user!="null" && user!="undefined") {
		console.log("Postoji ulogovan korisnik");
		$("#logovanje").hide();
		var korisnik = JSON.parse(user);
		$("#imeKorisnika").text(korisnik.ime);
		if(korisnik.tip == 'ADMIN_SISTEM'){
			
			$("#reserveFlight").hide();
			$("#reserveHotel").hide();
			$("#reserveCar").hide();
			$("#zaAdminaSistema").show();
			
		}
		if(korisnik.tip == 'ADMIN_AVIO' ||korisnik.tip == 'ADMIN_HOTEL'||korisnik.tip == 'ADMIN_RENT' ){
			$("#reserveFlight").hide();
			$("#reserveHotel").hide();
			$("#reserveCar").hide();
		
		}
		if(korisnik.tip == 'REGISTROVAN'){
			$("#history").show();
			$("#friendsList").show();
			  ispisiIstoriju();
						
		}
		imaKorisnika = "ima";
		
	}else{
		$("#prikazKorisnika").hide();
		$("#odjava").hide();
		$("#history").hide();
			
	}
	$("#pozadinaAuto").hide();
	$("#ispisiSelect").hide();
	
	$("#pozadinaHotel").hide();
	$("#reserveCar").hide();
	$("#sortCar").hide();
	$("#sortHotele").hide();
	$("#reserveHotel").hide();
	$("#pretragaDiv").hide();
	
	
	planeShow();
	refreshHotel();
	refreshCar();
	refreshFlight();

}

function refreshFlight(){
	
	$.ajax({
		method:'POST',
		url: "/api/reservationTickets/refreshResFlight",
		success: function(){
			console.log('Zavrseno');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
}
function refreshHotel(){
	
	$.ajax({
		method:'POST',
		url: "/api/rezervacijehotel/refreshResHotel",
		success: function(){
			console.log('Zavrseno');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
}
function refreshCar(){
	
	$.ajax({
		method:'POST',
		url: "/api/rezervacijerent/refreshResCar",
		success: function(){
			console.log('Zavrseno');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
}
function ispisiIstoriju(){
	console.log('Usao u ispisiIstoriju');
		dodajIstorijuPlane();
		dodajIstorijuHotel();	
		dodajIstorijuRent();
	
}
function dodajIstorijuHotel(){
	console.log('Usao u dodajistoriju Hotela');
	$.ajax({
		method:'GET',
		url: "/api/rezervacijehotel/istorijaHotela",
		success: function(lista){
			if(lista == null){
				console.log('Istorija je prazna');
				istorijaPrazna();
			}else if(lista.length == 0){
				console.log('Istorija je prazna');
				istorijaPrazna(1);
			}else{
				ispisiIstorijuHotel(lista);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

	
}	
function ispisiIstorijuHotel(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#historyHotel").empty();
	 $("#historyHotel").append("<h4>Hotel booking history</h4>");	
	$("#historyHotel").append("<table class=\"table table-striped\" id=\"histTableHotel\" ><tr><th>Hotel</th><th>Check-in date</th><th>Check-out date</th><th>Price</th><th>Status</th><th></th><th></th><th></th></tr>");
		console.log('Ispisujemo niz rez hotela');
		console.log(pom);
		
		$.each(pom, function(index, clan) {
		    console.log(clan);
			var datDol=clan.checkIn;
			var datOdl=clan.checkOut;
			console.log('Datum dolaska je '+datDol);
			console.log('Datum odlaska je '+datOdl);
			var date1=datDol.split('T')[0];
			var date2=datOdl.split('T')[0];
			var idRez= clan.id;
			var nazHot = "oceniH"+idRez;
			var btnHot = "hotelB"+idRez;
			var hotel=clan.nazivHotela;
			var btnRoom = "roomB"+idRez;
			var today = new Date().toISOString().split('T')[0];

			var statId="statH"+idRez
			
			const timeDiff  = (new Date(date1)) - (new Date(today));
			const numberDays = timeDiff / (1000 * 60 * 60 * 24)
			var btnOtkH = "otkH"+idRez;
			
			console.log("Broj dana je "+numberDays);
			if(clan.status == "ZAVRSENA"){
				if(numberDays >=3 ){
					$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td id=\""+statId+"\"> "+clan.status+"</td><td><button  class=\"btn btn-info\" id="+btnOtkH+" onclick=\"otkaziHotel("+clan.id+")\">Cancel</button></td></tr>");
							
				}else{
					$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td></tr>");
		
				}
			}else if(clan.status=='OTKAZANA'){
				$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td></tr>");
						
			}else{
				console.log('zavrsena je')
				//nije ocenjen hotel
				if(clan.ocenjenHotel == false){
					if(clan.brojLjudi==0){
						console.log('Sve sobe su ocenjene');
						$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td><td> <input type=\"number\" min=\"1\" max=\"5\" id="+nazHot+"></td><td><button  class=\"btn btn-info\" id="+btnHot+" onclick=\"oceniHotel("+clan.id+")\">Rate Hotel</button></td></tr>");
						
					}else{
						console.log('Ima soba za ocenjivanje');
						$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td><td> <input type=\"number\" min=\"1\" max=\"5\" id="+nazHot+"></td><td><button  class=\"btn btn-info\" id="+btnHot+" onclick=\"oceniHotel("+clan.id+")\">Rate Hotel</button></td><td><button  class=\"btn btn-info\" id="+btnRoom+" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Click this button to see the rooms that you can rate.\" onclick=\"oceniSobe("+clan.id+")\">Rate rooms</button></td></tr>");
						
					}
						
				}else{
					//hotel je vec ocenjen
					if(clan.brojLjudi==0){
						$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td></tr>");
					}else{
						$("#histTableHotel").append("<tr><td class=\"hoverName\">"+hotel+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td><td><button  class=\"btn btn-info\" id="+btnRoom+" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Click this button to see the rooms that you can rate.\" onclick=\"oceniSobe("+clan.id+")\">Rate rooms</button></td></tr>");
							
					}
				}
			
			}
		});
	 $("#historyHotel").append("</table>");

}
function otkaziHotel(idRez){
	console.log('otkaziHotel')
	var statId="statH"+idRez
	var btnOtkH = "otkH"+idRez;
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/otkaziHotel/"+idRez,
		success : function(pov) {
			if( pov == null){	
				alert('Prazno');
			}else{
				    $("#"+statId).html('OTKAZANA');
					$("#"+btnOtkH).hide();

		     	alert('You have successfully canceled the reservation')
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

}
function oceniSobe(idRez){
	console.log('Usao u oceni sve sobe');
	console.log(idRez);
	
	$.ajax({
		method:'GET',
		url: "/api/rezervacijehotel/listaSoba/"+idRez,
		success: function(lista){
			if(lista == null){
				 $("#ratingRooms").empty();
					
				console.log('Nema soba');
			}else if(lista.length == 0){
				 $("#ratingRooms").empty();
					
				console.log('Nema soba');
			}else{
				prikaziSobe(lista);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

	
}
function prikaziSobe(lista){
	console.log('Usao u prikazi sobe');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#ratingRooms").empty();
	 $("#ratingRooms").append("<h4>Rate rooms</h4>");

	 $("#ratingRooms").append("<table class=\"table table-striped\" id=\"rateRoomTable\" ><tr><th>Hotel</th><th>Tip</th><th></th><th></th></tr>");

	$.each(pom, function(index, soba) {
		var nazSobe = "oceniS"+soba.id+"3"+soba.idRez;
		var btnSoba = "sobaBtn"+soba.id+"3"+soba.idRez;
		
		console.log("Param " +btnSoba);
		console.log("Naziv " + nazSobe);
		
		$("#rateRoomTable").append("<tr><td class=\"hoverName\">"+soba.hotel+"</td><td> "+soba.tip+"</td><td> <input type=\"number\" min=\"1\" max=\"5\" id="+nazSobe+"></td><td><button  class=\"btn btn-info\" id="+btnSoba+" onclick=\"rateRoom("+soba.id+","+soba.idRez+")\">Rate</button></td></tr>");
		
	});
	 $("#ratingRooms").append("</table>");

}
function rateRoom(idSobe, idRez){
	console.log('Usao u rateRoom ');
	console.log(idSobe+ " , "+idRez);
	var nazSobe = "oceniS"+idSobe+"3"+idRez;
	var btnSoba = "sobaBtn"+idSobe+"3"+idRez;
	
	var ocena =  $("#"+nazSobe).val();
	
	console.log(ocena);
	if(ocena<1 || ocena>5){
		alert('Grade must be between 1 and 5');
	}else{
		
		var podatak = idRez+"="+idSobe+"="+ocena;
		$.ajax({
			type : 'POST',
			url : "/api/hoteli/oceniSobu/"+podatak,
			success : function(pov) {
				if( pov == null){	
					alert('Prazno');
				}else{
					$("#"+btnSoba).prop('disabled', true);
					$("#"+nazSobe).prop('disabled',true);
			     	alert('You have successfully rated the room')
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});

	}
}

function oceniHotel(idRez){
	console.log('Usao u oceniRent ');
	console.log(idRez);
	var nazHot = "oceniH"+idRez;
	var btnHot = "hotelB"+idRez;
	var ocena =  $("#"+nazHot).val();

	console.log(ocena);
	if(ocena<1 || ocena>5){
		alert('Grade must be between 1 and 5');
	}else{
		var podatak = idRez+"="+ocena;
		$.ajax({
			type : 'POST',
			url : "/api/hoteli/oceniHotel/"+podatak,
			success : function(pov) {
				if( pov == null){	
					alert('Prazno');
				}else{
					$("#"+btnHot).prop('disabled', true);
					$("#"+nazHot).prop('disabled',true);
			     	alert('You have successfully rated the hotel')
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});

		
	}	
}
function dodajIstorijuRent(){
	console.log('usao u dodajIstoriju rent');
	$.ajax({
		method:'GET',
		url: "/api/rezervacijerent/istorijaRent",
		success: function(lista){
			if(lista == null){
				console.log('Istorija je prazna');
				istorijaPrazna();
			}else if(lista.length == 0){
				console.log('Istorija je prazna');
				istorijaPrazna(2);
			}else{
				ispisiIstorijuRent(lista);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

}
function ispisiIstorijuRent(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#historyRent").empty();
	 $("#historyRent").append("<h4>Rent-A-Car booking history</h4>");
	$("#historyRent").append("<table class=\"table table-striped\" id=\"histTableRent\" ><tr><th>Company</th><th>Brand of car</th><th>Model</th><th>Pick-up date</th><th>Drop-off date</th><th>Price</th><th>Status</th><th></th><th></th><th></th><th></th></tr>");
		console.log('Ispisujemo niz rez rent');	
		console.log(pom);
		$.each(pom, function(index, clan) {
			console.log(clan);
			var datDol=clan.datumPreuzimanja;
			var datOdl=clan.datumVracanja;
			
			var date1=datDol.split('T')[0];
			var date2=datOdl.split('T')[0];
			var voz=clan.vozilo;
			var idRez=clan.id;
			var nazRent = "oceniR"+idRez;
			var nazVozilo = "oceniV"+idRez;
			var btnRent = "rentB"+idRez;
			var btnCar = "carB"+idRez;
			var btnOtk = "otkB"+idRez;
			var statId="stat"+idRez
			console.log(clan.status);
			var today = new Date().toISOString().split('T')[0];
		
			const timeDiff  = (new Date(date1)) - (new Date(today));
			const numberDays = timeDiff / (1000 * 60 * 60 * 24)
		
			console.log("Broj dana je "+numberDays);
			
			if(clan.status == "AKTIVNA"){
				if(numberDays >= 3){
					$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td id=\""+statId+"\"> "+clan.status+"</td><td><button  class=\"btn btn-info\" id="+btnOtk+" onclick=\"otkaziVozilo("+voz.id+","+clan.id+")\">Cancel</button></td></tr>");
						
				}else{
					$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td > "+clan.status+"</td></tr>");
							
				}
			}else if(clan.status == "ZAVRSENA"){
				//nije ocenio ni vozilo ni rent-a-car servis
				if(clan.ocenjenVozilo == false && clan.ocenjenRent == false){
					console.log("1");
					$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td><td> <input type=\"number\" min=\"1\" max=\"5\" id="+nazRent+"></td><td><button  class=\"btn btn-info\" id="+btnRent+" onclick=\"oceniRent("+voz.id+","+clan.id+")\">Rate Rent Service</button></td><td> <input type=\"number\" id="+nazVozilo+" min=\"1\" max=\"5\"></td><td><button  class=\"btn btn-info\" id="+btnCar+" onclick=\"oceniVozilo("+voz.id+","+clan.id+")\">Rate Car</button></td></tr>");
				}else if(clan.ocenjenVozilo == false){
					//nije ocenio vozilo, ali je ocenio rent-a-car
					console.log("2");
					$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td> <td><input type=\"number\" id="+nazVozilo+" min=\"1\" max=\"5\"></td><td><button  class=\"btn btn-info\" id="+btnCar+" onclick=\"oceniVozilo("+voz.id+","+clan.id+")\">Rate Car</button></td></tr>");
						
				}else if(clan.ocenjenRent == false){
					//nije ocenio rent-a-car ali je ocenio vozilo
					console.log("3");
					$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td > "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td><td> <input type=\"number\" min=\"1\" max=\"5\" id="+nazRent+"></td><td><button  class=\"btn btn-info\" id="+btnRent+" onclick=\"oceniRent("+voz.id+","+clan.id+")\">Rate Rent Service</button></td></tr>");
					
				}else{
					//ocenio je i rent-a-car i vozilo
					console.log("4");
					$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td > "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td> "+clan.status+"</td></tr>");
					
				}
			}else{
				//otkazana je
				$("#histTableRent").append("<tr><td class=\"hoverName\">"+clan.mestoVracanja+"</td><td> "+voz.marka+"</td><td> "+voz.model+"</td><td> "+date1+"</td><td> "+date2+"</td><td> "+clan.cena+"</td><td > "+clan.status+"</td></tr>");
				
			}
			
			});
	 $("#historyRent").append("</table>");

}
function otkaziVozilo(idVoz, idRez){
	console.log('Usao u otkazi vozilo');
	console.log(idVoz+" "+idRez);
	var podatak = idVoz+"="+idRez;
	var btnCar = "otkB"+idRez;
	var statId="stat"+idRez;
	
	$.ajax({
		type : 'POST',
		url : "/api/vozila/otkaziVozilo/"+podatak,
		success : function(pov) {
			if( pov == null){	
				alert('Prazno');
			}else{
				 $("#"+statId).html('OTKAZANA');

				$("#"+btnCar).hide();
				
				alert('You have successfully cancelled the reservation.')
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

	
}
function oceniVozilo(idVoz,idRez){
	console.log('Usao u oceniVozilo ');
	console.log(idVoz);
	console.log(idRez);
	var nazVozilo = "oceniV"+idRez;
	var butCar = "carB"+idRez;
	
	var ocena =  $("#"+nazVozilo).val();
	console.log(ocena);
	if(ocena<1 || ocena>5){
		alert('Grade must be between 1 and 5');
	}else{
		var salji = idVoz+"="+ocena+"="+idRez;
		$.ajax({
			type : 'POST',
			url : "/api/vozila/oceniVozilo/"+salji,
			success : function(pov) {
				if( pov == null){	
					alert('Prazno');
				}else{
					$("#"+butCar).prop('disabled', true);
					$("#"+nazVozilo).prop('disabled',true);
			     	alert('You have successfully rated the car')
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});

		
	}
}
function oceniRent(idVoz,idRez){
	console.log('Usao u oceniRent');
	console.log(idVoz);
	console.log(idRez);

	var nazRent = "oceniR"+idRez;
	var ocena =  $("#"+nazRent).val();
	var btnRent = "rentB"+idRez;
	
	console.log(ocena);
	
	if(ocena<1 || ocena>5){
		
		alert('Grade must be between 1 and 5');
	
	}else{
		var salji = idVoz+"="+ocena+"="+idRez;
		
		$.ajax({
			type : 'POST',
			url : "/api/vozila/cekirajOcenu/"+salji,
			success : function(pov) {
				if( pov == null){	
					console.log('Prazno');
				}else{
					$("#"+btnRent).prop('disabled', true);
					$("#"+nazRent).prop('disabled',true);
			     
					cekirajOcenuRent(pov);
			     }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});

		}
}
function cekirajOcenuRent(vozilo){
	console.log('usao u cekiraj');
	var salji =JSON.stringify(vozilo);
	console.log(salji);
	console.log(vozilo);
	
	$.ajax({
		type : 'POST',
		url : "/api/rents/oceniRent",
		contentType : "application/json",
		data: salji,
		dataType : 'json',		
		success : function(pov) {
			if( pov == null){	
				alert('Prazno');
			}else{
				alert('You have successfully rated the car')
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});	

}
function dodajIstorijuPlane(){
	console.log('Usao u dodajistoriju avion');
	$.ajax({
		method:'GET',
		url: "/api/reservationTickets/istorijaAvion",
		success: function(lista){
			if(lista == null){
				console.log('Istorija je prazna');
				istorijaPrazna(3);
			}else if(lista.length == 0){
				console.log('Istorija je prazna');
				istorijaPrazna(3);
			}else{
				ispisiIstorijuLetova(lista);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});

	
}	

function ispisiIstorijuLetova(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	$("#historyAirplane").empty();
	 $("#historyAirplane").append("<h4>Flight booking history</h4>");
	$("#historyAirplane").append("<table class=\"table table-striped\" id=\"histTableLet\"><tr><th>Company</th><th>From</th><th>To</th><th>Departure date</th><th>Arrival date</th><th>Status</th><th></th><th></th><th></th><th></th></tr>");
		console.log(pom);
		$.each(pom, function(index, clan) {
			var datDol=clan.datumPoletanja;
			var datOdl=clan.datumSletanja;
			var date1=datDol.split('T')[0];
			var date2=datOdl.split('T')[0];
			var endDest=clan.mestoSletanja;
			var startDest=clan.mestoPoletanja;
			var status=clan.status;
			var idRez=clan.id;
			var kompanija= clan.nazivKompanije;
			
			var nazKomp = "oceniAv"+idRez;
			var nazLet = "oceniLet"+idRez;
			var btnKomp = "kompB"+idRez;
			var btnOtk = "otkBP"+idRez;
			var btnLet = "letB"+idRez;
			var statId="statPlane"+idRez
			console.log(clan.status);
			var today = new Date().toISOString().split('T')[0];
		
			const timeDiff  = (new Date(date1)) - (new Date(today));
			const numberHours = timeDiff / (1000 * 60 * 60 )
			if(status == "OTKAZANA"){
				$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td></tr>");
					
			}else if(status == "ZAVRSENA"){
				if(clan.ocenjenaKompanija==false && clan.ocenjenLet==false){
					$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td><td> <input type=\"number\" id="+nazKomp+" min=\"1\" max=\"5\"></td><td><button  class=\"btn btn-info\" id="+btnKomp+" onclick=\"oceniKompaniju("+clan.idKompanije+","+idRez+")\">Rate company</button></td><td> <input type=\"number\" id="+nazLet+" min=\"1\" max=\"5\"></td><td><button  class=\"btn btn-info\" id="+btnLet+" onclick=\"oceniLet("+clan.idLet+","+idRez+")\">Rate Flight</button></td></tr>");
						
				}else if(clan.ocenjenaKompanija==false){
					$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td><td> <input type=\"number\" id="+nazKomp+" min=\"1\" max=\"5\"></td><td><button  class=\"btn btn-info\" id="+btnKomp+" onclick=\"oceniKompaniju("+clan.idKompanije+","+idRez+")\">Rate company</button></td></tr>");
					
				}else if(clan.ocenjenLet==false){
					$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td><td> <input type=\"number\" id="+nazLet+" min=\"1\" max=\"5\"></td><td><button  class=\"btn btn-info\" id="+btnLet+" onclick=\"oceniLet("+clan.idLet+","+idRez+")\">Rate Flight</button></td></tr>");
					
				}else{
					$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td></tr>");

				} 
				
			}else{
				//aktivna je ovde ide otkazivanje
				if(numberHours >= 3)
				{
					$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td><td><button  class=\"btn btn-info\" id="+btnOtk+" onclick=\"otkaziRezervaciju('"+idRez+"')\">Cancel</button></td></tr>");
				}
				else
				{
					$("#histTableLet").append("<tr><td class=\"hoverName\">"+kompanija+"</td><td> "+startDest+"</td><td> "+endDest+"</td><td> "+date1+"</td><td> "+date2+"</td><td id=\""+statId+"\"> "+status+"</td></tr>");
				}			
			}
			
			});
	 $("#historyAirplane").append("</table>");

}

function otkaziRezervaciju(id)
{
	var idRezervacije = id;
	$.ajax
	({
		type : 'POST',
		url : 'api/reservationTickets/otkaziRezervaciju/'+idRezervacije,
		success : function(data)
		{
			alert('Uspesno otkazano!');
			window.location = "mainPage.html";
		}
		
	});
	
	
	
	
}




function oceniKompaniju(idKompanije, idRez){
	console.log('Usao u oceniKompaniju');
	console.log(idKompanije);
	console.log(idRez);

	var nazKomp = "oceniAv"+idRez;
	var btnKomp = "kompB"+idRez;
	var ocena =  $("#"+nazKomp).val();

	if(ocena<1 || ocena>5){
		
		alert('Grade must be between 1 and 5');
	
	}else{
		var parametar=idKompanije+"="+ocena;
		console.log(ocena);
		var broj=1;
		$.ajax({
			type : 'POST',
			url : "/api/kompanije/oceniKompaniju/"+parametar,
			success : function(pov) {
				if( pov == null){	
					alert('Prazno');
				}else{
					console.log('uspehh');
					cekirajOcenuAviona(idRez , broj);
					
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});
	}
}
function cekirajOcenuAviona(idRez,broj){
console.log('usao u cekiraj');
console.log(idRez);
console.log(broj);
var nazKomp = "oceniAv"+idRez;
var btnKomp = "kompB"+idRez;
var nazLet = "oceniLet"+idRez;
var btnLet = "letB"+idRez;

	var parametar=idRez+"="+broj;
	$.ajax({
		method:'POST',
		url: "/api/korisnici/cekirajOcenu/"+parametar,
		success: function(pov){
			if(pov == null){
				console.log('Prazno');
				
			}else {
				if(broj==2){
					$("#"+btnLet).prop('disabled', true);
					$("#"+nazLet).prop('disabled',true);
					alert('You have successfully rated the flight.')
					
				}else{
					$("#"+btnKomp).prop('disabled', true);
					$("#"+nazKomp).prop('disabled',true);
					alert('You have successfully rated the company.')
					
				}
				
				console.log('Uspesno');
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	
}
function oceniLet(idLet,idRez){
	console.log(idLet);
	console.log(idRez);
	var nazLet = "oceniLet"+idRez;
	var btnLet = "letB"+idRez;
	var ocena =  $("#"+nazLet).val();	
	if(ocena<1 || ocena>5){
		
		alert('Grade must be between 1 and 5');
	
	}else{
		console.log(ocena);
		var parametar=idLet+"="+ocena;
		console.log(ocena);
		var broj=2;
		$.ajax({
			type : 'POST',
			url : "/api/letovi/oceniLet/"+parametar,
			success : function(pov) {
				if( pov == null){	
					alert('Prazno');
				}else{
					console.log('uspeeh');
					cekirajOcenuAviona(idRez,broj);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});
	}
}

function istorijaPrazna(broj){
	if(broj==1){
		 $("#historyHotel").append("<h4>Hotel booking history</h4>");	

		$("#historyHotel").append("<h4>There is no any record in your history.</h4>");
			
	}else if(broj == 2){
		 $("#historyRent").append("<h4>Rent-A-Car booking history</h4>");	

		$("#historyRent").append("<h4>There is no any record in your history.</h4>");
		
	}else{
		//historyAirplane
		 $("#historyAirplane").append("<h4>Flight booking history</h4>");	

		$("#historyAirplane").append("<h4>There is no any record in your history.</h4>");
		
	}
	
}
function planeShow(){

	$("#pozadinaAvion").show();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();
	$("#ispisiSelect").hide();
	$("#ispisiTabelu").empty();
	$("#reserveCar").hide();	
	$("#sortCar").hide();
	$("#sortHotele").hide();
	$("#reserveHotel").hide();
	$("#sortAvione").show();
	$("#sortPlane").val("none");
	$("#ratingRooms").empty();
	var user = sessionStorage.getItem("ulogovan");

	if(user!=null && user!="null" && user!="undefined") {
		console.log("Postoji ulogovan korisnik");
		$("#logovanje").hide();
		var korisnik = JSON.parse(user);
		if(korisnik.tip == 'ADMIN_AVIO' ||korisnik.tip == 'ADMIN_SISTEM'|| korisnik.tip == 'ADMIN_HOTEL' || korisnik.tip == 'ADMIN_RENT' ){
			$("#reserveFlight").hide();
			$("#reserveHotel").hide();
			$("#reserveCar").hide();
		}else{
			$('#reserveFlight').show();
				
		}
	}else{
		$('#reserveFlight').show();
		 	
	}
	
	$.ajax({
		method:'GET',
		url: "/api/kompanije/all",
		success: function(lista){
			if(lista == null){
				console.log('Nema aviokompanija');
			}else{
				ispisiAviokompanije(lista);
			}
		}
	});
}
function hotelShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").hide();
	
	$("#pozadinaHotel").show();
	$("#ispisiTabelu").empty();
	$("#sortHotele").show();
	$("#sortHotel").val("none");
	$("#reserveCar").hide();
	$("#sortCar").hide();
	$("#sortAvione").hide();
	$("#ispisiTabelu").empty();
	$("#ispisiSelect").hide();
	$("#ratingRooms").empty();
	$('#reserveFlight').hide();
	var user = sessionStorage.getItem("ulogovan");

	if(user!=null && user!="null" && user!="undefined") {
		console.log("Postoji ulogovan korisnik");
		$("#logovanje").hide();
		var korisnik = JSON.parse(user);
		if(korisnik.tip == 'ADMIN_AVIO' ||korisnik.tip == 'ADMIN_SISTEM'|| korisnik.tip == 'ADMIN_HOTEL' || korisnik.tip == 'ADMIN_RENT' ){
			$("#reserveFlight").hide();
			$("#reserveHotel").hide();
			$("#reserveCar").hide();
		}else{
			$("#reserveHotel").show();
				
		}
	}else{
		$("#reserveHotel").show();
		 	
	}		
	
	$.ajax({
		method:'GET',
		url: "/api/hoteli/all",
		success: function(lista){
			if(lista == null){
				console.log('Nema servise');
			}else{
				iscrtajHotele(lista);
			}
		}
	});
}

function ispisiAviokompanije(lista){
	 $("#ispisiTabelu").empty();
		
	console.log('usao u ispisi aviokompanije');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	
	 var user = sessionStorage.getItem("ulogovan");
		var adminsistem = false; 
		
		if(user!=null && user!="null" && user!="undefined") {
				console.log('ima korisnika');
				var korisnik=JSON.parse(user);
				console.log(korisnik.tip);
			if(korisnik.tip == 'ADMIN_SISTEM'){
				adminsistem = true;
			$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaAvion\" ><tr><th> Name </th><th> Promotional description</th><th>Address</th><th></th><th></th></tr>");
				
				$.each(pom, function(index, avio) {
					$("#tabelaAvion").append("<tr><td class=\"hoverName\" >"+avio.naziv+"</td><td > "+avio.opis+"</td><td > "+avio.adresa+"</td><td><button  class=\"btn btn-info\" onclick=\"profileCompany('"+avio.id+"')\">Profile</button></td><td><button  class=\"btn btn-info\" onclick=\"deleteCompany('"+avio.id+"')\">Delete</button></td></tr>");
				});
			 $("#ispisiTabelu").append("</table>");
		
				}
			}
		if(adminsistem == false){
			$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaAvion\" ><tr><th> Name </th><th> Promotional description</th><th>Address</th><th></th></tr>");
			
			$.each(pom, function(index, avio) {
				$("#tabelaAvion").append("<tr><td class=\"hoverName\" >"+avio.naziv+"</td><td > "+avio.opis+"</td><td > "+avio.adresa+"</td><td><button  class=\"btn btn-info\" onclick=\"profileCompany('"+avio.id+"')\">Profile</button></td></tr>");
			});
		 $("#ispisiTabelu").append("</table>");
	
			
		}
}


function profileCompany(id)
{
	console.log('Usao u profileComp, dobio id: '+id);
	window.location="AirCompProfile.html?id="+id;

}

function deleteCompany(id)
{
	$.ajax(
			{
				method:'POST',
				url: "/api/kompanije/obrisiKompaniju/"+id,
				success: function(data)
				{
					window.location="mainPage.html";
				}
			});

}




function sortirajAvione(){
	 console.log('usao u sortiraj avione');
	 var uslov=$("#sortPlane").val();
	 console.log('uslov je '+uslov);
	 
	 if(uslov!="none"){
		 $.ajax({
				method:'GET',
				url: "/api/kompanije/sort/"+uslov,
				success: function(lista){
					if(lista == null){
						console.log('Nema aviokompanija')
					}else if(lista.length==0){
						console.log('Nema aviokompanija')
					}else{
						ispisiAviokompanije(lista);
						
					}
				}
			});
	 }
	}

function sortirajHotele(){
	 console.log('usao u sortiraj hotele');
	 var uslov=$("#sortHotel").val();
	 console.log('uslov je '+uslov);
	 
	 if(uslov!="none"){
		 $.ajax({
				method:'GET',
				url: "/api/hoteli/sort/"+uslov,
				success: function(lista){
					if(lista == null){
						console.log('Nema hotela')
					}else if(lista.length==0){
						console.log('Nema hotela')
					}else{
						iscrtajHotele(lista);
						
					}
				}
			});
	 }
	}
function iscrtajHotele(lista){
	console.log('usao u ispisi hotele u js');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	$("#ispisiTabelu").empty();
	
	var user = sessionStorage.getItem("ulogovan");
	var adminsistem = false; 
	
	if(user!=null && user!="null" && user!="undefined") {
			console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);	
		if(korisnik.tip == 'ADMIN_SISTEM'){
	
			adminsistem = true;
			$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> City </th><th> Address </th><th>Grade</th><th></th><th></th></tr>");
				
				$.each(pom, function(index, servis) {
					$("#tabelaHotel").append("<tr><td class=\"hoverName\" onclick=\"hotelProfil('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.grad+"</td><td > "+servis.adresa+"</td><td > "+servis.ocena+"</td><td><button  class=\"btn btn-dark\" onclick=\"changeHotel('"+servis.id+"')\">Change</button></td><td><button  class=\"btn btn-dark\" onclick=\"deleteHotel('"+servis.id+"')\">Delete</button></td></tr>");
				});
			 $("#ispisiTabelu").append("</table>");
			 }
		}
	if(adminsistem == false){
		$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> City </th><th> Address </th><th>Rating</th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaHotel").append("<tr><td class=\"hoverName\" onclick=\"hotelProfil('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.grad+"</td><td > "+servis.adresa+"</td><td > "+servis.ocena+"</td></tr>");
		});
	 $("#ispisiTabelu").append("</table>");
	
	}
}
function changeHotel(id){
	window.location = "changeHotel.html?id="+id;
	
}
function deleteHotel(id){
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/obrisiHotel/"+id,
		success : function(data) {
				console.log('obrisan hotel');
				hotelShow();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	
	
}
function carShow(){
	$("#pozadinaAvion").hide();
	$("#sortAvione").hide();
	$("#pozadinaAuto").show();
	$("#sortCar").show();
	$("#sortAuto").val("none");
	$("#reserveHotel").hide();
	$("#sortHotele").hide();
	$("#pozadinaHotel").hide();	
	 $("#ispisiTabelu").empty();
	 $("#ratingRooms").empty();
	 $("#ispisiSelect").hide();
	 $('#reserveFlight').hide();
	var user = sessionStorage.getItem("ulogovan");

	 if(user!=null && user!="null" && user!="undefined") {
			console.log("Postoji ulogovan korisnik");
			$("#logovanje").hide();
			var korisnik = JSON.parse(user);
			if(korisnik.tip == 'ADMIN_AVIO' ||korisnik.tip == 'ADMIN_SISTEM'|| korisnik.tip == 'ADMIN_HOTEL' || korisnik.tip == 'ADMIN_RENT' ){
				$("#reserveFlight").hide();
				$("#reserveHotel").hide();
				$("#reserveCar").hide();
			}else{
				$("#reserveCar").show();
							
			}
		}else{
			$("#reserveCar").show();
			 	
		}
	$.ajax({
		method:'GET',
		url: "/api/rents/all",
		success: function(lista){
			if(lista == null){
				console.log('Nema servise')
			}else{
				ispisiAutoservise(lista);
				
			}
		}
	});
}
function ispisiAutoservise(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	$("#ispisiTabelu").empty();

	var user = sessionStorage.getItem("ulogovan");
	var adminsistem = false; 
	
	if(user!=null && user!="null" && user!="undefined") {
			console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);
		if(korisnik.tip == 'ADMIN_SISTEM'){
			adminsistem = true;
			$("#ispisiTabelu").append("<table class=\"table table-striped table-hover\" id=\"tabelaRent\" ><tr><th> Name </th><th> City </th><th>Address</th><th></th><th></th></tr>");
		
			$.each(pom, function(index, servis) {
			$("#tabelaRent").append("<tr><td class=\"hoverName\" onclick=\"visitCar('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.grad+"</td><td > "+servis.adresa+"</td><td><button  class=\"btn btn-info\" onclick=\"izmeniRent('"+servis.id+"')\">Izmeni</button><td><button  class=\"btn btn-info\" onclick=\"obrisiRent('"+servis.id+"')\">Obrisi</button></td></tr>");
			
			});
			$("#ispisiTabelu").append("</table>");
		}
	 }
	if(adminsistem == false){
		$("#ispisiTabelu").append("<table class=\"table table-striped table-hover\" id=\"tabelaRent\" ><tr><th> Name </th><th> City </th><th>Address</th></tr>");
		
		$.each(pom, function(index, servis) {
		$("#tabelaRent").append("<tr><td class=\"hoverName\" onclick=\"visitCar('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.grad+"</td><td > "+servis.adresa+"</td></tr>");
		
		});
		$("#ispisiTabelu").append("</table>");
	
		
	}
	
}
function sortirajRent(){
	 console.log('usao u sortiraj auto');
	 var uslov=$("#sortAuto").val();
	 if(uslov!="none"){
		 $.ajax({
				method:'GET',
				url: "/api/rents/sort/"+uslov,
				success: function(lista){
					if(lista == null){
						console.log('Nema servise')
					}else if(lista.length==0){
						console.log('Nema servise')
					}else{
						ispisiAutoservise(lista);
						
					}
				}
			});
	 }
	}
function findRent(){
	var ispravno = true;
	
	$("#errorName").text("");
	$("#errorKraj").text("");
	$("#errorPocetak").text("");
	
	console.log('usao u findRent');
	var grad=$("#nameRent").val();
	
	if(grad == ""){
		ispravno = false;
		$("#errorName").text(" Fill out this field").css('color', 'red');
	}
	
	var pocetak=$("#pocetak").val();
	if(pocetak == ""){
		ispravno = false;
		$("#errorPocetak").text(" Fill out this field").css('color', 'red');
	}
	var kraj=$("#kraj").val();
	if(kraj == ""){
		ispravno=false;
		$("#errorKraj").text(" Fill out this field").css('color', 'red');
	}
	var date1 = Date.parse(pocetak);
	var date2 = Date.parse(kraj);
	if (date1 > date2) {
		$("#errorKraj").text("Drop-off date must be greater than pick-up date").css('color', 'red');
		ispravno=false;
	}
	console.log(grad + " , "+pocetak+" , "+kraj);

	if(ispravno){
		
		var podatak=grad+"="+pocetak+"="+kraj;
		
		$.ajax({
			type : 'GET',
			url : "/api/rents/findRents/"+podatak,
			success : function(data) {
				if(data == null){
					console.log('Prazno');
					$("#ispisiTabelu").empty();
					 
				}else if(data.length==0){
					console.log('Prazno');
					$("#ispisiTabelu").empty();
					 
				}else{

					console.log('Ima vozila');
					ispisiAutoservise(data);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});
		
	}
}

function findHotels(){
	var ispravno = true;
	
	$("#errorName").text("");
	$("#errorKraj").text("");
	$("#errorPocetak").text("");
	
	var hotel=$("#nameHotel").val();
	var today = new Date().toISOString().split('T')[0];
	
	if(hotel == ""){
		ispravno = false;
		$("#errorName").text(" Fill out this field").css('color', 'red');
	}
	
	var pocetak=$("#hotelCheckIn").val();
	if(pocetak == ""){
		ispravno = false;
		$("#errorPocetak").text(" Fill out this field").css('color', 'red');
	}else if(pocetak < today){
		$("#errorPocetak").text("You can not select the date that passed").css('color', 'red');
		ispravno=false;
		
	}
	var kraj=$("#hotelCheckOut").val();
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
	
	if(ispravno){
		
		$.ajax({
			type : 'POST',
			url : "/api/hoteli/pronadjiHotele/"+hotel,
			contentType : 'application/json',
			dataType : "json",
			data:preuzmiPodatke(),
			success : function(data) {
					
					if(data == null){
						console.log('nisu pronadjeni')
						nemaHotela();
					}else if(data.length  == 0){
						console.log('nisu pronadjeni')
						nemaHotela();
					}else{
						console.log('pronadjeni')
						ispisiHotele(data);
					}	
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("greska pri unosu nove kat");
				   
			}
		});
	}
}

function findFlights()
{
	
	$("#errorDestination").text("");
	$("#errorEndDestination").text("");
	$("#errorBrojOsoba").text("");
	$("#errorDatePoletanja").text("");
	$("#errorDatePovratka").text("");
	
	var startDestination = $("#startDestination").val();
	var endDestination = $("#endDestination").val();
	
	var today = new Date().toISOString().split('T')[0];
	var ispravno = true;
	
	
	var pocetak=$("#datumPoletanja").val();
	if(pocetak == ""){
		ispravno = false;
		$("#errorDatePoletanja").text(" Fill out this field").css('color', 'red');
	}else if(pocetak < today){
		$("#errorDatePoletanja").text("You can not select the date that passed").css('color', 'red');
		ispravno=false;
		
	}
	var tip = $('#selectType').val();
	if(tip == "round-trip")
	{
		var kraj=$("#datumPovratka").val();
		if(kraj == ""){
			ispravno=false;
			$("#errorDatePovratka").text(" Fill out this field").css('color', 'red');
		}
		var date1 = Date.parse(pocetak);
		var date2 = Date.parse(kraj);
		if (date1 > date2) {
			$("#errorDatePovratka").text("Return date must be greater than depart date").css('color', 'red');
			ispravno=false;
		}	
	
	}
	else
	{
		kraj = "nema";
	}
	
	
	var klasa = $('#selectClass').val();
	var brPutnika = $('#brojOsoba').val();
	
	if(isNaN(brPutnika))
	{
		brPutnika = 1;
	}
	
	if(startDestination == ""){
		ispravno = false;
		$("#errorDestination").text(" Fill out this field").css('color', 'red');
	}
	
	if(endDestination == ""){
		ispravno = false;
		$("#errorEndDestination").text(" Fill out this field").css('color', 'red');
	}
	alert(ispravno);
	if(ispravno)
	{
		alert(pretragaToJson(pocetak,kraj,tip,klasa,startDestination,endDestination,brPutnika));

		$.ajax({
					type : 'POST',
					url : "api/letovi/findFlights",
					contentType: "application/json",
					dataType : "json",
					data : pretragaToJson(pocetak,kraj,tip,klasa,startDestination,endDestination,brPutnika),
					
					success : function(data)
					{
						
						
						if(data.length > 0)
						{	
							var maxCena = 0;
							var maxDuration = 0;
							
							
							var letovi = data;
							var filterData = data;
							$('#pretragaLetova').empty();
							var text = '<table class="table table-striped">';
							text += '<thead><tr><th>#IDLeta</th><th>Vreme poletanja/sletanja</th><th>Broj presedanja: </th><th>Trajanje leta</th><th>Kompanija</th><th>Klasa</th><th>Cena</th></tr></thead><tbody>';
							$.each(letovi,function(index,value)
									{
										
										if(maxCena < value.cena)
											maxCena = value.cena;
										
										if(maxDuration < value.duzina)
											maxDuration = value.duzina;
								
										text += '<tr><td>'+value.idLeta+'</td><td>'+value.vremePoletanja+'/'+value.vremeSletanja+'</td>';
										text += '<td>'+value.brojPresedanja+'</td><td>'+value.duzina+'</td><td>'+value.nazivKompanije+'</td><td>'+value.klasa+'</td><td>'+value.cena+'</td>';
										if(imaKorisnika == "ima")
										{
											text += '<td><button type="button" id="'+value.idLeta+'='+value.klasa+'='+value.datumSletanja+'" onclick="book(this)" class="btn btn-primary">Book</button></td>';
										}
										text += '</tr>';
									});
							text += '</tbody></table>';
							
							
							
							$('#pretragaLetova').html(text);
							$("#pretragaDiv").show();
							
							$("#rangePrice").attr({
							       "max" : maxCena,       
							       "min" : 0         
							    });
							$("#rangeDuration").attr({
							       "max" : maxDuration,       
							       "min" : 0         
							    });
							
							$.ajax({
								method:'GET',
								url: "/api/kompanije/all",
								success: function(lista){
									if(lista == null){
										
									}else{
										var text1 = "";
										$('#selectKompanijaFilter').empty();
										$.each(lista,function(index,value)
												{
													text1 += '<option value="'+value.naziv+'">'+value.naziv+'</option>';
												});	
										$('#selectKompanijaFilter').html(text1);
										  $(".filteri").change(function(){
											  filter(letovi);
										  });

									}
								}
							});
							
							
						}
						
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
							alert('greska');
					}
					
				});
	
	}


}

function book(button)
{
	var id = button.id;
	var endDestination = $("#endDestination").val();
	
	window.location = "ReserveFlight.html?id="+id+'='+endDestination;

}



function pretragaToJson(pocetak,kraj,tip,klasa,startDestination,endDestination,brPutnika)
{
	return JSON.stringify(
			{
				"datumPoletanja" : pocetak,
				"datumPovratka" : kraj,
					"tip" : tip,
					"klasa" : klasa,
					"lokPoletanja" : startDestination,
					"lokSletanja" : endDestination,
					"brojLjudi" : brPutnika
			});
	
}











function preuzmiPodatke() {
	
	var kat = JSON.stringify({
		"checkIn":$('#hotelCheckIn').val(),
		"checkOut":$('#hotelCheckOut').val()
	});
	return kat;
}
function ispisiHotele(lista){
	console.log('usao u ispisi hotele u js kad pretrazuje hotele');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	$("#ispisiTabelu").empty();
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> City </th><th> Adress </th><th>Grade</th><th></th><th></th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaHotel").append("<tr><td class=\"hoverName\" onclick=\"hotelProfil('"+servis.id+"')\">"+servis.naziv+"</td><td> "+servis.grad+"</td><td> "+servis.adresa+"</td><td > "+servis.ocena+"</td><td><button  class=\"btn btn-dark\" onclick=\"changeHotel('"+servis.id+"')\">Change</button></td><td><button  class=\"btn btn-dark\" onclick=\"deleteHotel('"+servis.id+"')\">Delete</button></td></tr>");
		});
	 $("#ispisiTabelu").append("</table>");
}
function nemaHotela(){
	$("#ispisiTabelu").empty();
	$("#ispisiTabelu").append("<div id=\"nemaPonuda\"><p><h2>Unfortunately we don't have rigth offer for you.</h2></p></div>");
}
function izmeniRent(id){
	window.location = "izmeniRent.html?id="+id;

}
function obrisiRent(id){

	
	$.ajax({
		type : 'DELETE',
		url : "/api/rents/obrisiRent/"+id,
		success : function(data) {
				console.log('obrisan rent');
				carShow();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	

}

$(document).on("mouseenter", ".hoverName",function(){
			
    $(this).css("color", "purple");
		
	
});
$(document).on("mouseleave", ".hoverName",function(){
	
    $(this).css("color", "black");
		
	
});


//proveriti zasto mi ne radi filter funckija
function filter(data)
{
	var letovi = data;
	
	var klasa = $('#selectKlasaFilter').val();
	var price = $('#rangePrice').val();
	var duration = $('#rangeDuration').val();
	var kompanija = $('#selectKompanijaFilter').val();
	
	
	$('#pretragaLetova').empty();
	var text = '<table class="table table-striped">';
	text += '<thead><tr><th>Vreme poletanja/sletanja</th><th>Broj presedanja: </th><th>Trajanje leta</th><th>Kompanija</th><th>Klasa</th><th>Cena</th></tr></thead><tbody>';
	$.each(letovi,function(index,value)
			{
				
				
				
				if(klasa == value.klasa && value.duzina <= duration && value.cena <= price && value.nazivKompanije == kompanija)
				{
					
					text += '<tr><td>'+value.vremePoletanja+'/'+value.vremeSletanja+'</td>';
					text += '<td>'+value.brojPresedanja+'</td><td>'+value.duzina+'</td><td>'+value.nazivKompanije+'</td><td>'+value.klasa+'</td><td>'+value.cena+'</td><td><button type="button" id="'+value.idLeta+'='+value.klasa+'" onclick="book(this)" class="btn btn-primary">Book</button></td>';
					text += '</tr>';
				}
			});
	text += '</tbody></table>';
	$('#pretragaLetova').html(text);
	

}	    
   


$(document).ready(function(){
	   $("li#odjava").click(function(){
	    	console.log("usao u funkciju");
	    	sessionStorage.setItem("ulogovan",null);
	    	$.ajax({
	    		method:'POST',
	    		url: "/api/korisnici/logout",
	    		success: function(ime){ 			
					window.location.href = 'mainPage.html';

	    		},
	    		error : function(XMLHttpRequest, textStatus, errorThrown) {
	    			alert("Greska");
	    		}	});
	 
	    
	    });
	   
	   
	   
	
   
	
	   
	   	
});
function visitCar(id){
	console.log('Usao u visitCar, dobio id: '+id);
	window.location="profileCar.html?id="+id;
}
function hotelProfil(id){
	console.log('usao u hotel profil');
	window.location = "profileHotel.html?id="+id;
}

function visitCompany(id)
{
	console.log('Usao u visitCar, dobio id: '+id);
	window.location="profilCompany.html?id="+id;

}


function addPlane(){
	window.location = "newAirplaneCompany.html";
}
function addHotel(){
	window.location = "newHotel.html";
	
}
function addCarHire(){
	window.location = "newRentACar.html";
		
}

function bonusPopust(){
	$("#ispisiSelect").hide();
	$("#divBodPopust").show();
	  
	$("#ispisiTabelu").empty();
	 $.ajax({
			method:'GET',
			url: "/api/special/all",
			success: function(lista){
				if(lista == null){
					console.log('Nema popusta')
				}else if(lista.length==0){
					console.log('Nema popusta')
				}else{
					ispisiProcente(lista);
					
				}
			}
		});

	
}
function ispisiProcente(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	console.log('dosao u ispisi procente')
    $("#sortHotele").hide();
	$("#sortCar").hide();
	$("#sortAvione").hide();
	
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaProcenti\" ><tr><th> Number of user points </th><th> Percentage</th><th></th></tr>");
		$.each(pom, function(index, servis) {
			$("#tabelaProcenti").append("<tr><td>"+servis.bodovi+"</td><td>"+servis.vrijednost+"</td><td><button type=\"button\" onclick=\"deleteProcenat("+servis.id+")\" class=\"btn btn-light\">Remove</button></td></tr>");
		});
	 $("#ispisiTabelu").append("</table>");
	 dodajNovogAdmina();
}
function deleteProcenat(id){
	$.ajax({
		type : 'POST',
		url : "/api/special/obrisiSpecijalni/"+id,
		success : function(data) {
			
				console.log('obrisana popust specijalni');
				bonusPopust();
			
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	
	
}
$(document).on('submit','.bodpopust',function(e){
	e.preventDefault();	
	var bodovi=$('#brojBodova').val();
	var procenat=$('#procenat').val();
	let ispravno = true;
	
	if(!bodovi){
		$("#errorBodovi").text("You need to fill out this field.").css('color', 'red');
		ispravno = false;		
	}
	if(isNaN(bodovi)){
		ispravno = false;
		$("#errorBodovi").text("You need to enter digits.").css('color', 'red');
		
	}
	if(bodovi<=0){
		ispravno = false;
		$("#errorBodovi").text("Number of points must be greater than 0.").css('color', 'red');
		
	}
	
	if(!procenat){
		ispravno = false;
		$("#errorPopust").text("You need to fill out this field.").css('color', 'red');
		
	}
	if(isNaN(procenat)){
		ispravno = false;
		$("#errorPopust").text("You need to enter digits.").css('color', 'red');
		
	}
	if(procenat<=0){
		ispravno = false;
		$("#errorPopust").text("Number of percentage must be greater than 0.").css('color', 'red');
	}
	
	if(ispravno == true){

	
	$.ajax({
		type : 'POST',
		url : "/api/special/newspecial/",
		contentType : 'application/json',
		dataType : "json",
		data:toJSON(),
		success : function(data) {
			if(data.bodovi == 0){
				alert('Discount for the selected number of user points exists.');

			}else{
				console.log('tu sam');
				pomocnaFa();		
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove sobe");
			
		}
	});
	}else{
		alert('You need to fill out all the fields correctly.');
	}
});
function pomocnaFa(){
	console.log('dosao u ispisi uspjeh');
	$('#procenat').val('');
	$('#brojBodova').val('');
	$("#errorBodovi").text('');
	$("#errorPopust").text('');
	bonusPopust();
}

function toJSON(){
	return JSON.stringify({
		"vrijednost" : $('#procenat').val(),			
		"bodovi" : $('#brojBodova').val()
	});
}
function ispisiUspjeh(){
	console.log('stigao ovdje');
}

function adminSistem(){
	$("#divBodPopust").hide();
	
	$("#ispisiSelect").show();
	$("#ispisiTabelu").empty();
	 $.ajax({
			method:'GET',
			url: "/api/korisnici/vratiDefinisanePopuste",
			success: function(lista){
				if(lista == null){
					console.log('Nema admina')
				}else if(lista.length==0){
					console.log('Nema admina')
				}else{
					ispisiAdmineSistema(lista);
					
				}
			}
		});
	
}
function ispisiAdmineSistema(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	console.log('dosao u ispisi adminaSistema')
    $("#sortHotele").hide();
	$("#sortCar").hide();
	$("#sortAvione").hide();
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaAdmini\" ><tr><th> Name </th><th> Surname</th></tr>");
		$.each(pom, function(index, servis) {
			$("#tabelaAdmini").append("<tr><td>"+servis.ime+"</td><td>"+servis.prezime+"</td></tr>");
		});
	 $("#ispisiTabelu").append("</table>");
	 dodajNovogAdmina();
}
function dodajNovogAdmina(){
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
	
}
function izmjeniAdmineSistema(){
	var idUser =$('#adminSelect').val();
	console.log('dosao u izmjeni adminaSistema '+idUser)
	 $.ajax({
			method:'POST',
			url: "/api/korisnici/newAdminSistem/"+idUser,
			success: function(lista){
				adminSistem();
			}
		});
}