package innovatech.customer.v3.full.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import innovatech.customer.v3.full.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    List<Customer> findByApellido(String apellido);

    Customer findByCorreo(String correo);

    List<Customer> findByNombreAndApellido(String nombre, String apellido);

}
