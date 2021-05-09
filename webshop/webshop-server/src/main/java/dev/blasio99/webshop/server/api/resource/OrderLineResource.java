package dev.blasio99.webshop.server.api.resource;

import java.util.ArrayList;

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
import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.service.OrderLineService;

@CrossOrigin("*")
@RestController
public class OrderLineResource {

	@Autowired
	private OrderLineService orderLineService;

	@Autowired
	private OrderAssembler orderAssembler;
	
	@GetMapping("admin/api/orderline/show")
	public ArrayList<OrderLine> showOrderline(){
		return orderLineService.getOrderLines();
	}
	
	@PostMapping("api/orderline/add")
	public OrderLine addOrderLine(@RequestBody OrderDTO dto){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return orderLineService.addOrderLine(orderAssembler.createModel(dto), authentication.getName());
	}
}