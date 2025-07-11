package innovatech.customer.v3.full.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import innovatech.customer.v3.full.model.Customer;
import innovatech.customer.v3.full.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(long id){
        return customerRepository.findById(id).get();
    }

    public Customer save(Customer costumer){
        return customerRepository.save(costumer);
    }

    public void deleteById(Long id){
        customerRepository.deleteById(id);
    }
}
