package com.toripizi.farmhub.machine.repository;


import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.machine.entity.Machine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MachineRepository {

    List<Machine> findAll();

    Optional<Machine> find(UUID id);

    void create(Machine machine);

    void update(Machine machine);

    void delete(Machine machine);

    List<Machine> findAllByCategory(Category category);
}
