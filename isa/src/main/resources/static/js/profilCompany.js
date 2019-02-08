/**
 * funkcije za rad sa profilom kompanije koje koristim
 */
$(document).ready(function($) {
	var user = sessionStorage.getItem("ulogovan");
	var chartDay;
	var chartWeek;
	var chartMonth;
	var podatak = window.location.search.substring(1);
	console.log("Usao u showGraf");
	var niz= podatak.split("=");
	
	if(niz.length > 2){
		console.log('ima rezervacija');
		  $("#res").show();
		  $("#fastRes").show();
		
	}else{
		$("#res").hide();
		$("#fastRes").hide();
	}
	var id= niz[1];
	
	if(user!=null && user!="null" && user!="undefined") {
			console.log('ima korisnika');
			var korisnik=JSON.parse(user);
			console.log(korisnik.tip);
			if(korisnik.tip != 'ADMIN_SISTEM'){
				 
				 $("#planes").hide();
				 $("#discount").hide();
				 $(".side").hide();
				 $("#planes").hide();
				 $("#report").hide();
				 $("#discount").hide();
				 $("#admini").hide();
				 $("#divLozinka").hide();
				 $("#divPopust").hide();
			}
			else
			{
				 imaAdmina = "ima";
			}
	
	}else{
		
		 $("#planes").hide();
		 $("#discount").hide();
		 $(".side").hide();
		 $("#planes").hide();
		 $("#report").hide();
		 $("#admini").hide();
		 $("#divLozinka").hide();
		 $("#divPopust").hide();
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
			            label: 'Number of reserved tickets',
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
			url: "/api/reservationTickets/dailychart/"+podatak,
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
			url: "/api/reservationTickets/weeklychart/"+podatak,
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
			url: "/api/reservationTickets/monthlychart/"+podatak,
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
						url : "/api/korisnici/changePass?oldPass="+oldLoz+ "&lozinka1="+loz1+"&lozinka2="+loz2,
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
	
	$('.filterableTicket .btn-filter').click(function(){
        var $panel = $(this).parents('.filterableTicket'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });
	$('.filterableFlights .btn-filter').click(function(){
        var $panel = $(this).parents('.filterableFlights'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });
	
	$('.filterableTicketFast .btn-filter').click(function(){
        var $panel = $(this).parents('.filterableTicketFast'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });
	
	$('.filterableServices .btn-filter').click(function(){
        var $panel = $(this).parents('.filterableServices'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });
	
	
	
	
});












var id;
var imaAdmina = "nema";

function onLoad(){
	$("#adminStrana").hide();
	$("#flightsStrana").hide();
	$("#planesStrana").hide();
	$("#fastTicketsStrana").hide();
	$("#priceListStrana").hide();
 	$('#izvestaj').hide();
 	$("#divLozinka").hide();
 	$("#discountStrana").hide();
 	 $("#divPopust").hide();
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	id = adresa.split('=')[1];
	$.ajax({
		method:'GET',
		url: "/api/kompanije/findById/"+id,
		success: function(kompanija){
			if(kompanija == null){
				console.log('Nema servise');
			}else{
				ispisiProfilKompanije(kompanija);
				
			}
		}
	});
}

function info()
{
	$("#informacije").show();
	$("#adminStrana").hide();
	$("#flightsStrana").hide();
	$("#planesStrana").hide();
	$("#fastTicketsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#divLozinka").hide();
	 $("#divPopust").hide();
}

function ispisiProfilKompanije(kompanija){
	console.log("id "+kompanija.id);
	var adr = kompanija.adresa;
	$("#naziv").text('Welcome to '+kompanija.naziv);
	$("#opis").text(kompanija.opis);
	$("#adresa").append(kompanija.adresa);
	var adresa=	adr.replace(" ", "%20");
    
	$("#adresa").append("<div class=\"mapouter\"><div class=\"gmap_canvas\"><iframe width=\"600\" height=\"500\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q="+adresa+"&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe><a href=\"https://www.embedgooglemap.net\">embedgooglemap.net</a></div><style>.mapouter{text-align:right;height:500px;width:600px;}.gmap_canvas {overflow:hidden;background:none!important;height:500px;width:600px;}</style></div>")
	
	

}

function addPlane()
{
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "newAirplane.html?id="+id;
	
}

function addFlight()
{
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "newFlight.html?id="+id;

}

function addDestination()
{
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "newDestination.html?id="+id;
}


function showPlanes()
{
	$("#adminStrana").hide();
	$("#informacije").hide();
	$("#flightsStrana").hide();
	$("#fastTicketsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#divLozinka").hide();
	 $("#divPopust").hide();
	
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	$.ajax
	({
		type : 'GET',
		url : 'api/kompanije/airplanes/'+id,
		dataType : 'json',
		success : function(data)
		{
			if(data == null || data.length == 0)
			{
				$("#planesStrana").append('<h1>Trenutno ne postoje avioni!<h1>');
			}
			else
			{
				$("#planesStrana").empty();
				var text = "";
				
				text = "<table class=\"table table-striped\" id=\"tabelaAvion\" ><tr><th> Name </th><th>Configuration</th><th> Operations </th></tr>";

				$.each(data,function(index,value)
				{
					text += "<tr><td class=\"hoverName\" >"+value.naziv+"</td><td>"+value.konfiguracija+'</td><td><button type="button" id="'+value.id+'" onclick="changeConfiguration(this)" class="btn btn-info">Change configuration</button></td><td><button type="button" id="'+value.id+'" onclick="delete(this)" class="btn btn-warning">Delete</button></td></tr>'

				});
				text += "</table>"
				$("#planesStrana").append(text);
				$("#planesStrana").show();
			}
			
		}
		
		
	});

}

function changeConfiguration(btn)
{
	var id = btn.id;
	window.location = "PlaneProfile.html?id="+id;

}



//ovde za letove odraditi ispravku koja mi treba jos
function showFlights()
{
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	$("#adminStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#fastTicketsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#izvestaj").hide();
	$("#discountStrana").hide();
	$("#divLozinka").hide();
	 $("#divPopust").hide();
	
	$.ajax
	({
		type : 'GET',
		url : 'api/kompanije/flights/'+id,
		dataType : 'json',
		success : function(data)
		{
			if(data == null || data.length == 0)
			{
				alert('ne postoje trenutno letovi!');
			}
			else
			{
				           
				$("#tabelaLetovi").empty();
				var text = "";
				
				$.each(data,function(index,value)
				{
					text += '<tr><td>' + value.lokPoletanja + '</td>';
					text += '<td>' + value.lokPoletanja + '</td>';
					text += '<td>' + value.datumPoletanja + '</td>';
					text += '<td>' + value.datumSletanja + '</td>';
					text += '<td>' + value.avion + '</td>';
					text += '<td>' + value.presedanja.length + '</td>';
					text += '<td>' + value.duzina + '</td>';
					
					alert(imaAdmina);
					if(imaAdmina == "ima")
					{
						text += '<td><button type="button" class="btn btn-primary" onclick="updateFlight('+value.idLeta+')">Update flight</button></td>';
						text += '<td><button type="button" class="btn btn-danger" onclick="removeFlight('+value.idLeta+')">Remove flight</button></td></tr>';
					}
					
				});
				
				$("#tabelaLetovi").append(text);
				
				 $('.filterableFlights .filters input').unbind().keyup(function(e){
				        /* Ignore tab key */
					 	
				        var code = e.keyCode || e.which;
				        if (code == '9') return;
				        /* Useful DOM data and selectors */
				        var $input = $(this),
				        inputContent = $input.val().toLowerCase(),
				        $panel = $input.parents('.filterableFlights'),
				        column = $panel.find('.filters th').index($input.parents('th')),
				        $table = $panel.find('.table'),
				        $rows = $table.find('tbody tr');
				        /* Dirtiest filter function ever ;) */
				        var $filteredRows = $rows.filter(function(){
				            var value = $(this).find('td').eq(column).text().toLowerCase();
				            return value.indexOf(inputContent) === -1;
				        });
				        /* Clean previous no-result if exist */
				        $table.find('tbody .no-result').remove();
				        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
				        $rows.show();
				        $filteredRows.hide();
				        /* Prepend no-result row if all rows are filtered */
				        if ($filteredRows.length === $rows.length) {
				            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
				        }
				    });
				
			}
			
		}
	
		
	});
	$("#flightsStrana").show();

}

function showFastTickets()
{
	$("#adminStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#izvestaj").hide();
	$("#discountStrana").hide();
	$("#divLozinka").hide();
	$("#divPopust").hide();
	
	
	
	$.ajax
	({
		type : 'GET',
		url : 'api/kompanije/fastTickets/'+id,
		dataType : 'json',
		success : function(data)
		{
			
		
        	$('#tabelaBrzeKarte').empty();	
        	if(data == null || data.length == 0)
        	{
        		alert('nema brzih karata!');
        	}
        	else
        	{
        		var text = "";
        		$.each(data, function(index,karta)
        				{
        					text += '<tr><td>' + karta.id +'</td>';
        					text += '<td>'+karta.lokPoletanja+'/'+karta.lokSletanja+'</td>';
        					text += '<td>'+karta.datumPoletanja+'</td>';
        					text += '<td>'+karta.red+'-'+karta.kolona+'</td>';
        					text += '<td>'+karta.klasa+'</td>';
        					text += '<td>'+karta.cena+'</td>';
        					text += '<td>'+karta.popust+'</td>';
        					text += '<td>'+(karta.popust*karta.cena)/100+'</td>';
        					text += '<td><button type="button" class="btn btn-primary" id="'+karta.id+'-'+karta.idPopusta+'" onclick="bookFast(this)">Book</button></td></tr>';
        					
        				});
        		$('#tabelaBrzeKarte').append(text);
        		$('.filterableTicketFast .filters input').unbind().keyup(function(e){
			        /* Ignore tab key */
				 	
			        var code = e.keyCode || e.which;
			        if (code == '9') return;
			        /* Useful DOM data and selectors */
			        var $input = $(this),
			        inputContent = $input.val().toLowerCase(),
			        $panel = $input.parents('.filterableTicketFast'),
			        column = $panel.find('.filters th').index($input.parents('th')),
			        $table = $panel.find('.table'),
			        $rows = $table.find('tbody tr');
			        /* Dirtiest filter function ever ;) */
			        var $filteredRows = $rows.filter(function(){
			            var value = $(this).find('td').eq(column).text().toLowerCase();
			            return value.indexOf(inputContent) === -1;
			        });
			        /* Clean previous no-result if exist */
			        $table.find('tbody .no-result').remove();
			        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
			        $rows.show();
			        $filteredRows.hide();
			        /* Prepend no-result row if all rows are filtered */
			        if ($filteredRows.length === $rows.length) {
			            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
			        }
			    });
        		
        		
        		
        	}
		}
		
		
	});
	
	
	
	$("#fastTicketsStrana").show();
	
	
}

function bookFast(btn)
{
	var podatak = btn.id;
	alert(podatak);
	
	var id = podatak.split('-')[0];
	var idPopust = podatak.split('-')[1];
	
	$.ajax
	({
		type : 'POST',
		url : 'api/letovi/fastReservation/'+id+'/'+idPopust,
		success : function(data)
		{
			alert('Uspesno ste rezervisali kartu!');
			showFastTickets();
		}
		
	});
	
	
}




function showAdministrators()
{
	
	$("#fastTicketsStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#priceListStrana").hide();
	$("#izvestaj").hide();
	$("#discountStrana").hide();
	$("#divLozinka").hide();
	 $("#divPopust").hide();
	
	$("#adminStrana").show();


}

function priceList()
{
	$("#adminStrana").show();
	$("#fastTicketsStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#izvestaj").hide();
	$("#discountStrana").hide();
	$("#divLozinka").hide();
	$("#divPopust").hide();
	
	
	$.ajax
	({
		type : 'GET',
		url : 'api/kompanije//flight/'+id,
		dataType : 'json',
		success : function(data)
		{
			if(data == null || data.length == 0)
			{
				
			}
			else
			{
				
				$('#tabelaServices').empty();
				
				
				var text = "";				    
				$.each(data,function(index,value)
						{
							text += "<tr><td class=\"hoverName\" >"+value.naziv+"</td><td>"+value.cena+'</td><td>'+value.klasa+'</td><td>'+value.opis+'</td><td><button type="button" class="btn btn-danger" onclick="removeUsluga('+value.id+')">Remove</button>' +'</td></tr>';                  
						});
				$('#tabelaServices').append(text);
				 $('.filterableServices .filters input').unbind().keyup(function(e){
				        /* Ignore tab key */
					 	
					   
				        var code = e.keyCode || e.which;
				        if (code == '9') return;
				        /* Useful DOM data and selectors */
				        var $input = $(this),
				        inputContent = $input.val().toLowerCase(),
				        $panel = $input.parents('.filterableServices'),
				        column = $panel.find('.filters th').index($input.parents('th')),
				        $table = $panel.find('.table'),
				        $rows = $table.find('tbody tr');
				        /* Dirtiest filter function ever ;) */
				        var $filteredRows = $rows.filter(function(){
				            var value = $(this).find('td').eq(column).text().toLowerCase();
				            return value.indexOf(inputContent) === -1;
				        });
				        /* Clean previous no-result if exist */
				        $table.find('tbody .no-result').remove();
				        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
				        $rows.show();
				        $filteredRows.hide();
				        /* Prepend no-result row if all rows are filtered */
				        if ($filteredRows.length === $rows.length) {
				            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
				        }
				    });
				
				
			}
			
		}
		
		
	});
	
	
	
	$("#priceListStrana").show();

}

$(document).ready(function(){
	
	$("#adminStrana").hide();
	
    $("#admini").click(function(){
    	ispisiAdmine();
    	$("#adminStrana").show();
    	
		$("#informacije").hide();
	
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
	adminiServisa();
}

function adminiServisa(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	 $.ajax({
			method:'GET',
			url: "/api/korisnici/getAdminsServis/"+id,
			success: function(lista){
				if(lista == null){
					nemaAdmina();
					console.log('Nema admina');
				}else if(lista.length==0){
					nemaAdmina();
					console.log('Nema admina');
				}else{
					ispisiAdmineServisa(lista);
					
				}
			}
		});
	
}
function nemaAdmina(){
	$("#adminDiv").empty();
	$("#adminDiv").append("<div><h3 id = \"h2Ad\">No registered administrators</h3></div>");
}

function ispisiAdmineServisa(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	console.log('dosao u ispisi admina Servisa')

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
			url: "/api/korisnici/removeAdminServis/"+id,
			success: function(lista){
				console.log('obrisao');
				ispisiAdmine();
				
			}
		});
}

function izmjeniAdmineSistema(){
	var idUser =$('#adminSelect').val();
	console.log('dosao u izmjeni adminaServisa '+idUser);
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	var pomocna = idUser + "-" + id;
	 $.ajax({
			method:'POST',
			url: "/api/korisnici/newAdminServis/"+pomocna,
			success: function(lista){
				console.log('izmjenio');
				ispisiAdmine();
				
			}
		});
	
}

function discount()
{
	$("#fastTicketsStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#izvestaj").hide();
	$("#divLozinka").hide();
	 $("#divPopust").hide();
	
	$.ajax
	({
		type : 'GET',
		url : 'api/kompanije/tickets/'+id,
		dataType : 'json',
		success : function(data)
		{
			$('#tabelaKarte').empty();
			var karte = data;
			if(data == null || data.length == 0)
			{
				alert('nema karata u sistemu!');
			}
			else
			{
				var text = ""
				$.each(karte,function(index,karta)
						{
							
					
							text += '<tr><td>'+karta.id+'</td><td>'+karta.lokPoletanja+'/'+karta.lokSletanja+'</td><td>'+karta.datumPoletanja+'</td><td>'+karta.red+'-'+karta.kolona+'</td><td>'+karta.klasa+'</td><td>'+karta.cena+'</td>';
							text += '<td><button type="button" class="btn btn-primary" onclick="viewDiscount('+karta.id+')"> Discount</button></td></tr>';
						});
				$('#tabelaKarte').append(text);
				 $('.filterableTicket .filters input').unbind().keyup(function(e){
				        /* Ignore tab key */
					 	
				        var code = e.keyCode || e.which;
				        if (code == '9') return;
				        /* Useful DOM data and selectors */
				        var $input = $(this),
				        inputContent = $input.val().toLowerCase(),
				        $panel = $input.parents('.filterableTicket'),
				        column = $panel.find('.filters th').index($input.parents('th')),
				        $table = $panel.find('.table'),
				        $rows = $table.find('tbody tr');
				        /* Dirtiest filter function ever ;) */
				        var $filteredRows = $rows.filter(function(){
				            var value = $(this).find('td').eq(column).text().toLowerCase();
				            return value.indexOf(inputContent) === -1;
				        });
				        /* Clean previous no-result if exist */
				        $table.find('tbody .no-result').remove();
				        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
				        $rows.show();
				        $filteredRows.hide();
				        /* Prepend no-result row if all rows are filtered */
				        if ($filteredRows.length === $rows.length) {
				            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
				        }
				    });
				
			}			
		}	
	});
	
	$('#discountStrana').show();
	
	
	
	
}


function viewDiscount(id)
{
	var idKarte = id;
	alert("nikola"+id);
	
	
	
	$("#fastTicketsStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#discountStrana").hide();
	$("#divLozinka").hide();
	$('#izvestaj').hide();
	
	
	$.ajax
	({
		type : 'GET',
		url : 'api/karte/getDiscounts/'+idKarte,
		dataType : 'json',
		success : function(data)
		{
			console.log(data);
			$('#postojeciPopusti').empty();
			var text = '<table class="table table-striped"><tr><th>#</th><th>Broj bodova</th><th>Popust</th></tr>'
			
			if(data.length > 0)
			{
				$.each(data,function(index,popusti)
						{
							text += '<tr><td>'+popusti.idPopusta+'</td><td>'+popusti.bodovi+'</td><td>'+popusti.popust+'</td>';
							text += '<td><button id="'+popusti.idPopusta+'" type="button" onclick="removePopust(this)" class="btn btn-warning">Remove</button></td></tr>';
						
						});
			}
				
			text += '</table>';
			$('#postojeciPopusti').html(text);
			pokupiPostojecePopuste();
			
			$("#btnPopust").unbind().click(function() {
				var idPopust = $('#selectBodove').val();
				alert(idPopust);
				
				$.ajax
				({
					type : 'POST',
					url : 'api/karte/addDiscount/'+idKarte+'/'+idPopust,
					success : function(data)
					{
						alert(data);
						viewDiscount(idKarte);
					}
					
				});
				
			});
			
		}
		
		
	});
	
	$('#divPopust').show();
	

}

function removePopust(btn)
{
	var ida = btn.id;
	$.ajax
	({
		type : 'POST',
		url : 'api/karte/removeDiscount/'+ida,
		success : function(data)
		{
			if(data == null)
			{
				alert('Ne valja!')
			}
			else
			{
				alert(data);
				viewDiscount(data);
			}
		}
		
		
	});
	
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


function report()
{
	
	$("#fastTicketsStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#discountStrana").hide();
	$("#divLozinka").hide();
	 $("#divPopust").hide();
	
	
	$('#izvestaj').show();
	
}

function changePassword()
{
	$("#fastTicketsStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#flightsStrana").hide();
	$("#priceListStrana").hide();
	$("#adminStrana").hide();
	$("#discountStrana").hide();
	$('#izvestaj').hide();
	 $("#divPopust").hide();
	

	$("#divLozinka").show();
	
}


function changePersonalData()
{
	window.location = "changePersonalData.html?id="+id+"=air"
}





