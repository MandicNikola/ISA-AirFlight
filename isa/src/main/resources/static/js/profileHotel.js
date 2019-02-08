/**
 * 
 */

function sakrijAdminovo(){
	$("#admin").hide();
	$("#adminKat").hide();
	$("#adminAdditional").hide();
	$("#adminDisc").hide();
	$("#adminPersonal").hide();
	$("#adminPass").hide();
	$("#config").hide();
	$("#sistemPopust").hide();
	$("#business").hide();
	
}
function prikaziAdminovo(){
	$("#admin").show();
	$("#adminKat").show();
	$("#adminAdditional").show();
	$("#adminDisc").show();
	$("#adminPersonal").show();
	$("#adminPass").show();
	$("#price").show();
	$("#config").show();
	$("#sistemPopust").show();
	$("#business").show();
	$("#reservation").hide();
	$("#fast").hide();
	$("#rooms").show();	
	
}
function prikaziAdminaSistema(){
	$("#admini").show();	
}
function sakrijAdminaSistema(){
	$("#admini").hide();	
	$("#rooms").hide();	

}
function prikaziOstalima(){
	$("#rooms").show();	
	$("#price").show();
	
}

$(document).ready(function($) {
	var user = sessionStorage.getItem("ulogovan");
	console.log('dosao u gornji ready');
	var dnevnichart;
	var sedmicnichart;
	var mjesecnichart;
	sakrijAdminovo();
	sakrijAdminaSistema();
	if(user!=null && user!="null" && user!="undefined") {
			console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);
			var podatak = window.location.search.substring(1);
			
			var id= podatak.split("=")[1];
			if(korisnik.tip == 'ADMIN_HOTEL'){
				if(korisnik.servis==id){
					console.log('servis je u korisnika '+korisnik.servis);
					prikaziAdminovo();
					
				}
				
			}else if(korisnik.tip == 'ADMIN_SISTEM'){
				prikaziAdminaSistema();
			}else{
				prikaziOstalima();
			}
	
	}else{
		prikaziOstalima();
		
	}	
	
	$( "#dnevniDugme" ).click(function() {
		var podatak = window.location.search.substring(1);
		console.log("Usao u showGraf");
		var niz= podatak.split("=");
		var id= niz[1];
		
		var godina = $("#godinaChart").val();
		var mjesec = $("#mjesecChart").val();
		if(isNaN(godina)){
			console.log('nije broj');
			alert('Enter correct year');
		}else if(godina.length!=4){
			console.log('duzina ne valja');
			alert('Enter correct year');
		}else if(godina < 2016){
			alert('Year must be greater than 2016');
		}else{
			console.log('sve okej godina je '+godina);
		
			
			$.ajax({
				method:'GET',
				url: "/api/rezervacijehotel/dnevnigrafik/"+id+"/brojMjeseci/"+mjesec+"/godina/"+godina,
				success: function(lista){
					if(lista == null){
						console.log('Nema podataka');
					}else if(lista.length==0){
						console.log('Nema podataka');
					}else{
						console.log("ima podataka");
						 	
						iscrtajGrafik(lista,dnevnichart,"myChart","Number of reservations per day");
						
					}
				}
			});

		}
	});

	
	$("#mjesecniDugme").click(function() {
		var podatak = window.location.search.substring(1);
		console.log("Usao u showGraf");
		var niz= podatak.split("=");
		var id= niz[1];
		
		var godina = $("#yearChart").val();
		var mjesec = $("#monthChart").val();
		if(isNaN(godina)){
			console.log('nije broj');
			alert('Enter correct year');
		}else if(godina.length!=4){
			console.log('duzina ne valja');
			alert('Enter correct year');
		}else if(godina < 2016){
			alert('Year must be greater than 2016');
		}else{
			console.log('sve okej godina je '+godina);
		
		$.ajax({
			method:'GET',
			url: "/api/rezervacijehotel/sedmicnigrafik/"+id+"/brojMjeseci/"+mjesec+"/godina/"+godina,
			success: function(lista){
				if(lista == null){
					console.log('Nema podataka');
				}else if(lista.length==0){
					console.log('Nema podataka');
				}else{
					console.log("ima podataka");
					iscrtajGrafik(lista,sedmicnichart,"weekChart","Number of reservations per week");
					
				}
			}
		});
		}
	});

		$(document).on('click','#potvrdiBtn1',function(e){
			e.preventDefault();	
			
			var oldLoz =  $('#lozinkaOld').val();
			console.log(oldLoz);
			
			var loz1 = $('#lozinka1').val();
			console.log(loz1);
			var loz2 = $('#lozinka2').val();
			console.log(loz2);
			
						$.ajax({
							type : 'GET',
							url : "/api/korisnici/changePass/"+oldLoz+"/lozinka1/"+loz1+"/lozinka2/"+loz2,
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

		


		
		$( "#godisnjiDugme" ).click(function() {
			var podatak = window.location.search.substring(1);
			console.log("Usao u showGraf");
			var niz= podatak.split("=");
			var id= niz[1];
			
			var godina = $("#yearGodisnji").val();
			
			if(isNaN(godina)){
				console.log('nije broj');
				alert('Enter correct year');
			}else if(godina.length!=4){
				console.log('duzina ne valja');
				alert('Enter correct year');
			}else if(godina < 2016){
				alert('Year must be greater than 2016');
			}else{
				console.log('sve okej godina je '+godina);
			
			$.ajax({
				method:'GET',
				url: "/api/rezervacijehotel/mjesecnigrafik/"+id+"/godina/"+godina,
				success: function(lista){
					if(lista == null){
						console.log('Nema podataka');
					}else if(lista.length==0){
						console.log('Nema podataka');
					}else{
						console.log("ima podataka");
						iscrtajGrafik(lista,mjesecnichart,"mjesecniChart","Number of reservations per month");
						
					}
				}
			});
			
			}

	
	
	});

	function iscrtajGrafik(lista,chart,id,title){
		var labele=new Array();
		var vrednosti=new Array();
		 for (var i = 0; i < lista.length; i++) {
			 var datum = lista[i].datum.split('T')[0];
		 		labele.push(datum);
		 		vrednosti.push(lista[i].broj);
		  	}
		
		var ctx = $("#"+id);
		console.log('usao u iscrtaj grafik');
		if(chart != null){ 
			chart.destroy();
			}

		chart = new Chart(ctx, {
		    type: 'bar',
		    data: {
		        labels: labele,
		        datasets: [{
		            label: "Number of reservations",
		            data: vrednosti,
		            borderWidth: 1,
		            borderColor: 'rgba(214, 111, 239,1)',
		            backgroundColor: 'rgba(220, 146, 239,1)'
		        }]
		    },
		    options: {
		        scales: {
		        	yAxes: [{
		                ticks: {
		                    beginAtZero:true
		                }
		            }]
		        },
		        title: {
		            display: true,
		            text: title,
		            fontSize: 24
		        }
		    }
		});		
	}

});

function onLoad(){
	$("#konfig").hide();
	$("#sobe").hide();
	$("#cijene").hide();
	$("#divPopust").hide();
	$("#rezervacije").hide();
	$("#adminStrana").hide();
	$("#promjenaLozinke").hide();
	$("#dodajPopust").hide();
	$("#izvestaj").hide();
	//?
	$("#dnevniGrafik").hide();
	$("#fastDiv").hide();
	
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
	var grad = hotel.grad;
	$("#naziv").text('Welcome to '+hotel.naziv);
	$("#opis").empty();
	$("#opis").append(hotel.opis);
	$("#ocijenaHotela").text('Rating of hotel is:' + hotel.ocena);
	
	$("#adresa").append(hotel.adresa+", "+grad);
	
	var adresa=	adr.replace(" ","%20");
	var gradic = grad.replace(" ","%20");	
    var adresagrad = adresa+"%20"+gradic;
	$("#adresa").append("<div class=\"mapouter\"><div class=\"gmap_canvas\"><iframe width=\"600\" height=\"500\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q="+adresagrad+"&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe><a href=\"https://www.embedgooglemap.net\">embedgooglemap.net</a></div><style>.mapouter{text-align:right;height:500px;width:600px;}.gmap_canvas {overflow:hidden;background:none!important;height:500px;width:600px;}</style></div>")
	
	var adresa = window.location.search.substring(1);
	var url = adresa.split('=');
	var id = url[1];
	
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
	var user = sessionStorage.getItem("ulogovan");
	
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#sobe").empty();
	 $("#sobe").show();
	 var adminhotela = false;
	 if(user!=null && user!="null" && user!="undefined") {
			console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);
			var adresa = window.location.search.substring(1);
			var id = adresa.split('=')[1];
			
		if(korisnik.tip == 'ADMIN_HOTEL'){
			if(korisnik.servis==id){
				
			console.log(' ADMIN DOSAO DA VIDI SOBE');
			adminhotela = true;
	 
	 $("#sobe").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Price per night</th><th>Floor</th><th>Balcony</th><th></th><th></th><th></th></tr>");
	 //id, String tip, double ocena, int sprat, int kapacitet, double cijena, String balkon,boolean imarez) {
			
		$.each(pom, function(index, data) {
			if(data.imarez){
				if(data.balkon == 'da'){
					 $("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td>"+data.sprat+"</td><td><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" ></td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" disabled = \"disabled\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" disabled = \"disabled\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
				}else{
					$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td>"+data.sprat+"</td><td><input type=\"checkbox\"  disabled=\"disabled\" ></td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" disabled = \"disabled\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" disabled = \"disabled\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
						
				}
			}else{
				console.log('dosao ovdje');
				if(data.balkon == 'da'){
						$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td>"+data.sprat+"</td><td><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" ></td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
				}else{
					$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td>"+data.sprat+"</td><td><input type=\"checkbox\"  disabled=\"disabled\" ></td><td><button type=\"button\" onclick=\"changePrice("+data.id+","+data.cijena+")\" class=\"btn btn-light\">Change the price</button></td><td><button type=\"button\" onclick=\"deleteRoom("+data.id+")\" class=\"btn btn-light\">Delete</button></td><td><button type=\"button\" onclick=\"changeRoom("+data.id+")\" class=\"btn btn-light\">Change</button></td></tr>");
					
				}	
			}
			
		});
		
	 $("#sobe").append("</table>");
			}
		}		
	 }
	 if(adminhotela == false){
		 console.log('NIJE ADMIN DOSAO DA VIDI SOBE');
		 var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
		 $("#sobe").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Price for night</th><th>Floor</th><th>Grade</th><th>Balcony</th></tr>");
			console.log('dosao ovdje'+pom.length);
			$.each(pom, function(index, data) {
				if(data.balkon == 'da'){
					
					$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td><td><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" ></td></tr>");			
				}else{
					$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.cijena+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td><td><input type=\"checkbox\"  disabled=\"disabled\" ></td></tr>");			
					
				}
			});
		 $("#sobe").append("</table>");
		
		 
	 }
	 $("#sobe").show();
	 
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
		$("#formaKat").append("<input  type = \"text\" class=\"form-control\" id=\"katNaziv\" placeholder=\"Configuration name\"><span id = \"errorKatNaziv\"></span>"); 	
		$("#formaKat").append("</div><div><button type=\"submit\" class=\"btn btn-default\">Add</button></div></form>");
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
					resetProfil();
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska ");
			   
		}
	});
	
	
}

