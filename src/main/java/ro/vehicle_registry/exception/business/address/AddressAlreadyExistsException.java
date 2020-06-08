package ro.vehicle_registry.exception.business.address;

public class AddressAlreadyExistsException extends RuntimeException {

    public AddressAlreadyExistsException() {
        super("ADDRESS ALREADY EXISTS.");
    }
}
