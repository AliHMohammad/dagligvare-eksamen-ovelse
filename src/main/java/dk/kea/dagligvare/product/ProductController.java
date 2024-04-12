package dk.kea.dagligvare.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam Optional<String> name) {
        if (name.isPresent()) return ResponseEntity.ok(productService.getProductsByName(name.get()));

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody RequestProductDTO requestProductDTO) {
        Product product = productService.createProduct(requestProductDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") long id, @RequestBody RequestProductDTO requestProductDTO) {
        return ResponseEntity.ok(productService.updateProductById(id, requestProductDTO));
    }
}
