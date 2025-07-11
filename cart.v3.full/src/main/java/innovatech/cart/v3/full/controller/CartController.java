package innovatech.cart.v3.full.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import innovatech.cart.v3.full.dto.CartDTO;
import innovatech.cart.v3.full.dto.CartItemDTO;
import innovatech.cart.v3.full.service.CartService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/cart")
public class CartController {

   @Autowired
    private CartService cartService;

    // Obtener el carrito de un cliente
    @GetMapping("/{customerId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Integer customerId){
        CartDTO cart = cartService.getOrCreateCart(customerId);
        return ResponseEntity.ok(cart);
    }

    // Agregar producto al carrito
    @PostMapping("/{customerId}/add")
    public ResponseEntity<Void> addProductToCart(
            @PathVariable Integer customerId,
            @RequestBody CartItemDTO cartItemDTO) {
        cartService.addProduct(customerId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok().build();
    }

    // Eliminar producto del carrito
    @DeleteMapping("/{customerId}/remove/{productId}")
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

