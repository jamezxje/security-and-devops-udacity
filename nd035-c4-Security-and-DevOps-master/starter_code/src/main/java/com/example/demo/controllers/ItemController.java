package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getItemsByName(@PathVariable String name) {
        List<Item> items = itemRepository.findByName(name);

        return items.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found")
                : ResponseEntity.ok(items);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }

        return ResponseEntity.ok(item);
    }
}
