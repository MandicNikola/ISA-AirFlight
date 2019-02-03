/**
 * 
 */

function onLoad()
{
	var adresa = window.location.search.substring(1);
	var idLet = adresa.split('=')[1];
	var tip = adresa.split('=')[2];
	
	
	
	

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
	for(i = 0; i < brojSedistaPoRedu.length; i++)
	{
		text += '<div class="col-md-'+Math.floor(12/brojSedistaPoRedu.length)+'"><table id="t"'+i+'></table></div>';
		ukupnoSedistaPoRedu += brojSedistaPoRedu[i];
	}
	
	$.('#seats').append(text);
	
	
	var brojRedova = Math.floor(seats.length/ukupnoSedistaPoRedu)+1;
	var counter = 0;
	
	for(i = 0; i < brojRedova; i++)
	{
		
		
	}
	

}


