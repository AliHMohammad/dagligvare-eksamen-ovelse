package dk.kea.dagligvare.product;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product with id " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Product deleteProductById(long id) {
        Product productInDb = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product with id " + id));

        productRepository.delete(productInDb);

        return productInDb;
    }

    public Product createProduct(RequestProductDTO requestProductDTO) {
        Product product = toEntity(requestProductDTO);
        return productRepository.save(product);
    }

    public Product updateProductById(long id, RequestProductDTO requestProductDTO) {
        if (!productRepository.existsById(id))
            throw new EntityNotFoundException("Product with id " + id + "not found");

        Product product = toEntity(requestProductDTO);
        product.setId(id);
        productRepository.save(product);

        return product;
    }


    public Product toEntity(RequestProductDTO requestProductDTO) {
        return new Product(
                requestProductDTO.name(),
                requestProductDTO.price(),
                requestProductDTO.weightInGrams()
        );
    }


}
