package ro.vehicle_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.vehicle_registry.model.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address getById(int id);

    boolean existsByCountryAndCountyAndCityAndStreetNameAndStreetNoAndAptNo(
            String country, String county, String city, String streetName, String streetNo, String aptNo
    );

    Address getByCountryAndCountyAndCityAndStreetNameAndStreetNoAndAptNo(
            String country, String county, String city, String streetName, String streetNo, String aptNo
    );
}
