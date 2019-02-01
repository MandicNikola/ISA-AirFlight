/**
 * 
 */
function onLoad(){
	var adresa = window.location.search.substring(1);
	console.log('adresa je '+adresa);
	var id = adresa.split('=')[1];
	
	
	$.ajax({
		  url : "/api/hoteli/getKonfiguracije/"+id,
		  type: 'get',
		  success: function(pom) {
			  if(pom == null){
				  ispisiUpozorenje();
			  }else if(pom.length == 0){
				  console.log('prazan');
				  ispisiUpozorenje();
			  }else{
				  popuniPolja(pom);
			  }
			  
			}
		});

}
function ispisiUpozorenje(){
	$('#sakrij').hide();
	
	$('#sastav').empty();
	
	$('#sastav').append("<p>You have to add configuration of the room first.</p><p><button type=\"button\" onclick=\"vratiSe()\" class=\"btn btn-link\">Go to add</button></p>");

	
}
function vratiSe(){
	var adresa = window.location.search.substring(1);
	console.log('adresa je '+adresa);
	var id = adresa.split('=')[1];
	window.location = "profileHotel.html?id="+id;
	
}
function popuniPolja(lista){
	var pom = lista == null ? [] : (lista instanceof Array ? lista : [ lista ]);
	$.each(pom, function(index, data) {
		 $("#tip").append("<option  value=\""+data.naziv+"\">"+data.naziv+"</option>");	 	 
	 });
}
$(document).on('submit','.soba',function(e){
	e.preventDefault();	
	$.ajax({
		type : 'POST',
		url : "/api/rooms/newroom",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
				if(data.tip == "Tip"){
					console.log('niste odabrali kategoriju');					
					
				}else if(data.tip == "Kreveti"){
					console.log('niste odabrali broj kreveta');					
					
				}else if(data.tip == "Sprat"){
					console.log('niste odabrali dozvoljeni sprat');					
					
				}else if(data == null){
					alert('dodavanje nije super');	
				}else{
					dodajHotelu(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu nove sobe");
			   
		}
	});
	
});

function dodajHotelu(data){
	var soba = JSON.stringify(data);
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	
	
	
	$.ajax({
		type : 'POST',
		url : "/api/hoteli/addRoom/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:soba,
		success : function(data) {
				if(data == null){
					alert('greska pri dodavanju sobe u hotel');		
				}else{
			prikaziHotel();
				}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri prikazu hotela");
			   
		}
	});

	
}
function prikaziHotel(){
	var adresa = window.location.search.substring(1);
	console.log('adresa je '+adresa);
	var id = adresa.split('=')[1];
	window.location = "profileHotel.html?id="+id;
		
}
function formToJSON() {
	var rez = 'ne';
	if ($('#balkon').is(":checked")){
		rez = 'da';
	}
	return JSON.stringify({
		"tip" : $('#tip').val(),			
		"sprat" : $('#sprat').val(),
		"kapacitet": $('#kreveti').val(),
		"cijena":$('#cijena').val(),
		"balkon" : rez			
	});
}
