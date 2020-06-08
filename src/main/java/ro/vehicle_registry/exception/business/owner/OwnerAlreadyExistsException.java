package ro.vehicle_registry.exception.business.owner;

public class OwnerAlreadyExistsException extends RuntimeException {

    public OwnerAlreadyExistsException() {
        super("OWNER ALREADY EXISTS.");
    }
}
