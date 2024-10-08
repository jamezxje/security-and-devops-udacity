package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/item")
public class ItemController {

  @Autowired
  private ItemRepository itemRepository;

  @GetMapping
  public ResponseEntity<List<Item>> getItems() {
    return ResponseEntity.ok(this.itemRepository.findAll());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    return ResponseEntity.of(this.itemRepository.findById(id));
  }

  @GetMapping(path = "/name/{name}")
  public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
    List<Item> items = this.itemRepository.findByName(name);

    return items == null || items.isEmpty() ? ResponseEntity.notFound().build()
        : ResponseEntity.ok(items);
  }

}
