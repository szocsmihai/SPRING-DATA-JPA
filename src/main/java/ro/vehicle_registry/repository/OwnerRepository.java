package ro.vehicle_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.vehicle_registry.model.entity.Owner;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Owner getById(int id);

    Optional<Owner> findByCnp(long cnp);

    Owner getByCnp(long cnp);

    boolean existsByCnp(long cnp);
}
