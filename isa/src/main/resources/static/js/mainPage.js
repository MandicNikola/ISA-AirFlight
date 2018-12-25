function onLoad(){
	var user = sessionStorage.getItem("ulogovan");
	console.log(user);
	console.log('provera logovanja');
	
	if(user!=null && user!="null" && user!="undefined") {
		console.log("Postoji ulogovan korisnik");
		$("#logovanje").hide();
		var korisnik = JSON.parse(user);
		$("#imeKorisnika").text(korisnik.ime);
	
	}else{
		$("#prikazKorisnika").hide();
		$("#odjava").hide();
			
	}
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();
	$("#reserveCar").hide();
	$("#reserveHotel").hide();

}

function planeShow(){
	$("#pozadinaAvion").show();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();
	$("#ispisiTabelu").empty();
	$("#reserveCar").hide();
	$("#reserveHotel").hide();

}
function hotelShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").show();
	$("#ispisiTabelu").empty();
	$("#reserveHotel").show();
	$("#reserveCar").hide();
	
	$.ajax({
		method:'GET',
		url: "/api/hoteli/all",
		success: function(lista){
			if(lista == null){
				console.log('Nema servise');
			}else{
				ispisiHotele(lista);
			}
		}
	});
}

function ispisiHotele(lista){
	console.log('usao u ispisi hotele u js');
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> Promotional description</th><th>Grade</th><th></th><th></th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaHotel").append("<tr><td class=\"hoverName\" onclick=\"hotelProfil('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.opis+"</td><td > "+servis.ocena+"</td><td><button  class=\"btn btn-dark\" onclick=\"changeHotel('"+servis.id+"')\">Change</button></td><td><button  class=\"btn btn-dark\" onclick=\"deleteHotel('"+servis.id+"')\">Delete</button></td></tr>");
		});
	 $("#ispisiTabelu").append("</table>");
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
	$("#pozadinaAuto").show();
	$("#reserveCar").show();
	$("#reserveHotel").hide();

	$("#pozadinaHotel").hide();	
	 $("#ispisiTabelu").empty();
		
	
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
	 
	 $("#ispisiTabelu").append("<table class=\"table table-striped table-hover\" id=\"tabelaRent\" ><tr><th> Name </th><th> Promotional description</th><th></th><th></th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaRent").append("<tr><td class=\"hoverName\" onclick=\"visitCar('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.opis+"</td><td><button  class=\"btn btn-info\" onclick=\"izmeniRent('"+servis.id+"')\">Izmeni</button><td><button  class=\"btn btn-info\" onclick=\"obrisiRent('"+servis.id+"')\">Obrisi</button></td></tr>");
			
		});
	 $("#ispisiTabelu").append("</table>");
	 
	
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
				}else if(data.length==0){
					console.log('Prazno');
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
	
	if(hotel == ""){
		ispravno = false;
		$("#errorName").text(" Fill out this field").css('color', 'red');
	}
	
	var pocetak=$("#hotelCheckIn").val();
	if(pocetak == ""){
		ispravno = false;
		$("#errorPocetak").text(" Fill out this field").css('color', 'red');
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
	$("#ispisiTabelu").append("<table class=\"table table-striped\" id=\"tabelaHotel\" ><tr><th> Name </th><th> Promotional description</th><th>Grade</th><th></th><th></th></tr>");
		
		$.each(pom, function(index, servis) {
			$("#tabelaHotel").append("<tr><td class=\"hoverName\" onclick=\"hotelProfil('"+servis.id+"')\">"+servis.naziv+"</td><td > "+servis.opis+"</td><td > "+servis.ocena+"</td><td><button  class=\"btn btn-dark\" onclick=\"changeHotel('"+servis.id+"')\">Change</button></td><td><button  class=\"btn btn-dark\" onclick=\"deleteHotel('"+servis.id+"')\">Delete</button></td></tr>");
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
		type : 'POST',
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

function addPlane(){
	window.location = "newHotel.html";
}
function addHotel(){
	window.location = "newHotel.html";
	
}
function addCarHire(){
	window.location = "newRentACar.html";
	
	
}