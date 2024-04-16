package dk.kea.dagligvare.van;

import dk.kea.dagligvare.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface VanRepository extends JpaRepository<Van, Long> {


    Optional<Van> findByDeliveries_Id(long deliveryId);
}
