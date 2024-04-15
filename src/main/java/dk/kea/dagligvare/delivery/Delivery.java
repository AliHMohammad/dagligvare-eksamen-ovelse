package dk.kea.dagligvare.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.kea.dagligvare.productorder.ProductOrder;
import dk.kea.dagligvare.van.Van;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;

    private String fromWarehouse; //TODO: Replace with Warehouse Entity?

    private String destination;

    @OneToMany
    @JoinColumn(name = "delivery_id")
    private Set<ProductOrder> productOrders = new HashSet<>();

    public Delivery(LocalDate deliveryDate, String fromWarehouse, String destination) {
        this.deliveryDate = deliveryDate;
        this.fromWarehouse = fromWarehouse;
        this.destination = destination;
    }

    public Delivery(LocalDate deliveryDate, String fromWarehouse, String destination, Set<ProductOrder> productOrders) {
        this.deliveryDate = deliveryDate;
        this.fromWarehouse = fromWarehouse;
        this.destination = destination;
        this.productOrders = productOrders;
    }


    public void addProductOrder(ProductOrder productOrder) {
        productOrders.add(productOrder);
    }

    public int getTotalWeightInKilos() {
        double weight = 0;
        for (ProductOrder productOrder : productOrders) {
            weight += productOrder.getTotalWeightInGrams();
        }

        return (int) Math.ceil(weight / 1000);
    }
}
