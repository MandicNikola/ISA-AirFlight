function loadInformacije(){
	
	var podatak = window.location.search.substring(1);
	var niz= podatak.split("=");
	var id= niz[1]; 
	
	//$.ajax({
		//  url : "/api/filijale/vratiFilijalu/"+id,
		  //type: 'get',
		  //success: function(filijala) {
			//  iscrtajStranicu(filijala);
			//}
	//	});


}