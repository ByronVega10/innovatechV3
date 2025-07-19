package innovatech.cart.v3.full.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import innovatech.cart.v3.full.dto.CartItemDTO;
import innovatech.cart.v3.full.model.Cart;
import innovatech.cart.v3.full.repository.CartRepository;
import innovatech.cart.v3.full.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/apiv3/carritos")
@Tag(name = "Carritos", description = "Operaciones relacionadas a los carritos")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;


    //accion
    @GetMapping
    //comentario
    @Operation(
        summary = "Listar todos los carritos", 
        description = "Lista una lista de todos los carritos")
    //logica
    public ResponseEntity<List<Cart>> listarProducto(){
        List<Cart> carts = cartService.findAll();
        if (carts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.ok(carts); 
    }


    //accion
    @GetMapping("/clientes/{customerId}")
    //comentario
    @Operation(
        summary = "Obtener el carrito de un cliente",
        description = "Busca y retorna el carrito asociado al ID del cliente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")})
    //logica
    public ResponseEntity<Cart> getCartByCustomerId(
        @Parameter(description = "ID del cliente",required = true)
        @PathVariable Integer customerId){
            
            Optional<Cart> cart = cartRepository.findByCustomerId(customerId);
            return cart.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }


    //accion
    @PostMapping("/clientes/{customerId}/add")
    //comentario
    @Operation(
        summary = "Agregar producto al carrito",
        description = "Agrega un producto (existente) al carrito del cliente indicado y devuelve el carrito actualizado.")
    //logica
    public ResponseEntity<Cart> addProductToCart(
        @Parameter(description = "ID del cliente",required = true)
        @PathVariable Integer customerId,
        @RequestBody CartItemDTO cartItemDTO) {
               
            Cart cart = cartService.addProductToCart(customerId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
            return ResponseEntity.ok(cart);
    }


    //accion
    @DeleteMapping("/clientes/{customerId}/remove/{productId}")
    //comentario
    @Operation(
        summary = "Eliminar producto del carrito",
        description = "Elimina un producto (por su ID) del carrito del cliente indicado.")
    //logica
    public ResponseEntity<Void> removeProductFromCart(
        @Parameter(description = "ID del cliente",required = true)
        @PathVariable Integer customerId,
        @Parameter(description = "ID del producto a eliminar",required = true)
        @PathVariable Integer productId) {
                
            cartService.removeProduct(customerId, productId);
            return ResponseEntity.ok().build();
    }


    //accion
    @DeleteMapping("/{customerId}/clear")
    //comentario
    @Operation(
        summary = "Vaciar el carrito",
        description = "Elimina todos los productos del carrito del cliente, dejándolo vacío.")
    //logica
    public ResponseEntity<Void> clearCart(
        @Parameter(description = "ID del cliente",required = true)
        @PathVariable Integer customerId) {
            
            cartService.clearCart(customerId);
            return ResponseEntity.ok().build();
    }
}

