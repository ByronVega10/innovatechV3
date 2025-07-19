package innovatech.product.v3.full.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import innovatech.product.v3.full.assemblers.ProductModelAssembler;
import innovatech.product.v3.full.model.Product;
import innovatech.product.v3.full.service.ProductService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/apiv3/v2/productos")
public class ProductControllerV2 {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Product>> getAllProducts(){
        List<EntityModel<Product>> products = productService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(products,
                linkTo(methodOn(ProductControllerV2.class).getAllProducts()).withSelfRel());       
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Product> getProductById(@PathVariable Integer id){
        Product product = productService.findById(id);
        return assembler.toModel(product);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Product>> createProduct(@RequestBody Product product){
        Product newProduct = productService.save(product);
        return ResponseEntity
                .created(linkTo(methodOn(ProductControllerV2.class).getProductById(newProduct.getId())).toUri())
                .body(assembler.toModel(newProduct));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Product>> updateProduct(@PathVariable Integer id, @RequestBody Product product){
        product.setId(id);
        Product updatedProduct = productService.save(product);
        return ResponseEntity
                .ok(assembler.toModel(updatedProduct));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity
                .noContent().build();
    }
}
