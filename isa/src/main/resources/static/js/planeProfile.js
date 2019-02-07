/**
 * 
 */

var idAviona;
var selektovanoSediste ="";


function onLoad()
{
	var adresa = window.location.search.substring(1);
	idAviona = adresa.split('=')[1];
	
	$.ajax(
	{
		type : 'GET',
		url: 'api/avioni/'+idAviona,
		dataType : 'json',
		success : function(data)
		{
			$('#configurationPan').hide();
			$('#services').hide();
			
			$('#name').val(data.naziv);
			$('#configuration').val(data.konfiguracija);
			
			$('#planeInfo').show();
		}
	});
	

}


function info()
{
	onLoad();
}

function konfiguracija()
{
	
	$.ajax
	({
		type : 'GET',
		url : 'api/avioni/seats/'+idAviona+'/ekonomska',
		dataType : 'json',
		success : function(data)
		{
			alert(data);
			var configuration = data[0].konfiguracija;
			var klasa = "economic";
			drawSeats(data,configuration,klasa);
			
			$.ajax
			({
				type : 'GET',
				url : 'api/avioni/seats/'+idAviona+'/biznis',
				dataType : 'json',
				success : function(data1)
				{
					var klasa = "business";
					var configuration = data1[0].konfiguracija;
					drawSeats(data1,configuration,klasa);
					
					$.ajax
					({
						type : 'GET',
						url : 'api/avioni/seats/'+idAviona+'/first',
						dataType : 'json',
						success : function(data2)
						{
							var klasa = "first";
							var configuration = data2[0].konfiguracija;
							drawSeats(data2,configuration,klasa);
							$('#planeInfo').hide();
							$('#configurationPan').show();
							$('#services').hide();
						}
					});
				}
			});
			
		}
	});
	
}

