/**
 * 
 */
function onLoad(){
	var user1 = sessionStorage.getItem("ulogovan");
	console.log(user1);
	var user = JSON.parse(user1);
	$("#ime").val(user.ime);
	$("#prezime").val(user.prezime);
	$("#telefon").val(user.telefon);
	$("#grad").val(user.grad);
	$("#mail").val(user.mail);
	
}

$(document).on('submit','.user',function(e){
	e.preventDefault();	
	
	var ime = $('#ime').val();
	var prezime= $('#prezime').val();
	var mail =  $('#mail').val();
	var telefon = $('#telefon').val();
	var grad = $('#grad').val();
	var ispravno = true;
	
	  $('#imeError').html('');
	  $('#prezimeError').html('');
	  $('#mailError').html('');
	  $('#brojError').html('');
	  $('#gradError').html('');
	
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
	
	
	console.log(ispravno);
		
	if(ispravno == true){
			
				var newuser={
								ime : $('#ime').val(),
								prezime : $('#prezime').val(),
								mail : $('#mail').val(),
								telefon : $('#telefon').val(),
								grad :  $('#grad').val()			
				}
				
				senduser= JSON.stringify(newuser);			
				console.log('user je ' + senduser);
				 
				$.ajax({
					type : 'POST',
					url : "/api/korisnici/changePersonalData",
					contentType : "application/json",
					data: senduser,
					dataType : 'json',
					success : function(pov) {
						if( pov == null){	
							 alert("Mail is already in use.");
						}else{
							sessionStorage.setItem('ulogovan',JSON.stringify(pov));
							goBack();
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('greska');
					}
				});
		}
	});
function goBack(){
	alert('dosao je');
	var adresa = window.location.search.substring(1);
	var id = adresa.split('=')[1];
	var podatak = adresa.split('=')[2]
	if(podatak == "hotel"){
		window.location = "profileHotel.html?id="+id;
	}else if(podatak == "rent"){
		window.location = "profileCar.html?id="+id;
	}
	
}