$(document).ready(function($) {
	var user = sessionStorage.getItem("ulogovan");
	var chartDay;
	var chartWeek;
	var chartMonth;
	var podatak = window.location.search.substring(1);
	console.log("Usao u showGraf");
	var niz= podatak.split("=");
	var id= niz[1];
	
	if(niz.length > 2){
		console.log('ima rezervacija');
		  $("#res").show();
		  $("#fastRes").show();
		
	}else{
		$("#res").hide();
		$("#fastRes").hide();
	}
	
	if(user!=null && user!="null" && user!="undefined") {
			console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);
			if(korisnik.tip != 'ADMIN_RENT'){
				 $("#business").hide();
				 $("#sistemPopust").hide();
				 $(".side").hide();
			}
			if(korisnik.tip == 'ADMIN_RENT'){
				if(korisnik.servis!=id){
					$("#business").hide();
					 $("#sistemPopust").hide();
					 $(".side").hide();
		
				} 
			}
			
			if(korisnik.tip != 'ADMIN_SISTEM'){
				$("#admini").hide();
				 
			}
	
	}else{
		 $("#business").hide();
		 $("#admini").hide();
		 $("#sistemPopust").hide();
		 $(".side").hide();
	}	
	
	
	
	function iscrtajGrafik(lista, kontekst, naslov,chart){
		var labele=new Array();
		var vrednosti=new Array();
		console.log('dnevni grafik');
		console.log(lista);
		 for (var i = 0; i < lista.length; i++) {
			 
			 var datum = lista[i].datum.split('T')[0];
			 console.log(datum)
			 labele.push(datum);
		 		vrednosti.push(lista[i].broj);
		  	}
		
		var ctx = $("#"+kontekst);
			if(chart != null){
				 chart.destroy();
			 }	
			console.log('usao u iscrtaj grafik');
			chart = new Chart(ctx, {
			    type: 'bar',
			    data: {
			        labels: labele,
			        datasets: [{
			            label: 'Number of reserved cars',
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
			            text: naslov,
			            fontSize: 24
			        }
			    }
			});		
	
	}
	$("#showGraf1").click(function() {
		var podatak = window.location.search.substring(1);
		console.log("Usao u showGraf");
		
		var godina = $("#yearCh2").val();
		var mesec = $("#monthCh2").val();
		if(isNaN(godina)){
			console.log('nije broj');
			alert('Enter correct year');
		}else if(godina.length!=4){
			console.log('duzina ne valja');
			alert('Enter correct year');
		}else if(godina < 2018){
			alert('Year must be greater than 2018');
		}else{
			console.log('sve okej godina je '+godina);
		console.log(mesec);
		
		var podatak = id+"="+godina+"="+mesec;

		$.ajax({
			method:'GET',
			url: "/api/rezervacijerent/dailychart/"+podatak,
			success: function(lista){
				if(lista == null){
					console.log('Nema podataka');
				}else if(lista.length==0){
					console.log('Nema podataka');
				}else{
					var kont="dayChart";
					var naslov="Number of reservations per day";
					console.log("ima podataka");
					iscrtajGrafik(lista,kont,naslov, chartDay);
					
				}
			}
		});
		
		}
	});

	
	$("#showGraf").click(function() {
		var podatak = window.location.search.substring(1);
		console.log("Usao u showGraf");
		
		var godina = $("#yearChart").val();
		var mesec = $("#monthChart").val();
		if(isNaN(godina)){
			console.log('nije broj');
			alert('Enter correct year');
		}else if(godina.length!=4){
			console.log('duzina ne valja');
			alert('Enter correct year');
		}else if(godina < 2018){
			alert('Year must be greater than 2018');
		}else{
			console.log('sve okej godina je '+godina);
		console.log(mesec);
		
		var podatak = id+"="+godina+"="+mesec;
		$.ajax({
			method:'GET',
			url: "/api/rezervacijerent/weeklychart/"+podatak,
			success: function(lista){
				if(lista == null){
					console.log('Nema podataka');
				}else if(lista.length==0){
					console.log('Nema podataka');
				}else{
					console.log("ima podataka");
					var kont="weekChart";
					var naslov="Number of reservations per week";
					iscrtajGrafik(lista,kont,naslov, chartWeek);
					
				}
			}
		});
		
		}
	});

	$("#findFast").click(function() {
		$("#errorFast").text('');
		var urlDelovi= podatak.split("=");
		//http://localhost:8080/profileHotel.html?id=1115=254=2019-02-06=Novi%20Sad=1
		var idRez = urlDelovi[2];
		var end = $("#datumFast").val();
		var start=urlDelovi[3];
		var grad=urlDelovi[4];
		//kasnije dodati
		var date1 = Date.parse(start);
		var date2 = Date.parse(end);
		
		if (date1 > date2) {
			$("#errorFast").text("Drop-off date must be greater than pick-up date").css('color', 'red');
		}else{
			$.ajax({
				method:'GET',
				url: "/api/vozila/getFast/"+id+"/start/"+start+"/end/"+end+"/grad/"+grad,
				success: function(lista){
					if(lista == null){
						console.log('Nema podataka');
					}else if(lista.length==0){
						console.log('Nema podataka');
					}else{
						console.log("ima podataka");
						console.log(lista.length);
						ispisiBrzeRez(lista,start,end);
					}
				}
			});
			

		}
		
	});

	$("#showGraf2").click(function() {
		
		var godina = $("#yearCh").val();
		if(isNaN(godina)){
			console.log('nije broj');
			alert('Enter correct year');
		}else if(godina.length!=4){
			console.log('duzina ne valja');
			alert('Enter correct year');
		}else if(godina < 2018){
			alert('Year must be greater than 2018');
		}else{
			console.log('sve okej godina je '+godina);
		
		var podatak = id+"="+godina;
		$.ajax({
			method:'GET',
			url: "/api/rezervacijerent/monthlychart/"+podatak,
			success: function(lista){
				if(lista == null){
					console.log('Nema podataka');
				}else if(lista.length==0){
					console.log('Nema podataka');
				}else{
					console.log("ima podataka");
					var kont="monthGrafik";
					var naslov="Number of reservations per moth";
					iscrtajGrafik(lista,kont,naslov, chartMonth);
					
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
								alert("You have successfully changed your password.");
								$("#divLozinka").hide();						        
								$("#pozTabovi").show();
						        $("#informacije").show();
						    	
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							alert('greska');
						}
					});
			
		});

	$("#seeIncome").click(function() {
		$("#errorIncome").text("");
		var ispravno=true;
		var start=$("#incomeDate").val();
		if(start == ""){
			ispravno = false;
			$("#errorIncome").text(" Fill out this field").css('color', 'red');
		}
		if(ispravno){
			console.log(start);
			$.ajax({
					method:'GET',
					url: "/api/rezervacijerent/getIncome/"+id+"/start/"+start,
					success: function(iznos){
						console.log(iznos);
						$("#incomeResult").empty();
						$("#incomeResult").append("<p> Total income is "+iznos+"<i class=\"glyphicon glyphicon-euro\"></i> </span></p>");
					}
			});
			
		}
		
	});
});

function loadPodatke(){
	$("#adminStrana").hide();
	$("#divPopust").hide();
	
	var podatak = window.location.search.substring(1);
	console.log("Usao u loadPodatke, dobio je "+ podatak);
	var niz= podatak.split("=");
	var id= niz[1];
	console.log("id servisa je "+id);

	$.ajax({
			  url : "/api/rents/vratiRentId/"+id,
			  type: 'get',
			  success: function(pom) {
				  iscrtajStranicu(pom);
				}
			});
	
	
}
function iscrtajStranicu(servis){
	console.log('usao u iscrtaj stranicu dobio je '+ servis);
	
    $("#naslov").text(servis.naziv);
    $("#prosecnaOcena").append(servis.ocena);
    
    $("#headquarter").append(servis.adresa);
    var pom= servis.adresa;
    var grad = servis.grad+" ";
    grad= grad.replace(" ", "%20");
    var adr= pom.replace(" ", "%20");
    var konacna=grad+adr;
    $("#adresa").append("<div class=\"mapouter\"><div class=\"gmap_canvas\"><iframe width=\"500\" height=\"400\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q="+konacna+"&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe><a href=\"https://www.embedgooglemap.net\">embedgooglemap.net</a></div><style>.mapouter{text-align:right;height:500px;width:600px;}.gmap_canvas {overflow:hidden;background:none!important;height:500px;width:600px;}</style></div>")
    $("#opis").empty();
    $("#opis").append("<h3>"+servis.opis+"</h3>");
    $("#ratingServis").append("<h4>Rating of our company is "+servis.ocena+"</h4>");
	
    var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1];
	
	popuniFilijale();
    popuniVozila();
    //ispisiTabeluCenovnik();
}

function popuniFilijale(){
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1];
	
    $.ajax({
		method:'GET',
		url: "/api/rents/getFilijale/"+id,
		success: function(data){
			if(data == null){
				console.log('Nema filijala');
			}else{
			    ispisiFilijale(data);
	    	}
		}
	});
	
}

