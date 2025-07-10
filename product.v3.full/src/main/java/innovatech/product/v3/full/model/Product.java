package innovatech.product.v3.full.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Product {
    
    @Id   
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer stock;
}
