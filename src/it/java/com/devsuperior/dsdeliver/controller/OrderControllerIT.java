package com.devsuperior.dsdeliver.controller;

import com.devsuperior.dsdeliver.AbstractMockBeanIT;
import com.devsuperior.dsdeliver.DatabaseContainerSetupIT;
import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.entities.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = DatabaseContainerSetupIT.Initializer.class)
@Rollback
@Transactional
public class OrderControllerIT extends AbstractMockBeanIT {

    @Test
    void givenInitialDatabase_whenFindAllPendingOrders_thenReturn() throws Exception {
        final MvcResult mvcGetResult = this.mockApiRest.perform(
                get("/orders")
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final String response = mvcGetResult.getResponse().getContentAsString(UTF_8);
        final List<OrderDTO> orders = mapper.readValue(response, new TypeReference<>() {});
        assertEquals(5, orders.size());
    }

    @Test
    void givenOneOrderWithoutProduct_whenInsert_thenDoNotInsert() throws Exception {
        final OrderDTO expectedOrder = new OrderDTO(null, "abc", 10.0, 10.0, null, OrderStatus.PENDING, 0.0);

        final String requestBody = mapper.writeValueAsString(expectedOrder);

        final MvcResult mvcPostResult = this.mockApiRest.perform(
                post("/orders")
                    .contentType(APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isUnprocessableEntity())
            .andReturn();

        final String postResponse = mvcPostResult.getResponse().getContentAsString(UTF_8);
        // validar mensagem de erro
        assertEquals(7, this.orderRepository.findAll().size());
    }

    @Test
    void givenOneOrderWithProductThatExists_whenInsert_thenOk() throws Exception {
        final OrderDTO orderDTO = new OrderDTO(null, "abc", 10.0, 10.0, Instant.now(), OrderStatus.PENDING, 49.9);
        final Product product = new Product();
        product.setId(1);
        orderDTO.getProducts().add(new ProductDTO(product));

        final String requestBody = mapper.writeValueAsString(orderDTO);

        final MvcResult mvcResponse = this.mockApiRest.perform(
                post("/orders")
                    .contentType(APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isCreated())
            .andReturn();

        final String response = mvcResponse.getResponse().getContentAsString(UTF_8);
        final OrderDTO order = mapper.readValue(response, OrderDTO.class);
        assertEquals(orderDTO.getTotal(), order.getTotal());
        assertEquals(orderDTO.getAddress(), order.getAddress());
        assertEquals(orderDTO.getProducts().size(), order.getProducts().size());
        assertEquals(orderDTO.getLatitude(), order.getLatitude());
        assertEquals(orderDTO.getLongitude(), order.getLongitude());
        assertEquals(orderDTO.getStatus(), order.getStatus());
        // cannot use equals, we do not know details about product to compare with equals
        // validar mensagem de erro
        assertEquals(8, this.orderRepository.findAll().size());
    }

    @Test
    void givenOneOrderWithProductThatDoNotExists_whenInsert_thenFail() throws Exception {
        final OrderDTO orderDTO = new OrderDTO(null, "abc", 10.0, 10.0, Instant.now(), OrderStatus.PENDING, 100.0);
        final Product product = new Product();
        product.setId(0);
        orderDTO.getProducts().add(new ProductDTO(product));

        final String requestBody = mapper.writeValueAsString(orderDTO);

        final MvcResult mvcResponse = this.mockApiRest.perform(
                post("/orders")
                    .contentType(APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isUnprocessableEntity())
            .andReturn();

        final String postResponse = mvcResponse.getResponse().getContentAsString(UTF_8);
        // validar mensagem de erro
        assertEquals(7, this.orderRepository.findAll().size());
    }

    @Test
    void givenOrder_whenSetDelivered_thenOk() throws Exception {
        final MvcResult mvcResponse = this.mockApiRest.perform(
                put("/orders/1/delivered")
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final String response = mvcResponse.getResponse().getContentAsString(UTF_8);
        final OrderDTO order = mapper.readValue(response, OrderDTO.class);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    void givenOrderThatDoNotExists_whenSetDelivered_thenFail() throws Exception {
        final MvcResult mvcResponse = this.mockApiRest.perform(
                put("/orders/0/delivered")
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andReturn();

        final String response = mvcResponse.getResponse().getContentAsString(UTF_8);
        // validar mensagem de erro
    }
}
