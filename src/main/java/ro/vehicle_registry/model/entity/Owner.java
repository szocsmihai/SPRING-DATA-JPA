package ro.vehicle_registry.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "owners")
public class Owner {

    // START OF DATABASE MODEL -----------------------------------------------------------------------------------------

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "cnp", nullable = false, unique = true)
    private long cnp;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;

    // END OF DATABASE MODEL -------------------------------------------------------------------------------------------

    public Owner() {
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
