function loadPodatke(){
	
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
    $("#adresa").append(servis.adresa);
    var pom= servis.adresa;
    var adr= pom.replace(" ", "%20");
    $("#adresa").append("<div class=\"mapouter\"><div class=\"gmap_canvas\"><iframe width=\"600\" height=\"500\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q="+adr+"&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe><a href=\"https://www.embedgooglemap.net\">embedgooglemap.net</a></div><style>.mapouter{text-align:right;height:500px;width:600px;}.gmap_canvas {overflow:hidden;background:none!important;height:500px;width:600px;}</style></div>")
    $("#opis").append(servis.opis);
	
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
	$("#cenovnikKategorije").append("<p><i class=\"glyphicon glyphicon-calendar\"> </i> Effective date : "+data.datum_primene+"</p>");
	
}


function ispisiCenovnik(skup){
		
		console.log('usao u ispisiCenovnik');
		
		//ovde su usluge od odredjene kategorije, pravimo njenu tabelu
		
		
		var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
			
		$("#cenovnikKategorije").append("<table class=\"table table-hover\" id=\"tabelaCenovnik\" ><tr><th>Od</th><th>Do</th><th>Price per day</th></tr>");
		console.log(lista.length);
		var pod = lista[0];
		var a=pod.id+"="+pod.kategorija;
		
		$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">1</td><td>"+pod.prekoTrajanja+"</td><td ><input class=\"form-control\" type = \"number\"  id=\""+pod.id+"\"  value=\""+pod.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+a+"')\">Change price</button></td></tr>");
		
		var duzina = lista.length-1;
		
		$.each(lista, function(index, clan) {
			var pomocna=clan.id+"="+clan.kategorija;
			console.log("duzina niza je " +duzina);
			
			if(index != 0){
				if(index != duzina){
					var prethodni = lista[index-1];
					$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+prethodni.prekoTrajanja+"</td><td> "+clan.prekoTrajanja+"</td><td ><input class=\"form-control\" type = \"number\"  id=\""+clan.id+"\"  value=\""+clan.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+pomocna+"')\">Change price</button></td></tr>");
				}
			}
			});
		var podd = lista[duzina];
		var b=podd.id+"="+podd.kategorija;
		
		$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+podd.prekoTrajanja+"</td><td>-</td><td ><input class=\"form-control\" type = \"number\"  id=\""+podd.id+"\"  value=\""+podd.cena+"\"></td><td><button class=\"btn btn-info\" onclick=\"izmeniUslugu('"+b+"')\">Change price</button></td></tr>");
	
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
			}
		}
	});
	
}
function ispisiFilijale(skup){
	
var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
	
		$("#filijale").empty();
		$.each(lista, function(index, fil) {
			$("#filijale").append("<div id=\""+index+"\"class=\"panel panel-default\">");
			console.log(fil.grad);
			console.log(fil.index);
			$("#" + index).append("<div class=\"panel-heading\">"+fil.grad+"</div>");
			$("#" + index).append("<div class=\"panel-body\">"+fil.ulica+"</div>");
			$("#" + index).append("<div class=\"panel-footer\"><button  class=\"btn btn-info\" onclick=\"izmeniFilijalu('"+fil.id+"')\">Izmeni</button> <button  class=\"btn btn-info\" onclick=\"obrisiFilijalu('"+fil.id+"')\">Obrisi</button></div>");
			 $("#filijale").append("</div>");
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
	
	$("#pregledVozila").empty();
	console.log('usao u ispisivozila');
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
		
	$("#pregledVozila").append("<table class=\"table table-hover\" id=\"tabelaVozilo\" ><thead><tr><th>Name</th><th>Brand</th><th>Model</th><th>Model year</th><th>Number of seats </th><th>Category</th><th></th><th></th></tr></thead>");
	
	$.each(lista, function(index, vozilo) {
		$("#tabelaVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.naziv+"</td><td > "+vozilo.marka+"</td><td > "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td > "+vozilo.sedista+"</td><td > "+vozilo.kategorija+"</td><td><button class=\"btn btn-info\" onclick=\"izmeniVozilo('"+vozilo.id+"')\">Change</button></td><td><button class=\"btn btn-info\" onclick=\"obrisiVozilo('"+vozilo.id+"')\">Delete</button></td></tr>");
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
	$("#automobili").hide();
	$("#addUsluge").hide();
	$("#cenovnik").hide();
	
	
    $("p#vozilo").click(function(){
		window.location="addCar.html?id="+id;


   });
    $("p#filijala").click(function(){
    	
		window.location="addOffice.html?id="+id;

   });
    $("p#usluga").click(function(){
    	$("#pozTabovi").hide();
    	$("#cenovnik").hide();
    	$("#informacije").hide();
    	$("#automobili").hide();
    
    	$("#addUsluge").show();		

   });
        
    $("a#veh").click(function(){
    	$("#informacije").hide();
    	$("#cenovnik").hide();
     	$("#addUsluge").hide();		

    	$("#automobili").show();
    	
    		console.log('vozilo');
   });
    $("a#price").click(function(){
    	
		console.log('price');
		$("#informacije").hide();
		$("#automobili").hide();
	 	$("#addUsluge").hide();		

		$("#cenovnik").show();
		$("#cenovnikKategorije").empty();
    });
    
    $("a#info").click(function(){
    	console.log('pritisnuo');
    	$("#cenovnik").hide();
    	$("#automobili").hide();
     	$("#addUsluge").hide();		

    	$("#informacije").show();
    });
    
});

$(document).on('submit','.dodavanje',function(e){
	e.preventDefault();	
	
	var pom=window.location.search.substring(1);
	var id= pom.split('=')[1];
	
	trajanje = $('#days').val();
    katA = $('#catA').val();
	katB = $('#catB').val();
	katC = $('#catC').val();
	katD =  $('#catD').val();
	katE = $('#catE').val();
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
});

function resetuj(){
	$("#pozTabovi").show();
	$("#cenovnik").hide();
	$("#informacije").show();
	$("#automobili").hide();
	$("#addUsluge").hide();		
	
}
