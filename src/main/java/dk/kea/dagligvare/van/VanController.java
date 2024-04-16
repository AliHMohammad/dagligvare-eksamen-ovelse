package dk.kea.dagligvare.van;

import dk.kea.dagligvare.delivery.ResponseDeliveryDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/vans")
public class VanController {


    private final VanService vanService;

    public VanController(VanService vanService) {
        this.vanService = vanService;
    }


    @GetMapping("/{id}/deliveries")
    public ResponseEntity<List<ResponseDeliveryDTO>> getAllDeliveriesByVanId(@PathVariable("id") long id) {
        return ResponseEntity.ok(vanService.getAllDeliveriesByVanId(id));
    }


    @PatchMapping("/{vanId}/deliveries/{deliveryId}")
    public ResponseEntity<ResponseVanDTO> assignDeliveryToVanById(@PathVariable("vanId") long vanId, @PathVariable("deliveryId") long deliveryId) throws BadRequestException {
        return ResponseEntity.ok(vanService.assignDeliveryToVanById(vanId, deliveryId));
    }

    @GetMapping
    public ResponseEntity<List<ResponseVanDTO>> getAllVans() {
        return ResponseEntity.ok(vanService.getAllVans());
    }

    @GetMapping("/{id}/weight")
    public ResponseEntity<Integer> getCurrentWeightForVanById(@PathVariable("id") long id) {
        return ResponseEntity.ok(vanService.getCurrentWeightForVanById(id));
    }
}
