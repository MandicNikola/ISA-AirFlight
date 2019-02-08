/**
 * 
 */

var selectedSeats = [];
var status ="";
var brojPrijatelja = 0;
var invitedFriends = [];
var imaPrijatelje = false;
var passengers = [];

var brojKarata;

var idRezervacije = -1;
var lokacija;
var datumSletanja;

function onLoad()
{
	var adresa = decodeURIComponent(window.location.search.substring(1));
	
	var idLet = adresa.split('=')[1];
	var tip = adresa.split('=')[2];
	lokacija = adresa.split('=')[4];
	datumSletanja = adresa.split('=')[3];
	alert(lokacija);
	
	status = "izborSedista";
	
	$('#passengers').hide();
	$('#pregledPrijatelja').hide();
	$('#finishReservation').hide();
	
	alert('api/letovi/seats/'+idLet+'/'+tip);
	$.ajax({
				type : 'GET',
				url : 'api/letovi/seats/'+idLet+'/'+tip,
				dataType: 'json',
				success : function(data)
				{
					var seats = data;
					if(seats.length > 0)
					{
						var configuration = seats[0].konfiguracija;
						
						
						drawSeats(seats,configuration);
						$.ajax(
								{
									type : 'GET',
									url : 'api/korisnici/friends',
									dataType: 'json',
									success : function(data)
									{
										if(data == null || data.length == 0)
										{
											imaPrijatelje = false;
										}
										else
										{
											popuniPrijatelje(data);
										}
									}
								});
					}
						
				}
				
			});
}

/*
 *funkcija za iscrtavanja sedista koja mi je potrebna da bi znao koja sedista da rezervisem i ostalo 
 */
function drawSeats(seats,configuration)
{
	
	var brojSedistaPoRedu = configuration.split('-');
	
	var i;
	var text = "";
	var ukupnoSedistaPoRedu = 0;
	
	var tableID = [];
	
	for(i = 0; i < brojSedistaPoRedu.length; i++)
	{
		text += '<div class="col-md-'+Math.floor(12/brojSedistaPoRedu.length)+'"><table id="t'+i+'"></table></div>';
		ukupnoSedistaPoRedu += parseInt(brojSedistaPoRedu[i]);
		tableID.push("t"+i);
	}
	var brojRedova = Math.floor(seats.length/ukupnoSedistaPoRedu)+1;
	var ostatak = seats.length % ukupnoSedistaPoRedu;
	
	$('#seats').html(text);
	
	
	var counter = 0;
	var counter1 = 0;
	
	var j;
	var k;
	text = "";
	for(k = 0; k < tableID.length; k++)
	{
		counter1 = counter1 + parseInt(brojSedistaPoRedu[k]);
		for(i = 0; i < brojRedova; i++)
		{
			if(i != (brojRedova-1))
			{
				text += '<tr>';
				for(j = counter; j < counter1; j++)
				{
					text += '<td id="'+i+'-'+j+'">'+i+'</td>';
				}
				text += '</tr>';
			}
			else if(i == (brojRedova-1) && ostatak > 0)
			{
				text += '<tr>';
				for(j = counter; j < counter1; j++)
				{
					text += '<td id="'+i+'-'+j+'">'+i+'</td>';
					ostatak--;
					if(ostatak == 0)
						break;
				}
				text += '</tr>';
			}
		}
		counter = counter1;
		$('#'+tableID[k]).html(text);
		
		text = "";
		
	}
	$.each(seats,function(index,value)
			{
		
				$('#'+value.brojReda+'-'+value.brojKolone).css('background-color','rgb(255, 255, 255)');
				
				if(value.rezervisano == true)
				{
					$('#'+value.brojReda+'-'+value.brojKolone).css('background-color', 'grey');
				}
				else
				{
					$('#'+value.brojReda+'-'+value.brojKolone).click(function(){
						if($('#'+value.brojReda+'-'+value.brojKolone).css('background-color') === 'rgb(255, 255, 255)')
						{
							
							$('#'+value.brojReda+'-'+value.brojKolone).css('background-color', 'rgb(187, 148, 231)');
							dodajSediste(value.brojReda+'-'+value.brojKolone+'-'+value.idKarte);
						}
						else
						{
							
							$('#'+value.brojReda+'-'+value.brojKolone).css('background-color', 'rgb(255, 255, 255)');
							obrisiSediste(value.brojReda+'-'+value.brojKolone+'-'+value.idKarte);
						}				   
					  });			
				}
			});
			
}

