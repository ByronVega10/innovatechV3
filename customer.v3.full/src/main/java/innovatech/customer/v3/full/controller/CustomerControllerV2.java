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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Tag(name = "Clientes", description = "Operaciones REST sobre clientes (HATEOAS incluido)")
@RestController
@RequestMapping("/apiv3/v2/clientes")
public class CustomerControllerV2 {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerModelAssembler assembler;


    //accion
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Obtener todos los clientes",
        description = "Devuelve la colección de todos los clientes con enlaces HATEOAS.")
    //logica
    public CollectionModel<EntityModel<Customer>> getAllCustomers(){
        List<EntityModel<Customer>> customer = customerService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(customer,
                linkTo(methodOn(CustomerControllerV2.class).getAllCustomers()).withSelfRel());       
    }


    //accion
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Obtener un cliente por su id",
        description = "Devuelve el modelo HATEOAS del cliente con el ID proporcionado. Retorna 404 si no existe.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    //logica
    public EntityModel<Customer> getCustomerById(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer id){
            Customer customer = customerService.findById(id);
            return assembler.toModel(customer);
    }


    //accion
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Crear un cliente",
        description = "Crea un nuevo cliente y retorna el modelo HATEOAS del cliente creado.")
    //logica
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer customer){
        Customer newCustomer = customerService.save(customer);
        return ResponseEntity
                .created(linkTo(methodOn(CustomerControllerV2.class).getCustomerById(newCustomer.getId())).toUri())
                .body(assembler.toModel(newCustomer));
    }


    //accion
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza los datos del cliente con el ID proporcionado.")
    //logica
    public ResponseEntity<EntityModel<Customer>> updateCustomer(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer id, @RequestBody Customer customer){
            customer.setId(id);
            Customer updatedCustomer = customerService.save(customer);
            return ResponseEntity
                .ok(assembler.toModel(updatedCustomer));
    }


    //accion
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    //comentario
    @Operation(
        summary = "Eliminar cliente",
        description = "Elimina el cliente con el ID proporcionado.")
    //logica
    public ResponseEntity<?> deleteCustomer(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer id){
            customerService.deleteById(id);
            return ResponseEntity
                .noContent().build();
    }
}