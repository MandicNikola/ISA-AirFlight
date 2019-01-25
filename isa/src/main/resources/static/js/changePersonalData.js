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