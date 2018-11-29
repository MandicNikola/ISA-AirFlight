$(document).on('submit','.registracija',function(e){
	e.preventDefault();	
	$.ajax({
		type : 'POST',
		url : "../isa/rest/api/korisnici/registracija",
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
			if(data==null){				
				alert("Mail je zauzet.");
			}else {
				alert('Uspesno ste se registrovali');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("greskaa");
		}
	});
});

function formToJSON() {
	return JSON.stringify({
		"ime" : $('#ime').val(),
		"prezime" : $('#prezime').val(),
		"mail" : $('#mail').val(),
		"telefon" : $('#broj').val(),
		"grad" : $('#grad').val(),
		"lozinka" : $('#lozinka1').val()
	});
}

$(document).ready(function(){
	
	function validacija(forma){
		let ime = $('#ime').val();
		let prezime= $('#prezime').val();
		let mail =  $('#mail').val();
		let telefon = $('#broj').val();
		let grad = $('#grad').val();
		let loz1 = $('#lozinka1').val();
		let loz2 = $('#lozinka2').val();
		let ispravno = true;
		
		  $('#imeError').html('');
		  $('#prezimeError').html('');
		  $('#mailError').html('');
		  $('#brojError').html('');
		  $('#gradError').html('');
		  $('#lozinkaError').html('');
		  $('#poruka').html('');
		
		if (!ime) {
			  $('#imeError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		
		if (!prezime) {
			  $('#prezimeError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		if (!mail) {
			  $('#mailError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		if (!telefon) {
			  $('#brojError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		if (!grad) {
			  $('#gradError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		if (!loz1) {
			  $('#lozinkaError').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		if (!loz2) {
			  $('#poruka').html('Popunite polje').css('color', 'red');
			  ispravno = false;
		}
		return ispravno;
	}
	
	$('#lozinka1, #lozinka2').on('keyup', function () {
		  if ($('#lozinka1').val() == $('#lozinka2').val()) {
			  $('#potvrdiBtn').prop('disabled', false);
			  $('#poruka').html('Ispravno uneto').css('color', 'green');
		  } else {
	        $('#potvrdiBtn').prop('disabled', true);
		    $('#poruka').html('Ne podudaraju se').css('color', 'red');
		  }
	});

}