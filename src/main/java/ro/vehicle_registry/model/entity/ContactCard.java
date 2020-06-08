package ro.vehicle_registry.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "contact_cards")
public class ContactCard {

    // START OF DATABASE MODEL -----------------------------------------------------------------------------------------

    @Id
    @Column(updatable = false)
    private int id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNo;

    @OneToOne(cascade = CascadeType.DETACH)
    @MapsId
    private Owner owner; // TODO: Only get actual fields, not entity.

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // END OF DATABASE MODEL -------------------------------------------------------------------------------------------

    public ContactCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
