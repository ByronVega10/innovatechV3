package innovatech.cart.v3.full.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
}
