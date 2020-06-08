package ro.vehicle_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.vehicle_registry.model.entity.ContactCard;

import java.util.Optional;

@Repository
public interface ContactCardRepository extends JpaRepository<ContactCard, Integer> {

    ContactCard getById(int id);

    Optional<ContactCard> findByOwnerCnp(long ownerCnp);

    boolean existsByEmail(String email);

    boolean existsByPhoneNo(String phoneNo);

    boolean existsByOwnerCnp(long cnp);
}
