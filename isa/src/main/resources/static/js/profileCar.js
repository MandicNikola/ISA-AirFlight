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


function ispisiCenovnik(skup){
		
		console.log('usao u ispisiCenovnik');
		
		//ovde su usluge od odredjene kategorije, pravimo njenu tabelu
		
		$("#cenovnikKategorije").empty();
		var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
			
		$("#cenovnikKategorije").append("<table class=\"table table-hover\" id=\"tabelaCenovnik\" ><tr><th>Service</th><th>Price</th></tr>");
		
		$.each(lista, function(index, clan) {
			$("#tabelaCenovnik").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+clan.naziv+"</td><td > "+clan.cena+"</td></tr>");
		});
	    $("#cenovnikKategorije").append("</table>");
	 
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
	
		
		$.each(lista, function(index, fil) {
			$("#filijale").append("<div id=\""+index+"\"class=\"panel panel-default\">");
			console.log(fil.grad);
			console.log(fil.index);
			$("#" + index).append("<div class=\"panel-heading\">"+fil.grad+"</div>");
			$("#" + index).append("<div class=\"panel-body\">"+fil.ulica+"</div>");

			 $("#filijale").append("</div>");
		});
	
}

function ispisiVozila(skup){
	
	console.log('usao u ispisivozila');
	var lista = skup == null ? [] : (skup instanceof Array ? skup : [ skup ]);
		
	$("#pregledVozila").append("<table class=\"table table-hover\" id=\"tabelaVozilo\" ><thead><tr><th>Name</th><th>Brand</th><th>Model</th><th>Model year</th><th>Number of seats </th><th>Category</th></tr></thead>");
	
	$.each(lista, function(index, vozilo) {
		$("#tabelaVozilo").append("<tr class=\"thead-light \"><td class=\"hoverName\">"+vozilo.naziv+"</td><td > "+vozilo.marka+"</td><td > "+vozilo.model+"</td><td > "+vozilo.godiste+"</td><td > "+vozilo.sedista+"</td><td > "+vozilo.kategorija+"</td></tr>");
	});
    $("#pregledVozila").append("</table>");
 
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
	
	naziv = $('#ime').val();
    katA = $('#catA').val();
	katB = $('#catB').val();
	katC = $('#catC').val();
	katD =  $('#catD').val();
	katE = $('#catE').val();
	pom= id+"="+naziv+"="+katA+"="+katB+"="+katC+"="+katD+"="+katE;
	
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
