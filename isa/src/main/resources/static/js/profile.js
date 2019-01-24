/**
 * 
 */
var user;

function onLoad()
{
	adresa = window.location.search.substring(1);
	console.log('user je '+adresa);
	user = adresa.split('=')[1];
	
	
	



}
