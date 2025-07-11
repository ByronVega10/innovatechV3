package innovatech.cart.v3.full.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemDTO {
    private Integer productId;
    private int quantity;
}
