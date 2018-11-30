function validacija(forma){
		let mail =  $('#mail').val();
		let loz1 = $('#lozinka1').val();
		  $('#mailError').html('');
		  $('#lozinkaError').html('');
		
		if (!mail) {
			  $('#mailError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		
		if (!loz1) {
			  $('#lozinkaError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		
		return ispravno;
}
	
