package dk.kea.dagligvare.delivery;

import java.time.LocalDate;

public record ResponseDeliveryDTO(
        Long id,
        LocalDate deliveryDate,
        String fromWarehouse,
        String destination
) {
}