$(document).on('submit','.konfiguracija',function(e){
	e.preventDefault();
	
	var naziv=$('#katNaziv').val();
	
	let ispravno = true;
	if(!naziv){
		ispravno = false;
		$("#errorKatNaziv").text("You have to enter name a category name.").css('color', 'red');
	}
	if(ispravno == true){
		var adresa = window.location.search.substring(1);
		var id = adresa.split('=')[1];
		
	$.ajax({
		type : 'POST',
		url : "/api/kategorije/kat/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.naziv != "null"){
					alert('Postoji izabrana kategorija');
					
				}else{
					//alert('dodavanje super');
					dodajHoteluKategoriju(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove kat");
			   
		}
	});
	}else{
		alert("Enter the category name");
	}
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
	console.log('stigao ovdje gore');
	
	var pom=window.location.search.substring(1);
	var pom1 = pom.split("=");
	if(pom1.length >= 3){
		console.log('stigao ovdje');
		$("#reservation").show();
		$("#fast").show();
		
	}else{
		$("#reservation").hide();
		$("#fast").hide();
		
	}
	$("#rezervacije").hide();

	$("#sobe").hide();
	$("#cijene").hide();
	$("#divPopust").hide();
	$("#konfig").hide();
	$("#adminStrana").hide();
	$("#promjenaLozinke").hide();
	$("#dodajPopust").hide();
	$("#izvestaj").hide();
	$("#ispisiTabelu").hide();
	$("#fastDiv").hide();
	

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
		$("#fastDiv").hide();
		$("#izvestaj").hide();
			
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
		$("#izvestaj").hide();
		$("#fastDiv").hide();
		
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
		$("#fastDiv").hide();
		
		$("#rezervacije").show();
		$("#korak").empty();
		$("#korakDodatne").empty();
		$("#reserveHotel").show();
		$("#adminStrana").hide();
		$("#izvestaj").hide();
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
		$("#izvestaj").hide();
		$("#fastDiv").hide();
		
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
		$("#izvestaj").hide();
		$("#fastDiv").hide();
		
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
		$("#izvestaj").hide();
		$("#fastDiv").hide();
		
    });
    $("#sistemPopust").click(function(){
    	console.log('dosao u popust');
    	showRoomsForDiscounts();
    	$("#informacije").hide();
    	$("#divPopust").show();
    	$("#poruka").hide();
    	$("#postojeciPopust").hide();
    	$("#ispisiTabelu").hide();
		$("#sobe").hide(); 
		$("#cijene").hide();
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
		$("#izvestaj").hide();
		$("#fastDiv").hide();
		
    });
    $("#business").click(function(){
    	console.log('dosao u izvjestaj');
    	$("#informacije").hide();
    	$("#divPopust").hide();
    	$("#ispisiTabelu").hide();
		$("#sobe").hide(); 
		$("#cijene").hide();
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
		$("#izvestaj").show();
		dodajIzvjestaj();
		$("#fastDiv").hide();
		
    });
    
    $("#fast").click(function(){
    	console.log('dosao u fast rez');
    	$("#informacije").hide();
    	$("#divPopust").hide();
    	$("#ispisiTabelu").hide();
		$("#sobe").hide(); 
		$("#cijene").hide();
		$("#konfig").hide();
		$("#rezervacije").hide();
		$("#adminStrana").hide();
		$("#promjenaLozinke").hide();
		$("#dodajPopust").hide();
		$("#izvestaj").hide();
		$("#fastPonuda").empty();
		$("#fastDiv").show();
		$("#formaFast").show();
		
    });

});
function izlistajFast(){
	$("#fastPonuda").empty();
	var adresa = window.location.search.substring(1);
	var pom = adresa.split('=');
	
	var id = adresa.split('=')[1];
	var kraj=$('#checkoutFast').val();
	var pocetak = pom[3];
	
	console.log(kraj);
	 $("#fastPonuda").empty();

	$("#fastPonuda").append("<p><h2> Offers: </h2></p>");
	$("#fastPonuda").append("<p>FROM <span id=\"pocetakFast\">"+pocetak+"</span> TO <span id=\"krajFast\">"+kraj+"</span></p>");

	$.ajax({
		method:'GET',
		url: "/api/rooms/getFast/"+id+"/checkout/"+kraj+"/checkin/"+pocetak,
		success: function(data){
			if(data == null){
				nemaFast();
			}else if(data.length == 0){
				nemaFast();
			}else{
				ispisiFast(data);
				
			}
		}
	});
	
}
function nemaFast(){
	 $("#fastPonuda").empty();
	 $("#fastPonuda").show();
	 $("#fastPonuda").append("<div id = \"nemaFast\"><p>There are no rooms with a defined discount for user number of points</p></div>");
}
function ispisiFast(lista){
	console.log('stigao u ispisiFast');
	//$("#fastPonuda").empty();
		
	$("#fastPonuda").show();
		
	
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	// RoomDTO(room.getId(),room.getTip(),room.getOcjena(),room.getSprat(),room.getKapacitet(),room.getCijena(),room.getBalkon()		
	 $("#fastPonuda").append("<table class=\"table table-hover\" id=\"tabelaSobeFast\" ><tr><th>Room type </th><th>Capacity</th><th>Floor</th><th>Average Rating</th><th>Price per night</th><th>Discount (%)</th><th>Balcony</th><th></th><th></th></tr>");
		
		$.each(pom, function(index, data) {
			var prenos = data.id +"."+data.bodoviPopusta+"."+data.vrijednostPopusta;
			console.log(prenos);
			
			var dodatneusluge = '';
			var sveusluge  = data.nazivUsluga;
			$.each(sveusluge, function(index, podatak) {
				duzina = sveusluge.length-1;
				if(index !=duzina){
						dodatneusluge += podatak+"*";
					
					}else{
						dodatneusluge += podatak;
					
					}
				});
			
			console.log(dodatneusluge);
			if(data.balkon == 'da'){
				if(data.imaNazive){
					$("#tabelaSobeFast").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td><td>"+data.cijena+"</td><td>"+data.vrijednostPopusta+"</td><td><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" ></td><td><button  class=\"btn btn-info\" onclick=\"prikaziUkljucene('"+dodatneusluge+"')\">Included additional services</button></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"rezervisiFast('"+prenos+"')\">Reserve</button></td></tr>");
				}else{
					$("#tabelaSobeFast").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td><td>"+data.cijena+"</td><td>"+data.vrijednostPopusta+"</td><td><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" ></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"rezervisiFast('"+prenos+"')\">Reserve</button></td></tr>");
					
				}
	
			}else{
				
					if(data.imaNazive){
						        console.log('usao ovdje');                                                                                                                                                                                                                                                                            
						$("#tabelaSobeFast").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td><td>"+data.cijena+"</td><td>"+data.vrijednostPopusta+"</td><td><input type=\"checkbox\" disabled=\"disabled\" ></td><td><button  class=\"btn btn-info\" onclick=\"prikaziUkljucene('"+dodatneusluge+"')\">Included additional services</button></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"rezervisiFast('"+prenos+"')\">Reserve</button></td></tr>");
					}else{
				        console.log('usao u donje ovdje');                                                                                                                                                                                                                                                                            
						
						$("#tabelaSobeFast").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td><td>"+data.cijena+"</td><td>"+data.vrijednostPopusta+"</td><td><input type=\"checkbox\" disabled=\"disabled\" ></td><td><button id=\"buttonID\" class=\"btn btn-info\" onclick=\"rezervisiFast('"+prenos+"')\">Reserve</button></td></tr>");
						
					}	
				
			}
		});
		
	 $("#fastPonuda").append("</table><div class=\"container\" id = \"ukljuceneUsluge\"></div>");

	
}
function prikaziUkljucene(lista){
	console.log('dosao ovdje');
	var pom = lista.split('*');
	$("#ukljuceneUsluge").empty()
	
	$.each(pom, function(index, podatak) {
		var broj = index+1;
		$("#ukljuceneUsluge").append("<p> "+broj+". "+podatak+"</p>")

	});

	console.log(lista);
	
}
function rezervisiFast(sobapopust){
	var pocetak=$('#pocetakFast').text();
	var kraj=$('#krajFast').text();
	var info = pocetak+"*"+ kraj;	
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	console.log(info);
	console.log(sobapopust);
	var bodovi = sobapopust.split('.')[1];
	console.log(bodovi);
	var pom = adresa.split('=');
	
	var idRez = pom[2];
	console.log(idRez);
	 $.ajax({
			type : 'POST',
			url : "/api/rooms/rezervisiFast/"+info+"/sobapopust/"+sobapopust+"/idhotel/"+id+"/idRez/"+idRez,
			success : function(povratna) {
						if(povratna==""){
							console.log('neuspjesno');
							alert('Room is not more available. Please choose again.');
							ponoviFast();
						}else{
							dodajUseruBrzu(povratna,bodovi);		
							console.log('uspjesno');
						}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
			});
	
}
function ponoviFast(){
	$("#fastDiv").show();
	$("#fastPonuda").empty();
	
}
function dodajUseruBrzu(data,bodovi){
	 console.log('dosao je da doda rez korisniku');
	 console.log(bodovi);
	 var rezervacija= JSON.stringify(data);
	  $.ajax({
			type : 'POST',
			url : "/api/korisnici/addRezSobe/"+bodovi,
			contentType : "application/json",
			data: rezervacija,
			dataType : 'json',		
			success : function() {
				uspjesnaFast(data);
					
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log('usao u gresku');
			
			}
			});
	
}
function uspjesnaFast(data){
	console.log('usao u ispisi Uspjesno');
	$("#formaFast").hide();
	$("#fastPonuda").empty();
	$("#reservation").hide();
	$("#fast").hide();
	
	$("#fastPonuda").append("<div id= \"obavj\"><p>You have successfully made a reservation.</p><p>Total price:"+data.cijena+"</p><p>We are looking forward to have you as our guests</p></div>");
	var adresa = window.location.search.substring(1);
	// da li ide da rezervise rent a car
	var flagdalje  = adresa.split('=')[5];	
	if(flagdalje == 1){
		var idRezervacije = adresa.split('=')[2];
		var  datumSletanja = adresa.split('=')[3];
		var lokacija = adresa.split('=')[4];
		var brojKarata = adresa.split('=')[6];
		window.location = "redirekcija.html?id="+"rent"+'='+idRezervacije+"="+lokacija+"="+datumSletanja+"="+"0"+"="+brojKarata;
	}

}
function dodajIzvjestaj(){
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
				ispisiIzvjestajHotel(hotel);
				
			}
		}
	});
	
}

