/**
 * funkcije za rad sa profilom kompanije koje koristim
 */
function onLoad(){
	$("#adminStrana").hide();
	$("#flightsStrana").hide();
	$("#planesStrana").hide();
	$("#fastTicketsStrana").hide();
	$("#priceListStrana").hide();
 	
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
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
				
				text = "<table class=\"table table-striped\" id=\"tabelaAvion\" ><tr><th> Name </th><th colspan='2'> Operations </th></tr>";

				$.each(data,function(index,value)
				{
					text += "<tr><td class=\"hoverName\" >"+value.naziv+"</td><td><button  class=\"btn btn-info\" onclick=\"changeConfiguration('"+value.id+"')\">Change configuration</button></td><td><button  class=\"btn btn-info\" onclick=\"deletePlane('"+value.id+"')\">Delete</button></td></tr>";

				});
				text += "</table>"
				$("#planesStrana").append(text);
				$("#planesStrana").show();
			}
			
		}
		
		
	});

}

function showFlights()
{
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	$("#adminStrana").hide();
	$("#informacije").hide();
	$("#planesStrana").hide();
	$("#fastTicketsStrana").hide();
	$("#priceListStrana").hide();
	
	$.ajax
	({
		type : 'GET',
		url : 'api/kompanije/flights/'+id,
		dataType : 'json',
		success : function(data)
		{
			if(data == null || data.length == 0)
			{
				$("#flightsStrana").append('<h1>Trenutno ne postoje avioni!<h1>');
			}
			else
			{
				$("#flightsStrana").empty();
				var text = "";
				
				text = "<table class=\"table table-striped\" id=\"tabelaAvion\" ><tr><th> Name </th><th colspan='2'> Operations </th></tr>";

				$.each(data,function(index,value)
				{
					text += "<tr><td class=\"hoverName\" >"+value.naziv+"</td><td><button  class=\"btn btn-info\" onclick=\"changeConfiguration('"+value.id+"')\">Change configuration</button></td><td><button  class=\"btn btn-info\" onclick=\"deletePlane('"+value.id+"')\">Delete</button></td></tr>";

				});
				text += "</table>"
				$("#planesStrana").append(text);
				$("#flightsStrana").show();
			}
			
		}
		
		
	});
	

}

function showFastTickets()
{
	
}

function showAdministrators()
{
	


}

function searchFlights()
{
	

}



