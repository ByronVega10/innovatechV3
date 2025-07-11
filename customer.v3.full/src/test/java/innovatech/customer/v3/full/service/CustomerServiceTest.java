package innovatech.customer.v3.full.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import innovatech.customer.v3.full.model.Customer;
import innovatech.customer.v3.full.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testFindAll(){
        Customer customer = new Customer();

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> result = customerService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customer.getId(), result.get(0).getId());
    }

    @Test
    void testFindByIdFound(){
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(customerRepository).findById(1L);
    }

    @Test
    void testSave(){
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.save(customer);

        assertEquals(customer, result);
        verify(customerRepository).save(customer);
    }

    @Test
    void testDeletebyId(){
        long id = 49;

        customerService.deleteById(id);

        verify(customerRepository).deleteById(id);
    }
}
