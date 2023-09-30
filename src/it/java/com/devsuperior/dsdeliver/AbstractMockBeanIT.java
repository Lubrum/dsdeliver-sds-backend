package com.devsuperior.dsdeliver;

import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;
import com.devsuperior.dsdeliver.services.OrderService;
import com.devsuperior.dsdeliver.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = DatabaseContainerSetupIT.Initializer.class)
public abstract class AbstractMockBeanIT
{

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected MockMvc mockApiRest;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected ProductService productService;
}
