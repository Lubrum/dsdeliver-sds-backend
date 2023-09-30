package com.devsuperior.dsdeliver.services;

import com.devsuperior.dsdeliver.AbstractMockBeanIT;
import com.devsuperior.dsdeliver.DatabaseContainerSetupIT;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = DatabaseContainerSetupIT.Initializer.class)
@Rollback
@Transactional
public class ProductServiceIT extends AbstractMockBeanIT {

    @Test
    void givenInitialDatabase_whenFindAll_thenReturnAll() {
        final List<ProductDTO> productDTOList = this.productService.findAll();
        assertEquals(8, productDTOList.size());
    }

    @Test
    void givenSaveOneProduct_whenFindAll_thenReturnAll() {
        final Product product = new Product("teste", 10.0, "teste", "teste");
        this.productRepository.save(product);
        final List<ProductDTO> productDTOList = this.productService.findAll();
        assertEquals(9, productDTOList.size());
    }

    @Test
    void givenSaveOneProduct_whenFindAll_thenReturnAllWithNewProduct() {
        final Product product = new Product("teste", 10.0, "teste", "teste");
        this.productRepository.save(product);
        final List<ProductDTO> productDTOList = this.productService.findAll().stream().filter(p -> p.getName().equals("teste")).toList();
        assertEquals(1, productDTOList.size());
        final ProductDTO expectedProductDTO = new ProductDTO(product);
        assertEquals(expectedProductDTO, productDTOList.get(0));
    }

    @Test
    void givenSaveMultipleProducts_whenFindAll_thenReturnAll() {
        final Product product1 = new Product("teste", 10.0, "teste", "teste");
        final Product product2 = new Product("teste", 10.0, "teste", "teste");
        this.productRepository.save(product1);
        this.productRepository.save(product2);
        final List<ProductDTO> productDTOList = this.productService.findAll();
        assertEquals(10, productDTOList.size());
    }

    @Test
    void givenSaveMultipleProducts_whenFindAll_thenReturnAllWithTwoNewProduct() {
        final Product product1 = new Product("teste", 10.0, "teste", "teste");
        final Product product2 = new Product("teste", 10.0, "teste", "teste");
        this.productRepository.save(product1);
        this.productRepository.save(product2);
        final List<ProductDTO> productDTOList = this.productService.findAll().stream().filter(p -> p.getName().equals("teste")).toList();
        assertEquals(2, productDTOList.size());
        final ProductDTO expectedProductDTO1 = new ProductDTO(product1);
        final ProductDTO expectedProductDTO2 = new ProductDTO(product2);
        assertEquals(expectedProductDTO1, productDTOList.get(0));
        assertEquals(expectedProductDTO2, productDTOList.get(1));
    }
}
