
function sendMail(korisnik){
	
var mail = korisnik.mail;
console.log('mail korisnika je '+ mail);

	$.ajax({
		type : 'GET',
		url : "/api/korisnici/verifikacija/"+mail,
		success : function(pov) {
			window.location = "mainPage.html";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("greskaa");
		}
	});
}

$(document).on('submit','.registracija',function(e){
	e.preventDefault();	
	
	var ime = $('#ime').val();
	var prezime= $('#prezime').val();
	var mail =  $('#mail').val();
	var telefon = $('#telefon').val();
	var grad = $('#grad').val();
	var loz1 = $('#lozinka1').val();
	var loz2 = $('#lozinka2').val();
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
	
	console.log(ispravno);
		
	if(ispravno == true){
			
				var newuser={
								ime : $('#ime').val(),
								prezime : $('#prezime').val(),
								mail : $('#mail').val(),
								telefon : $('#telefon').val(),
								grad :  $('#grad').val(),
								lozinka : $('#lozinka1').val()				
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
							sendMail(pov);
							alert('Uspesno ste se registrovali');
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		}
	});

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

