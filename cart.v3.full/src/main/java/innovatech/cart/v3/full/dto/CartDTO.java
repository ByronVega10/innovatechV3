package innovatech.cart.v3.full.dto;
import java.util.List;

import innovatech.cart.v3.full.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDTO {

    private Integer cartId;
    private Integer customerId;
    private List<CartItemDTO> items;

    public CartDTO(Cart cart) {
    }
}
