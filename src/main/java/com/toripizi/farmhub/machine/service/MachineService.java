package com.toripizi.farmhub.machine.service;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.repository.CategoryRepository;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.FarmerRoles;
import com.toripizi.farmhub.farmer.repository.FarmerRepository;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.repository.MachineRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MachineService {

    private final MachineRepository machineRepository;
    private final CategoryRepository categoryRepository;
    private final FarmerRepository farmerRepository;

    private final SecurityContext securityContext;

    @Inject
    public MachineService(MachineRepository machineRepository, CategoryRepository categoryRepository, FarmerRepository farmerRepository, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.machineRepository = machineRepository;
        this.categoryRepository = categoryRepository;
        this.farmerRepository = farmerRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(FarmerRoles.ADMIN)
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    @RolesAllowed(FarmerRoles.USER)
    public List<Machine> findAll(Farmer farmer) {
        return machineRepository.findAllByFarmer(farmer);
    }

    @RolesAllowed(FarmerRoles.ADMIN)
    public Optional<List<Machine>> findAllByCategoryId(UUID id) {
        return categoryRepository.find(id).map(machineRepository::findAllByCategory);
    }

    @RolesAllowed(FarmerRoles.ADMIN)
    public Optional<Machine> find(UUID id) {
        return machineRepository.find(id);
    }

    @RolesAllowed(FarmerRoles.USER)
    public Optional<Machine> find(Farmer farmer, UUID machineId) {
        return machineRepository.findByIdAndFarmer(machineId, farmer);
    }

    @RolesAllowed(FarmerRoles.USER)
    public Optional<Machine> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(FarmerRoles.ADMIN)) {
            return find(id);
        } else {
            Farmer farmer = farmerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(IllegalStateException::new);
            return find(farmer, id);
        }
    }

    @RolesAllowed(FarmerRoles.USER)
    public List<Machine> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(FarmerRoles.ADMIN)) {
            return findAll();
        } else {
            Farmer farmer = farmerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(IllegalStateException::new);
            return findAll(farmer);
        }
    }

    @RolesAllowed(FarmerRoles.USER)
    public List<Machine> findAllByCategoryForCallerPrincipal(UUID categoryId) {
        if (securityContext.isCallerInRole(FarmerRoles.ADMIN)) {
            return findAllByCategoryId(categoryId).orElseThrow();
        } else {
            Farmer farmer = farmerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(IllegalStateException::new);
            return findAllByCategoryIdAndFarmer(categoryId, farmer);
        }
    }

    @RolesAllowed(FarmerRoles.USER)
    public List<Machine> findAllByCategoryIdAndFarmer(UUID categoryId, Farmer farmer) {
        Optional<Category> category = categoryRepository.find(categoryId);
        if (category.isPresent()) {
            return machineRepository.findAllByCategoryAndFarmer(category.get(), farmer);
        } else {
            return List.of();
        }
    }

    @RolesAllowed(FarmerRoles.USER)
    public void createForCallerPrincipal(Machine machine) {
        if (machineRepository.gitfind(machine.getId()).isPresent()) {
            throw new BadRequestException("Machine of id: " + machine.getId() + " already exists");
        }
        if (categoryRepository.find(machine.getCategory().getId()).isEmpty()) {
            throw new NotFoundException("Category of id: " + machine.getCategory().getId() + " does not exists");
        }
        Farmer farmer = farmerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        machine.setFarmer(farmer);
        machineRepository.create(machine);
    }

    @RolesAllowed(FarmerRoles.USER)
    public void create(Machine machine) {
        machineRepository.create(machine);
    }

    @RolesAllowed(FarmerRoles.USER)
    public void update(Machine machine) {
        checkAdminRoleOrOwner(machineRepository.find(machine.getId()));
        machineRepository.update(machine);
    }

    @RolesAllowed(FarmerRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(machineRepository.find(id));
        machineRepository.delete(machineRepository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find machine of id: " + id.toString())
        ));
    }

    private void checkAdminRoleOrOwner(Optional<Machine> machine) throws EJBAccessException {
        if (securityContext.isCallerInRole(FarmerRoles.ADMIN)) {
            return;
        } else if (securityContext.isCallerInRole(FarmerRoles.USER)
                && machine.isPresent()
                && machine.get().getFarmer().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
