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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @GetMapping
    @Operation(summary = "Listar todos los carritos", description = "Lista una lista de todos los carritos")
    public ResponseEntity<List<Cart>> listarProducto(){
        List<Cart> carts = cartService.findAll();
        if (carts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carts); 
    }


    // Obtener el carrito de un cliente
    @GetMapping("/clientes/{customerId}")
    public ResponseEntity<Cart> getCartByCustomerId(@PathVariable Integer customerId){
        Optional<Cart> cart = cartRepository.findByCustomerId(customerId);
        return cart.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Agregar producto al carrito
    @PostMapping("/clientes/{customerId}/add")
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable Integer id,
            @RequestBody CartItemDTO cartItemDTO) {
        Cart cart = cartService.addProductToCart(id, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok(cart);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/clientes/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @PathVariable Integer customerId,
            @PathVariable Integer productId) {
        cartService.removeProduct(customerId, productId);
        return ResponseEntity.ok().build();
    }

    // Vaciar el carrito
    @DeleteMapping("/{customerId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Integer customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}

