# ISA-AirFlight
Da biste pokrenuli testove neophodno je u bazu uneti naredne inserte
insert into filijale (id, grad, ulica, servis_id) values(133,'Novi Sad','Gunduliceva 18',1245);
insert into filijale (id, grad, ulica, servis_id) values(134,'Novi Sad','Temerinska 22',1245);
insert into filijale (id, grad, ulica, servis_id) values(138,'Beograd','Bele vode 5',1265);
insert into filijale (id, grad, ulica, servis_id) values(139,'Novi Sad','Temerinska 10',1255);

insert into vozila(id,broj, brojac, cena, godiste, imapopusta, kategorija, marka, model, ocena, sedista , filijala_id,version) values (148, 0, 0,0,2016,false,'D','Passat','B6',0,5,133,0);
insert into vozila(id,broj, brojac, cena, godiste, imapopusta, kategorija, marka, model, ocena, sedista , filijala_id,version) values (146, 0, 0,0,2015,false,'E','Audi','A5',0,5,133,0);

insert into room (id, balkon,broj_rezervacija,brojac,cijena,imapopusta,kapacitet,ocena,sprat,tip, hotel_id,version,kreveti,rezervisana) values (150,'da',0,0,3000,false,4,0,2,'Apartman',1245,0,4,false);
insert into room (id, balkon,broj_rezervacija,brojac,cijena,imapopusta,kapacitet,ocena,sprat,tip, hotel_id,version,kreveti,rezervisana) values (137,'ne',0,0,2000,false,2,0,2,'Apartman',1245,0,2,false);
