/**
 * 
 */
var user;

function onLoad()
{
	
	$.ajax(
			{
				method:'GET',
				url: "/api/korisnici/user",
				success : function(data)
				{
					var korisnik = data;
					if(data == null)
					{
						alert("Ne postoji ulogovan korisnik!");
					}
					else
					{
						$('#name').val(korisnik.ime);
						$('#lastname').val(korisnik.prezime);
						$('#email').val(korisnik.mail);
						$('#phone').val(korisnik.telefon);
						$('#town').val(korisnik.grad);
						
					}
					
				}
				
				
			});
	
	$("#profile").show();
	$("#Friends").hide();
	$("#Requests").hide();
	$("#Search").hide();
		
}

function profile()
{
	onLoad();
}


function friends()
{
	$('#friendsTable').empty();
	$.ajax(
			{
				method:'GET',
				url: "/api/korisnici/friends",
				success : function(data)
				{
					var korisnik = data;
					if(data == null)
					{
						alert("Ne postoji ulogovan korisnik!");
					}
					else if(data.length == 0)
					{
						alert("Nema prijatelja!");
					}
					else
					{
						$('#friendsTable').append('<thead><tr><th>Firstname</th><th>Lastname</th></tr></thead><tbody>');
						$.each(data,function(index,value)
								{
									var data = value.split("-");
									var name = data[0];
									var lastname = data[1];
									var frID = data[2];
									var type = data[3];
									var relID = data[4];
									
									$('#friendsTable').append("<tr><td>"+name+"</td><td>"+lastname+"</td>");
									if(type == "FRIENDS")
									{
										$('#friendsTable').append('<td><button type="button" class="btn btn-primary" id="'+relID+'" onclick="decline(this)">Remove</button></td></tr>');
									}
									else
									{
										$('#friendsTable').append('<td><button type="button" class="btn btn-primary" id="'+relID+'" onclick="decline(this)">Cancel Request</button></td></tr>');
									}
									
								});	
						
						$('#friendsTable').append('</tbody>');
					}
					
				}			
				
			});
	
	$("#Friends").show();
	$("#profile").hide();
	$("#Requests").hide();
	$("#Search").hide();

}


function requests()
{
	$('#requestsTable').empty();
	$.ajax(
			{
				method:'GET',
				url: "/api/korisnici/requests",
				success : function(data)
				{
					var korisnik = data;
					if(data == null)
					{
						alert("Ne postoji ulogovan korisnik!");
					}
					else if(data.length == 0)
					{
						alert("Nema zahteva!");
					}
					else
					{
						$('#requestsTable').append('<thead><tr><th>Firstname</th><th>Lastname</th></tr></thead><tbody>');
						$.each(data,function(index,value)
								{
									var data1 = value.split("-");
									var name = data1[0];
									var lastname = data1[1];
									var frID = data1[2];
									var type = data1[3];
									var relID = data1[4];
									
									$('#requestsTable').append("<tr><td>"+name+"</td><td>"+lastname+"</td><td>");								
									$('#requestsTable').append('<button type="button" class="btn btn-primary" id="'+relID+'" onclick="accept(this)">Accept</button></td><td><button type="button" class="btn btn-danger" id="'+relID+'" onclick="decline(this)">Decline</button></td>');
									$('#requestsTable').append('</tr>');
																	
								});	
						
						$('#requestsTable').append('</tbody>');
					}				
				}			
				
			});
	
	$("#Requests").show();
	$("#profile").hide();
	$("#Friends").hide();
	$("#Search").hide();	
	

}


function accept(pom)
{
	var id = pom.id;
	
	$.ajax(
			{
				method : 'POST',
				url: "/api/korisnici/accept/"+id,
				success : function(data)
						{
							if(data == "neuspesno")
							{
								alert("Neuspesno!");
							}
							else
							{
								alert("Dodavanje prijatelja uspesno!");
								window.location="profileUser.html";
							}
					
						}
				
			});
	
}

function decline(pom)
{
	var id = pom.id;
	
	$.ajax(
			{
				method : 'POST',
				url: "/api/korisnici/remove/"+id,
				success : function(data)
						{
							if(data == "neuspesno")
							{
								alert("Neuspesno!");
							}
							else
							{
								window.location="profileUser.html";
							}
					
						}
				
			});
}


function add(pom)
{
	var id = pom.id;
	$.ajax(
			{
				method : 'POST',
				url: "/api/korisnici/add/"+id,
				success : function(data)
						{
							if(data == "neuspesno")
							{
								alert("Neuspesno!");
							}
							else
							{
								alert("Dodavanje prijatelja uspesno!");
								search();
							}
					
						}
				
			});
	
	
}


function searchTable()
{
	$('#searchTable').empty();
	$("#Requests").hide();
	$("#profile").hide();
	$("#Friends").hide();
	$("#Search").show();	
	

}

function search()
{
	$('#searchTable').empty();
	var ime = $('#nameSearch').val();
	var prezime = $('#lastNameSearch').val();
	
	if(ime == null || ime == "")
	{
		ime = "nothing";
	}
	if(prezime == null || prezime == "")
	{
		prezime = "nothing";
	}
				
				$.ajax(
						{
							method:'GET',
							url: "/api/korisnici/search/"+ime+"-"+prezime,
							success : function(data)
							{
								
								if(data == null)
								{
									alert("Ne postoji ulogovan korisnik!");
								}
								else if(data.length == 0)
								{
									alert("Ne postoje rezutati pretrage");
								}
								else
								{
									$('#searchTable').append('<thead><tr><th>Firstname</th><th>Lastname</th></tr></thead><tbody>');
									$.each(data,function(index,value)
											{
												var korisnik = value;
												
												
												$('#searchTable').append("<tr><td>"+korisnik.ime+"</td><td>"+korisnik.prezime+"</td><td>"+'<button type="button" class="btn btn-primary" id="'+korisnik.id+'" onclick="add(this)">Add friend</button>');								
												$('#searchTable').append('</td></tr>');
																				
											});	
									
									$('#searchTable').append('</tbody>');
								}				
							}			
							
						});
				
			
}

$(document).on('submit','.user',function(e){
	e.preventDefault();	
	
	var ime = $('#name').val();
	var prezime= $('#lastname').val();
	var mail =  $('#email').val();
	var telefon = $('#phone').val();
	var grad = $('#town').val();
		
			
		var newuser={
						ime : $('#name').val(),
						prezime : $('#lastname').val(),
						mail : $('#email').val(),
						telefon : $('#phone').val(),
						grad :  $('#town').val()			
		}
		
		senduser= JSON.stringify(newuser);			
		console.log('user je ' + senduser);
		 
		$.ajax({
			type : 'POST',
			url : "/api/korisnici/changeInfo",
			contentType : "application/json",
			data: senduser,
			success : function(pov) {
				if( pov == "Mejl nije jedinstven"){	
					 alert("Mail is already in use.");
				}else{
					alert("Profil izmenjen uspesno!");
					window.location="profileUser.html";
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
});










