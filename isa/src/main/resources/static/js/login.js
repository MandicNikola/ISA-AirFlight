$(document).on('submit','.logovanje',function(e){
	e.preventDefault();	
	
	var mail =  $('#mail').val();
	var loz1 = $('#lozinka').val();
	var ispravno = true;
	
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
	
	if(ispravno == true){
				 
				$.ajax({
					type : 'GET',
					url : "/api/korisnici/logovanje?mail="+mail + "&lozinka="+loz1,
					success : function(pov) {
						if( pov.verifikovan == ""){	
							 alert("Mail i lozinka se ne poklapaju");
						}else if(pov.verifikovan == "null"){
							 alert("Ne postoji nalog sa unetim mail-om.");										
						}else if(pov.verifikovan == "aktivacija"){
							 alert("Morate prvo aktivirati nalog.");
								
						}else{
							sessionStorage.setItem('ulogovan',JSON.stringify(pov));
							window.location.href = "mainpage.html";
						
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		}
	});