function ispisiBrzeRez(skup,odDat,doDat){
	
	console.log('Usao u ispisi brze rez');
	$("#resultFast").empty();
	
	$("#resultFast").append("<p><h2> Offers: </h2></p>");
	$("#resultFast").append("<p>FROM <span id=\"startDate\">"+odDat+"</span> TO <span id=\"endDate\">"+doDat+"</span></p>");
	
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
	
	$("#resultFast").append("<table class=\"table table-hover\" id=\"tabelaFast\" ><tr><th>Model</th><th>Brand</th><th>Original price</th><th>Discount</th><th></th></tr>");
	$.each(lista, function(index, clan) {
		var param=clan.id+","+clan.cena+","+clan.popust+","+clan.bodovi;
		console.log(param);
     	$("#tabelaFast").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+clan.model+"</td><td> "+clan.marka+"</td><td>"+clan.cena+"</td><td>"+clan.popust+"</td><td><button class=\"btn btn-info\" onclick=\"rezervisiVozilo('"+param+"')\">Reserve</button></td></tr>");

	});
}

//ovde treba da se preuzmu usluge od odredjene kategorije
function kategorija(naziv){
	console.log('usao u kategorija dobio je '+naziv);
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	$("#cenovnikKategorije").empty();
	$("#cenovnikKategorije").append("<h4>Pricelist of category "+naziv+"</h4>");
	
	var pom=id+"="+naziv;
	
	
	$.ajax({
		method:'GET',
		url: "/api/rents/katUsluge/"+pom,
		success: function(data){
			if(data==null){
				console.log('Nema usluga');
			}else if(data.length == 0){
				console.log('Prazne usluga');
			}else{
				console.log('Ima usluga u cenovniku');
				ispisiCenovnik(data);
			}
		}
	});
	
}
function dodajCen(){
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	$.ajax({
		method:'GET',
		url: "/api/rents/getAktivanCenovnik/"+id,
		success: function(data){
			if(data==null){
				console.log('Nema usluga');
			}else if(data.length == 0){
				console.log('Prazne usluga');
			}else{
				console.log('Ima usluga u cenovniku');
				dodajDel(data);
			}
		}
	});
	

}
function dodajDel(data){
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id=niz[1];
	var user = sessionStorage.getItem("ulogovan");

	if(user!=null && user!="null" && user!="undefined") {
		var korisnik = JSON.parse(user);
		if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis == id ){
			$("#obrisiCen").append("<button class=\"btn btn-info\" onclick=\"obrisiCenovnik('"+data.id+"')\">Delete</button>")

		}
		
	}
}
function dodajDatum(){
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	$.ajax({
		method:'GET',
		url: "/api/rents/getAktivanCenovnik/"+id,
		success: function(data){
			if(data==null){
				console.log('Nema usluga');
			}else if(data.length == 0){
				console.log('Prazne usluga');
			}else{
				console.log('Ima usluga u cenovniku');
				ispisiDatum(data);
			}
		}
	});
	
	
}
function ispisiDatum(data){
	var dat1 = data.datum_primene.split('T')[0];
	
	$("#cenovnikKategorije").append("<p><i class=\"glyphicon glyphicon-calendar\"> </i> Effective date : "+dat1+"</p>");
	}

