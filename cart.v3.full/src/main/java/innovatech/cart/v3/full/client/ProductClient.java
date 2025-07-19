package innovatech.cart.v3.full.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import innovatech.cart.v3.full.dto.ProductDTO;

@FeignClient(name = "product-service", url = "http://localhost:8082")
public interface ProductClient {
    
    @GetMapping("/product/{id}")
    ProductDTO getProductById(@PathVariable Integer id);
}
