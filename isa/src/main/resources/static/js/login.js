function onload(){
	 $('#divChangePass').hide();
	 $('#divLogin').show();
			
	
}
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
							checkUser(pov);
						
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		}
	});


function checkUser(pov){
	
	if(pov.tip == 'REGISTROVAN' || pov.tip == 'ADMIN_SISTEM'){
		window.location.href = "mainpage.html";
		
	}else if(pov.adminPotvrdio){
		window.location.href = "mainpage.html";
		
	}else{
		
		 $('#divLogin').hide();
		 $('#divChangePass').show();
		
	}
}

$(document).on('submit','.changePassword',function(e){
	e.preventDefault();	
	
	var oldLoz =  $('#lozinkaOld').val();
	var loz1 = $('#lozinka1').val();
	var loz2 = $('#lozinka2').val();
			 
				$.ajax({
					type : 'GET',
					url : "/api/korisnici/changePass?oldPass="+oldLoz+ "&lozinka1="+loz1+"&lozinka2="+loz2,
					success : function(pov) {
						if( pov.verifikovan == "stara"){	
							 alert("Old password is not valid");
							 repeatChange();
								
						}else if(pov.verifikovan == "ponavljanje"){
							 alert("Passwords do not match.");										
							 repeatChange();
						}else {
							 alert("Izmjenio .");
							 window.location.href = "mainpage.html";
									
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		
	});

function repeatChange(){
	 $('#divLogin').hide();
	 $('#divChangePass').show();

}