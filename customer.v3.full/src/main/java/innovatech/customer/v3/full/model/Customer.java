package innovatech.customer.v3.full.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name="cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private Integer id;
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
}
