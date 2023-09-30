package com.devsuperior.dsdeliver.services;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findOrdersPendingWithProducts() {
        final var list = repository.findOrdersWithProducts();
        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO insert(final OrderDTO dto) {
        var order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(), OrderStatus.PENDING);
        if (dto.getProducts().isEmpty()) {
            throw new IllegalStateException("Obrigatório pelo menos um produto no pedido.");
        }
        for (final var p : dto.getProducts()) {
            final var product = productRepository.getReferenceById(p.getId());
            order.getProducts().add(product);
        }
        order = repository.save(order);
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO setDelivered(final Long id) {
        var order = repository.getReferenceById(id); //do not touch database, JPA monitors this order
        order.setStatus(OrderStatus.DELIVERED);
        order = repository.save(order);
        return new OrderDTO(order);
    }

}