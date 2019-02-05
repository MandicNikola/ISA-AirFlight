/**
 * 
 */
var idPozivnica;

function onLoad()
{
	var pom=window.location.search.substring(1);
	var idPozivnica = pom.split('=')[1];
	$.ajax(
			{
				type : 'GET',
				url : 'api/letovi/pozivnicaFlight/'+idPozivnica,
				dataType : 'json',
				success : function(data)
				{
					$('#klasaPuta').val(data.klasa);
					$('#selectPoletanje').val(data.lokPoletanja);
					$('#selectSletanjee').val(data.lokSletanja);
					$('#datumPoletanja').val(data.datumPoletanja);
					$('#vremePoletanja').val(data.vremePoletanja);
					$('#datumSletanja').val(data.datumSletanja);
					$('#vremeSletanja').val(data.vremeSletanja);
					$('#cenaPutovanja').val(data.cena);
					
					
				}
			});


}

function accept()
{
	$.ajax(
			{
				type : 'POST',
				url : 'api/letovi/acceptInvitation/'+idPozivnica,
				success : function(data)
				{
					window.location = "mainPage.html";
				}
			});

}

function decline()
{
	$.ajax(
			{
				type : 'POST',
				url : 'api/letovi/declineInvitation/'+idPozivnica,
				success : function(data)
				{
					window.location = "mainPage.html";
				}
			});


}