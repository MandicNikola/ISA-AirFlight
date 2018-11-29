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
		"telefon" : $('#telefon').val(),
		"grad" : $('#grad').val(),
		"lozinka" : $('#lozinka1').val()
	});
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