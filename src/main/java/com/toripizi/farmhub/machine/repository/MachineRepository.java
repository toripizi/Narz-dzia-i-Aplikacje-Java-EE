package com.toripizi.farmhub.machine.repository;


import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MachineRepository {

    List<Machine> findAll();

    List<Machine> findAllByFarmer(Farmer farmer);

    List<Machine> findAllByCategory(Category category);

    List<Machine> findAllByCategoryAndFarmer(Category category, Farmer farmer);

    Optional<Machine> find(UUID machineId);

    Optional<Machine> findByIdAndFarmer(UUID machineId, Farmer farmer);

    void create(Machine machine);

    void update(Machine machine);

    void delete(Machine machine);
}
