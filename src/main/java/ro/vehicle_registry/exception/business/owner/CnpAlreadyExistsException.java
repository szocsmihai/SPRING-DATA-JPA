package ro.vehicle_registry.exception.business.owner;

public class CnpAlreadyExistsException extends RuntimeException {

    public CnpAlreadyExistsException() {
        super("CNP ALREADY EXISTS.");
    }
}
