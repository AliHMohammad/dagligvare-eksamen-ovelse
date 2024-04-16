package dk.kea.dagligvare.van;

import dk.kea.dagligvare.delivery.Delivery;
import dk.kea.dagligvare.delivery.DeliveryRepository;
import dk.kea.dagligvare.delivery.DeliveryService;
import dk.kea.dagligvare.delivery.ResponseDeliveryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VanService {


    private final VanRepository vanRepository;
    private final DeliveryService deliveryService;
    private final DeliveryRepository deliveryRepository;

    public VanService(VanRepository vanRepository, DeliveryService deliveryService, DeliveryRepository deliveryRepository) {
        this.vanRepository = vanRepository;
        this.deliveryService = deliveryService;
        this.deliveryRepository = deliveryRepository;
    }


    public List<ResponseDeliveryDTO> getAllDeliveriesByVanId(long id) {
        Van vanInDb = vanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Van with id " + id + "not found"));

        List<ResponseDeliveryDTO> deliveryDTOS = new ArrayList<>();

        for (Delivery delivery : vanInDb.getDeliveries()) {
            deliveryDTOS.add(deliveryService.toDTO(delivery, id));
        }

        return deliveryDTOS;
    }

    public Van assignDeliveryToVanById(long vanId, long deliveryId) throws BadRequestException {
        Van vanInDb = vanRepository.findById(vanId)
                .orElseThrow(() -> new EntityNotFoundException("Van with id " + vanId + "not found"));

        Delivery deliveryInDb = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery with id " + deliveryId + " not found"));

        int deliveryWeightInKilos = (int) Math.ceil(deliveryService.getTotalDeliveryWeightInGrams(deliveryInDb) / 1000);

        // Hvis vans nuværende gods samt den delivery, vi tilføjer, overstiger vans kapacitet, så throw.
        if (vanInDb.getCombinedWeightOfDeliveries() + deliveryWeightInKilos > vanInDb.getCapacity())
            throw new BadRequestException("The total weight for delivery with id " + deliveryId + " exceeds the vans capacity by " + (deliveryWeightInKilos - vanInDb.getCapacity()) + " kilos");

/*
        if (vanInDb.getCapacity() < deliveryWeightInKilos)
            throw new BadRequestException("The total weight for delivery with id " + deliveryId + " exceeds the vans capacity by " + (deliveryWeightInKilos - vanInDb.getCapacity()) + " kilos");
            */

        vanInDb.assignDelivery(deliveryInDb);
        vanRepository.save(vanInDb);
        return vanInDb;
    }
}
