package ro.vehicle_registry.exception.business.contact_card;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("EMAIL ALREADY EXISTS.");
    }
}
