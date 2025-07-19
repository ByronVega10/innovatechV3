package innovatech.product.v3.full.controller;

import innovatech.product.v3.full.assemblers.ProductModelAssembler;
import innovatech.product.v3.full.service.ProductService;
import innovatech.product.v3.full.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.stream.Collectors;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Productos", description = "Operaciones sobre productos")
@RestController
@RequestMapping("/apiv3/v2/productos")
public class ProductControllerV2 {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductModelAssembler assembler;

    //accion
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Obtener todos los productos", 
        description = "Devuelve una colección de productos, cada uno con enlaces HATEOAS.")
    //logica
    public CollectionModel<EntityModel<Product>> getAllProducts(){
        List<EntityModel<Product>> products = productService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(products,
                linkTo(methodOn(ProductControllerV2.class).getAllProducts()).withSelfRel());       
    }
    

    //accion
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Buscar un producto por ID",
        description = "Devuelve el producto con su ID, o 404 si no existe.", 
        responses = {@ApiResponse(responseCode = "200", description = "Producto encontrado"),
                     @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    //logica                 
    public EntityModel<Product> getProductById(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable Integer id) {
        Product product = productService.findById(id);
        return assembler.toModel(product);
    }


    //accion
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Añade un producto al inventario y devuelve el producto creado con enlaces HATEOAS.")

    //logica
    public ResponseEntity<EntityModel<Product>> createProduct(@RequestBody Product product){
        Product newProduct = productService.save(product);
        return ResponseEntity
                .created(linkTo(methodOn(ProductControllerV2.class).getProductById(newProduct.getId())).toUri())
                .body(assembler.toModel(newProduct));
    }
    

    //accion
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Actualizar un producto",
        description = "Modifica los datos de un producto existente.")
    //logica
    public ResponseEntity<EntityModel<Product>> updateProduct(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable Integer id, @RequestBody Product product) {
            product.setId(id);
            Product updatedProduct = productService.save(product);
            return ResponseEntity
                .ok(assembler.toModel(updatedProduct));
    }


    //accion
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto por su ID.")
    //logica
    public ResponseEntity<?> deleteProduct(
        @Parameter(description = "ID del producto",required = true)
        @PathVariable Integer id){
        productService.deleteById(id);
        return ResponseEntity
                .noContent().build();
    }
}
