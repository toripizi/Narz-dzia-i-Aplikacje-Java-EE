package com.toripizi.farmhub.farmer.controller;

import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.farmer.dto.CreateFarmerRequest;
import com.toripizi.farmhub.farmer.dto.GetFarmerResponse;
import com.toripizi.farmhub.farmer.dto.GetFarmersResponse;
import com.toripizi.farmhub.farmer.dto.UpdateFarmerRequest;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.service.FarmerService;
import com.toripizi.farmhub.machine.dto.GetMachineryResponse;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.Path;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("farmers")
public class FarmerSimpleController implements FarmerController {
    private FarmerService service;
    protected MachineService machineService;
    @Inject
    public Pbkdf2PasswordHash passwordHash;
    @Inject
    public FarmerSimpleController(FarmerService service, MachineService machineService, Pbkdf2PasswordHash passwordHash) {
        this.service = service;
        this.machineService = machineService;
        this.passwordHash = passwordHash;
    }

    @Override
    public GetFarmersResponse getFarmers() {
        List<Farmer> all = service.findAll();
        Function<Collection<Farmer>, GetFarmersResponse> mapper = GetFarmersResponse.entityToDtoMapper();
        return mapper.apply(all);
    }

    @Override
    public GetFarmerResponse getFarmer(UUID id) {
        Function<Farmer, GetFarmerResponse> mapper = GetFarmerResponse.entityToDtoMapper();
        Farmer farmer = service.find(id).orElseThrow(
                () -> new NotFoundException("Could not find farmer of id: " + id.toString())
        );
        return mapper.apply(farmer);
    }

    @Override
    public void createFarmer(CreateFarmerRequest req) {
        try {
            req.setPassword(passwordHash.generate(req.getPassword().toCharArray()));
            service.create(
                    CreateFarmerRequest.dtoToEntityMapper().apply(req)
            );
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public GetMachineryResponse getMachinery(UUID id) {
        List<Machine> all = machineService.findAll();
        List<Machine> filteredMachines = all.stream()
                .filter(machine -> machine.getFarmer().getId().equals(id))
                .collect(Collectors.toList());
        Function<Collection<Machine>, GetMachineryResponse> mapper = GetMachineryResponse.entityToDtoMapper();
        return mapper.apply(filteredMachines);
    }


    @Override
    public void updateFarmer(UUID id, UpdateFarmerRequest req) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(
                        UpdateFarmerRequest.dtoToEntityMapper().apply(entity, req)
                ),
                () -> {
                    throw new NotFoundException("Could not find farmer of id: " + id.toString());
                }
        );
    }

    @Override
    public void deleteFarmer(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException("Could not find farmer of id: " + id.toString());
                }
        );
    }

    @Override
    public byte[] getFarmerAvatar(UUID id) {
        return service.findAvatar(id);
    }

    @Override
    public void putFarmerAvatar(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> service.updateAvatar(id, portrait),
                () -> {
                    throw new NotFoundException("Could not find farmer of id: " + id.toString());
                }
        );
    }

    @Override
    public void deleteFarmerAvatar(UUID id) {
        service.deleteAvatar(id);
    }
}