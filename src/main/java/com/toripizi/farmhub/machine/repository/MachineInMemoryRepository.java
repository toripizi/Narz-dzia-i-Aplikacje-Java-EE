package com.toripizi.farmhub.machine.repository;

import com.toripizi.farmhub.datastore.DataStore;
import com.toripizi.farmhub.machine.entity.Machine;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class MachineInMemoryRepository implements MachineRepository {
    private final DataStore dataStore;

    @Inject
    public MachineInMemoryRepository(DataStore dataStore){
        this.dataStore = dataStore;
    }

    @Override
    public List<Machine> findAll() {
        return dataStore.findAllMachinery();
    }

    @Override
    public Optional<Machine> find(UUID id) {
        return dataStore.findAllMachinery().stream()
                .filter(machine -> machine.getId().equals(id))
                .findFirst();
    }

    @Override
    public void create(Machine machine) {
        dataStore.createMachine(machine);
    }

    @Override
    public void update(Machine machine) {
        dataStore.updateMachine(machine);
    }

    @Override
    public void delete(Machine machine) {
        dataStore.deleteMachine(machine.getId());
    }

    @Override
    public List<Machine> findAllByCategoryId(UUID id) {
        return dataStore.findAllMachinery().stream()
                .filter(machine -> machine.getCategory().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByCategoryId(UUID id) {
        this.findAllByCategoryId(id).forEach(machine -> dataStore.deleteMachine(machine.getId()));
    }
}
