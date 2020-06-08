package ro.vehicle_registry.exception.business.address;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException() {
        super("OWNER ADDRESS NOT FOUND.");
    }
}
