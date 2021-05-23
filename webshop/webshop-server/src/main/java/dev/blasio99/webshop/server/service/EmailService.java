package dev.blasio99.webshop.server.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.enums.Environment;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.observer.Observer;

@Service
public class EmailService implements Observer{
	
	private Environment environment = new Environment();

	@Override
	public void update(Product product, String email){
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(environment.getHost());
		mailSenderImpl.setPort(environment.getPort());
		mailSenderImpl.setUsername(environment.getUsername());
		mailSenderImpl.setPassword(environment.getPassword());

		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol", "smtp");

		mailSenderImpl.setJavaMailProperties(properties);

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(environment.getUsername());
		message.setTo(email);
		message.setSubject("[NOREPLY] New product in the store!");
		message.setText(mailBodyFormatter(product));

		System.out.println(message);
		mailSenderImpl.send(message);
	}

	private String mailBodyFormatter(Product product){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(product.toString() + '\n');

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);

		stringBuilder.append("Message sent at: " + formatDateTime + '\n');
		stringBuilder.append("\nThis message was sent automatically.\n\nWe hope you the best,\nHockeyMag Webshop Company.\n");
	
		return stringBuilder.toString();
	}
}


