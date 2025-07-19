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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Carritos", description = "Operaciones REST sobre carritos de compra (HATEOAS incluido)")
@RestController
@RequestMapping("/apiv3/v2/carritos")
public class CartControllerV2 {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartModelAssembler assembler;


    //accion
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Listar todos los carritos",
        description = "Devuelve todos los carritos creados, cada uno con enlaces HATEOAS.")
    //logica
    public CollectionModel<EntityModel<Cart>> getAllCarts(){
        List<EntityModel<Cart>> carts = cartService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(carts,
                linkTo(methodOn(CartControllerV2.class).getAllCarts()).withSelfRel());       
    }

    //accion
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Buscar carrito por ID",
        description = "Busca un carrito por su ID y lo devuelve con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")})
    //logica
    public EntityModel<Cart> getCart(
        @Parameter(description = "ID del carrito", required = true)
        @PathVariable Integer id){
            Cart cart = cartService.findById(id);
            return assembler.toModel(cart);
    }


    //accion
    @GetMapping(value = "/clientes/{customerId}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Buscar carrito por ID del cliente",
        description = "Devuelve el carrito asociado a un cliente determinado por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")})
    //logica
    public ResponseEntity<EntityModel<Cart>> getCartByCustomerId(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer customerId) {

            Optional<Cart> cart = cartService.findByCustomerId(customerId);

            return cart
                .map(value -> ResponseEntity.ok(assembler.toModel(value)))
                .orElse(ResponseEntity.notFound().build());
    }


    //accion
    @PostMapping(value = "/clientes/{customerId}/add", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Agregar producto al carrito",
        description = "Agrega un producto existente al carrito del cliente y responde con el carrito actualizado."
    )
    //logica
    public ResponseEntity<EntityModel<Cart>> addProductToCart(
        @Parameter(description = "ID del cliente",required = true)
        @PathVariable Integer customerId,
        @RequestBody CartItemDTO cartItemDTO) {

            Cart cart = cartService.addProductToCart(customerId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
            return ResponseEntity.ok(assembler.toModel(cart));
    }


    //accion
    @DeleteMapping(value = "/clientes/{customerId}/remove/{productId}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
     @Operation(
        summary = "Eliminar producto del carrito",
        description = "Elimina un producto específico del carrito del cliente y responde con el carrito actualizado.")
    //logica
    public ResponseEntity<EntityModel<Cart>> removeProductFromCart(
        @Parameter(description = "ID del cliente",required = true)
        @PathVariable Integer customerId, 
        @Parameter(description = "ID del producto",required = true)
        @PathVariable Integer productId) {    
        
            Cart cart = cartService.removeProduct(customerId, productId);
            return ResponseEntity.ok(assembler.toModel(cart));
    }

    //accion
    @DeleteMapping(value = "/{customerId}/vaciar", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Vaciar carrito",
        description = "Elimina todos los productos del carrito del cliente, dejando el carrito vacío.")
    //logica
    public ResponseEntity<EntityModel<Cart>> clearCart(
        @Parameter(description = "ID del cliente")
        @PathVariable Integer customerId) {
            Cart cart = cartService.clearCart(customerId);
            return ResponseEntity.ok(assembler.toModel(cart));
    }
}
