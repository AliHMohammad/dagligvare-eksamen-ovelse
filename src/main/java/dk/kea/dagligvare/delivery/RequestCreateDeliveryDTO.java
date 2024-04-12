package dk.kea.dagligvare.delivery;

import java.time.LocalDate;

public record RequestCreateDeliveryDTO(
        LocalDate deliveryDate,
        String fromWarehouse,
        String destination
) {
}
