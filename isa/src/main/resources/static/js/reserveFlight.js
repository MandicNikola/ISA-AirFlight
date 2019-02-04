/**
 * 
 */

var selectedSeats = [];
var status;
var brojPrijatelja = -1;
var invitedFriends = [];

function onLoad()
{
	var adresa = window.location.search.substring(1);
	var idLet = adresa.split('=')[1];
	var tip = adresa.split('=')[2];
	
	status = "izborSedista";
	
	$('#passengers').hide();
	$('#pregledPrijatelja').hide();
	$('#finishReservation').hide();
	
	
	$.ajax({
				type : 'GET',
				url : 'api/letovi/'+idLet+'/'+tip,
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
										popuniPrijatelje(data);
									}
										
								});
						$('#pregledPrijatelja').show();
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
	var brojRedova = Math.floor(seats.length/ukupnoSedistaPoRedu)+1;
	var tableID = [];
	var ostatak = seats.length % brojRedova;
	for(i = 0; i < brojSedistaPoRedu.length; i++)
	{
		text += '<div class="col-md-'+Math.floor(12/brojSedistaPoRedu.length)+'"><table id="t"'+i+'></table></div>';
		ukupnoSedistaPoRedu += brojSedistaPoRedu[i];
		tableID.push('t'+i);
	}
	
	
	
	$('#seats').html(text);
	
	
	var counter = 0;
	var counter1 = 0;
	
	var j;
	var k;
	text = "";
	for(k = 0; k < tableID.length; k++)
	{
		counter1 = counter1 + brojSedistaPoRedu[i];
		for(i = 0; i < brojRedova; i++)
		{
			if(i != (brojRedova-1))
			{
				text += '<tr>';
				for(j = counter; i < counter1; j++)
				{
					text += '<td id="'+i+'-'+j+'">'+i+'</td>';
				}
				text += '</tr>';
			}
			else if(i == (brojRedova-1) && ostatak > 0)
			{
				text += '<tr>';
				for(j = counter; i < counter1; j++)
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
				if(value.rezervisano == true)
				{
					$('#'+value.brojReda+'-'+value.brojKolone).css('background-color', 'grey');
				}
				else
				{
					$('#'+value.brojReda+'-'+value.brojKolone).unbind("click").click(function(){
						if($(this).css('background-color') === 'rgb(255, 255, 255)')
						{
							$('#'+value.brojReda+'-'+value.brojKolone).css('background-color', 'rgb(187, 148, 231)');
							dodajSediste(value.brojReda+'-'+value.brojKolone);
						}
						else
						{
							$('#'+value.brojReda+'-'+value.brojKolone).css('background-color', 'rgb(255, 255, 255)');
							obrisiSediste(value.brojReda+'-'+value.brojKolone);
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
		}
		
		if(selectedSeats.length > 0)
		{
			$('#pregledSedista').hide();
			$('#pregledPrijatelja').show();
			
			status = "pozivanjePrijatelja";
		}	
	}
	else if(status == "pozivanjePrijatelja")
	{
		if(invitedFriends.length > (selectedSeats - 1))
		{
			alert('Pozvano je vise prijatelja nego rezervisanih mesta');
		}
		else if(invitedFriends.length == (selectedSeats - 1))
		{
			$('#pregledPrijatelja').hide();
			$('#finishReservation').show();
			status = "finishRezervation-pozivanjePrijatelja";
		}
		else if(invitedFriends.length < (selectedSeats - 1))
		{
			brojPrijatelja = (selectedSeats - 1) - invitedFriends.length;
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
			status = "finishRezervation-passengers";
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
		$('#pregledPrijatelja').show();
		$('#finishReservation').hide();
	}
	else if(status == "finishRezervation-izborSedista")
	{
		$('#finishReservation').hide();
		$('#pregledSedista').show();
		
	}
	else if(status == "passengers")
	{
		$('#pregledPrijatelja').show();
		$('#passengers').hide();
		
	}
	else if(status == "pozivanjePrijatelja")
	{
		$('#pregledPrijatelja').hide();
		$('#pregledSedista').show();
		invitedFriends = [];
		
	}
	

}


/*
 * funkcija za zavrsetak rezervacije koja mi je potrebna sacuvam rezervaciju 
 */
function zavrsiRezervaciju(mode)
{
	
	

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



