package com.toripizi.farmhub.machine.service;

import com.toripizi.farmhub.category.repository.CategoryRepository;
import com.toripizi.farmhub.farmer.repository.FarmerRepository;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.repository.MachineRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class MachineService {

    private final MachineRepository machineRepository;
    private final CategoryRepository categoryRepository;
    private final FarmerRepository farmerRepository;

    @Inject
    public MachineService(MachineRepository machineRepository, CategoryRepository categoryRepository, FarmerRepository farmerRepository) {
        this.machineRepository = machineRepository;
        this.categoryRepository = categoryRepository;
        this.farmerRepository = farmerRepository;
    }

    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    public Optional<List<Machine>> findAllByCategoryId(UUID id) {
        return categoryRepository.find(id).map(machineRepository::findAllByCategory);
    }

    public Optional<Machine> find(UUID id) {
        return machineRepository.find(id);
    }

    @Transactional
    public void create(Machine machine) {
        if (machineRepository.find(machine.getId()).isPresent()) {
            throw new BadRequestException("Machine of id: " + machine.getId() + " already exists");
        }
        if (categoryRepository.find(machine.getCategory().getId()).isEmpty()) {
            throw new NotFoundException("Category of id: " + machine.getCategory().getId() + " does not exists");
        }
        if (farmerRepository.find(machine.getFarmer().getId()).isEmpty()) {
            throw new NotFoundException("Farmer of id: " + machine.getFarmer().getId() + " does not exists");
        }
        machineRepository.create(machine);
    }

    @Transactional
    public void update(Machine machine) {
        machineRepository.update(machine);
    }

    @Transactional
    public void delete(UUID id) {
        machineRepository.delete(machineRepository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find machine of id: " + id.toString())
        ));
    }
}
