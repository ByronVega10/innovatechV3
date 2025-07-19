package innovatech.product.v3.full.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import innovatech.product.v3.full.model.Product;
import innovatech.product.v3.full.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Integer id){
        return productRepository.findById(id).get();
    }

    public Product save(Product producto){
        return productRepository.save(producto);
    }

    public void deleteById(Integer id){
        productRepository.deleteById(id);
    }
}
