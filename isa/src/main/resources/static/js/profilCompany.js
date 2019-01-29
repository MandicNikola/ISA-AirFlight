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
	


}

function showFlights()
{
	

}

function showFastTickets()
{
	
}

function showAdministrators()
{
	


}