function popuniPrijatelje(prijatelji)
{
	
	var text = '<table class="table table-striped" id="tableFriends">';
	    text += '<thead><tr><th scope="col">First Name</th> <th scope="col">Last Name</th> <th scope="col">Invite</th>  </tr>  </thead><tbody>'
	  	
	$.each(prijatelji,function(index,value)
			{
				var info = value.split('-');
				var friendName = info[0];
				var friendLastName = info[1];
				var friendID = info[2];
				var tip = info[3];
				if(tip == "FRIENDS")
				{
					imaPrijatelje = true;
					text += '<tr><td>'+friendName+'</td><td>'+friendLastName+'</td><td> <input type="checkbox" id="'+friendID+'ch"></td></tr>'
				}
			});
	    
	    text += '</tbody></table>';
	
	    $('#prijatelji').html(text);
	    $.each(prijatelji,function(index,value)
				{
					var info = value.split('-');
					var friendID = info[2];
					
					$('#'+friendID+'ch').change(function()
							{
								if($('#'+friendID+'ch').is(":checked"))
								{
								   invitedFriends.push(friendID);
								}
								else
								{
									invitedFriends.splice(invitedFriends.indexOf(friendID),1);
								}
							});			
				});
	    
	    
	        
}



function obrisiSediste(sediste)
{
	selectedSeats.splice(selectedSeats.indexOf(sediste),1);
}



function dodajSediste(sediste)
{
	selectedSeats.push(sediste);
}

/*
 * radim dalju redirekciju koja mi je potrebna za to
 */
function dalje()
{
	
	if(status == "izborSedista")
	{
		if(selectedSeats.length == 1)
		{
			$('#pregledSedista').hide();
			$('#finishReservation').show();
			status = "finishRezervation-izborSedista";
			$("#nextBtn").val('Finish');
		}
		
		if(selectedSeats.length > 0)
		{
			if(imaPrijatelje)
			{
				$('#pregledSedista').hide();
				$('#finishReservation').hide();
				
				$('#pregledPrijatelja').show();
				status = "pozivanjePrijatelja";
			}
			else
			{
				$('#finishReservation').hide();
				$('#pregledSedista').hide();
				$('#passengers').show();
				brojPrijatelja = selectedSeats.length  - 1;
				status = "passengers";
			}
			
		}	
	}
	else if(status == "pozivanjePrijatelja")
	{
		if(invitedFriends.length > (selectedSeats.length  - 1))
		{
			alert('Pozvano je vise prijatelja nego rezervisanih mesta');
		}
		else if(invitedFriends.length == (selectedSeats.length  - 1))
		{
			$('#pregledPrijatelja').hide();
			$('#finishReservation').show();
			$("#nextBtn").val('Finish');
			status = "finishRezervation-pozivanjePrijatelja";
		}
		else if(invitedFriends.length < (selectedSeats.length  - 1))
		{
			brojPrijatelja = (selectedSeats.length - 1) - invitedFriends.length;
			status = "passengers";
			$('#pregledPrijatelja').hide();
			$('#passengers').show();
				
		}
	}
	else if(status == "passengers")
	{
		if(brojPrijatelja == 0)
		{
			$('#passengers').hide();
			$('#finishReservation').show();
			$("#nextBtn").val('Finish');
			status = "finishRezervation-passengers";
			$("#nextBtn").val('Finish');
		}
		else
		{
			alert('Popunite sve putnike!');
		}
	}
	else if(status.includes("finish"))
	{
		var mode = "zavrsi";
		zavrsiRezervaciju(mode);
	}
	
	
	
}

function back()
{
	
	if(status == "finishRezervation-passengers")
	{
		$('#finishReservation').hide();
		$('#passengers').show();
		status = "passengers";
		
	}
	else if(status == "finishRezervation-pozivanjePrijatelja")
	{
		status = "pozivanjePrijatelja";
		$('#pregledPrijatelja').show();
		$('#finishReservation').hide();
		$("#nextBtn").val('Next');
	}
	else if(status == "finishRezervation-izborSedista")
	{
		status = "izborSedista";
		$('#finishReservation').hide();
		$('#pregledSedista').show();
		$("#nextBtn").val('Next');
	}
	else if(status == "passengers")
	{
		if(imaPrijatelje)
		{
			status = "pozivanjePrijatelja";
			$('#pregledPrijatelja').show();
			$('#passengers').hide();
			passengers = [];
		}
		else
		{	
			status = "izborSedista";
			$('#passengers').hide();
			$('#pregledSedista').show();
			passengers = [];
		}
	}
	else if(status == "pozivanjePrijatelja")
	{
		$('#pregledPrijatelja').hide();
		$('#pregledSedista').show();
		status = "izborSedista";
		
	}
	

}


