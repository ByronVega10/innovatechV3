package innovatech.cart.v3.full.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import innovatech.cart.v3.full.assemblers.CartModelAssembler;
import innovatech.cart.v3.full.dto.CartItemDTO;
import innovatech.cart.v3.full.model.Cart;
import innovatech.cart.v3.full.service.CartService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/apiv3/v2/carritos")
public class CartControllerV2 {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartModelAssembler assembler;

    //Listar todos los carritos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Cart>> getAllCarts(){
        List<EntityModel<Cart>> carts = cartService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(carts,
                linkTo(methodOn(CartControllerV2.class).getAllCarts()).withSelfRel());       
    }

    //Buscar un carrito por ID del carrito
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Cart> getCart(@PathVariable Integer id){
        Cart cart = cartService.findById(id);
        return assembler.toModel(cart);
    }

    //Buscar un carrito por ID del cliente
    @GetMapping(value = "/clientes/{customerId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cart>> getCartByCustomerId(@PathVariable Integer customerId) {

    Optional<Cart> cart = cartService.findByCustomerId(customerId);

    return cart
        .map(value -> ResponseEntity.ok(assembler.toModel(value)))
        .orElse(ResponseEntity.notFound().build());
    }

    //Agrgar un producto al carrito
    @PostMapping(value = "/clientes/{customerId}/add", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cart>> addProductToCart(
        @PathVariable Integer customerId,
        @RequestBody CartItemDTO cartItemDTO) {

    Cart cart = cartService.addProductToCart(customerId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
    return ResponseEntity.ok(assembler.toModel(cart));
    }

    //Eliminar un producto del carrito
    @DeleteMapping(value = "/clientes/{customerId}/remove/{productId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cart>> removeProductFromCart(@PathVariable Integer customerId, @PathVariable Integer productId) 
    {    
        Cart cart = cartService.removeProduct(customerId, productId);
        return ResponseEntity.ok(assembler.toModel(cart));
    }

    //Vaciar carrito
    @DeleteMapping(value = "/{customerId}/vaciar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cart>> clearCart(@PathVariable Integer customerId) {
        Cart cart = cartService.clearCart(customerId);
        return ResponseEntity.ok(assembler.toModel(cart));
    }
}
