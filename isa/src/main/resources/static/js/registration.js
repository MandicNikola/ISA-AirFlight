$(document).on('submit','.registracija',function(e){
	e.preventDefault();	
	console.log('sadas');

	var imek=$('#ime').val();
	var przk= $('#prezime').val();
	var mailk=$('#mail').val();
	var telk=$('#telefon').val();
	var gradk= $('#grad').val();
	var lozinkak = $('#lozinka1').val();
	
	console.log('ime ' + imek + 'prezime ' + przk + 'mail ' + mailk);
	console.log('telefon ' + telk + 'grad ' + gradk + 'lozinka '+lozinkak);
	
	var newuser={
					ime : imek,
					prezime : przk,
					mail : mailk,
					telefon : telk,
					grad : gradk,
					lozinka : lozinkak				
	}
	
	senduser= JSON.stringify(newuser);
	
	console.log('user je ' + senduser);
	 
	$.ajax({
		type : 'POST',
		url : "/api/korisnici/registracija",
		contentType : "application/json",
		data: senduser,
		dataType : 'json',
		success : function(pov) {
			if( pov.verifikovan == "null"){	
				 alert("Mail je zauzet.");
			}else{
				alert('Uspesno ste se registrovali');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("greskaa");
		}
	});
});

function validation(forma){
		let ime = $('#ime').val();
		let prezime= $('#prezime').val();
		let mail =  $('#mail').val();
		let telefon = $('#telefon').val();
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

