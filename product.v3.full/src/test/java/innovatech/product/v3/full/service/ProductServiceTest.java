package innovatech.product.v3.full.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import innovatech.product.v3.full.model.Product;
import innovatech.product.v3.full.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testFindAll(){
        List<Product> mockList = List.of(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(mockList);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void testFindByIdFound(){
        Product product = new Product();
        product.setId(1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productRepository).findById(1L);
    }

    @Test
    void testSave(){
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.save(product);

        assertEquals(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void testDeletebyId(){
        long id = 49;

        productService.deleteById(id);

        verify(productRepository).deleteById(id);
    }
}
