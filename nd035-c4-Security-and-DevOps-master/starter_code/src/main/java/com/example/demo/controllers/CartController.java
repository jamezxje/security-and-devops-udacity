package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.util.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/addToCart")
    public ResponseEntity<Object> addToCart(@RequestBody ModifyCartRequest request, @RequestHeader("Authorization") String token) {
        String username = tokenUtil.getUsername(token);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {
            logger.info("Can not found Item with id: " + request.getItemId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        Cart cart = user.getCart();

        for (int i = 0; i < request.getQuantity(); i++) {
            cart.addItem(item.get());
        }

        cartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Object> removeFromCart(@RequestBody ModifyCartRequest request, @RequestHeader("Authorization") String token) {
        String username = tokenUtil.getUsername(token);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<Item> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {
            logger.info("Can not found Item with id: " + request.getItemId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        Cart cart = user.getCart();

        for (int i = 0; i < request.getQuantity(); i++) {
            cart.removeItem(item.get());
        }

        cartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }

}
