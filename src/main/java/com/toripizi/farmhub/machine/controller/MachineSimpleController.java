package com.toripizi.farmhub.machine.controller;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.machine.dto.CreateMachineRequest;
import com.toripizi.farmhub.machine.dto.GetMachineryResponse;
import com.toripizi.farmhub.machine.dto.GetMachineResponse;
import com.toripizi.farmhub.machine.dto.UpdateMachineRequest;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("categories/{categoryId}/machinery")
public class MachineSimpleController implements MachineController {
    private MachineService machineService;
    private CategoryService categoryService;

    @PathParam("categoryId")
    private String categoryId;

    @Inject
    public MachineSimpleController(MachineService machineService, CategoryService categoryService) {
        this.machineService = machineService;
        this.categoryService = categoryService;
    }

    @Override
    public GetMachineryResponse getMachinery() {
        List<Machine> all = machineService.findAll().stream().filter(
                machine -> machine.getCategory().getId().equals(UUID.fromString(this.categoryId))
        ).collect(Collectors.toList());
        if (all.isEmpty()) {
            throw new jakarta.ws.rs.NotFoundException("Could not find machinery of category id: " + this.categoryId);
        }
        return GetMachineryResponse.entityToDtoMapper().apply(all);
    }

    @Override
    public GetMachineResponse getMachine(UUID id) {
        Machine machine = machineService.findForCallerPrincipal(id).filter(
            the_machine -> the_machine.getCategory().getId().equals(UUID.fromString(this.categoryId))
        ).orElseThrow(
            () -> new jakarta.ws.rs.NotFoundException("Could not find machine of id: " + id.toString())
        );
        return GetMachineResponse.entityToDtoMapper().apply(machine);
    }

    @Override
    public void createMachine(CreateMachineRequest req) {
        Optional<Category> category = categoryService.find(UUID.fromString(this.categoryId));
        if (category.isPresent()) {
            req.setCategoryId(UUID.fromString(this.categoryId));
            try {
                machineService.create(
                        CreateMachineRequest.dtoToEntityMapper().apply(req)
                );
            } catch (IllegalArgumentException ex) {
                throw new jakarta.ws.rs.BadRequestException(ex);
            }
        } else {
            throw new jakarta.ws.rs.BadRequestException("Could not find category of id: " + this.categoryId);
        }
    }

    @Override
    public void updateMachine(UUID id, UpdateMachineRequest req) {
        System.out.println(id);
        machineService.find(id).filter(
            machine -> machine.getCategory().getId().equals(UUID.fromString(this.categoryId))
        ).ifPresentOrElse(
            entity -> machineService.update(
                    UpdateMachineRequest.dtoToEntityMapper().apply(entity, req)
            ),
            () -> {
                throw new NotFoundException("Could not find machine of id: " + id.toString());
            }
        );
    }

    @Override
    public void deleteMachine(UUID id) {
        machineService.find(id).filter(
                machine -> machine.getCategory().getId().equals(UUID.fromString(this.categoryId))
        ).ifPresentOrElse(
                entity -> machineService.delete(id),
                () -> {
                    throw new NotFoundException("Could not find machine of id: " + id.toString());
                }
        );
    }
}