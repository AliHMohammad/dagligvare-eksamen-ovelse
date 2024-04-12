package dk.kea.dagligvare.productorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.kea.dagligvare.delivery.Delivery;
import dk.kea.dagligvare.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne

    private Product product;

}
