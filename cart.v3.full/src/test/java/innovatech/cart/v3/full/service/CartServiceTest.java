package innovatech.cart.v3.full.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import innovatech.cart.v3.full.repository.CartRepository;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;
}
