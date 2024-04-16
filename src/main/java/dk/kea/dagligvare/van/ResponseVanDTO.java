package dk.kea.dagligvare.van;

import dk.kea.dagligvare.delivery.ResponseDeliveryDTO;

import java.util.List;

public record ResponseVanDTO(
        Long id,
        String model,
        int capacity
) {
}
