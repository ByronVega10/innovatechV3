package innovatech.customer.v3.full.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import innovatech.customer.v3.full.model.Customer;
import innovatech.customer.v3.full.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @Operation(summary = "Listar todos los Clientes", description = "Lista una lista de todos los clientes")
    public ResponseEntity<List<Customer>> listarCliente(){
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers); 
    }

    @PostMapping
    @Operation(summary = "Guardar un cliente", description = "Crea y guarda un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                         content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no creado")
            })
    public ResponseEntity<Customer> guardarCliente(@RequestBody Customer cliente){
        Customer  newCustomer = customerService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar un cliente", description = "Busca un cliente por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            })  
    public ResponseEntity<Customer> buscarCliente(@PathVariable Integer id){
        try{
            Customer customer = customerService.findById(id);
            return ResponseEntity.ok(customer);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente", description = "Actualiza un cliente ya existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
                         content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            })
    public ResponseEntity<Customer> actualizarCliente(@PathVariable Integer id, @RequestBody Customer customer){
        try {
            Customer cust = customerService.findById(id);
            cust.setId(id);
            cust.setRun(customer.getRun());
            cust.setNombre(customer.getNombre());
            cust.setApellido(customer.getApellido());
            cust.setCorreo(customer.getCorreo());

            customerService.save(cust);
            return ResponseEntity.ok(customer);
            
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un Cliente", description = "Elimina un cliente por su codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }) 
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id){
        try {
            customerService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
