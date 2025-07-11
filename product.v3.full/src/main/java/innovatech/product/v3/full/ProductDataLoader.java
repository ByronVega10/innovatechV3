package innovatech.product.v3.full;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import innovatech.product.v3.full.model.Product;
import innovatech.product.v3.full.repository.ProductRepository;
import net.datafaker.Faker;

@Profile("dev")
@Component
public class ProductDataLoader implements CommandLineRunner{

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();

        String[] nombresProductos = {
            "PlayStation 5", "PlayStation 4 Pro", "PlayStation 4 Slim",
            "Xbox Series X", "Xbox Series S", "Xbox One X",
            "Nintendo Switch", "Nintendo Switch OLED",
            "Samsung Galaxy S24", "Samsung Galaxy S23", "Samsung Galaxy S22 Ultra",
            "iPhone 15 Pro", "iPhone 15", "iPhone 14 Pro", "iPhone 14",
            "Google Pixel 8", "Google Pixel 7",
            "OnePlus 11", "OnePlus 10 Pro",
            "Motorola Edge 40", "Xiaomi 13 Pro", "Huawei P60 Pro",
            "LG OLED TV 55\"", "LG OLED TV 65\"",
            "Samsung QLED TV 65\"", "Samsung QLED TV 55\"",
            "AOC 4K Monitor 27\"", "AOC Curved Monitor 32\"",
            "Sony Bravia 4K 55\"", "Sony Bravia 4K 65\"",
            "Apple Watch Series 9", "Apple Watch SE",
            "Samsung Galaxy Watch 6", "Samsung Galaxy Watch 5",
            "Garmin Fenix 7", "Garmin Venu 3",
            "Fitbit Versa 4", "Fitbit Sense",
            "Xiaomi Mi Band 7", "Huawei Watch GT 3",
            "Sony WH-1000XM5 Auriculares", "Bose QuietComfort 45",
            "JBL Flip 6", "Beats Studio3",
            "Sennheiser HD 450BT", "Anker Soundcore Life Q30",
            "Logitech G Pro X Auriculares", "Razer BlackShark V2",
            "SteelSeries Arctis 7", "HyperX Cloud II"
        };

        // Generar productos
        for (int i = 0; i < 50; i++) {
            Product product = new Product();

            String nombre = nombresProductos[faker.random().nextInt(nombresProductos.length)];

            product.setId(i + 1);
            product.setNombre(nombre);
            product.setDescripcion(faker.lorem().sentence());
            product.setPrecio(faker.number().numberBetween(20000, 2000000));
            product.setStock(faker.number().numberBetween(20, 100));
            productRepository.save(product);
        }
    }
}