function ispisiIzvjestajHotel(hotel){
	$("#prosecnaOcijena").append(hotel.ocena);
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
				ispisiSobeZaIzvjestaj(lista);
				
			}
		}
	});

}
function ispisiSobeZaIzvjestaj(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#izvestajSoba").empty();
	 $("#izvestajSoba").show();
	//public RoomDTO(Long id, String tip, int kapacitet, int sprat,boolean imapopust) 
			
	 $("#izvestajSoba").append("<table class=\"table table-hover\" id=\"tabelaSobaIzv\" ><tr><th>Room type </th><th>Capacity</th><th>Floor</th><th>Average Rating</th></tr>");
		
		$.each(pom, function(index, data) {
		
				$("#tabelaSobaIzv").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.sprat+"</td><td>"+data.ocena+"</td></tr>");
			
		});
		
	 $("#izvestajSoba").append("</table>");
	    
	
}

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
	
	$("#cijeneDodatne").empty();
	var lista = data == null ? [] : (data instanceof Array ? data : [ data ]);
		

	var user = sessionStorage.getItem("ulogovan");
	
	 var adminhotela = false;
	 if(user!=null && user!="null" && user!="undefined") {
		 	console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);
		if(korisnik.tip == 'ADMIN_HOTEL'){
			var adresa = window.location.search.substring(1);
			var id = adresa.split('=')[1];			
			if(korisnik.servis==id){
				
				adminhotela = true;
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
		} 
		}
	 if(adminhotela == false){
		 $("#cijeneDodatne").append("<table class=\"table table-hover\" id=\"tblDodatne\" ><tr><th>Service</th><th>Price</th><th>Discount</th></tr>");
			
			$.each(lista, function(index, usluga) {
				
					console.log(usluga.konfiguracija);
				if(usluga.konfiguracija == 'da'){
					console.log('dosao u dodavanje za uslugu');
					$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td class=\"hoverName\">"+usluga.cena+"</td><td class=\"hoverName\">"+usluga.popust+"</td></tr>");
				}else{
					$("#tblDodatne").append("<tr class=\"thead-light\"><td class=\"hoverName\">"+usluga.naziv+"</td><td class=\"hoverName\">"+usluga.cena+"</td><td class=\"hoverName\"></td></tr>");
						
				}
			});
			$("#cijeneDodatne").append("</table>");
		 
	 }
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
	console.log('datum je  '+data.datum_primene);
	var dat1 = data.datum_primene.split('T')[0];
	
	$("#datCijene").empty();
	$("#datCijene").append("<i class=\"glyphicon glyphicon-calendar\"> </i> Effective date : "+dat1);

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
		$("#ispisiTabelu").append("</select><div class=\"container\"><input  type = \"number\"  min=\"1\" max=\"99\" class=\"form-control\" id=\"popust\" placeholder=\"discount in %\"><span id=\"errorPopust\"></span></div><div><button id=\"dugmePopustHotel\"  class=\"btn btn-info\" onclick=\"popustFunkcija()\"  >Change</button></div></div>");

}
function popustFunkcija(){
	
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	var vr =$('#popust').val();
	var idUsluga =$('#dodatne').val();
	console.log("izbor je "+idUsluga);
	
	let ispravno = true;
	
	if(!vr){
		$("#errorPopust").text("You need to fill out this field.").css('color', 'red');
		ispravno = false;		
	}
	if(isNaN(vr)){
		ispravno = false;
		$("#errorPopust").text("You need to enter digits.").css('color', 'red');
		
	}
	if(vr<=0){
		ispravno = false;
		console.log('usao ovdje');
		$("#errorPopust").text("Value of discount must be greater than 0.").css('color', 'red');
		
	}
	
	var pom = idUsluga+"-"+vr+"-"+id;
	if(ispravno == true){
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

	}else{
		alert('The discount must be a non-negative number.')
	}
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
	    $("#korakDodatne").append("<p><button type=\"button\" onclick = \"povratakSobe()\" class=\"btn btn-outline-secondary\">Back</button><button onclick = \"odustaniRez("+niz+")\" type=\"button\" class=\"btn btn-success\">Cancel</button><button onclick = \"zavrsiRezBezUsluga("+niz+")\" type=\"button\" class=\"btn btn-success\">Finish</button></p>")
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
	    $("#korakDodatne").append("<p><button type=\"button\" onclick = \"povratakSobe()\" class=\"btn btn-outline-secondary\">Back</button><button onclick = \"odustaniRez("+niz+")\" type=\"button\" class=\"btn btn-success\">Cancel</button><button onclick = \"zavrsiRez("+niz+")\" type=\"button\" class=\"btn btn-success\">Finish</button></p>")
	   
	   
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
    var idRez = adresa.split('=')[2];
    var brKarata = adresa.split('=')[6];
    if(osobe > brKarata){
    	console.log(' nedovoljno karata');
    	alert('Please choose again. Number of people can not be greater then number of beds.')
    	
    	nemogucaRez();
    }else{
	  
	  $.ajax({
			type : 'POST',
			url : "/api/hoteli/rezervisi/"+info+"/sobe/"+nizSoba+"/nizUsluga/"+listaUsl+"/idHotela/"+id+"/idRez/"+idRez,
			success : function(povratna) {
						if(povratna == ""){
							alert('Some of selected rooms are not more available. Please choose again.');
							nemogucaRez();
							console.log('neuspjesno');
						}else{
							dodajUseruRez(povratna);
							console.log('uspjesno');
						}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
			});
    }
}
function nemogucaRez(){
	$("#reserveHotel").show();		
	$("#korak").hide();
	$("#korakDodatne").hide();
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
	
		var idRez = adresa.split('=')[2];
	    var brKarata = adresa.split('=')[6];
	    if(osobe > brKarata){
	    	alert('Please choose again. Number of people can not be greater then number of beds.')
	    	
	    	nemogucaRez();
	    }else{
		
	  $.ajax({
			type : 'POST',
			url : "/api/hoteli/rezervisi/"+info+"/sobe/"+nizSoba+"/nizUsluga/"+listaUsl+"/idHotela/"+id+"/idRez/"+idRez,
			success : function(povratna) {
					if(povratna == ""){
						alert('Some of selected rooms are not more available. Please choose again.');
						nemogucaRez();
						console.log('neuspjesno');
					}else{
						dodajUseruRez(povratna);
					}
					},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
			});
	    }
}
function dodajUseruRez(data){
	 console.log('dosao je da doda rez korisniku');
	 var rezervacija= JSON.stringify(data);
	  $.ajax({
			type : 'POST',
			url : "/api/korisnici/addRezSobe/0",
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
	$("#reservation").hide();
	$("#fast").hide();
	
	$("#korakDodatne").empty();
	$("#korakDodatne").append("<div id= \"obavj\"><p>You have successfully made a reservation.</p><p>Total price:"+data.cijena+"</p><p>We are looking forward to have you as our guests</p></div>");
	setTimeout(redirekcija, 1500);
	
}
function redirekcija(){
	var adresa = window.location.search.substring(1);
	// da li ide da rezervise rent a car
	
	var flagdalje  = adresa.split('=')[5];	
	var id = adresa.split('=')[1];
	if(flagdalje == 1){
		var idRezervacije = adresa.split('=')[2];
		var  datumSletanja = adresa.split('=')[3];
		var lokacija = adresa.split('=')[4];
		var brojKarata = adresa.split('=')[6];
		window.location = "redirekcija.html?id="+"rent"+'='+idRezervacije+"="+lokacija+"="+datumSletanja+"="+"0"+"="+brojKarata;
	}else{
		window.location = "profileHotel.html?id="+id;

	}

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
     $("#ispisiTabelu").hide();

 	 $('#lozinkaOld').html('');
 	 $('#lozinka1').html('');
 	 $('#lozinka2').html('');	
 		prikaziPromjenu();
	
}
function prikaziPromjenu(){
    $("#promjenaLozinke").show();

}
//prikaze profil hotela posle promjene lozinke admina
function ispisiHotel(){
	$("#informacije").show();
	 $("#promjenaLozinke").hide();
	 $("#tabovi").show();
		
 	
}
function changeData(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	window.location = "changePersonalData.html?id="+id+"=hotel";
	
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
	 $("#postojeciPopusti").hide();
		
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
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	//resetujem greske iz prethodnog slucaja
	$("#errorDatPopust").text('');
	$("#errorEndPopust").text('');

	$("#postojeciPopusti").hide();
	$("#sobePopusti").hide();
	$("#poruka").hide();
		
	$("#dugmePopust").empty();
	$("#dugmePopust").append("<button type=\"button\" id=\"dugmePopustBonus\" class=\"btn btn-lg\" onclick = \"pronadjiIzabraneBod("+idRoom+")\">Add</button></div>");
	pokupiPostojecePopuste();
	
}
function pokupiPostojecePopuste(){
	$.ajax({
		method:'GET',
		url: "/api/special/all",
		success: function(lista){
			if(lista == null){
				nemaBonusPopusta();
			}else if(lista.length==0){
				nemaBonusPopusta();
			}else{
				dodajProcente(lista);
				
			}
		}
	});
	
}
function nemaBonusPopusta(){
	 $("#dodajPopust").hide();
	 $("#poruka").show();
		
}
function dodajProcente(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	console.log('dosao u izbor procenata')
	$("#selectBodove").empty();
	 $.each(pom, function(index, data) {
		 	
		 $("#selectBodove").append("<option value=\""+data.id+"\" >"+data.bodovi+"</option>");	 
		 
	 });
	 $("#dodajPopust").show();
		
	
}
function pronadjiIzabraneBod(idRoom){
	var idPopusta = $("#selectBodove").val();
	console.log('idPopusta');
	$.ajax({
		method:'GET',
		url: "/api/special/getById/"+idPopusta,
		success: function(lista){
				dodajPopustSistem(lista,idRoom);
			
		}
	});
	
}
function dodajPopustSistem(lista,idRoom){
	
	console.log(idRoom);
	var pocetak=$('#sincewhen').val();
	var kraj=$('#untilwhen').val();
	var bodovi=lista.bodovi;
	var procenat=lista.vrijednost;
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];	
	let ispravno = true;
	 
	if(!pocetak){
		ispravno = false;
		$("#errorDatPopust").text("You need to select the date").css('color', 'red');
	}
	if(!kraj){	
		$("#errorEndPopust").text("You need to select the date").css('color', 'red');
		ispravno = false;		
	}
	

	if(ispravno == true){
		$("#dodajPopust").hide();
		$('#sincewhen').val('');
		$('#untilwhen').val('');
		
		
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

	}else{
		alert('You need to fill out all the fields correctly.')
	}	
}
function listOfDiscount(idRoom){
	//$("#sobePopusti").hide();
	$("#poruka").hide();
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	
	$.ajax({
		method:'GET',
		url: "/api/hoteli/getRoomDiscount/"+id+"/idRoom/"+idRoom,
		success: function(lista){
				writeDiscountsOfRoom(lista,idRoom);
			
		}
	});
}
function writeDiscountsOfRoom(lista,idRoom){
	 var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#postojeciPopusti").empty();
	 $("#postojeciPopusti").show();
	//public RoomDTO(Long id, String tip, int kapacitet, int sprat,boolean imapopust) 
			
	 $("#postojeciPopusti").append("<table class=\"table table-hover\" id=\"popustiTab\" ><tr><th>Since when </th><th>Until when</th><th>Number of user pointst</th><th>Discount percentage</th><th></th></tr>");
		
		$.each(pom, function(index, data) {
				var slanje = data.id +"."+idRoom;
				var dat1 = data.datumod.split('T')[0];
				var dat2 = data.datumdo.split('T')[0];
				
				$("#popustiTab").append("<tr><td class=\"hoverName\">"+dat1+"</td><td> "+dat2+"</td><td>"+data.bodovi+"</td><td>"+data.vrijednost+"</td><td><button type=\"button\" onclick=\"removeDisc("+slanje+")\" class=\"btn btn-light\">Remove</button></td></tr>");
			
			
		});
		
	 $("#postojeciPopusti").append("</table>");

}
function removeDisc(slanje){
	$.ajax({
		type : 'POST',
		url : "/api/popusti/ukloniPopust/"+slanje,
		success : function(povratna) {
						console.log('uspjesno');
						promjeniBrojPopusta(slanje);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
		});

	
}
function promjeniBrojPopusta(slanje){
	console.log(slanje);
	
	$.ajax({
		type : 'POST',
		url : "/api/rooms/ukloniPopust/"+slanje,
		success : function(povratna) {
						console.log('uspjesno');
						ispisiOpetSobu();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
		});

}
function ispisiOpetSobu(){
	$("#postojeciPopusti").hide();
	showRoomsForDiscounts();
}
//ovu funckiju treba uraditi
function pronadjiPrihode(){
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	var pocetak=$('#odPrihodi').val();
	$('#odPrihodi').val('');
	$.ajax({
		method:'GET',
		url: "/api/rezervacijehotel/vratiPrihode/"+id+"/pocetak/"+pocetak,
		success: function(lista){
				promjeniPrihod(lista);
			
		}
	});
	
}

function promjeniPrihod(iznos){
	console.log()
	$("#divVrijednost").empty();
	$("#divVrijednost").append("<input id = \"vrijednostPrihoda\"  class=\"form-control\" type=\"number\" disabled>");
	
	$('#vrijednostPrihoda').val(iznos);
}
function odustaniRez(){
	var adresa = window.location.search.substring(1);
	// da li ide da rezervise rent a car
	var flagdalje  = adresa.split('=')[5];	
	var id = adresa.split('=')[1];
	if(flagdalje == 1){
		var idRezervacije = adresa.split('=')[2];
		var  datumSletanja = adresa.split('=')[3];
		var lokacija = adresa.split('=')[4];
		var brojKarata = adresa.split('=')[6];
		window.location = "redirekcija.html?id="+"rent"+'='+idRezervacije+"="+lokacija+"="+datumSletanja+"="+"0"+"="+brojKarata;
	}else{
		//treba da posalje mandi podatke
		window.location = "profileHotel.html?id="+id;
	}
	
}


