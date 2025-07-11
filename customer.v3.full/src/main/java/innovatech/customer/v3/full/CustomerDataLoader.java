package innovatech.customer.v3.full;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import innovatech.customer.v3.full.model.Customer;
import innovatech.customer.v3.full.repository.CustomerRepository;
import net.datafaker.Faker;

@Profile("dev")
@Component
public class CustomerDataLoader implements CommandLineRunner{

    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Generar clientes
        for (int i = 0; i < 50; i++) {
            Customer customer = new Customer();
            customer.setId(i + 1);
            customer.setRun(faker.idNumber().valid());
            customer.setNombre(faker.name().firstName());
            customer.setApellido(faker.name().lastName());
            customer.setCorreo(faker.internet().emailAddress());
            customerRepository.save(customer);
        }
    }
}