/*
 * funkcija za zavrsetak rezervacije koja mi je potrebna sacuvam rezervaciju 
 */
function zavrsiRezervaciju(mode)
{
	
	
	if(passengers.length >= 0)
	{
		var putnici = [];
		
		$.each(passengers,function(index,value)
				{
					var sediste = selectedSeats.pop();
					var putnik = value;
					putnik.idKarte = sediste.split("-")[2];
					putnici.push(putnik);
				});
		
		brojKarata = selectedSeats.length;
		var idTicket = selectedSeats.pop().split("-")[2];
		
		
		$.ajax
		({
			type : 'POST',
			url : 'api/letovi/makeReservation/'+idTicket,
			contentType: "application/json",
			data : JSON.stringify(putnici),
			success : function(data)
			{
				
				idRezervacije = data;
				
				if(invitedFriends.length > 0)
				{
					var pozivnice = [];
					$.each(invitedFriends,function(index,value)
							{
								var sediste = selectedSeats.pop();
								var friendId = value;
								var idKarte =  sediste.split("-")[2];
								
								
								var promenljiva = {ticketID : idKarte, korisnikID : friendId};
								pozivnice.push(promenljiva);
							});
					
					$.ajax({
						type : 'POST',
						url : 'api/letovi/makeInvitations',
						contentType: "application/json",
						data : JSON.stringify(pozivnice),
						success : function(data1)
						{
							alert("uspesno rezervacija");	
												
						}
				
					});
				}
				if(mode == "oba")
				{
					window.location = "redirekcija.html?id="+"hotel"+'='+idRezervacije+"="+lokacija+"="+datumSletanja+"="+"1"+"="+brojKarata;
				}
				else if(mode == "rentAcar")
				{
					window.location = "redirekcija.html?id="+"rent"+'='+idRezervacije+"="+lokacija+"="+datumSletanja+"="+"0"+"="+brojKarata;
				}
				else if(mode == "hotels")
				{
					window.location = "redirekcija.html?id="+"hotel"+'='+idRezervacije+"="+lokacija+"="+datumSletanja+"="+"0"+"="+brojKarata;
				}
				else
				{
					window.location = "mainPage.html";
				}
				
			}
					
		});
		
		
	}
}


function both()
{
	var mode = "oba";
	zavrsiRezervaciju(mode);
}

function rentAcar()
{
	var mode = "rentAcar";
	zavrsiRezervaciju(mode);

}

function hotels()
{
	var mode = "hotels";
	zavrsiRezervaciju(mode);
	
}


function addPassenger()
{
	alert('usao u dugme add');
	if(brojPrijatelja > 0)
	{
		var ispravno = true;
		
		if($('#name').val() == "")
			ispravno = false;
		if($('#lastName').val() == "")
			ispravno = false;
		if($('#phone').val() == "")
			ispravno = false;
		if($('#passport').val() == "")
			ispravno = false;
		if($('#email').val() == "")
			ispravno = false;
		if($('#birthday').val() == "")
			ispravno = false;
		
		var passenger = {ime : $('#name').val(), prezime : $('#lastName').val(), 
			telefon : $('#phone').val(), passport : $('#passport').val(), mail : $('#email').val(),
			datumRodjenja : $('#birthday').val()
		};

		if(ispravno)
		{
			alert("putnik uspesno dodat!");
			passengers.push(passenger);
			brojPrijatelja--;
			$('#name').val("");
			$('#lastName').val("");
			$('#passport').val("");
			$('#email').val("");
			$('#birthday').val("");
			$('#phone').val("");
			
		}
	}
	
}


/*
 * filter funkcije koja mi treba za rad vezan za ovo
 */

$(document).ready(function(){
	  $("#searchFriends").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#tableFriends tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});



