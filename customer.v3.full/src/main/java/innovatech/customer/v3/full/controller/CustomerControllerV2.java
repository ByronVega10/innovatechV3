package innovatech.customer.v3.full.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import innovatech.customer.v3.full.assemblers.CustomerModelAssembler;
import innovatech.customer.v3.full.model.Customer;
import innovatech.customer.v3.full.service.CustomerService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



@RestController
@RequestMapping("/apiv3/v2/clientes")
public class CustomerControllerV2 {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Customer>> getAllCustomers(){
        List<EntityModel<Customer>> customer = customerService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(customer,
                linkTo(methodOn(CustomerControllerV2.class).getAllCustomers()).withSelfRel());       
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Customer> getCustomerById(@PathVariable Integer id){
        Customer customer = customerService.findById(id);
        return assembler.toModel(customer);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer customer){
        Customer newCustomer = customerService.save(customer);
        return ResponseEntity
                .created(linkTo(methodOn(CustomerControllerV2.class).getCustomerById(newCustomer.getId())).toUri())
                .body(assembler.toModel(newCustomer));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Customer>> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer){
        customer.setId(id);
        Customer updatedCustomer = customerService.save(customer);
        return ResponseEntity
                .ok(assembler.toModel(updatedCustomer));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        customerService.deleteById(id);
        return ResponseEntity
                .noContent().build();
    }
}