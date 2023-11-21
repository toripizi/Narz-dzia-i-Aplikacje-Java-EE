package com.toripizi.farmhub.machine.controller;

import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
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
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("categories/{categoryId}/machinery")
public class MachineSimpleController implements MachineController {
    private final MachineService machineService;

    @PathParam("categoryId")
    private String categoryId;

    @Inject
    public MachineSimpleController(MachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public GetMachineryResponse getMachinery() {
        Stream<Machine> machinery = machineService.findAll().stream().filter(
            machine -> machine.getCategory().getId().equals(UUID.fromString(this.categoryId))
        );
        List<Machine> all = machinery.collect(Collectors.toList());
        return GetMachineryResponse.entityToDtoMapper().apply(all);
    }

    @Override
    public GetMachineResponse getMachine(UUID id) {
        Machine machine = machineService.find(id).filter(
            the_machine -> the_machine.getCategory().getId().equals(UUID.fromString(this.categoryId))
        ).orElseThrow(
            () -> new jakarta.ws.rs.NotFoundException("Could not find machine of id: " + id.toString())
        );
        return GetMachineResponse.entityToDtoMapper().apply(machine);
    }

    @Override
    public void createMachine(CreateMachineRequest req) {
        req.setCategoryId(this.categoryId);
        try {
            machineService.create(CreateMachineRequest.dtoToEntityMapper().apply(req));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
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