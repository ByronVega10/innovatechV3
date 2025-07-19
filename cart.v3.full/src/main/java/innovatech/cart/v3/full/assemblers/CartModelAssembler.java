package innovatech.cart.v3.full.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import innovatech.cart.v3.full.controller.CartControllerV2;
import innovatech.cart.v3.full.model.Cart;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<Cart, EntityModel<Cart>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Cart> toModel(Cart cart){
        return EntityModel.of(cart,
                linkTo(methodOn(CartControllerV2.class).getCart(cart.getId())).withSelfRel(),
                linkTo(methodOn(CartControllerV2.class).getAllCarts()).withRel("productos"));
    }           
}