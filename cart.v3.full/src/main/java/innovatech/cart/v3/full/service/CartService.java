package innovatech.cart.v3.full.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import innovatech.cart.v3.full.repository.CartRepository;
import innovatech.cart.v3.full.model.CartItem;
import innovatech.cart.v3.full.client.ProductClient;
import innovatech.cart.v3.full.dto.ProductDTO;
import innovatech.cart.v3.full.model.Cart;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private RestTemplate restTemplate;


    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public Cart findById(Integer id){
        return cartRepository.findById(id).get();
    }

    public Optional<Cart> findByCustomerId(Integer customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    //Obtener carrito del cliente
    public Cart getCartEntity(Integer customerId){
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(customerId);
                    return cartRepository.save(newCart);
                });
    }

    //Agrgar Producto al carrito
    public Cart addProductToCart(Integer customerId, Integer productId, int quantity){
        Cart cart = cartRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new RuntimeException("Carrito no Encontrado"));

        ProductDTO product = productClient.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Producto no Encontrado");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);

        return cartRepository.save(cart);
    }

    //Eliminar producto del carrito
    public Cart removeProduct(Integer customerId, Integer productId){
        Cart cart = cartRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new RuntimeException("Carrito no Encontrado"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        return cartRepository.save(cart);
    }

    //Vaciar carrito
    public Cart clearCart(Integer customerId){
        Cart cart = cartRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new RuntimeException("Carrito no Encontrado"));
                    
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

}
