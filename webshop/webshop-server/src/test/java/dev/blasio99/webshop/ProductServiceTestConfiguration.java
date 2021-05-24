package dev.blasio99.webshop;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import dev.blasio99.webshop.server.repo.ProductRepository;
import dev.blasio99.webshop.server.service.ProductService;

@Profile("test")
@Configuration
public class ProductServiceTestConfiguration {
   @Bean
   @Primary
   public ProductService productService() {
      return Mockito.mock(ProductService.class);
   }

   @Bean
   @Primary
   public ProductRepository productRepository() {
      return Mockito.mock(ProductRepository.class);
   }
}

