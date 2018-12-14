/**
 * 
 */

$(document).on('submit','.soba',function(e){
	e.preventDefault();	
	
	var adresa = window.location.search.substring(1);
	console.log('adesa je '+adresa);
	var id = adresa.split('=')[1];
	
	$.ajax({
		type : 'POST',
		url : "/api/rooms/newroom/"+id,
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
					alert('dodavanje je super');
					dodajHotelu(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog sobe");
			   
		}
	});
	
});
function dodajHotelu(data){
	$.ajax({
		type : 'POST',
		url : "/api/rooms/newroom/"+id,
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
					alert('dodavanje je super');
					dodajHotelu(data);
				}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("greska pri unosu novog sobe");
			   
		}
	});
	
}
function formToJSON() {
	var rez = 'ne';
	if ($('#balkon').is(":checked")){
		rez = 'da';
	}
	return JSON.stringify({
		"tip" : $('#tip').val(),
		"kreveti" : $('#kreveti').val(),			
		"sprat" : $('#adr').val(),
		"balkon" : rez			
	});
}
