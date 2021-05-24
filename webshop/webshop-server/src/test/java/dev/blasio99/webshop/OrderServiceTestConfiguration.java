package dev.blasio99.webshop;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import dev.blasio99.webshop.server.service.OrderService;

@Profile("test")
@Configuration
public class OrderServiceTestConfiguration {
   @Bean
   @Primary
   public OrderService orderService() {
      return Mockito.mock(OrderService.class);
   }
}

