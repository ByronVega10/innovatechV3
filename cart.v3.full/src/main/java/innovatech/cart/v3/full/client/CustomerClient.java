package innovatech.cart.v3.full.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import innovatech.cart.v3.full.dto.CustomerDTO;

@FeignClient(name = "customer-service", url = "http://localhost:8081")
public interface CustomerClient {
    @GetMapping("customer/{id}/exists")
    CustomerDTO getCustomerById(@PathVariable Integer id);
}
