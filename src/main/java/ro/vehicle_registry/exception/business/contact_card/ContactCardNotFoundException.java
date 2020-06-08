package ro.vehicle_registry.exception.business.contact_card;

public class ContactCardNotFoundException extends RuntimeException {

    public ContactCardNotFoundException() {
        super("CONTACT INFO FOR SPECIFIED OWNER NOT FOUND.");
    }
}
