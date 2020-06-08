package ro.vehicle_registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.vehicle_registry.exception.business.address.AddressAlreadyExistsException;
import ro.vehicle_registry.exception.business.address.AddressNotFoundException;
import ro.vehicle_registry.exception.business.contact_card.ContactCardNotFoundException;
import ro.vehicle_registry.model.entity.Address;
import ro.vehicle_registry.model.entity.ContactCard;
import ro.vehicle_registry.model.entity.Owner;
import ro.vehicle_registry.repository.AddressRepository;
import ro.vehicle_registry.repository.ContactCardRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactCardRepository contactCardRepository;

    @PostMapping("/contact-card-id/{contactCardId}")
    public Address saveByContactCardId(@RequestBody @NotNull Address address, @PathVariable int contactCardId) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(contactCardId);
        if (optionalContactCard.isPresent()) {
            if (addressDoesNotExistByDetails(address)) {
                Address savedAddress = addressRepository.save(address);
                ContactCard contactCard = optionalContactCard.get();
                contactCard.setAddress(savedAddress);
                contactCardRepository.save(contactCard);
                return savedAddress;
            } else {
                throw new AddressAlreadyExistsException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @PostMapping("/owner-cnp/{ownerCnp}")
    public Address saveByOwnerCnp(@RequestBody @NotNull Address address, @PathVariable long ownerCnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(ownerCnp);
        if (optionalContactCard.isPresent()) {
            if (addressDoesNotExistByDetails(address)) {
                Address savedAddress = addressRepository.save(address);
                ContactCard contactCard = optionalContactCard.get();
                contactCard.setAddress(savedAddress);
                contactCardRepository.save(contactCard);
                return savedAddress;
            } else {
                throw new AddressAlreadyExistsException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @GetMapping("/all")
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Address getById(@PathVariable int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            return optionalAddress.get();
        } else {
            throw new AddressNotFoundException();
        }
    }

    @GetMapping("/contact-card-id/{contactCardId}")
    public Address getByContactCardId(@PathVariable int contactCardId) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(contactCardId);
        if (optionalContactCard.isPresent()) {
            ContactCard contactCard = optionalContactCard.get();
            if (contactCard.getAddress() != null) {
                return contactCard.getAddress();
            } else {
                throw new AddressNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @GetMapping("/owner-cnp/{ownerCnp}")
    public Address getByOwnerCnp(@PathVariable long ownerCnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(ownerCnp);
        if (optionalContactCard.isPresent()) {
            ContactCard contactCard = optionalContactCard.get();
            if (contactCard.getAddress() != null) {
                return contactCard.getAddress();
            } else {
                throw new AddressNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @PutMapping
    public Address update(@RequestBody @NotNull Address newAddress) {
        Optional<Address> optionalAddress = addressRepository.findById(newAddress.getId());
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            updateDetails(address, newAddress);
            return addressRepository.save(address);
        } else {
            throw new AddressNotFoundException();
        }
    }

    @PutMapping("/id/{id}")
    public Address updateById(@RequestBody @NotNull Address newAddress, @PathVariable int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            updateDetails(address, newAddress);
            return addressRepository.save(address);
        } else {
            throw new AddressNotFoundException();
        }
    }

    @PutMapping("/contact-card-id/{id}")
    public Address updateByContactCardId(@RequestBody @NotNull Address newAddress, @PathVariable int id) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(id);
        if (optionalContactCard.isPresent()) {
            Address address = optionalContactCard.get().getAddress();
            if (address != null) {
                updateDetails(address, newAddress);
                return addressRepository.save(address);
            } else {
                throw new AddressNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @PutMapping("/owner-cnp/{cnp}")
    public Address updateByOwnerCnp(@RequestBody @NotNull Address newAddress, @PathVariable long cnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(cnp);
        if (optionalContactCard.isPresent()) {
            Address address = optionalContactCard.get().getAddress();
            if (address != null) {
                updateDetails(address, newAddress);
                return addressRepository.save(address);
            } else {
                throw new AddressNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @DeleteMapping
    public Address delete(@RequestBody Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(address.getId());
        if (optionalAddress.isPresent()) {
            addressRepository.delete(optionalAddress.get());
            return optionalAddress.get();
        } else {
            throw new AddressNotFoundException();
        }
    }

    @DeleteMapping("/id/{id}")
    public Address deleteById(@PathVariable int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.delete(optionalAddress.get());
            return optionalAddress.get();
        } else {
            throw new AddressNotFoundException();
        }
    }

    @DeleteMapping("/contact-card-id/{id}")
    public Address deleteByContactCardId(@PathVariable int id) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(id);
        if (optionalContactCard.isPresent()) {
            ContactCard contactCard = optionalContactCard.get();
            Optional<Address> optionalAddress = addressRepository.findById(contactCard.getAddress().getId());
            if (optionalAddress.isPresent()) {
                addressRepository.delete(optionalAddress.get());
            } else {
                throw new AddressNotFoundException();
            }
            return optionalAddress.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @DeleteMapping("/owner-cnp/{cnp}")
    public Address deleteByOwnerCnp(@PathVariable long cnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(cnp);
        if (optionalContactCard.isPresent()) {
            ContactCard contactCard = optionalContactCard.get();
            Optional<Address> optionalAddress = addressRepository.findById(contactCard.getAddress().getId());
            if (optionalAddress.isPresent()) {
                addressRepository.delete(optionalAddress.get());
            } else {
                throw new AddressNotFoundException();
            }
            return optionalAddress.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    private boolean addressDoesNotExistByDetails(Address address) {
        return !addressRepository.existsByCountryAndCountyAndCityAndStreetNameAndStreetNoAndAptNo(
                address.getCountry(),
                address.getCounty(),
                address.getCity(),
                address.getStreetName(),
                address.getStreetNo(),
                address.getAptNo()
        );
    }

    private boolean detailsHaveChanged(Address currentAddress, Address newAddress) {
        return !((currentAddress.getCountry().compareTo(newAddress.getCountry()) == 0) &&
                (currentAddress.getCounty().compareTo(newAddress.getCounty()) == 0) &&
                (currentAddress.getCity().compareTo(newAddress.getCity()) == 0) &&
                (currentAddress.getStreetName().compareTo(newAddress.getStreetName()) == 0) &&
                (currentAddress.getStreetNo().compareTo(newAddress.getStreetNo()) == 0) &&
                (currentAddress.getAptNo().compareTo(newAddress.getAptNo()) == 0));
    }

    private void updateDetails(Address currentAddress, Address newAddress) {
        if (detailsHaveChanged(currentAddress, newAddress)) {
            currentAddress.setCountry(newAddress.getCountry());
            currentAddress.setCounty(newAddress.getCounty());
            currentAddress.setCity(newAddress.getCity());
            currentAddress.setStreetName(newAddress.getStreetName());
            currentAddress.setStreetNo(newAddress.getStreetNo());
            currentAddress.setAptNo(newAddress.getAptNo());
        }
    }
}
