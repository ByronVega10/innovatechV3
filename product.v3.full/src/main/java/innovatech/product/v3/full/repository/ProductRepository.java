package innovatech.product.v3.full.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import innovatech.product.v3.full.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{


    Product findByNombre(String nombre);

    List<Product> findByNombreAndDescripcion(String nombre, String descripcion);

}
