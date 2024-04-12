package dk.kea.dagligvare.delivery;


import dk.kea.dagligvare.productorder.ProductOrder;
import dk.kea.dagligvare.productorder.ProductOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {


    private final DeliveryRepository deliveryRepository;
    private final ProductOrderService productOrderService;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductOrderService productOrderService) {
        this.deliveryRepository = deliveryRepository;
        this.productOrderService = productOrderService;
    }

    public List<ResponseDeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream().map(this::toDTO).toList();
    }

    public ResponseDetailedDeliveryDTO getSingleDeliveryById(long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery with id " + id + "not found"));

        return toDetailedDTO(delivery);
    }

    public ResponseDetailedDeliveryDTO toDetailedDTO(Delivery delivery){
        return new ResponseDetailedDeliveryDTO(
                delivery.getId(),
                delivery.getDeliveryDate(),
                delivery.getFromWarehouse(),
                delivery.getDestination(),
                delivery.getProductOrders().isEmpty() ? new ArrayList<>() : delivery.getProductOrders().stream().map(productOrderService::toDTO).toList()
        );
    }

    public Delivery createDelivery(RequestCreateDeliveryDTO requestCreateDeliveryDTO) {
        Delivery delivery = toEntity(requestCreateDeliveryDTO);
        return deliveryRepository.save(delivery);
    }

    public ResponseDeliveryDTO toDTO(Delivery delivery){
        return new ResponseDeliveryDTO(
                delivery.getId(),
                delivery.getDeliveryDate(),
                delivery.getFromWarehouse(),
                delivery.getDestination()
        );
    }


    public Delivery toEntity(RequestCreateDeliveryDTO requestCreateDeliveryDTO) {
        return new Delivery(
                requestCreateDeliveryDTO.deliveryDate(),
                requestCreateDeliveryDTO.fromWarehouse(),
                requestCreateDeliveryDTO.destination()
        );
    }


    @Transactional
    public ResponseDetailedDeliveryDTO updateDeliveryById(RequestUpdateDeliveryDTO requestUpdateDeliveryDTO, long id) {
        Delivery deliveryInDb = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery with id " + id + " not found"));

        requestUpdateDeliveryDTO.items()
                .forEach(i -> {
                    ProductOrder createdProductOrder = productOrderService.createProductOrder(i);
                    deliveryInDb.addProductOrder(createdProductOrder);
                });

        deliveryRepository.save(deliveryInDb);
        return toDetailedDTO(deliveryInDb);
    }
}
