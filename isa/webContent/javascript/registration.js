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
				alert("Mail je zauzet");
			}else {
				alert('Uspesno dodat korisnik');
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
		"lozinka" : $('#lozinka').val()
	});
}