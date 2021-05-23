package dev.blasio99.webshop.server.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.enums.Environment;
import dev.blasio99.webshop.server.exception.OrderException;
import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.observer.Observer;

@Service
public class EmailService implements Observer{
	
	private Environment environment = new Environment();

	@Autowired
	private OrderLineService orderLineService;

	@Override
	public void update(OrderLine orderLine, String email){
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
		message.setSubject("[NOREPLY] Receipt for order!");
		message.setText(mailBodyFormatter(orderLine));

		System.out.println(message);
		mailSenderImpl.send(message);
	}

	private String mailBodyFormatter(OrderLine orderLine){
		
		ArrayList<Product> products = orderLineService.getProductsFromOrderLine(orderLine);
		
		StringBuilder stringBuilder = new StringBuilder();
		Integer counterForMail = 0;

		stringBuilder.append("Dear Customer!\n\n");
		stringBuilder.append("Thank you for choosing HockeyMag. Your order is being listed below:\n\n");

		if(products.isEmpty()) throw new OrderException();
		
		for(Product product : products)
			stringBuilder.append(product.toString() + "\n\n" + (++counterForMail) + ". product on the line:\n");
		
		stringBuilder.append("__________________________________\n");
		stringBuilder.append("\n");
		stringBuilder.append("Total price: \t" + orderLineService.getTotalPrice(products) + " RON\n");
		stringBuilder.append("__________________________________\n\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);

		stringBuilder.append("Message sent at: " + formatDateTime + '\n');
		stringBuilder.append("\nThis message was sent automatically.\n\nWe hope you the best,\nHockeyMag Webshop Company.\n");
	
		return stringBuilder.toString();
	}
}


