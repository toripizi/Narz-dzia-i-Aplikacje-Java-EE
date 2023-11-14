package com.toripizi.farmhub.machine.controller;

import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.machine.dto.CreateMachineRequest;
import com.toripizi.farmhub.machine.dto.GetMachineryResponse;
import com.toripizi.farmhub.machine.dto.GetMachineResponse;
import com.toripizi.farmhub.machine.dto.UpdateMachineRequest;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@ApplicationScoped
public class MachineSimpleController implements MachineController {
    private final MachineService service;

    @Inject
    public MachineSimpleController(MachineService service) {
        this.service = service;
    }

    @Override
    public GetMachineryResponse getMachinery() {
        List<Machine> all = service.findAll();
        Function<Collection<Machine>, GetMachineryResponse> mapper = GetMachineryResponse.entityToDtoMapper();
        return mapper.apply(all);
    }

    @Override
    public GetMachineResponse getMachine(UUID id) {
        Function<Machine, GetMachineResponse> mapper = GetMachineResponse.entityToDtoMapper();
        Machine machine = service.find(id).orElseThrow(
                () -> new NotFoundException("Could not find machine of id: " + id.toString())
        );
        return mapper.apply(machine);
    }

    @Override
    public void createMachine(CreateMachineRequest req) {
        try {
            service.create(
                    CreateMachineRequest.dtoToEntityMapper().apply(req)
            );
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateMachine(UUID id, UpdateMachineRequest req) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(
                        UpdateMachineRequest.dtoToEntityMapper().apply(entity, req)
                ),
                () -> {
                    throw new NotFoundException("Could not find machine of id: " + id.toString());
                }
        );
    }

    @Override
    public void deleteMachine(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException("Could not find machine of id: " + id.toString());
                }
        );
    }
}