package com.devsuperior.dsdeliver.controller;

import com.devsuperior.dsdeliver.AbstractMockBeanIT;
import com.devsuperior.dsdeliver.DatabaseContainerSetupIT;
import com.devsuperior.dsdeliver.dto.ProductDTO;
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

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = DatabaseContainerSetupIT.Initializer.class)
@Rollback
@Transactional
public class ProductControllerIT extends AbstractMockBeanIT {

    @Test
    void givenInitialDatabase_whenFindAll_thenReturn() throws Exception {
        final MvcResult mvcGetResult = this.mockApiRest.perform(
                get("/products")
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final String response = mvcGetResult.getResponse().getContentAsString(UTF_8);
        final List<ProductDTO> orders = mapper.readValue(response, new TypeReference<>() {});
        assertEquals(8, orders.size());
    }

    @Test
    void givenInitialDatabasePlusOneProduct_whenFindAll_thenReturn() throws Exception {
        final Product product = new Product("teste", 10.0, "teste", "teste");
        this.productRepository.save(product);

        final MvcResult mvcGetResult = this.mockApiRest.perform(
                get("/products")
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final String response = mvcGetResult.getResponse().getContentAsString(UTF_8);
        final List<ProductDTO> orders = mapper.readValue(response, new TypeReference<>() {});
        assertEquals(9, orders.size());
    }
}
