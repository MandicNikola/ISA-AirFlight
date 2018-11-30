$(document).on('submit','.registracija',function(e){
	e.preventDefault();	
	console.log('sadas');
	$.ajax({
		type : 'POST',
		url : "../isa/rest/api/korisnici/registracija",
		contentType : 'application/json',
		dataType : "json",
		data: formToJSON(),
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
	console.log('Usao u formToJSON');
	return JSON.stringify({
		"id": 5,
		"ime" : $('#ime').val(),
		"prezime" : $('#prezime').val(),
		"mail" : $('#mail').val(),
		"telefon" : $('#broj').val(),
		"grad" : $('#grad').val(),
		"verifikovan":  $('#lozinka2').val() ,
		"lozinka" : $('#lozinka1').val()
	});
}

	
	function validation(forma){
		let ime = $('#ime').val();
		let prezime= $('#prezime').val();
		let mail =  $('#mail').val();
		let telefon = $('#broj').val();
		let grad = $('#grad').val();
		let loz1 = $('#lozinka1').val();
		let loz2 = $('#lozinka2').val();
		var ispravno = true;
		
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

$( document ).ready(function() {
		
   $('#lozinka1, #lozinka2').on('keyup', function () {
    	
			  if ($('#lozinka1').val() == $('#lozinka2').val()) {
				  $('#potvrdiBtn').prop('disabled', false);
				  $('#poruka').html('Ispravno uneto').css('color', 'green');
			  } else {
		        $('#potvrdiBtn').prop('disabled', true);
			    $('#poruka').html('Ne podudaraju se').css('color', 'red');
			  }
	
	});
});

