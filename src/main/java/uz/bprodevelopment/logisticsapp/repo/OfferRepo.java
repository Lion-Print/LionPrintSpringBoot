package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Offer;

import java.util.List;


public interface OfferRepo extends JpaRepository<Offer, Long>,
        JpaSpecificationExecutor<Offer> {

    List<Offer> findAllByStatusAndUserId(int status, Long userId);

    List<Offer> findAllByStatusAndCargoId(int status, Long cargoId);
}
