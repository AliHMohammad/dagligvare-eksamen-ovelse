package dk.kea.dagligvare.productorder;

public record RequestProductOrderDTO(
        int quantity,
        long productId
) {
}
