package dk.kea.dagligvare.delivery;

import dk.kea.dagligvare.productorder.RequestProductOrderDTO;

import java.util.List;

public record RequestUpdateDeliveryDTO(
    List<RequestProductOrderDTO> items
) {
}
