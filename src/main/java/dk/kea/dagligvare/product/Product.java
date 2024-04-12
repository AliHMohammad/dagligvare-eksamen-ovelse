package dk.kea.dagligvare.product;


import dk.kea.dagligvare.productorder.ProductOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private int weightInGrams;


    public Product(String name, double price, int weightInGrams) {
        this.name = name;
        this.price = price;
        this.weightInGrams = weightInGrams;
    }
}
