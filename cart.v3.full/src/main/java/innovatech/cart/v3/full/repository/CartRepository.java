package innovatech.cart.v3.full.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import innovatech.cart.v3.full.model.Cart;
import java.util.Optional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    Optional<Cart> findByCustomerId(Integer customerId);

    List<Cart>findAllByCustomerId(Integer customerId);
}
