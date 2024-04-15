package dk.kea.dagligvare.productorder;

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

    public ProductOrder(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public double getTotalWeightInGrams() {
        return (double) quantity * product.getWeightInGrams();
    }

}
