package dk.kea.dagligvare.van;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VanRepository extends JpaRepository<Van, Long> {
}
