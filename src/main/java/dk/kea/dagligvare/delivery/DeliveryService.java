package dk.kea.dagligvare.delivery;


import dk.kea.dagligvare.product.ProductRepository;
import dk.kea.dagligvare.productorder.ProductOrder;
import dk.kea.dagligvare.productorder.ProductOrderService;
import dk.kea.dagligvare.productorder.RequestProductOrderDTO;
import dk.kea.dagligvare.van.Van;
import dk.kea.dagligvare.van.VanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {


    private final DeliveryRepository deliveryRepository;
    private final ProductOrderService productOrderService;
    private final VanRepository vanRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductOrderService productOrderService, ProductRepository productRepository,
                           VanRepository vanRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productOrderService = productOrderService;
        this.vanRepository = vanRepository;
    }

    public List<ResponseDeliveryDTO> getAllDeliveries() {
        var deliveries =  deliveryRepository.findAll();

        List<ResponseDeliveryDTO> deliveryDTOS = new ArrayList<>();

        for (Delivery delivery: deliveries) {
            Optional<Van> van = vanRepository.findByDeliveries_Id(delivery.getId());
            if (van.isPresent()) {
                deliveryDTOS.add(toDTO(delivery, van.get().getId()));
            } else {
                deliveryDTOS.add(toDTO(delivery, null));
            }
        }

        return deliveryDTOS;
    }

    public ResponseDetailedDeliveryDTO getSingleDeliveryById(long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery with id " + id + "not found"));

        Optional<Van> van = vanRepository.findByDeliveries_Id(delivery.getId());

        return toDetailedDTO(delivery, van.isPresent() ? van.get().getId() : null);
    }

    public double getTotalDeliveryWeightInGrams(Delivery delivery) {
        double sumInGrams = 0;

        for (ProductOrder productOrder : delivery.getProductOrders()) {
            sumInGrams += productOrder.getTotalWeightInGrams();
        }

        return sumInGrams;
    }

    public ResponseDetailedDeliveryDTO toDetailedDTO(Delivery delivery, Long vanId){
        return new ResponseDetailedDeliveryDTO(
                delivery.getId(),
                delivery.getDeliveryDate(),
                delivery.getFromWarehouse(),
                delivery.getDestination(),
                delivery.getProductOrders().isEmpty() ? new ArrayList<>() : delivery.getProductOrders().stream().map(productOrderService::toDTO).toList(),
                vanId
        );
    }

    public Delivery createDelivery(RequestCreateDeliveryDTO requestCreateDeliveryDTO) {
        Delivery delivery = toEntity(requestCreateDeliveryDTO);
        return deliveryRepository.save(delivery);
    }

    public ResponseDeliveryDTO toDTO(Delivery delivery, Long vanId ){
        return new ResponseDeliveryDTO(
                delivery.getId(),
                delivery.getDeliveryDate(),
                delivery.getFromWarehouse(),
                delivery.getDestination(),
                vanId
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
    public ResponseDetailedDeliveryDTO updateDeliveryById(List<RequestProductOrderDTO> requestUpdateDeliveryDTO, long id) {
        Delivery deliveryInDb = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery with id " + id + " not found"));

        requestUpdateDeliveryDTO
                .forEach(i -> {
                    ProductOrder createdProductOrder = productOrderService.createProductOrder(i);
                    deliveryInDb.addProductOrder(createdProductOrder);
                });

        deliveryRepository.save(deliveryInDb);

        Optional<Van> van = vanRepository.findByDeliveries_Id(deliveryInDb.getId());

        return toDetailedDTO(deliveryInDb, van.isPresent() ? van.get().getId() : null);
    }
}
