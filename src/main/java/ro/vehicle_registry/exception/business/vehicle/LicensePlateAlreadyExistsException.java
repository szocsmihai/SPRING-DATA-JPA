package ro.vehicle_registry.exception.business.vehicle;

public class LicensePlateAlreadyExistsException extends RuntimeException {

    public LicensePlateAlreadyExistsException() {
        super("VEHICLE LICENSE PLATE ALREADY EXISTS.");
    }
}
