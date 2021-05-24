package dev.blasio99.webshop;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import dev.blasio99.webshop.server.service.SecureTokenService;

@Profile("test")
@Configuration
public class SecureTokenServiceTestConfiguration {
   @Bean
   @Primary
   public SecureTokenService productService() {
      return Mockito.mock(SecureTokenService.class);
   }
}

