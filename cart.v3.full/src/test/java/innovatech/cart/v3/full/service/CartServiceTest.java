package innovatech.cart.v3.full.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import innovatech.cart.v3.full.repository.CartRepository;
import innovatech.cart.v3.full.client.ProductClient;
import innovatech.cart.v3.full.model.CartItem;
import innovatech.cart.v3.full.dto.ProductDTO;
import innovatech.cart.v3.full.model.Cart;

import org.junit.jupiter.api.Test;
import java.util.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductClient productClient;

    @Test
    void testFindAll() {
        Cart cart = new Cart();
        
        when(cartRepository.findAll()).thenReturn(List.of(cart));

        List<Cart> result = cartService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cart.getId(), result.get(0).getId());
    }


    @Test
    void testFindById() {
        Cart cart = new Cart();
        cart.setId(1);
        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

        Cart result = cartService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(cartRepository).findById(1);
    }

    
    @Test
    void testFindByCustomerId() {
        Cart cart = new Cart();
        cart.setCustomerId(50);
        when(cartRepository.findByCustomerId(50)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.findByCustomerId(50);

        assertNotNull(result);
        assertEquals(50, result.get().getCustomerId());
        verify(cartRepository).findByCustomerId(50);
    }

    
    @Test
    void testGetCartEntity_existing() {
        Cart cart = new Cart();
        cart.setCustomerId(8);

        when(cartRepository.findByCustomerId(8)).thenReturn(Optional.of(cart));

        Cart result = cartService.getCartEntity(8);
        assertEquals(8,result.getCustomerId());
    }

    @Test
    void testGetCartEntity_new() {
        when(cartRepository.findByCustomerId(30)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = cartService.getCartEntity(30);
        assertEquals(30,result.getCustomerId());
    }

    @Test
    void testAddProductToCart_ok() {
        Cart cart = new Cart();
        cart.setCustomerId(18);
        cart.setItems(new ArrayList<>());

        when(cartRepository.findByCustomerId(18)).thenReturn(Optional.of(cart));
        ProductDTO product = new ProductDTO();
        product.setId(2000);
        when(productClient.getProductById(2000)).thenReturn(product);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = cartService.addProductToCart(18, 2000, 3);

        assertEquals(1, result.getItems().size());
        assertEquals(2000, result.getItems().get(0).getProductId());
        assertEquals(3, result.getItems().get(0).getQuantity());
    }

    @Test
    void testAddProductToCart_productNotFound() {
        Cart cart = new Cart();
        cart.setCustomerId(1);
        cart.setItems(new ArrayList<>());
        when(cartRepository.findByCustomerId(1)).thenReturn(Optional.of(cart));
        when(productClient.getProductById(123)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
        cartService.addProductToCart(1, 123, 1);});
        assertTrue(exception.getMessage().contains("Producto no Encontrado"));
    }

    @Test
    void testRemoveProduct() {
        Cart cart = new Cart();
        cart.setCustomerId(5);
        CartItem ci = new CartItem();
        ci.setProductId(99); ci.setQuantity(2); ci.setCart(cart);
        cart.setItems(new ArrayList<>(List.of(ci)));

        when(cartRepository.findByCustomerId(5)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.removeProduct(5, 99);
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void testClearCart() {
        Cart cart = new Cart();
        cart.setCustomerId(7);
        cart.setItems(new ArrayList<>(List.of(new CartItem(), new CartItem())));

        when(cartRepository.findByCustomerId(7)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.clearCart(7);
        assertTrue(result.getItems().isEmpty());
    }
}
