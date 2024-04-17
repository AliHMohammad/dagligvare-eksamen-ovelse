package dk.kea.dagligvare.config;

import dk.kea.dagligvare.delivery.Delivery;
import dk.kea.dagligvare.delivery.DeliveryRepository;
import dk.kea.dagligvare.product.Product;
import dk.kea.dagligvare.product.ProductRepository;
import dk.kea.dagligvare.productorder.ProductOrder;
import dk.kea.dagligvare.productorder.ProductOrderRepository;
import dk.kea.dagligvare.van.Van;
import dk.kea.dagligvare.van.VanRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class InitData implements ApplicationRunner {


    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductOrderRepository productOrderRepository;
    private final VanRepository vanRepository;
    List<Product> products = new ArrayList<>();
    List<ProductOrder> productOrders = new ArrayList<>();
    List<Delivery> deliveries = new ArrayList<>();
    List<Van> vans = new ArrayList<>();

    public InitData(ProductRepository productRepository, DeliveryRepository deliveryRepository, ProductOrderRepository productOrderRepository,
                    VanRepository vanRepository) {
        this.productRepository = productRepository;
        this.deliveryRepository = deliveryRepository;
        this.productOrderRepository = productOrderRepository;
        this.vanRepository = vanRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Creating initial data:");
        createInitialData();

    }

    private void createInitialData() {
        createProducts();
        createProductOrders();
        createDeliveries();
        createVans();
    }

    private void createVans() {
        vans.add(new Van("Audi", 3500, new HashSet<>(List.of(deliveries.get(0), deliveries.get(1)))));
        vans.add(new Van("Mercedes", 2000, new HashSet<>(List.of(deliveries.get(2), deliveries.get(3)))));
        vans.add(new Van("Tesla", 500));

        vanRepository.saveAll(vans);
    }

    private void createProductOrders() {
        productOrders.add(new ProductOrder(2, products.get(1)));
        productOrders.add(new ProductOrder(3, products.get(0)));
        productOrders.add(new ProductOrder(1, products.get(1)));
        productOrders.add(new ProductOrder(1, products.get(0)));
        productOrders.add(new ProductOrder(3, products.get(2)));
        productOrders.add(new ProductOrder(2, products.get(2)));
        productOrders.add(new ProductOrder(1000, products.get(3)));
        productOrders.add(new ProductOrder(2, products.get(3)));


        productOrderRepository.saveAll(productOrders);
    }

    private void createProducts() {

        products.add(new Product("Kyllingelår", 20.0, 450));
        products.add(new Product("Oksekød", 50.0, 800));
        products.add(new Product("Ost", 12.0, 100));
        products.add(new Product("Mælk", 10, 1000));
        products.add(new Product("Flødeboller", 20, 250));
        products.add(new Product("Kakao", 15, 400));
        products.add(new Product("Smørrebrød", 30, 200));
        products.add(new Product("Wienerbrød", 25, 150));
        products.add(new Product("Chokoladebar", 10, 100));
        products.add(new Product("Kransekage", 35, 300));
        products.add(new Product("Kaffe", 5, 250));

        productRepository.saveAll(products);
    }


    private void createDeliveries() {

        deliveries.add(new Delivery(LocalDate.now().plusDays(2), "w1", "København H", new HashSet<>(List.of(productOrders.get(1), productOrders.get(2), productOrders.get(7)))));
        deliveries.add(new Delivery(LocalDate.now().plusDays(1), "w2", "Esbjerg", new HashSet<>(List.of(productOrders.get(0)))));
        deliveries.add(new Delivery(LocalDate.now().plusDays(3), "w2", "Aalborg", new HashSet<>(List.of(productOrders.get(3)))));
        deliveries.add(new Delivery(LocalDate.now().plusDays(1), "w3", "København H", new HashSet<>(List.of(productOrders.get(3), productOrders.get(4)))));
        deliveries.add(new Delivery(LocalDate.now().plusDays(6), "w4", "Odense", new HashSet<>()));

        deliveryRepository.saveAll(deliveries);
    }
}
