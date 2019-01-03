insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka) values (4564,'Marko', 'Marković','marko@gmail.com', 063215258, 'Novi Sad', 'da', '074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81');
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka) values (42164,'Marina', 'Marić','marina@gmail.com', 0645691,'Beograd', 'ne','mare');
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip) values (192,'AdminAVION', 'Marić','avio@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','ADMIN_AVIO');
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip) values (193,'AdminHOTEL', 'Marić','hotel@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','ADMIN_HOTEL');
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip) values (194,'AdminRENT', 'Marić','rent@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','ADMIN_RENT');
insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka,tip) values (195,'AdminSISTEM', 'Admin','admin@gmail.com', 0645691,'Beograd', 'da','074af2bf8e84854902eead553819b4517c2bac61c5d367a45831b05bf7789a81','ADMIN_SISTEM');

insert into kompanije ( id, naziv, adresa, opis) values (575,'Fly', 'Dunavska','Punctual');
insert into kompanije ( id, naziv, adresa, opis) values (757,'BrrClouds', 'Matice Srpska','Fast');

insert into hotel ( id, naziv, adresa, opis, ocena) values (1245,'Marinas', 'Temerinska 10','divno', 10);
insert into hotel ( id, naziv, adresa, opis, ocena) values (1115,'Anjica', 'Fruskogorska 18','divno', 3);
insert into cenovnik( id, datum_primene,aktivan) values (125,'2018-11-11',false);
insert into cenovnik( id, datum_primene,aktivan) values (128,'2017-11-11',false);
insert into rentacar ( id, naziv, adresa, opis, ocena) values (1245,'Fast car', 'Kisacka 10','samo brzo',0);
insert into rentacar ( id, naziv, adresa, opis, ocena) values (1255,'Rent a car', 'Kosovska 18','sigurno i lako',0);
insert into rentacar ( id, naziv, adresa, opis, ocena) values (1265,'NS the best', 'Dunavska 22','samo brzo',0);
