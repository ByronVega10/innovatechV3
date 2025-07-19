package innovatech.product.v3.full.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import innovatech.product.v3.full.controller.ProductControllerV2;
import innovatech.product.v3.full.model.Product;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Product> toModel(Product product){
        return EntityModel.of(product,
                linkTo(methodOn(ProductControllerV2.class).getProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductControllerV2.class).getAllProducts()).withRel("productos"),
                linkTo(methodOn(ProductControllerV2.class).deleteProduct(product.getId())).withRel("delete"),
                linkTo(methodOn(ProductControllerV2.class).updateProduct(product.getId(), product)).withRel("update")
    );
    }           
}
