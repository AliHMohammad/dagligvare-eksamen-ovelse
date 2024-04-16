package dk.kea.dagligvare.delivery;

import dk.kea.dagligvare.productorder.ProductOrder;
import dk.kea.dagligvare.productorder.ResponseProductOrderDTO;

import java.time.LocalDate;
import java.util.List;

public record ResponseDetailedDeliveryDTO(
        Long id,
        LocalDate deliveryDate,
        String fromWarehouse,
        String destination,
        List<ResponseProductOrderDTO> products,
        Long vanId
) {
}