function services()
{
	$.ajax(
			{
				type : 'GET',
				url: 'api/avioni/usluge/'+idAviona,
				dataType : 'json',
				success : function(data)
				{
					if(data == null || data.length == 0)
					{
						$('#planeInfo').hide();
						$('#configurationPan').hide();
						$('#services').show();
					}
					else
					{
						
					
						
						$('#tabelaFil').empty();
						
						
						var text = "";				    
						$.each(data,function(index,value)
								{
									text += "<tr><td class=\"hoverName\" >"+value.naziv+"</td><td>"+value.cena+'</td><td>'+value.klasa+'</td><td>'+value.opis+'</td><td><button type="button" class="btn btn-danger" onclick="removeUsluga('+value.id+')">Remove</button>' +'</td></tr>';                  
								});
						$('#tabelaFil').append(text);
						
						 $('.filterable .filters input').unbind().keyup(function(e){
						        /* Ignore tab key */
							 	
							   
						        var code = e.keyCode || e.which;
						        if (code == '9') return;
						        /* Useful DOM data and selectors */
						        var $input = $(this),
						        inputContent = $input.val().toLowerCase(),
						        $panel = $input.parents('.filterable'),
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
						
						$('#planeInfo').hide();
						$('#configurationPan').hide();
						$('#services').show();
					}
				}
			});
	
}

function removeUsluga(id)
{
	$.ajax
	({
		type : 'POST',
		url : 'api/avioni/obrisiUslugu/'+id,
		dataType : 'text',
		success : function(data)
		{
			alert(data);
			services();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert(textStatus);
		}
	});
	
}


$(document).on('submit','.plane',function(e){
	e.preventDefault();	
	
	var ime = $('#name').val();
	var prezime= $('#configuration').val();
	var ispravno = true;
	if(ime == "" || prezime == "")
	{	
		ispravno = false;
		alert("nije sve popunjeno unesite imena!");
	}
	
	if(ispravno)
	{
		var updatePlane={
						naziv : $('#name').val(),
						konfiguracija : $('#configuration').val()			
		}
		
		sendPlane= JSON.stringify(updatePlane);			
		 
		$.ajax({
			type : 'POST',
			url : "/api/avioni/updatePlane/"+idAviona,
			contentType : "application/json",
			data: sendPlane,
			success : function(pov) {
				alert(pov);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
});



$(document).on('submit','.service',function(e){
	e.preventDefault();	
	
	var naziv = $('#nazivUsluga').val();
	var opis= $('#opisUsluge').val();
	var cena = $('#cenaUsluga').val();
	var klasa= $('#selectClassUsluga').val();
	
	
	
	var ispravno = true;
	if(naziv == "" || opis == "" || cena == "" || klasa == "" )
	{	
		ispravno = false;
		alert("Nije sve popunjeno");
	}
	
	if(ispravno)
	{
		var service={
				naziv : $('#nazivUsluga').val(),
				opis : $('#opisUsluge').val(),
				cena : $('#cenaUsluga').val(),
						klasa : $('#selectClassUsluga').val()
		}
		
		sendService= JSON.stringify(service);			
		
		 
		$.ajax({
			type : 'POST',
			url : "/api/avioni/addService/"+idAviona,
			contentType : "application/json",
			data: sendService,
			success : function(pov) {
				alert(pov);
				services();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
});



$(document).ready(function(){
    $('.filterable .btn-filter').click(function(){
        var $panel = $(this).parents('.filterable'),
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



function drawSeats(seats,configuration,klasa)
{
	
	var brojSedistaPoRedu = configuration.split('-');
	
	var i;
	var text = "";
	var ukupnoSedistaPoRedu = 0;
	
	var tableID = [];
	
	for(i = 0; i < brojSedistaPoRedu.length; i++)
	{
		text += '<div class="col-md-'+Math.floor(12/brojSedistaPoRedu.length)+'"><table class="tt" id="t'+i+klasa+'"></table></div>';
		ukupnoSedistaPoRedu += parseInt(brojSedistaPoRedu[i]);
		tableID.push("t"+i+klasa);
	}
	var brojRedova = Math.floor(seats.length/ukupnoSedistaPoRedu)+1;
	var ostatak = seats.length % ukupnoSedistaPoRedu;
	
	$('#'+klasa+'Class').html(text);
	
	
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
					text += '<td class="tdd" id="'+i+'-'+j+'-'+klasa+'">'+i+'</td>';
				}
				text += '</tr>';
			}
			else if(i == (brojRedova-1) && ostatak > 0)
			{
				text += '<tr>';
				for(j = counter; j < counter1; j++)
				{
					text += '<td class="tdd" id="'+i+'-'+j+'-'+klasa+'">'+i+'</td>';
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
		
				$('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).css('background-color','rgb(255, 255, 255)');
				
				if(value.rezervisano == true)
				{
					$('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).css('background-color', 'grey');
				}
				else if(value.status == "obrisano")
				{
					$('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).css('background-color', 'red');
				}
				else
				{	//ostaje mi da dovrsim ovo ujutro sta mi fali
					$('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).click(function(){
						if($('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).css('background-color') === 'rgb(255, 255, 255)')
						{
							if(selektovanoSediste == "" || selektovanoSediste == null)
							{
								selektovanoSediste = value.brojReda+'-'+value.brojKolone+'-'+klasa+'-'+value.idSedista;
							}
							else
							{
								var podaci = selektovanoSediste.split('-');
								$('#'+podaci[0]+'-'+podaci[1]+'-'+klasa).css('background-color', 'rgb(255, 255, 255)');
								selektovanoSediste = value.brojReda+'-'+value.brojKolone+'-'+klasa+'-'+value.idSedista;
							}
								
							$('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).css('background-color', 'rgb(187, 148, 231)');
							
						}
						else
						{
							
							$('#'+value.brojReda+'-'+value.brojKolone+'-'+klasa).css('background-color', 'rgb(255, 255, 255)');
							selektovanoSediste = "";
						}				   
					  });			
				}
			});
	
	
	
	
}




function addSeat(klasa)
{
	alert(klasa);
	
	$.ajax
	({
		type : 'POST',
		url : 'api/avioni/dodajSediste/'+idAviona+'/'+klasa,
		success : function(data)
		{
			alert(data);
			konfiguracija();
		}
	});
	
	
}

function removeSeat(klasa)
{
	alert(klasa);
	if(selektovanoSediste == "" || selektovanoSediste == null)
	{
		alert("selektujte sediste");
	}
	else
	{
		alert(selektovanoSediste);
		var podaci = selektovanoSediste.split('-');
		
		var podatak = {idSedista : podaci[3]};
		
		$.ajax
		({
			type : 'POST',
			url : 'api/avioni/obrisiSediste/'+idAviona+'/'+klasa,
			contentType: "application/json",
			data : JSON.stringify(podatak),
			success: function(data)
			{
				alert(data);
				konfiguracija();
			}
			
			
		});
	}
	
}










