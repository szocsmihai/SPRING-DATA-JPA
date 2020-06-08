package ro.vehicle_registry.exception.business.vehicle;

public class VinAlreadyExistsException extends RuntimeException {

    public VinAlreadyExistsException() {
        super("VEHICLE WITH SPECIFIED VIN NUMBER ALREADY EXISTS.");
    }
}
