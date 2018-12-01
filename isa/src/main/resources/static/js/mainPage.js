/**
 * 
 */

function onLoad(){
	
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();
}

function planeShow(){
	$("#pozadinaAvion").show();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").hide();

	
}
function hotelShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").hide();
	$("#pozadinaHotel").show();
}
function carShow(){
	$("#pozadinaAvion").hide();
	$("#pozadinaAuto").show();
	$("#pozadinaHotel").hide();
	
}