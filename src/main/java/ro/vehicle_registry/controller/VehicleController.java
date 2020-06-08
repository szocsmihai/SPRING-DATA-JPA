package ro.vehicle_registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.vehicle_registry.exception.business.owner.OwnerNotFoundException;
import ro.vehicle_registry.exception.business.vehicle.LicensePlateAlreadyExistsException;
import ro.vehicle_registry.exception.business.vehicle.VehicleAlreadyExistsException;
import ro.vehicle_registry.exception.business.vehicle.VehicleNotFoundException;
import ro.vehicle_registry.exception.business.vehicle.VinAlreadyExistsException;
import ro.vehicle_registry.model.entity.Owner;
import ro.vehicle_registry.model.entity.Vehicle;
import ro.vehicle_registry.repository.OwnerRepository;
import ro.vehicle_registry.repository.VehicleRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping("/owner-id/{ownerId}")
    public Vehicle saveByOwnerId(@RequestBody @NotNull Vehicle vehicle, @PathVariable @NotNull int ownerId) {
        if (!vehicleRepository.existsById(vehicle.getId())) {
            if (!vehicleRepository.existsByVin(vehicle.getVin())) {
                if (!vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
                    Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
                    if (optionalOwner.isPresent()) {
                        Owner owner = optionalOwner.get();
                        Vehicle savedVehicle = vehicleRepository.save(vehicle);
                        owner.addVehicle(savedVehicle);
                        ownerRepository.save(owner);
                        return savedVehicle;
                    } else {
                        throw new OwnerNotFoundException();
                    }
                } else {
                    throw new LicensePlateAlreadyExistsException();
                }
            } else {
                throw new VinAlreadyExistsException();
            }
        } else {
            throw new VehicleAlreadyExistsException();
        }
    }

    @PostMapping("/owner-cnp/{ownerCnp}")
    public Vehicle saveByOwnerCnp(@RequestBody @NotNull Vehicle vehicle, @PathVariable @NotNull long ownerCnp) {
        if (!vehicleRepository.existsById(vehicle.getId())) {
            if (!vehicleRepository.existsByVin(vehicle.getVin())) {
                if (!vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
                    Optional<Owner> optionalOwner = ownerRepository.findByCnp(ownerCnp);
                    if (optionalOwner.isPresent()) {
                        Owner owner = optionalOwner.get();
                        Vehicle savedVehicle = vehicleRepository.save(vehicle);
                        owner.addVehicle(savedVehicle);
                        ownerRepository.save(owner);
                        return savedVehicle;
                    } else {
                        throw new OwnerNotFoundException();
                    }
                } else {
                    throw new LicensePlateAlreadyExistsException();
                }
            } else {
                throw new VinAlreadyExistsException();
            }
        } else {
            throw new VehicleAlreadyExistsException();
        }
    }

    @GetMapping("/all")
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Vehicle getById(@PathVariable int id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            return optionalVehicle.get();
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @GetMapping("/vin/{vin}")
    public Vehicle getByVin(@PathVariable String vin) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
        if (optionalVehicle.isPresent()) {
            return optionalVehicle.get();
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @GetMapping("/owner-id/{ownerId}")
    public Set<Vehicle> getByOwnerId(@PathVariable int ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            Set<Vehicle> vehicles = owner.getVehicles();
            if (!vehicles.isEmpty()) {
                return vehicles;
            } else {
                throw new VehicleNotFoundException();
            }
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @GetMapping("/owner-cnp/{ownerCnp}")
    public Set<Vehicle> getByOwnerCnp(@PathVariable long ownerCnp) {
        Optional<Owner> optionalOwner = ownerRepository.findByCnp(ownerCnp);
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            Set<Vehicle> vehicles = owner.getVehicles();
            if (!vehicles.isEmpty()) {
                return vehicles;
            } else {
                throw new VehicleNotFoundException();
            }
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @PutMapping
    public Vehicle update(@RequestBody @NotNull Vehicle newVehicle) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(newVehicle.getId());
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            updateVehicle(vehicle, newVehicle);
            return vehicleRepository.save(vehicle);
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @PutMapping("/id/{id}")
    public Vehicle updateById(@RequestBody @NotNull Vehicle newVehicle, @PathVariable int id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            updateVehicle(vehicle, newVehicle);
            return vehicleRepository.save(vehicle);
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @PutMapping("/vin/{vin}")
    public Vehicle updateById(@RequestBody @NotNull Vehicle newVehicle, @PathVariable String vin) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            updateVehicle(vehicle, newVehicle);
            return vehicleRepository.save(vehicle);
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @DeleteMapping
    public Vehicle delete(@RequestBody Vehicle vehicle) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicle.getId());
        if (optionalVehicle.isPresent()) {
            vehicleRepository.delete(optionalVehicle.get());
            return optionalVehicle.get();
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @DeleteMapping("/id/{id}")
    public Vehicle deleteById(@PathVariable int id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            vehicleRepository.delete(optionalVehicle.get());
            return optionalVehicle.get();
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @DeleteMapping("/vin/{vin}")
    public Vehicle deleteByVin(@PathVariable String vin) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
        if (optionalVehicle.isPresent()) {
            vehicleRepository.delete(optionalVehicle.get());
            return optionalVehicle.get();
        } else {
            throw new VehicleNotFoundException();
        }
    }

    @DeleteMapping("/owner-id/{ownerId}")
    public Set<Vehicle> deleteByOwnerId(@PathVariable int ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            Set<Vehicle> vehicles = owner.getVehicles();
            for(Vehicle vehicle : vehicles) {
                vehicleRepository.delete(vehicle);
            }
            return vehicles;
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @DeleteMapping("/owner-cnp/{ownerCnp}")
    public Set<Vehicle> deleteByOwnerId(@PathVariable long ownerCnp) {
        Optional<Owner> optionalOwner = ownerRepository.findByCnp(ownerCnp);
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            Set<Vehicle> vehicles = owner.getVehicles();
            for(Vehicle vehicle : vehicles) {
                vehicleRepository.delete(vehicle);
            }
            return vehicles;
        } else {
            throw new OwnerNotFoundException();
        }
    }

    private boolean vinHasChanged(Vehicle currentVehicle, Vehicle newVehicle) {
        return !currentVehicle.getVin().equals(newVehicle.getVin());
    }

    private boolean licensePlateHasChanged(Vehicle currentVehicle, Vehicle newVehicle) {
        return !currentVehicle.getLicensePlate().equals(newVehicle.getLicensePlate());
    }

    private void updateVin(Vehicle currentVehicle, Vehicle newVehicle) {
        if (vinHasChanged(currentVehicle, newVehicle)) {
            if (!vehicleRepository.existsByVin(newVehicle.getVin())) {
                currentVehicle.setVin(newVehicle.getVin());
            } else {
                throw new VinAlreadyExistsException();
            }
        }
    }

    private void updateLicensePlate(Vehicle currentVehicle, Vehicle newVehicle) {
        if (licensePlateHasChanged(currentVehicle, newVehicle)) {
            if (!vehicleRepository.existsByLicensePlate(newVehicle.getLicensePlate())) {
                currentVehicle.setLicensePlate(newVehicle.getLicensePlate());
            } else {
                throw new LicensePlateAlreadyExistsException();
            }
        }
    }

    private void updateVehicle(Vehicle currentVehicle, Vehicle newVehicle) {
        updateVin(currentVehicle, newVehicle);
        currentVehicle.setYear(newVehicle.getYear());
        currentVehicle.setMake(newVehicle.getMake());
        currentVehicle.setModel(newVehicle.getModel());
        updateLicensePlate(currentVehicle, newVehicle);
    }
}
