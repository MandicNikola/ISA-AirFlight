/**
 * 
 */

$(document).on('submit','.changePassword',function(e){
	e.preventDefault();	
	
	var oldLoz =  $('#lozinkaOld').val();
	var loz1 = $('#lozinka1').val();
	var loz2 = $('#lozinka2').val();
				$.ajax({
					type : 'GET',
					url : "/api/korisnici/changePass/"+oldLoz+"/lozinka1/"+loz1+"/lozinka2/"+loz2,
					success : function(pov) {
						if( pov.verifikovan == "stara"){	
							 alert("Old password is not valid");
						}else if(pov.verifikovan == "ponavljanje"){
							 alert("Passwords do not match.");										
						}else {
							 window.location.href = "mainpage.html";
									
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		
	});
