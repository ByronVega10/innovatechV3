package innovatech.cart.v3.full.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import innovatech.cart.v3.full.repository.CartRepository;
import innovatech.cart.v3.full.model.CartItem;
import innovatech.cart.v3.full.dto.CartDTO;
import innovatech.cart.v3.full.dto.ProductDTO;
import innovatech.cart.v3.full.model.Cart;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ProductDTO getProductById(Integer productId){
        return restTemplate.getForObject("http://localhost:8082/product/" + productId, ProductDTO.class);
    }

    //Obtener carrito del cliente o crearlo si no existe
    public Cart getOrCreateCartEntity(Integer customerId){
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(customerId);
                    return cartRepository.save(newCart);
                });
    }

    //Agrgar Producto al carrito
    public void addProduct(Integer customerId, Integer productId, int quantity){
        Cart cart = getOrCreateCartEntity(customerId);
        Optional<CartItem> itemOpt = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(productId)).findFirst();

        if (itemOpt.isPresent()){
            CartItem item = itemOpt.get();
            item.setQuantity(item.getQuantity()+quantity);
        
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }
    }

    //Eliminar producto del carrito
    public void removeProduct(Integer customerId, Integer productId){
        Cart cart = getOrCreateCartEntity(customerId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }

    //Vaciar carrito
    public void clearCart(Integer customerId){
        Cart cart = getOrCreateCartEntity(customerId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    // Obtener carrito
    public CartDTO getOrCreateCart(Integer customerId) {
        Cart cart = getOrCreateCartEntity(customerId);
        return new CartDTO(cart); // O usa tu mapper
}
}
