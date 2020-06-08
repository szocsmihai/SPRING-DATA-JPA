package ro.vehicle_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.vehicle_registry.model.entity.Vehicle;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle getById(int id);

    Optional<Vehicle> findByVin(String vin);

    boolean existsByVin(String vin);

    boolean existsByLicensePlate(String licensePlate);
}
