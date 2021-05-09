package dev.blasio99.webshop.server.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.blasio99.webshop.common.dto.OrderDTO;
import dev.blasio99.webshop.server.api.assembler.OrderAssembler;
import dev.blasio99.webshop.server.model.Orders;
import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.service.OrderLineService;
import dev.blasio99.webshop.server.service.OrderService;

@CrossOrigin("*")
@RestController
public class OrderResource {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderLineService orderLineService;

	@Autowired
	private OrderAssembler orderAssembler;
	
	@GetMapping("admin/api/order/show")
	public List<Orders> showOrder(){
		return orderService.getOrders();
	}
	
	/*@PostMapping("api/order/add")
	public Order addOrder(@RequestBody OrderDTO dto){
		return orderService.addOrder(orderAssembler.createModel(dto));
	}*/

	@PostMapping("api/orderline/add/order")
	public OrderLine addOrder(@RequestBody OrderDTO dto){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return orderLineService.addOrderToOrderLine(orderAssembler.createModel(dto), authentication.getName());
	}
}
