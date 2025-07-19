package innovatech.cart.v3.full;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import innovatech.cart.v3.full.model.Cart;
import innovatech.cart.v3.full.model.CartItem;
import innovatech.cart.v3.full.repository.CartRepository;
import net.datafaker.Faker;

@Profile("dev")
@Component
public class CartDataLoader implements CommandLineRunner{

    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Generar clientes
        for (int i = 0; i < 50; i++) {
            Cart cart = new Cart();
            cart.setId(i + 1);
            cart.setCustomerId(faker.number().numberBetween(1, 50));

            // Lista para items del carrito
            List<CartItem> items = new ArrayList<>();

            // Número aleatorio de items por carrito (por ejemplo, entre 1 y 5)
            int numItems = faker.number().numberBetween(1, 5);
            for (int j = 0; j < numItems; j++) {
                CartItem item = new CartItem();
                item.setProductId(faker.number().numberBetween(1, 50));
                item.setQuantity(faker.number().numberBetween(1, 10));
                item.setCart(cart); // Relación bidireccional
                items.add(item);
            }
            cartRepository.save(cart);
        }
    }
}