function obrisiCenovnik(id){

	$.ajax({
		type : 'POST',
		url : "/api/cenovnici/deleteCenovnik/"+id,
		success : function(pov) {
			    $("#cenovnikKategorije").empty();
				$("#obrisiCen").empty();
				console.log('obrisan cenovnik');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
}
function ispisiCenovnik(skup){
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	    var user = sessionStorage.getItem("ulogovan");

		console.log('usao u ispisiCenovnik');
		
		//ovde su usluge od odredjene kategorije, pravimo njenu tabelu
		
		
		var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
			
		$("#cenovnikKategorije").append("<table class=\"table table-hover\" id=\"tabelaCenovnik\" ><tr><th>Od</th><th>Do</th><th>Price per day</th></tr>");
		console.log(lista.length);
		var pod = lista[0];
		var a=pod.id+"="+pod.kategorija;
		
		if(lista.length == 1){
			
			if(user!=null && user!="null" && user!="undefined") {
				var korisnik = JSON.parse(user);
				if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis==id ){
					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>-</td><td ><input class=\"form-control\" type = \"number\"  id=\""+pod.id+"\"  value=\""+pod.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+a+"')\">Change price</button></td></tr>");
					
				}else{
					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>-</td><td >"+pod.cena+"</td></tr>");

				}
				
			}else{
				$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>-</td><td >"+pod.cena+"</td></tr>");

			}
				
		}else{
			var vrednost = lista[1];
			if(user!=null && user!="null" && user!="undefined") {
				var korisnik = JSON.parse(user);
				if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis == id ){
					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>"+vrednost.prekoTrajanja+"</td><td ><input class=\"form-control\" type = \"number\"  id=\""+pod.id+"\"  value=\""+pod.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+a+"')\">Change price</button></td></tr>");
					
				}else{
					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>"+vrednost.prekoTrajanja+"</td><td >"+pod.cena+"</td></tr>");
					
				}
				
			}else{
				$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>"+vrednost.prekoTrajanja+"</td><td >"+pod.cena+"</td></tr>");
				
			}
			
			var duzina = lista.length-1;
			
			$.each(lista, function(index, clan) {
				var pomocna=clan.id+"="+clan.kategorija;
				console.log("duzina niza je " +duzina);
				
				if(index != 0){
					if(index != duzina){
						var naredni = lista[index+1];
						if(user!=null && user!="null" && user!="undefined") {
							var korisnik = JSON.parse(user);
							if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis == id ){
								$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+clan.prekoTrajanja+"</td><td> "+naredni.prekoTrajanja+"</td><td ><input class=\"form-control\" type = \"number\"  id=\""+clan.id+"\"  value=\""+clan.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+pomocna+"')\">Change price</button></td></tr>");
								
							}else{
								$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+clan.prekoTrajanja+"</td><td> "+naredni.prekoTrajanja+"</td><td >"+clan.cena+"</td></tr>");
								
							}
							
						}else{
							$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+clan.prekoTrajanja+"</td><td> "+naredni.prekoTrajanja+"</td><td >"+clan.cena+"</td></tr>");
							
						}
									
					}
				}
				});
			var podd = lista[duzina];
			var b=podd.id+"="+podd.kategorija;
			
			if(user!=null && user!="null" && user!="undefined") {
				var korisnik = JSON.parse(user);
				if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis == id){
					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+podd.prekoTrajanja+"</td><td>-</td><td ><input class=\"form-control\" type = \"number\"  id=\""+podd.id+"\"  value=\""+podd.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+b+"')\">Change price</button></td></tr>");
						
				}else{

					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+podd.prekoTrajanja+"</td><td>-</td><td>"+podd.cena+"</td></tr>");		
				}
				
			}else{
				$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+podd.prekoTrajanja+"</td><td>-</td><td>"+podd.cena+"</td></tr>");
				
			}
		
			
			}
	    $("#cenovnikKategorije").append("</table>");
	 
	    dodajDatum();
	}
