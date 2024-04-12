package dk.kea.dagligvare.van;

import dk.kea.dagligvare.delivery.Delivery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Van {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private int capacity;

    @OneToMany()
    @JoinColumn(name = "van_id")
    private Set<Delivery> deliveries = new HashSet<>();


    public Van(String model, int capacity, Set<Delivery> deliveries) {
        this.model = model;
        this.capacity = capacity;
        this.deliveries = deliveries;
    }

    public Van(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public void assignDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }
}
