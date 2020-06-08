package ro.vehicle_registry.exception.business.vehicle;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException() {
        super("VEHICLE(S) NOT FOUND.");
    }
}
