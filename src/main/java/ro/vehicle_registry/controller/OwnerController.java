package ro.vehicle_registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.vehicle_registry.exception.business.owner.CnpAlreadyExistsException;
import ro.vehicle_registry.exception.business.owner.OwnerNotFoundException;
import ro.vehicle_registry.model.entity.Owner;
import ro.vehicle_registry.repository.OwnerRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping
    public Owner save(@RequestBody @NotNull Owner owner) {
        if (!ownerRepository.existsByCnp(owner.getCnp())) {
            return ownerRepository.save(owner);
        } else {
            throw new CnpAlreadyExistsException();
        }
    }

    @GetMapping("/all")
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Owner getById(@PathVariable int id) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            return optionalOwner.get();
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @GetMapping("/cnp/{cnp}")
    public Owner getByCnp(@PathVariable long cnp) {
        Optional<Owner> optionalOwner = ownerRepository.findByCnp(cnp);
        if (optionalOwner.isPresent()) {
            return optionalOwner.get();
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @PutMapping("/id/{id}")
    public Owner updateById(@RequestBody @NotNull Owner newOwner, @PathVariable int id) {
        if (ownerRepository.existsById(id)) {
            Owner owner = ownerRepository.getById(id);
            updateCnp(owner, newOwner);
            owner.setFirstName(newOwner.getFirstName());
            owner.setLastName(newOwner.getLastName());
            return ownerRepository.save(owner);
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @PutMapping("/cnp/{cnp}")
    public Owner updateByCnp(@RequestBody @NotNull Owner newOwner, @PathVariable(value = "cnp") long currentCnp) {
        if (ownerRepository.existsByCnp(currentCnp)) {
            Owner owner = ownerRepository.getByCnp(currentCnp);
            updateCnp(owner, newOwner);
            owner.setFirstName(newOwner.getFirstName());
            owner.setLastName(newOwner.getLastName());
            return ownerRepository.save(owner);
        } else {
            throw new OwnerNotFoundException();
        }
    }

    // TODO: @PutMapping to add vehicles to owner.

    @DeleteMapping("/id/{id}")
    public Owner deleteById(@PathVariable int id) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            ownerRepository.delete(optionalOwner.get());
            return optionalOwner.get();
        } else {
            throw new OwnerNotFoundException();
        }
    }

    @DeleteMapping("/cnp/{cnp}")
    public Owner deleteByCnp(@PathVariable long cnp) {
        Optional<Owner> optionalOwner = ownerRepository.findByCnp(cnp);
        if (optionalOwner.isPresent()) {
            ownerRepository.delete(optionalOwner.get());
            return optionalOwner.get();
        } else {
            throw new OwnerNotFoundException();
        }
    }

    private boolean cnpHasChanged(Owner currentOwner, Owner newOwner) {
        return currentOwner.getCnp() != newOwner.getCnp();
    }

    private void updateCnp(Owner currentOwner, Owner newOwner) {
        if (cnpHasChanged(currentOwner, newOwner)) {
            if (!ownerRepository.existsByCnp(newOwner.getCnp())) {
                currentOwner.setCnp(newOwner.getCnp());
            } else {
                throw new CnpAlreadyExistsException();
            }
        }
    }
}
