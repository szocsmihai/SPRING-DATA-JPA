package ro.vehicle_registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.vehicle_registry.exception.business.contact_card.ContactCardAlreadyExistsException;
import ro.vehicle_registry.exception.business.contact_card.ContactCardNotFoundException;
import ro.vehicle_registry.exception.business.contact_card.EmailAlreadyExistsException;
import ro.vehicle_registry.exception.business.contact_card.PhoneNoAlreadyExistsException;
import ro.vehicle_registry.exception.business.owner.OwnerNotFoundException;
import ro.vehicle_registry.model.entity.ContactCard;
import ro.vehicle_registry.model.entity.Owner;
import ro.vehicle_registry.repository.ContactCardRepository;
import ro.vehicle_registry.repository.OwnerRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact-cards")
public class ContactCardController {

    @Autowired
    private ContactCardRepository contactCardRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping
    public ContactCard save(@RequestBody @NotNull ContactCard contactCard) {
        Optional<Owner> optionalOwner = ownerRepository.findById(contactCard.getId());
        if (optionalOwner.isPresent()) {
            if (!contactCardRepository.existsById(contactCard.getId())) {
                if (!contactCardRepository.existsByEmail(contactCard.getEmail())) {
                    if (!contactCardRepository.existsByPhoneNo(contactCard.getPhoneNo())) {
                        contactCard.setOwner(optionalOwner.get());
                        return contactCardRepository.save(contactCard);
                    } else {
                        throw new PhoneNoAlreadyExistsException();
                    }
                } else {
                    throw new EmailAlreadyExistsException();
                }
            } else {
                throw new ContactCardAlreadyExistsException();
            }
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @PostMapping("/owner-id/{ownerId}")
    public ContactCard saveByOwnerId(@RequestBody @NotNull ContactCard contactCard, @PathVariable int ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            if (!contactCardRepository.existsById(ownerId)) {
                if (!contactCardRepository.existsByEmail(contactCard.getEmail())) {
                    if (!contactCardRepository.existsByPhoneNo(contactCard.getPhoneNo())) {
                        contactCard.setOwner(optionalOwner.get());
                        return contactCardRepository.save(contactCard);
                    } else {
                        throw new PhoneNoAlreadyExistsException();
                    }
                } else {
                    throw new EmailAlreadyExistsException();
                }
            } else {
                throw new ContactCardAlreadyExistsException();
            }
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @PostMapping("/owner-cnp/{ownerCnp}")
    public ContactCard saveByOwnerCnp(@RequestBody @NotNull ContactCard contactCard, @PathVariable long ownerCnp) {
        Optional<Owner> optionalOwner = ownerRepository.findByCnp(ownerCnp);
        if (optionalOwner.isPresent()) {
            if (!contactCardRepository.existsByOwnerCnp(ownerCnp)) {
                if (!contactCardRepository.existsByEmail(contactCard.getEmail())) {
                    if (!contactCardRepository.existsByPhoneNo(contactCard.getPhoneNo())) {
                        contactCard.setOwner(optionalOwner.get());
                        return contactCardRepository.save(contactCard);
                    } else {
                        throw new PhoneNoAlreadyExistsException();
                    }
                } else {
                    throw new EmailAlreadyExistsException();
                }
            } else {
                throw new ContactCardAlreadyExistsException();
            }
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @GetMapping("/all")
    public List<ContactCard> getAll() {
        return contactCardRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ContactCard getById(@PathVariable int id) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(id);
        if (optionalContactCard.isPresent()) {
            return optionalContactCard.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @GetMapping("/owner-cnp/{ownerCnp}")
    public ContactCard getByOwnerCnp(@PathVariable long ownerCnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(ownerCnp);
        if (optionalContactCard.isPresent()) {
            return optionalContactCard.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @PutMapping
    public ContactCard update(@RequestBody @NotNull ContactCard newContactCard) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(newContactCard.getId());
        if (optionalContactCard.isPresent()) {
            Optional<Owner> optionalOwner = ownerRepository.findById(newContactCard.getId());
            if (optionalOwner.isPresent()) {
                ContactCard contactCard = optionalContactCard.get();
                updateEmail(contactCard, newContactCard);
                updatePhoneNo(contactCard, newContactCard);
                contactCard.setOwner(optionalOwner.get());
                return contactCardRepository.save(contactCard);
            } else {
                throw new OwnerNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @PutMapping("/id/{id}")
    public ContactCard updateById(@RequestBody @NotNull ContactCard newContactCard, @PathVariable int id) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(id);
        if (optionalContactCard.isPresent()) {
            Optional<Owner> optionalOwner = ownerRepository.findById(id);
            if (optionalOwner.isPresent()) {
                ContactCard contactCard = optionalContactCard.get();
                updateEmail(contactCard, newContactCard);
                updatePhoneNo(contactCard, newContactCard);
                contactCard.setOwner(optionalOwner.get());
                return contactCardRepository.save(contactCard);
            } else {
                throw new OwnerNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @PutMapping("/owner-cnp/{cnp}")
    public ContactCard updateByOwnerCnp(@RequestBody @NotNull ContactCard newContactCard, @PathVariable long cnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(cnp);
        if (optionalContactCard.isPresent()) {
            Optional<Owner> optionalOwner = ownerRepository.findByCnp(cnp);
            if (optionalOwner.isPresent()) {
                ContactCard contactCard = optionalContactCard.get();
                updateEmail(contactCard, newContactCard);
                updatePhoneNo(contactCard, newContactCard);
                contactCard.setOwner(optionalOwner.get());
                return contactCardRepository.save(contactCard);
            } else {
                throw new OwnerNotFoundException();
            }
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @DeleteMapping
    public ContactCard delete(@RequestBody ContactCard contactCard) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(contactCard.getId());
        if (optionalContactCard.isPresent()) {
            contactCardRepository.delete(optionalContactCard.get());
            return optionalContactCard.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @DeleteMapping("/id/{id}")
    public ContactCard deleteById(@PathVariable int id) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findById(id);
        if (optionalContactCard.isPresent()) {
            contactCardRepository.delete(optionalContactCard.get());
            return optionalContactCard.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    @DeleteMapping("/owner-cnp/{cnp}")
    public ContactCard deleteByOwnerCnp(@PathVariable long cnp) {
        Optional<ContactCard> optionalContactCard = contactCardRepository.findByOwnerCnp(cnp);
        if (optionalContactCard.isPresent()) {
            contactCardRepository.delete(optionalContactCard.get());
            return optionalContactCard.get();
        } else {
            throw new ContactCardNotFoundException();
        }
    }

    private boolean emailHasChanged(ContactCard currentContactCard, ContactCard newContactCard) {
        return currentContactCard.getEmail().compareTo(newContactCard.getEmail()) != 0;
    }

    private boolean phoneNoHasChanged(ContactCard currentContactCard, ContactCard newContactCard) {
        return currentContactCard.getPhoneNo().compareTo(newContactCard.getPhoneNo()) != 0;
    }

    private void updateEmail(ContactCard currentContactCard, ContactCard newContactCard) {
        if (emailHasChanged(currentContactCard, newContactCard)) {
            if (!contactCardRepository.existsByEmail(newContactCard.getEmail())) {
                currentContactCard.setEmail(newContactCard.getEmail());
            } else {
                throw new EmailAlreadyExistsException();
            }
        }
    }

    private void updatePhoneNo(ContactCard currentContactCard, ContactCard newContactCard) {
        if (phoneNoHasChanged(currentContactCard, newContactCard)) {
            if (!contactCardRepository.existsByPhoneNo(newContactCard.getPhoneNo())) {
                currentContactCard.setPhoneNo(newContactCard.getPhoneNo());
            } else {
                throw new PhoneNoAlreadyExistsException();
            }
        }
    }
}
