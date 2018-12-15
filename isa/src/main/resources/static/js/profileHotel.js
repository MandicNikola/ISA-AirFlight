/**
 * 
 */

function onLoad(){
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	$.ajax({
		method:'GET',
		url: "/api/hoteli/findById/"+id,
		success: function(hotel){
			if(hotel == null){
				console.log('Nema servise')
			}else{
				ispisiProfilHotela(hotel);
				
			}
		}
	});
}
function ispisiProfilHotela(hotel){
	console.log("id "+hotel.id);
	$("#naziv").text('Welcome to '+hotel.naziv);
	preuzmiSobe(hotel);
}
function preuzmiSobe(hotel){
	$.ajax({
		method:'GET',
		url: "/api/hoteli/getRooms",
		success: function(lista){
			if(lista == null){
				console.log('Nema soba')
			}else{
				ispisiSobe(lista);
				
			}
		}
	});
	
}
function ispisiSobe(data){
	
	
}
function addRoom(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "NewRoom.html?id="+id;
	
	
}