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
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];

	$.ajax({
		method:'GET',
		url: "/api/hoteli/getRooms/"+id,
		success: function(lista){
			if(lista == null){
				console.log('Nema soba')
			}else{
				ispisiSobe(lista);
				
			}
		}
	});
	
}
function ispisiSobe(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	 $("#ispisiTabelu").empty();
	 
	 $("#ispisiTabelu").append("<table class=\"table table-hover\" id=\"tabelaSoba\" ><tr><th>Room type </th><th>Capacity</th><th>Beds</th><th>Price for night</th></tr>");
		
		$.each(pom, function(index, data) {
			$("#tabelaSoba").append("<tr><td class=\"hoverName\">"+data.tip+"</td><td> "+data.kapacitet+"</td><td>"+data.kreveti+"</td><td>"+data.cijena+"</td></tr>");
			
		});
	 $("#ispisiTabelu").append("</table>");
}

function addRoom(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	window.location = "NewRoom.html?id="+id;
		
}
function addConfiguration(){
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	
	
}