function izmeniUslugu(data){
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1];  //id servisa
	
	var niz2= data.split("=");
	var idUsluge = niz2[0];
	var kategorija = niz2[1];
	
	var vrednost= $("#"+idUsluge).val();
	var ispravno=true;
	
	if(!vrednost){
	  ispravno=false;	
	}
	if(isNaN(vrednost)){
		ispravno=false;
	}
	if(vrednost<=0){
		ispravno=false;
	}
	if(ispravno){
		var slanje=idUsluge+"="+vrednost+"="+id; //saljemo id usluge,kategoriju,novu vrednost i id servisa
		console.log('id servisa je '+ id + " id usluge je "+idUsluge + " kategorija je "+kategorija+" vrednost je"+ vrednost);
		
		
		$.ajax({
			type : 'POST',
			url : "/api/rents/izmeniUslugu/"+slanje,
			success : function(pov) {
				if( pov == null){	
					alert('Neispravna cena');
				}else{
					alert('Uspesno ste izmenili uslugu');
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
		});
	}else{
		alert('Enter price which is greater than 0.');
	}	
	
}
function popuniVozila(){
	console.log('usao u popuni vozila');
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
    $.ajax({
		method:'GET',
		url: "/api/rents/getVozila/"+id,
		success: function(data){
			if(data == null){
				console.log('Nema Vozila');
			}else{
			    ispisiVozila(data);
			    ispisiAdminuVozila(data);
			}
		}
	});
	
}
function ispisiAdminuVozila(skup){
	$("#izvestajVozila").empty();
	console.log('usao u ispisiadminuvozila');
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
		
	$("#izvestajVozila").append("<table class=\"table table-hover\" id=\"tabVozilo\" ><thead><tr><th>Brand</th><th>Model</th><th>Model year</th><th>Rating</th><th></th><th></th></tr></thead>");
	
	$.each(lista, function(index, vozilo) {
		$("#tabVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.marka+"</td><td> "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td> "+vozilo.ocena+"</td></tr>");
		
	});
    $("#izvestajVozila").append("</table>");
 

}
function ispisiFilijale(skup){
	var user = sessionStorage.getItem("ulogovan");
	var podatak = window.location.search.substring(1);
	var urlDelovi= podatak.split("=");
	var id=urlDelovi[1];

var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
	
		$("#filijale").empty();
		$.each(lista, function(index, fil) {
			$("#filijale").append("<div id=\""+index+"\"class=\"panel panel-default\">");
			console.log(fil.grad);
			$("#" + index).append("<div class=\"panel-heading\">"+fil.grad+"</div>");
			$("#" + index).append("<div class=\"panel-body\">"+fil.ulica+"</div>");
			if(user!=null && user!="null" && user!="undefined") {
				var korisnik=JSON.parse(user);
				if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis == id){
					$("#" + index).append("<div class=\"panel-footer\"><button  class=\"btn btn-info\" onclick=\"izmeniFilijalu('"+fil.id+"')\">Izmeni</button> <button  class=\"btn btn-info\" onclick=\"obrisiFilijalu('"+fil.id+"')\">Obrisi</button></div>");
				}
			
			}
			 $("#filijale").append("</div>");
		});
		var podatak = window.location.search.substring(1);
		var niz= podatak.split("=");
		if(niz.length>2){
			popuniSelect(skup);
		}
	
}
function popuniSelect(skup){
	var podatak = window.location.search.substring(1);
	var urlDelovi= podatak.split("=");
	var grad=urlDelovi[4];
	grad= grad.replace("%20"," ");
	console.log(grad);
	console.log('Usao u popuni select');
	
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
	 $.each(lista, function(index, data) {
		 var adresa=data.grad +", "+data.ulica;
			
		 if(data.grad == grad){
		 	 $("#pickLocation").append("<option  value=\""+data.id+"\">"+adresa+"</option>");
			 $("#dropLocation").append("<option  value=\""+data.id+"\">"+adresa+"</option>");

	     }
	
	 });
	
}
function izmeniFilijalu(id){
	window.location="changeOffice.html?id="+id;
}
function obrisiFilijalu(id){
	console.log('Treba obrisati filijalu sa id '+id);
	$.ajax({
		type : 'POST',
		url : "/api/filijale/deleteFilijalu/"+id,
		success : function(pov) {
				popuniFilijale();
				console.log('obrisana filijala');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
}

function ispisiVozila(skup){
	var user = sessionStorage.getItem("ulogovan");
	var podatak = window.location.search.substring(1);
	console.log("Usao u showGraf");
	var niz= podatak.split("=");
	var id= niz[1];
	
	$("#pregledVozila").empty();
	console.log('usao u ispisivozila');
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
		
	$("#pregledVozila").append("<table class=\"table table-hover\" id=\"tabelaVozilo\" ><thead><tr><th>Brand</th><th>Model</th><th>Model year</th><th>Number of seats </th><th>Category</th><th>Branch office</th><th>Rating</th><th></th><th></th></tr></thead>");
	
	$.each(lista, function(index, vozilo) {
		var filpom=vozilo.filijala.grad;
		var filulica=vozilo.filijala.ulica;
		var adresa=filpom + ", "+filulica;
		console.log(filpom);
		if(user!=null && user!="null" && user!="undefined") {
			var korisnik=JSON.parse(user);
			if(korisnik.tip == 'ADMIN_RENT' && korisnik.servis == id){
				if(vozilo.broj == 0){
					$("#tabelaVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.marka+"</td><td > "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td > "+vozilo.sedista+"</td><td > "+vozilo.kategorija+"</td><td> "+adresa+"</td><td> "+vozilo.ocena+"</td><td><button  class=\"btn btn-info\" onclick=\"izmeniVozilo('"+vozilo.id+"')\">Change</button></td><td><button class=\"btn btn-info\"  onclick=\"obrisiVozilo('"+vozilo.id+"')\">Delete</button></td></tr>");
				}else{
					$("#tabelaVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.marka+"</td><td > "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td > "+vozilo.sedista+"</td><td > "+vozilo.kategorija+"</td><td > "+adresa+"</td><td> "+vozilo.ocena+"</td><td><button  class=\"btn btn-info\" disabled=\"disabled\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"You can't change a car if it's in use\" onclick=\"izmeniVozilo('"+vozilo.id+"')\">Change</button></td><td><button class=\"btn btn-info\"  disabled=\"disabled\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"You can't delete a car if it's in use\" onclick=\"obrisiVozilo('"+vozilo.id+"')\">Delete</button></td></tr>");
						
				}
			}else{
				$("#tabelaVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.marka+"</td><td > "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td > "+vozilo.sedista+"</td><td > "+vozilo.kategorija+"</td><td > "+adresa+"</td><td> "+vozilo.ocena+"</td></tr>");
				
			}
		
		}else{
			$("#tabelaVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.marka+"</td><td > "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td > "+vozilo.sedista+"</td><td > "+vozilo.kategorija+"</td><td > "+adresa+"</td></tr>");
			
			
		}
		
		
	});
    $("#pregledVozila").append("</table>");
 
}

function izmeniVozilo(idVozila){
	window.location="changeVehicle.html?id="+idVozila;
	
	
}
function obrisiVozilo(idVozila){
	console.log('Vozilo koje treba obrisati ima id '+idVozila);
	
	$.ajax({
		type : 'POST',
		url : "/api/vozila/deleteVozilo/"+idVozila,
		success : function(pov) {
			popuniVozila();
				console.log('obrisano vozilo');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
	});
	
	
}
$(document).ready(function(){
	var pom=window.location.search.substring(1);
	var id= pom.split('=')[1];
	
	$("#divLozinka").hide();
	$("#divFast").hide();
	$("#automobili").hide();
	$("#addUsluge").hide();
	$("#cenovnik").hide();
	$("#bg").hide();
	$("#izvestaj").hide();
	$("#adminStrana").hide();
	$("#divPopust").hide();
	
    $("p#vozilo").click(function(){
		window.location="addCar.html?id="+id;


   });
    $("p#filijala").click(function(){
    	
		window.location="addOffice.html?id="+id;

   });
    $("p#dataAdmin").click(function(){
    	window.location = "changePersonalData.html?id="+id+"=rent";
   });
    $("p#usluga").click(function(){
    	$("#pozTabovi").hide();
    	$("#divLozinka").hide();
    	$("#cenovnik").hide();
    	$("#informacije").hide();
    	$("#automobili").hide();
    	$("#divFast").hide();
    	$("#adminStrana").hide();
    	$("#bg").hide();
    	$("#izvestaj").hide();
    	$("#addUsluge").show();		
    	$("#days").val("");
    	$("#catA").val("");
    	$("#catB").val("");
    	$("#catC").val("");
    	$("#catD").val("");
    	$("#catE").val("");
    	$("#divPopust").hide();
		
    });
    $("p#passAdmin").click(function(){
    	$("#cenovnik").hide();
     	$("#pozTabovi").hide();
        $("#informacije").hide();
    	$("#automobili").hide();
    	$("#adminStrana").hide();
    	$("#divFast").hide();
    	$("#bg").hide();
    	$("#izvestaj").hide();
    	$("#addUsluge").hide();	
    	 $('#lozinkaOld').html('');
    	 $('#lozinka1').html('');
    	 $('#lozinka2').html('');	
    	$("#divPopust").hide();
    	$("#divLozinka").show();
		
    });
    $("a#fastRes").click(function(){
    	$("#informacije").hide();
    	$("#divLozinka").hide();

    	$("#cenovnik").hide();
     	$("#addUsluge").hide();		
     	$("#bg").hide();
     	$("#izvestaj").hide();
     	$("#divPopust").hide();
		
     	$("#automobili").hide();
     	$("#adminStrana").hide();
     	$("#resultFast").empty();
    	$("#divFast").show();
     });        
    $("a#veh").click(function(){
    	$("#informacije").hide();
    	$("#divLozinka").hide();
    	$("#divFast").hide();
    	$("#cenovnik").hide();
     	$("#addUsluge").hide();		
     	$("#bg").hide();
     	$("#izvestaj").hide();
     	$("#divPopust").hide();
		
     	$("#automobili").show();
     	$("#adminStrana").hide();
     	popuniVozila();
    	$("#automobili").show();
    	console.log('vozilo');
   });

    $("a#res").click(function(){
    	$("#divPopust").hide();
		
    	$("#informacije").hide();
    	$("#cenovnik").hide();
     	$("#addUsluge").hide();
    	$("#divLozinka").hide();
    	$("#divFast").hide();
    	$("#automobili").hide();
    	$("#izvestaj").hide();
    	$("#adminStrana").hide();
    	$("#bg").show();
    	$("#rezultat").empty();
    	$("#divPopust").hide();
		
    	$("#anketa").show();
    	resetFormu();
    	console.log('rezervacije');
   });
    $("a#price").click(function(){
    	
		console.log('price');
		$("#informacije").hide();
		$("#automobili").hide();
	 	$("#addUsluge").hide();		
	 	$("#bg").hide();
		$("#divLozinka").hide();
		$("#divFast").hide();
	 	$("#izvestaj").hide();
	 	$("#divPopust").hide();
		$("#adminStrana").hide();
		$("#cenovnik").show();
		$("#cenovnikKategorije").empty();
		$("#obrisiCen").empty();
		dodajCen();

    });
    
  $("a#sistemPopust").click(function(){
    	
		console.log('popusti');
		$("#informacije").hide();
		$("#automobili").hide();
	 	$("#addUsluge").hide();		
	 	$("#bg").hide();
	 	$("#izvestaj").hide();
	 	$("#adminStrana").hide();
		$("#cenovnik").hide();
		$("#popustiTab").hide();
		
		$("#divPopust").show();
		$("#poruka").hide();
		
		$("#dodajPopust").hide();
		$("#divLozinka").hide();
		$("#divFast").hide();
		showCarsForDiscounts();
    	

  });
    
    
    $("a#info").click(function(){
    	console.log('pritisnuo');
    	$("#cenovnik").hide();
    	$("#automobili").hide();
     	$("#addUsluge").hide();		
     	$("#bg").hide();
    	$("#divLozinka").hide();
    	$("#divFast").hide();
    	$("#izvestaj").hide();
     	$("#adminStrana").hide();
    	$("#divPopust").hide();
    	$("#informacije").show();
    });
    $("a#admini").click(function(){
    	ispisiAdmine();
    	$("#informacije").hide();
		$("#automobili").hide();
	 	$("#addUsluge").hide();		
	 	$("#cenovnik").hide();
	 	$("#bg").hide();
		$("#divPopust").hide();
	 	$("#adminStrana").show();
		$("#divLozinka").hide();
		$("#divFast").hide();
		
		
    });
    
    $("a#business").click(function(){
    	$("#informacije").hide();
    	$("#cenovnik").hide();
     	$("#addUsluge").hide();		
     	$("#bg").hide();
    	$("#automobili").hide();
    	$("#izvestaj").show();
    	$("#divLozinka").hide();
    	$("#divFast").hide();
    	$("#adminStrana").hide();
    	$("#divPopust").hide();

    	console.log('izvestaj');
    	//dodajGrafik();
   });

    $(document).on('click','#potvrdiBtn',function(e){
    	e.preventDefault();	
    	console.log('usao u dodajUslugu');
    	var pom=window.location.search.substring(1);
    	var id= pom.split('=')[1];
    	var ispravno = true;
    	
    	trajanje = $('#days').val();
        katA = $('#catA').val();
    	katB = $('#catB').val();
    	katC = $('#catC').val();
    	katD =  $('#catD').val();
    	katE = $('#catE').val();
    	
    	if(!trajanje || isNaN(trajanje)){
    		ispravno=false;
    	}
    	if(trajanje<=0){
    		ispravno=false;
    	}
    	if(!katA || !katB || !katC || !katD || !katE){
    		ispravno=false;
    	}
    	if(isNaN(katA) || isNaN(katB)||isNaN(katC)||isNaN(katD)||isNaN(katE)){
    		ispravno=false;
    	}
    	if(katA<=0 || katB<=0 || katC<=0 || katD<=0 || katE<=0){
    		ispravno=false;
    	}
    	if(ispravno){
    		pom= id+"="+trajanje+"="+katA+"="+katB+"="+katC+"="+katD+"="+katE;
        	
    		$.ajax({
    			type : 'POST',
    			url : "/api/rents/dodajUslugu/"+pom,
    			success : function(pov) {
    				if( pov == null){	
    					alert('Naziv usluge vec postoji u sistemu');
    				}else{
    					alert('Uspesno ste dodali uslugu');
    					resetuj();
    				}
    			},
    			error: function(XMLHttpRequest, textStatus, errorThrown){
    				alert('greska');
    			}
    		});
	
    	}else{
    		alert('You need to fill out all fields and values must be greater than 0.');
    	}
    	
    	    });

    
});
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
	adminiRenta();
}

function izmjeniAdmineRenta(){
	var idUser =$('#adminSelect').val();
	console.log('dosao u izmjeni adminaHotela '+idUser);
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	var pomocna = idUser + "-" + id;
	 $.ajax({
			method:'POST',
			url: "/api/korisnici/newAdminRent/"+pomocna,
			success: function(lista){
				console.log('izmjenio');
				ispisiAdmine();
				//adminSistem();
			}
		});
}
function adminiRenta(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	 $.ajax({
			method:'GET',
			url: "/api/korisnici/getAdminsRent/"+id,
			success: function(lista){
				if(lista == null){
					nemaAdmina();
					console.log('Nema admina');
				}else if(lista.length==0){
					nemaAdmina();
					console.log('Nema admina');
				}else{
					ispisiAdmineRenta(lista);
					
				}
			}
		});
	
}
function nemaAdmina(){
	$("#adminDiv").empty();
	$("#adminDiv").append("<div><h3 id = \"h2Ad\">No registered administrators</h3></div>");
}
function ispisiAdmineRenta(lista){
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
			url: "/api/korisnici/removeAdminRent/"+id,
			success: function(lista){
				console.log('obrisao');
				ispisiAdmine();
				
			}
		});
}

function resetFormu(){
	$("#error1").text("");
	$("#error2").text("");
	$("#error3").text("");
	$("#pickDate").val("");
	$("#dropDate").val("");
	$("#tip").val("A");
	$("#putnici").val("");
}
function resetuj(){
	$("#pozTabovi").show();
	$("#cenovnik").hide();
	$("#informacije").show();
	$("#automobili").hide();
	$("#addUsluge").hide();		
	
}

function rezervisi(){

	$("#rezultat").empty();
	$("#error1").text("");
	$("#error2").text("");
	$("#error3").text("");
		
	var ispravno = true;
	
	var today = new Date().toISOString().split('T')[0];
	
	var pocetak=$("#pickDate").val();
	if(pocetak == ""){
		ispravno = false;
		$("#error1").text(" Fill out this field").css('color', 'red');
	}
	if(pocetak < today){
		$("#error1").text("You can not select the date that passed").css('color', 'red');
		ispravno=false;
		
	}
	var kraj=$("#dropDate").val();
	if(kraj == ""){
		ispravno=false;
		$("#error2").text(" Fill out this field").css('color', 'red');
	}
	
	var date1 = Date.parse(pocetak);
	var date2 = Date.parse(kraj);
	if (date1 > date2) {
		$("#error2").text("Drop-off date must be greater than pick-up date").css('color', 'red');
		ispravno=false;
	}
	var broj=$("#putnici").val();
	if(broj == ""){
		ispravno=false;
		$("#error3").text(" Fill out this field").css('color', 'red');
	}
	
	var pom=window.location.search.substring(1);
	var id= pom.split('=')[1];
		
		var newReservation={
				rentId : id,
				pickUp : $('#pickDate').val(),
				dropOff : $('#dropDate').val(),
				startLocation : $('#pickLocation').val(),
				endLocation :  $('#dropLocation').val(),
				tip : $('#tip').val(),
				putnici : $('#putnici').val()
		}
		var pocetak=$('#pickDate').val();
		var kraj=$('#dropDate').val();
		var osobe=$('#putnici').val();
		sendReservation= JSON.stringify(newReservation);			
		console.log('rezervacija je ' + sendReservation);
		
		if(ispravno == true){	
		$("#anketa").hide();
			 
		$("#rezultat").empty();
			
		$("#rezultat").append("<p><h2>Offers </h2></p>");
		$("#rezultat").append("<p>FROM <span id=\"pocetak\">"+pocetak+"</span> TO <span id=\"kraj\">"+kraj+"</span></p>");
		$("#rezultat").append("<p>FOR <span id=\"osobe\">"+osobe+"</span>  passengers</p>");
		
	
			$.ajax({
			type : 'POST',
			url : "/api/rents/checkRezervaciju",
			contentType : "application/json",
			data: sendReservation,
			dataType : 'json',
			success : function(povratna) {
						if(povratna == null){
							console.log('null je');
							nemaPonuda();
						}else if(povratna.length==0){
	
							console.log('povratna je 0');
							nemaPonuda();
						}else{
							izlistajPonude(povratna);
						}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert('greska');
			}
			});
		}

}
function nemaPonuda(){
	$("#anketa").hide();
	 
	$("#rezultat").empty();
	 $("#rezultat").append("<p><h2>Unfortunately we don't have rigth offer for you.</h2></p>");
}
function izlistajPonude(data){
	console.log('Usao u izlistaj ponude');
	
	var niz = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$("#rezultat").show();
	 $("#rezultat").append("<table class=\"table table-hover\" id=\"tabelaPonuda\" ><thead><tr><th>Brand</th><th>Model</th><th>Model year</th><th>Number of seats </th><th>Category</th><th>Total price</th><th></th></thead>");
		
		$.each(niz, function(index, pom) {
			
			var cena=pom.cena;
			var param=pom.id+","+cena+",0,0";
			$("#tabelaPonuda").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+pom.marka+"</td><td > "+pom.model+"</td><td > "+pom.godiste+"</td><td > "+pom.sedista+"</td><td> "+pom.kategorija+"</td><td> "+pom.cena+"</td><td><button class=\"btn btn-info\" onclick=\"rezervisiVozilo('"+param+"')\">Reserve</button></td><td></tr>");
				
		});
		
	 $("#rezultat").append("</table>");
	
}
function rezervisiVozilo(param){
	var podatak = window.location.search.substring(1);
	
	var id=param.split(',')[0];
	var cena= param.split(',')[1];
	var flag = param.split(',')[2];
	var bodovi = param.split(',')[3];
	console.log('Usao u rezervisi vozilo '+ id + " cena je "+cena+"a flag je "+flag);
	if(flag == 0){
		var pocetak =  $("#pocetak").text();
		var kraj =  $("#kraj").text();
			
	}else{
		var pocetak =  $("#startDate").text();
		var kraj =  $("#endDate").text();
		
	}
	var urlDelovi= podatak.split("=");
	var glavnaRez = 0;

	if(urlDelovi.length>2){
		glavnaRez=urlDelovi[2]
	}
	//http://localhost:8080/profileHotel.html?id=1115=254=2019-02-06=Novi%20Sad=1

	console.log("pocetak je"+ pocetak+" kraj je "+ kraj);
	var podatak = id+"="+pocetak+"="+kraj+"="+cena+"="+flag+"="+glavnaRez;
	$.ajax({
		type : 'POST',
		url : "/api/vozila/dodajRezervaciju/"+podatak,
		success : function(povratna) {
			console.log(povratna);
			if(povratna == null){
				console.log('Nije uspesno');
			    alert('This car is no more available. You can choose another car.');
			    $("#rezultat").empty();
			    $("#rezultat").hide();
			    
			    $("#anketa").show();
		    	resetFormu();
		    
			}else if(povratna==""){
				console.log('Prazno je');
			    alert('This car is no more available. You can choose another car.');
			    $("#rezultat").empty();
			    $("#rezultat").hide();
			    
			    $("#anketa").show();
		    	resetFormu();
		    
				
			}else if(povratna!=null){
				console.log('zavrsena rezervacija');
				poveziKorisnika(povratna, bodovi);
				
			}
			//	ispisiUspesno();
			//pozoviProfil(povratna.model);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
		});
}
function poveziKorisnika(pom, bodovi){
	console.log('usao u poveziKorisnika');
	console.log(pom);
 var sending= JSON.stringify(pom);			
	
	console.log(sending);
	
	$.ajax({
		type : 'POST',
		url : "/api/korisnici/dodajRez/"+bodovi,
		contentType : "application/json",
		data: sending,
		dataType : 'json',		
		success : function(pov) {
			console.log('usao u uspesno');
			ispisiUspesno(pov);
			
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log('usao u gresku');
			alert('greska');
		}
		});
}
function ispisiUspesno(pov){
	$("#res").hide();
	$("#fastRes").hide();

	$("#divFast").hide();
	$("#anketa").hide();
	 $("#bg").show();
	$("#rezultat").empty();
    $("#rezultat").append("<p><h2>You have successfully made a reservation</h2></p>");
    $("#rezultat").append("<p>Total price:"+pov.cena+"</p><p>We are looking forward to have you as our clients</p>");
	
    popuniVozila();
	
}
function pozoviProfil(data){
	
	window.location="profileCar.html?id="+data;
}
function showCarsForDiscounts(){
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/filijale/getCarsForDiscount/"+id,
		success: function(lista){
			if(lista == null){
				console.log('Nema soba')
			}else{
				writeCarsForDiscounts(lista);
				
			}
		}
	});
	
	
}
function writeCarsForDiscounts(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#autaPopusti").empty();
	 $("#autaPopusti").show();
	 $("#autaPopusti").append("<table class=\"table table-hover\" id=\"tabelaAuta1\" ><tr><th> Mark </th><th>Model</th><th>Year</th><th>Seats</th><th>Category</th><th></th><th></th></tr>");
		console.log('dosao ovdje');
		$.each(pom, function(index, data) {
			if(data.imapopusta){
				console.log('ima popust');
				
				$("#tabelaAuta1").append("<tr><td class=\"hoverName\">"+data.marka+"</td><td> "+data.model+"</td><td>"+data.godiste+"</td><td>"+data.sedista+"</td><td>"+data.kategorija+"</td><td><button type=\"button\" onclick=\"addDiscountForCars("+data.id+")\" class=\"btn btn-light\">Add discount</button></td><td><button type=\"button\" onclick=\"listOfDiscount("+data.id+")\" class=\"btn btn-light\">Discounts</button></td></tr>");
			}else{
				$("#tabelaAuta1").append("<tr><td class=\"hoverName\">"+data.marka+"</td><td> "+data.model+"</td><td>"+data.godiste+"</td><td>"+data.sedista+"</td><td>"+data.kategorija+"</td><td><button type=\"button\" onclick=\"addDiscountForCars("+data.id+")\" class=\"btn btn-light\">Add discount</button></td><td></td></tr>");
				
			}
			
		});
		
	 $("#autaPopusti").append("</table>");
	 

	
}
function addDiscountForCars(idCar){
	$("#autaPopusti").hide();
	$("#postojeciPopusti").hide();
	
	$("#poruka").hide();
	   
	$("#dugmePopust").empty();
	$("#dugmePopust").append("<button type=\"button\"  class=\"btn btn-lg\" onclick = \"pronadjiIzabraneBod("+idCar+")\">Add</button></div>");				
	
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
	console.log('dosao u dodaj procenata')
	
	$("#selectBodove").empty();
	 $.each(pom, function(index, data) {
		 	
		 $("#selectBodove").append("<option value=\""+data.id+"\" >"+data.bodovi+"</option>");	 
		 
	 });
	 $("#dodajPopust").show();
		
	
}

