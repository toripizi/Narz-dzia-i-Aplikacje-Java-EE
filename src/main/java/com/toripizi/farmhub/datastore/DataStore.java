package com.toripizi.farmhub.datastore;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.serialization.CloningUtility;
import com.toripizi.farmhub.farmer.entity.Farmer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Set<Farmer> farmers = new HashSet<>();
    private final Set<Category> categories = new HashSet<>();
    private final Set<Machine> machinery = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /**
     * Find all utility for entities.
     * @return list of entities: categories, machinery or farmers
     */
    public synchronized List<Category> findAllCategories() {
        return categories.stream().map(cloningUtility::clone).collect(Collectors.toList());
    }
    public synchronized List<Machine> findAllMachinery() {
        return machinery.stream().map(cloningUtility::clone).collect(Collectors.toList());
    }
    public synchronized List<Farmer> findAllFarmers() {
        return farmers.stream().map(cloningUtility::clone).collect(Collectors.toList());
    }

    /**
     * Create operations for entities.
     * @param entity to add
     * @throws IllegalArgumentException entity's id must be unique
     */
    public synchronized void createCategory(Category entity) throws IllegalArgumentException {
        if (categories.stream().anyMatch(category -> category.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException(String.format("Adding category %s failed. There already exists a category with this id.", entity.getId()));
        }
        categories.add(cloningUtility.clone(entity));
    }

    public synchronized void createMachine(Machine entity) throws IllegalArgumentException {
        if (machinery.stream().anyMatch(machine -> machine.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException(String.format("Adding machine %s failed. There already exists a machine with this id.", entity.getId()));
        }
        machinery.add(cloningUtility.clone(entity));
    }

    public synchronized void createFarmer(Farmer entity) throws IllegalArgumentException {
        if (farmers.stream().anyMatch(farmer -> farmer.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException(String.format("Adding farmer %s failed. There already exists a farmer with this id.", entity.getId()));
        }
        farmers.add(cloningUtility.clone(entity));
    }

    /**
     * Update operation for entities.
     * @param entity to be updated
     * @throws IllegalArgumentException if entity does not exist in memory
     */
    public synchronized void updateCategory(Category entity) throws IllegalArgumentException {
        if (categories.removeIf(category -> category.getId().equals(entity.getId()))) {
            categories.add(entity);
        } else {
            throw new IllegalArgumentException(String.format("The category with id \"%s\" does not exist.", entity.getId()));
        }
    }
    public synchronized void updateMachine(Machine entity) throws IllegalArgumentException {
        if (machinery.removeIf(machine -> machine.getId().equals(entity.getId()))) {
            machinery.add(entity);
        } else {
            throw new IllegalArgumentException(String.format("The machine with id \"%s\" does not exist.", entity.getId()));
        }
    }

    public synchronized void updateFarmer(Farmer entity) throws IllegalArgumentException {
        if (farmers.removeIf(farmer -> farmer.getId().equals(entity.getId()))) {
            farmers.add(entity);
        } else {
            throw new IllegalArgumentException(String.format("The farmer with id \"%s\" does not exist.", entity.getId()));
        }
    }

    /**
     * Delete operation for entities.
     * @param id of the object to delete
     * @throws IllegalArgumentException if id does not exist in memory
     */
    public synchronized void deleteCategory(UUID id) throws IllegalArgumentException {
        if (!categories.removeIf(category -> category.getId().equals(id))) {
            throw new IllegalArgumentException(String.format("The category with id \"%s\" does not exist.", id));
        }
    }
    public synchronized void deleteMachine(UUID id) throws IllegalArgumentException {
        if (!machinery.removeIf(machine -> machine.getId().equals(id))) {
            throw new IllegalArgumentException(String.format("The machine with id \"%s\" does not exist.", id));
        }
    }
    public synchronized void deleteFarmer(UUID id) throws IllegalArgumentException {
        if (!farmers.removeIf(farmer -> farmer.getId().equals(id))) {
            throw new IllegalArgumentException(String.format("The farmer with id \"%s\" does not exist.", id));
        }
    }
}
