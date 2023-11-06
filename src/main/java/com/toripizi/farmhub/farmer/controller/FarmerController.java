package com.toripizi.farmhub.farmer.controller;

import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.farmer.dto.CreateFarmerRequest;
import com.toripizi.farmhub.farmer.dto.GetFarmerResponse;
import com.toripizi.farmhub.farmer.dto.GetFarmersResponse;
import com.toripizi.farmhub.farmer.dto.UpdateFarmerRequest;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.service.FarmerService;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class FarmerController {

    private final FarmerService service;

    public FarmerController(FarmerService service) {
        this.service = service;
    }

    public GetFarmersResponse getFarmers() {
        List<Farmer> all = service.findAll();
        Function<Collection<Farmer>, GetFarmersResponse> mapper = GetFarmersResponse.entityToDtoMapper();
        return mapper.apply(all);
    }

    public GetFarmerResponse getFarmer(UUID id) {
        Function<Farmer, GetFarmerResponse> mapper = GetFarmerResponse.entityToDtoMapper();
        Farmer farmer = service.find(id).orElseThrow(
                () -> new NotFoundException("Could not find farmer of id: " + id.toString())
        );
        return mapper.apply(farmer);
    }

    public void createFarmer(CreateFarmerRequest req) {
        try {
            service.create(
                    CreateFarmerRequest.dtoToEntityMapper().apply(req)
            );
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

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

    public void deleteFarmer(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException("Could not find farmer of id: " + id.toString());
                }
        );
    }

    public byte[] getFarmerAvatar(UUID id) {
        return service.findAvatar(id);
    }

    public void putFarmerAvatar(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> service.updateAvatar(id, portrait),
                () -> {
                    throw new NotFoundException("Could not find farmer of id: " + id.toString());
                }
        );
    }

    public void deleteFarmerAvatar(UUID id) {
        service.deleteAvatar(id);
    }

}
