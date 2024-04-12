package dk.kea.dagligvare.productorder;

import dk.kea.dagligvare.product.Product;

public record ResponseProductOrderDTO(
        int quantity,
        Product item
) {
}
