package com.example.demo.controllers;

import com.example.demo.util.StringUtil;
import com.example.demo.util.TokenUtil;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	TokenUtil tokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit")
	public ResponseEntity<Object> submit(@RequestHeader("Authorization") String token) {
		User user = userRepository.findByUsername(tokenUtil.getUsername(token));

		if(user == null) {
			logger.info("User not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);

		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history")
	public ResponseEntity<Object> getOrdersForUser(@RequestHeader("Authorization") String token) {
		User user = userRepository.findByUsername(tokenUtil.getUsername(token));

		if(user == null) {
			logger.info("User not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
