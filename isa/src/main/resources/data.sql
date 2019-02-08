insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip,admin_potvrdio, bodovi) values (4564,'Marko', 'Markovic','marko@gmail.com', 063215258, 'Novi Sad', 'da', '074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','REGISTROVAN',false, 6);
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip,admin_potvrdio, bodovi) values (42164,'Marina', 'Maric','marina@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','REGISTROVAN',false, 5);
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip,admin_potvrdio, bodovi) values (192,'AdminAVION', 'Avionic','avio@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','REGISTROVAN',false, 0);
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip,admin_potvrdio, bodovi) values (193,'AdminHOTEL', 'Hotelic','hotel@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','REGISTROVAN',false, 0);
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip,admin_potvrdio, bodovi) values (194,'AdminRENT', 'Rentic','rent@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','REGISTROVAN',false, 0);
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip,admin_potvrdio, bodovi) values (195,'AdminSISTEM', 'Adminic','admin@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','ADMIN_SISTEM',false,0);

insert into kompanije ( id, naziv, adresa, opis, ocena, brojac ) values (575,'Fly', 'Dunavska','Punctual',0,0);
insert into kompanije ( id, naziv, adresa, opis, ocena, brojac) values (757,'BrrClouds', 'Matice Srpska','Fast',0,0);

insert into hotel ( id, naziv, adresa,grad, opis, ocena, brojac,Version) values (1245,'Marinas', 'Temerinska 10','Novi Sad','divno', 4, 0,0);
insert into hotel ( id, naziv, adresa,grad, opis, ocena, brojac,Version) values (1115,'Anjica', 'Fruskogorska 18','Novi Sad','divno',0, 0,0);
insert into cenovnik( id, datum_primene,aktivan) values (125,'2018-11-11',false);
insert into cenovnik( id, datum_primene,aktivan) values (128,'2017-11-11',false);
insert into rentacar ( id, naziv, adresa, grad, opis, ocena, brojac) values (1245,'Fast car','Kisacka 10', 'Novi Sad','Podedujemo veliki asortiman auta.',0, 0);
insert into rentacar ( id, naziv, adresa, grad, opis, ocena, brojac) values (1255,'Rent a car', 'Kosovska 18','Novi Sad','Nasa vozila su sigurna i bezbedna.',0, 0);
insert into rentacar ( id, naziv, adresa, grad, opis, ocena, brojac) values (1265,'NS the best', 'Resavska 10', 'Beograd','Nudimo veliki asortiman auta.',0, 0);


insert into filijale (id, grad, ulica, servis_id) values(133,'Novi Sad','Gunduliceva 18',1245);
insert into filijale (id, grad, ulica, servis_id) values(134,'Novi Sad','Temerinska 22',1245);
insert into filijale (id, grad, ulica, servis_id) values(138,'Beograd','Bele vode 5',1265);
insert into filijale (id, grad, ulica, servis_id) values(139,'Novi Sad','Temerinska 10',1255);

insert into vozila(id,broj, brojac, cena, godiste, imapopusta, kategorija, marka, model, ocena, sedista , filijala_id,version) values (148, 0, 0,0,2016,false,'D','Passat','B6',0,5,133,0);
insert into vozila(id,broj, brojac, cena, godiste, imapopusta, kategorija, marka, model, ocena, sedista , filijala_id,version) values (146, 0, 0,0,2015,false,'E','Audi','A5',0,5,133,0);

insert into room (id, balkon,broj_rezervacija,brojac,cijena,imapopusta,kapacitet,ocena,sprat,tip, hotel_id,version) values (150,'da',0,0,3000,false,4,0,2,'Apartman',1245,0);
insert into room (id, balkon,broj_rezervacija,brojac,cijena,imapopusta,kapacitet,ocena,sprat,tip, hotel_id,version) values (137,'ne',0,0,2000,false,2,0,2,'Apartman',1245,0);



