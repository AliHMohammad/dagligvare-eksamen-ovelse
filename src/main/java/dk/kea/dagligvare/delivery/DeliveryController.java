package dk.kea.dagligvare.delivery;

import dk.kea.dagligvare.productorder.RequestProductOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/deliveries")
public class DeliveryController {


    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseDeliveryDTO>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDetailedDeliveryDTO> getSingleDeliveryById(@PathVariable("id") long id){
        return ResponseEntity.ok(deliveryService.getSingleDeliveryById(id));
    }

    @PostMapping
    public ResponseEntity<Delivery> CreateDelivery(@RequestBody RequestCreateDeliveryDTO requestCreateDeliveryDTO) {
        Delivery delivery = deliveryService.createDelivery(requestCreateDeliveryDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(delivery.getId())
                .toUri();

        return ResponseEntity.created(location).body(delivery);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDetailedDeliveryDTO> updateDeliveryById(@RequestBody List<RequestProductOrderDTO> requestUpdateDeliveryDTO, @PathVariable("id") long id) {
        return ResponseEntity.ok(deliveryService.updateDeliveryById(requestUpdateDeliveryDTO, id));
    }


}
