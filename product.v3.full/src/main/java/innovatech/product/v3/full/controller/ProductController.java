package innovatech.product.v3.full.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import innovatech.product.v3.full.model.Product;
import innovatech.product.v3.full.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas a los productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Lista una lista de todos los productos")
    public ResponseEntity<List<Product>> listarProducto(){
        List<Product> productos = productService.findAll();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos); 
    }

    @PostMapping
    @Operation(summary = "Guardar un producto", description = "Crea y guarda un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                         content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no creado")
            })
    public ResponseEntity<Product> guardarProducto(@RequestBody Product producto){
        Product productoNuevo = productService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto", description = "Busca un producto por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            })  
    public ResponseEntity<Product> buscarProducto(@PathVariable Integer id){
        try{
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza un producto ya existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                         content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            })
    public ResponseEntity<Product> actualizarProducto(@PathVariable Integer id, @RequestBody Product product){
        try {
            Product prod = productService.findById(id);
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
    @Operation(summary = "Eliminar un Producto", description = "Elimina un producto por su codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            })       
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
