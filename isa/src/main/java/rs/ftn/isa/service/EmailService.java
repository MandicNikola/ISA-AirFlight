package rs.ftn.isa.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Pozivnica;
import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.User;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */
	@Autowired
	private Environment env;

	/*
	 * Anotacija za oznacavanje asinhronog zadatka
	 * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
	 */
	@Async
	public void sendNotificaitionAsync(User user) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h3>Pozdrav "+user.getIme()+"</h3><br> <p>Da biste aktivirali profil posetite  <a href=\"http://localhost:8080/api/korisnici/aktiviraj/"+user.getMail()+"\">link</a></p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(user.getMail());
		helper.setSubject("Verifikacija clanstva");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	/*	
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija clanstva");
		mail.setText("Pozdrav " + user.getIme() + ",\n\nhvala što koristite AirFlights.");
		javaMailSender.send(mail);
*/
		System.out.println("Email poslat!");
	}
	/*
	 * metoda za slanje pozivnica za let koje su mi potrebne
	 */
	@Async
	public void sendInvitationAsync(User user, Pozivnica pozivnica) throws MessagingException
	{
		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h3>Pozdrav "+user.getIme()+"</h3><br> <p>Da dobili ste pozvnicu za let  <a href=\"http://localhost:8080/pozivnica.html?id="+pozivnica.getId()+"\">link</a></p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(user.getMail());
		helper.setSubject("Pozivnica za let");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		
	}
	
	
	@Async
	public void sendReservationInfoAsync(User user, ReservationTicket rezKarte, RezervacijaRentCar rezHotel, RezervacijaRentCar rezRent) throws MessagingException
	{
		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h3>Pozdrav "+user.getIme()+"</h3><br> <p>Uspesno ste rezervisali Kartu</p>";
		htmlMsg += "<p>Datum rezervacije: "+rezKarte.getDatumRezervacije()+"</p>";
		htmlMsg += "<p>Cena : "+rezKarte.getUkupno()+"</p>";
		
		if(rezHotel != null)
		{
			htmlMsg += "<h3>Pozdrav "+user.getIme()+"</h3><br> <p>Uspesno ste rezervisali Hotel</p>";
			htmlMsg += "<p>Datum rezervacije: "+rezHotel.getDatumPreuzimanja()+"</p>";
			htmlMsg += "<p>Cena : "+rezHotel.getCena()+"</p>";
		}
		
		if(rezRent != null)
		{
			
			htmlMsg += "<h3>Pozdrav "+user.getIme()+"</h3><br> <p>Uspesno ste rezervisali Hotel</p>";
			htmlMsg += "<p>Datum rezervacije: "+rezRent.getDatumPreuzimanja()+"</p>";
			htmlMsg += "<p>Cena : "+rezHotel.getCena()+"</p>";
			
		}
		
		
		
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(user.getMail());
		helper.setSubject("Pozivnica za let");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		
	}


}
