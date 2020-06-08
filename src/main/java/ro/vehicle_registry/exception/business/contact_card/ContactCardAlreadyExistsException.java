package ro.vehicle_registry.exception.business.contact_card;

public class ContactCardAlreadyExistsException extends RuntimeException {

    public ContactCardAlreadyExistsException() {
        super("CONTACT INFO FOR SPECIFIED OWNER ALREADY EXISTS.");
    }
}
