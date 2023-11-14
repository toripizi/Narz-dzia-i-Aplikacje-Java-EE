package com.toripizi.farmhub.farmer.controller;

import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.farmer.dto.CreateFarmerRequest;
import com.toripizi.farmhub.farmer.dto.GetFarmerResponse;
import com.toripizi.farmhub.farmer.dto.GetFarmersResponse;
import com.toripizi.farmhub.farmer.dto.UpdateFarmerRequest;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.service.FarmerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@ApplicationScoped
public class FarmerSimpleController implements FarmerController {
    private final FarmerService service;

    @Inject
    public FarmerSimpleController(FarmerService service) {
        this.service = service;
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
            service.create(
                    CreateFarmerRequest.dtoToEntityMapper().apply(req)
            );
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
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