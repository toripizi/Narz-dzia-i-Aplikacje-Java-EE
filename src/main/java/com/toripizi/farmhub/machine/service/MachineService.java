package com.toripizi.farmhub.machine.service;

import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.repository.MachineRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class MachineService {
    private final MachineRepository repository;

    @Inject
    public MachineService(MachineRepository repository) {
        this.repository = repository;
    }

    public List<Machine> findAll() {
        return repository.findAll();
    }

    public Optional<Machine> find(UUID id) {
        return repository.find(id);
    }

    public void create(Machine machine) {
        repository.create(machine);
    }

    public void update(Machine machine) {
        repository.update(machine);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find machine of id: " + id.toString())
        ));
    }

    public void deleteByCategoryId(UUID id) {
        repository.deleteByCategoryId(id);
    }

    public List<Machine> findAllByCategoryId(UUID id) {
        return repository.findAllByCategoryId(id);
    }
}
