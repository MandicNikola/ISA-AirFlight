/**
 * 
 */

var tip;
var idRez;
var datumSletanja;
var status;
var lokacija;
var brojKarata;

function load()
{
	var pom=window.location.search.substring(1);
	
	var decoded = decodeURIComponent(pom);
	alert(decoded);
	
	 tip = decoded.split('=')[1];
	 idRez = decoded.split('=')[2];
	 lokacija = decoded.split('=')[3];
	 datumSletanja = decoded.split('=')[4];
	 status = decoded.split('=')[5];
	 brojKarata = decoded.split('=')[6];
	 lokacija = lokacija.replace('%20',' ');
	console.log(lokacija);
	if(tip == "hotel")
	{
		
		$.ajax({
			
			type : 'GET',
			url : 'api/hoteli/hotel/'+lokacija,
			dataType : 'json',
			success : function(data)
			{
				var text = "";
				
				text += "<table class=\"table table-hover\" id=\"tabVozilo\" ><thead><tr><th>Naziv</th><th>Adresa</th><th>Grad</th></tr></thead>";
				
				$.each(data, function(index, hotel) {
					text += "<tr class=\"thead-light \"><td class=\"hoverName\">"+hotel.naziv+"</td><td> "+hotel.adresa+"</td><td > "+hotel.grad+'</td><td><button type="button" id="'+hotel.id+'" onclick="go(this)" class="btn btn-info">Go</button></td></tr>';
					
				});
			    text += "</table>";
			    $('#ispis').html(text);
			}
		
			
			
			
		});
		
		
	}
	else
	{
		$.ajax({
			
			type : 'GET',
			url : 'api/filijale/poGradu/'+lokacija,
			dataType : 'json',
			success : function(data)
			{
				var text = "";
				
				text += "<table class=\"table table-hover\" id=\"tabVozilo\" ><thead><tr><th>Naziv</th><th>Adresa</th><th>Grad</th></tr></thead>";
				
				$.each(data, function(index, rent) {
					text += "<tr class=\"thead-light \"><td class=\"hoverName\">"+rent.naziv+" Filijala</td><td> "+rent.adresa+"</td><td > "+rent.grad+'</td><td><button type="button" id="'+rent.idServisa+'" onclick="goRent(this)" class="btn btn-info">Go</button></td></tr>';
					
				});
			    text += "</table>";
			    $('#ispis').html(text);
			}
		
			
			
			
		});		
		
		
	}
	
}

function go(button)
{
	var id = button.id;
	
	window.location = "profileHotel.html?id="+id+'='+idRez+'='+datumSletanja+'='+lokacija+'='+status+'='+brojKarata;
		
}

function goRent(button)
{
	var id = button.id;
	
	window.location = "profileCar.html?id="+id+'='+idRez+'='+datumSletanja+'='+lokacija+'='+status+'='+brojKarata;

		

}