function pronadjiIzabraneBod(idCar){
	var idPopusta = $("#selectBodove").val();
	console.log('idPopusta');
	$.ajax({
		method:'GET',
		url: "/api/special/getById/"+idPopusta,
		success: function(lista){
				dodajPopustSistem(lista,idCar);
			
		}
	});
	
}

function dodajPopustSistem(popust,idVozilo){
	
	let ispravno = true;
	var pocetak=$('#sincewhen').val();
	var kraj=$('#untilwhen').val();
	var bodovi=popust.bodovi;
	var procenat=popust.vrijednost;
	$("#errorDatPopust").empty();
	$("#errorEndPopust").empty();
	
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
			url : "/api/vozila/definisiPopust/"+idVozilo+"/pocetak/"+pocetak+"/kraj/"+kraj+"/bodovi/"+bodovi+"/procenat/"+procenat,
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
function pomocnaFA(){
	showCarsForDiscounts();
	 $("#postojeciPopusti").hide();
	
}
function listOfDiscount(idVozilo){
	//$("#autaPopusti").hide();
	
	$.ajax({
		method:'GET',
		url: "/api/vozila/getVoziloDiscount/"+idVozilo,
		success: function(lista){
				writeDiscountsOfVozilo(lista,idVozilo);
			
		}
	});

	
	
}
function writeDiscountsOfVozilo(lista,idVozilo){
	 var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#postojeciPopusti").empty();
	 $("#postojeciPopusti").show();
			
	 $("#postojeciPopusti").append("<table class=\"table table-hover\" id=\"popustiTab\" ><tr><th>Since when </th><th>Until when</th><th>Number of user points</th><th>Discount percentage</th><th></th></tr>");
		
		$.each(pom, function(index, data) {
				var slanje = data.id +"."+idVozilo;
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
						promeniBrojPopusta(slanje);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
		});

	
}
function promeniBrojPopusta(slanje){
	console.log(slanje);
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];

	$.ajax({
		type : 'POST',
		url : "/api/vozila/ukloniPopust/"+slanje,
		success : function(povratna) {
						console.log('uspjesno');
						ispisiOpetVoz();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('greska');
		}
		});

}
function ispisiOpetVoz(){
	$("#postojeciPopusti").hide();
	showCarsForDiscounts();

	
}