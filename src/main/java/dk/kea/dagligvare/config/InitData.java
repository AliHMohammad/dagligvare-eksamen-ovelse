package dk.kea.dagligvare.config;

import dk.kea.dagligvare.product.Product;
import dk.kea.dagligvare.product.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements ApplicationRunner {


    private final ProductRepository productRepository;

    public InitData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Creating initial data:");
        createInitialData();
    }

    private void createInitialData() {
        createProducts();
    }

    private void createProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Kyllingelår", 20.0, 450));
        products.add(new Product("Oksekød", 50.0, 800));

        productRepository.saveAll(products);
    }
}
