package snippet;

public class Snippet {
	insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka) values (4564,'Marko', 'Marković','marko@gmail.com', 063215258, 'Novi Sad', 'da', 'mare');
	insert into user ( id, ime, prezime, mail, telefon, grad, verifikovan, lozinka) values (42164,'Marina', 'Marić','marina@gmail.com', 0645691,'Beograd', 'ne','mare');
	insert into hotel ( id, naziv, adresa, opis, ocena) values (1245,'Marinas', 'Ljubinje','divno', 10);
	insert into hotel ( id, naziv, adresa, opis, ocena) values (1115,'Anjica', 'Novi Sad','divno', 3);
	insert into usluge( id, naziv, cena) values (123,'Pranje kola', '2000');
	insert into usluge( id, naziv, cena) values (124,'Privatni vozac', '10000');
	insert into cenovnik( id, datum_primene) values (125,'2018-11-11');
	insert into cenovnik( id, datum_primene) values (128,'2017-11-11');
	insert into rentacar ( id, naziv, adresa, opis) values (1245,'Caaar', 'Novi Sad','samo brzo');
	insert into rentacar ( id, naziv, adresa, opis) values (1255,'Rent a caar', 'Novi Sad','sigurno i lako');
	insert into rentacar ( id, naziv, adresa, opis) values (1265,'Caaar the best', 'Novi Sad','samo brzo');
	insert into cenovnik( id, datum_primene) values (725,'2018-11-11');
	insert into cenovnik( id, datum_primene) values (728,'2017-11-11');
}

