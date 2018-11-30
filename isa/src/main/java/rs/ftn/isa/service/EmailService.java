package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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
	public void sendNotificaitionAsync(User user) throws MailException, InterruptedException {

		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija clanstva");
		mail.setText("Pozdrav " + user.getIme() + ",\n\nhvala što koristite AirFlights.");
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}


}
