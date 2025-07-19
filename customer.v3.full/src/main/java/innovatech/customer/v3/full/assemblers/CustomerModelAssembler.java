package innovatech.customer.v3.full.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import innovatech.customer.v3.full.model.Customer;
import innovatech.customer.v3.full.controller.CustomerControllerV2;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Customer> toModel(Customer customer){
        return EntityModel.of(customer,
                linkTo(methodOn(CustomerControllerV2.class).getCustomerById(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerControllerV2.class).getAllCustomers()).withRel("customers"),
                linkTo(methodOn(CustomerControllerV2.class).deleteCustomer(customer.getId())).withRel("delete"),
                linkTo(methodOn(CustomerControllerV2.class).updateCustomer(customer.getId(), customer)).withRel("update")
        );
    }           
}
