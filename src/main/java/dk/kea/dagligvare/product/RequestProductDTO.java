package dk.kea.dagligvare.product;

public record RequestProductDTO(
        String name,
        double price,
        int weightInGrams
) {
}
