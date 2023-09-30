package com.devsuperior.dsdeliver.controllers;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        final var list = service.findOrdersPendingWithProducts();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO dto) {
        OrderDTO dtoSaved = service.insert(dto);
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoSaved);
    }

    @PutMapping("/{id}/delivered")
    public ResponseEntity<OrderDTO> setDelivered(@PathVariable Long id) {
        final var dto = service.setDelivered(id);
        return ResponseEntity.ok().body(dto);
    }

}
