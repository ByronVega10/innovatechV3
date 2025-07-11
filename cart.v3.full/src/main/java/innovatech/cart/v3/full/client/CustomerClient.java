package innovatech.cart.v3.full.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes-microservice", url = "http://localhost:8081")
public interface CustomerClient {
    @GetMapping("customer/{id}/exists")
    Boolean existsById(@PathVariable("id")Integer customerId);
}
