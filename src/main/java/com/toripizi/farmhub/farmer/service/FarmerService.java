package com.toripizi.farmhub.farmer.service;

import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.farmer.repository.FarmerRepository;
import com.toripizi.farmhub.farmer.entity.Farmer;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FarmerService {
    private final FarmerRepository repository;

    public FarmerService(FarmerRepository repository) {
        this.repository = repository;
    }

    public List<Farmer> findAll() {
        return repository.findAll();
    }

    public Optional<Farmer> find(UUID id) {
        return repository.find(id);
    }

    public void create(Farmer farmer) {
        repository.create(farmer);
    }

    public void update(Farmer farmer) {
        repository.update(farmer);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find farmer of id: " + id.toString())
        ));
    }

    public byte[] findAvatar(UUID id) {
        return repository.findAvatar(id);
    }

    public void updateAvatar(UUID id, InputStream is) {
        repository.updateAvatar(id, is);
    }

    public void deleteAvatar(UUID id) {
        repository.deleteAvatar(id);
    }
}
