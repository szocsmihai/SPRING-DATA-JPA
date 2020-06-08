package ro.vehicle_registry.exception.business.owner;

public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException() {
        super("OWNER NOT FOUND.");
    }
}
