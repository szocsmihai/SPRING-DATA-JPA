package ro.vehicle_registry.exception.business.contact_card;

public class PhoneNoAlreadyExistsException extends RuntimeException {

    public PhoneNoAlreadyExistsException() {
        super("PHONE NUMBER ALREADY EXISTS.");
    }
}
