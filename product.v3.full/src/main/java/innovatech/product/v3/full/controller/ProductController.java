package innovatech.product.v3.full.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import innovatech.product.v3.full.model.Product;
import innovatech.product.v3.full.service.ProductService;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listarProducto(){
        List<Product> productos = productService.findAll();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos); 
    }

    @PostMapping
    public ResponseEntity<Product> guardarProducto(@RequestBody Product producto){
        Product productoNuevo = productService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> buscarProducto(@PathVariable Integer id){
        try{
            Product product = productService.findbyId(id);
            return ResponseEntity.ok(product);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizarProducto(@PathVariable Integer id, @RequestBody Product product){
        try {
            Product prod = productService.findbyId(id);
            prod.setId(product.getId());
            prod.setNombre(product.getNombre());
            prod.setDescripcion(product.getDescripcion());
            prod.setPrecio(product.getPrecio());
            prod.setStock(product.getStock());

            productService.save(prod);
            return ResponseEntity.ok(product);
            
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        try {
            productService.delet(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
