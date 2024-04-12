package dk.kea.dagligvare.productorder;

import dk.kea.dagligvare.product.Product;
import dk.kea.dagligvare.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService {


    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
    }


    public ProductOrder createProductOrder(RequestProductOrderDTO requestProductOrderDTO) {
        Product product = productRepository.findById(requestProductOrderDTO.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + requestProductOrderDTO.productId() + " not found"));

        ProductOrder productOrder = new ProductOrder(
                requestProductOrderDTO.quantity(),
                product
        );

        return productOrderRepository.save(productOrder);
    }

    public ResponseProductOrderDTO toDTO(ProductOrder productOrder) {
        return new ResponseProductOrderDTO(
                productOrder.getQuantity(),
                productOrder.getProduct()
        );
    }
}
