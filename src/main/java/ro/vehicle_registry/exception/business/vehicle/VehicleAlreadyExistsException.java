package ro.vehicle_registry.exception.business.vehicle;

public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException() {
        super("VEHICLE ALREADY EXISTS.");
    }
}
