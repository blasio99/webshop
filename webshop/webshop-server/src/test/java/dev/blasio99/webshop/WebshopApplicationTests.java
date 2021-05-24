package dev.blasio99.webshop;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dev.blasio99.webshop.server.WebshopApplication;
import dev.blasio99.webshop.server.enums.Category;
import dev.blasio99.webshop.server.enums.Size;
import dev.blasio99.webshop.server.enums.Status;
import dev.blasio99.webshop.server.exception.IncorrectInputValueException;
import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.model.Orders;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.model.SecureToken;
import dev.blasio99.webshop.server.model.User;
import dev.blasio99.webshop.server.service.OrderLineService;
import dev.blasio99.webshop.server.service.ProductService;
import dev.blasio99.webshop.server.service.SecureTokenService;
import dev.blasio99.webshop.server.service.UserService;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-dev.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = WebshopApplication.class)
class WebshopApplicationTests {

	@Autowired
    private UserService userService;

	@Autowired
    private OrderLineService orderLineService;

	@Autowired
    private ProductService productService;

	@Autowired
    private SecureTokenService secureTokenService;
	
	// TEST OF TEST
	@Test
    public void testAdd() {
        assert(42 == Integer.sum(19, 23));
    }

	// User TESTS

	@Test
    public void shouldReturnEmptyListOfTokens() throws Exception {
        List<SecureToken> tokens = secureTokenService.findAllTokens();

        assert(tokens.isEmpty());
    }
	
	@Test
	public void whenValidEmail_thenUserShouldBeFound() {
		User fromDb = userService.getUserByEmail("benedekbalazs1999@gmail.com");
		assert(fromDb.getUsername().equals("blasio99"));
	}

	@Test
	public void whenValidUsername_thenUserShouldBeFound() {
		User fromDb = userService.getUserByUsername("blasio99");
		assert(fromDb.getEmail().equals("benedekbalazs1999@gmail.com"));
	}

	@Test 
	public void whenUserIsProvided_thenDeleteUser(){
		User fromDb = userService.getUserByUsername("alex_badea99");
		Integer sizeBefore = userService.getClients().size();

		userService.deleteUser(fromDb.getUsername());

		Integer sizeAfter = userService.getClients().size();

		assert(sizeBefore - 1 == sizeAfter);
	}


	// Product TESTS

	@Test
	public void whenExistingProductIsProvided_thenProductsNumberRemainsTheSame(){
		Product product = productService.getProductByName("CCM 300 Pro").get(0);
		
		Integer sizeBefore = productService.findAllProducts().size();

		product.setQuantity(product.getQuantity() + 3);
		productService.addProduct(product);

		Integer sizeAfter = productService.findAllProducts().size();
		assert(sizeAfter == sizeBefore);
	
	}

	@Test
	public void whenNewProductIsProvided_thenProductsNumberIsIncremented(){
		Product product = new Product();
		product.setCategory(Category.CLOTHES);
		product.setDescription("Clothes for testing");
		product.setName("Test clothes");
		product.setPrice(0.0);
		product.setQuantity(100);
		product.setSize(Size.M);
		
		Integer sizeBefore = productService.findAllProducts().size();

		productService.addProduct(product);

		Integer sizeAfter = productService.findAllProducts().size();
		assert(sizeAfter == sizeBefore + 1);
	
	}

	// Ordering TESTS

	public Orders createOrder (Long productId, Integer quantity, String username){
		Orders order = new Orders();

		order.setProductId(productId);
		order.setQuantity(quantity);
		order.setUsername(username);

		return order;
	}

	@Test
	public void whenExistingOrderIsProvidedToOrderLine_thenOrderIdListSizeIsIncremented(){
		Orders order = new Orders();
		Integer orderListSizeBefore = 0;
		order.setProductId(1L);
		order.setQuantity(20);
		order.setUsername("benedek.balazs");

		OrderLine orderLine = orderLineService.getOrderLineByUsername(order.getUsername());
		if(orderLine != null)
			orderListSizeBefore = orderLine.getOrderIdList().size();

		orderLineService.addOrderToOrderLine(order, order.getUsername());
		
		Integer orderListSizeAfter = orderLineService.getOrderLineByUsername(order.getUsername()).getOrderIdList().size();
		
		assert(orderListSizeBefore == orderListSizeAfter);
	}

	@Test 
	public void whenNewOrderIsProvidedToOrderLine_thenUpdateOrderInOrderListAndDoNotCreateAnother(){
		Orders order = new Orders();
		Integer orderListSizeBefore = 0;
		order.setProductId(1L);
		order.setQuantity(10);
		order.setUsername("benedek.balazs");

		OrderLine orderLine = orderLineService.getOrderLineByUsername(order.getUsername());
		if(orderLine != null)
			orderListSizeBefore = orderLine.getOrderIdList().size();

		orderLineService.addOrderToOrderLine(order, order.getUsername());
		
		Integer orderListSizeAfter = orderLineService.getOrderLineByUsername(order.getUsername()).getOrderIdList().size();
		
		assert(orderListSizeBefore + 1 == orderListSizeAfter);

	}

	@Test
	public void whenFinishingOrderLine_thenFinishOrderLine() throws IncorrectInputValueException{
		OrderLine orderLine = new OrderLine();
		String username = "blasio99";
		String email = "benedekbalazs1999@gmail.com";
		Orders order1 = createOrder(1L, 20, username);
		Orders order2 = createOrder(3L, 10, username);
		
		orderLine = orderLineService.addOrderToOrderLine(order1, username);
		orderLine = orderLineService.addOrderToOrderLine(order2, username);

		orderLine.setOrderDate(LocalDateTime.now());
		orderLine.setUsername(username);

		Status statusBefore = orderLine.getStatus();

		orderLineService.finishOrder("PAY_ON_DELIVERY", username, email);

		Status statusAfter = orderLine.getStatus();

		assert(statusBefore == null && statusAfter == Status.NEW);

	}

}
