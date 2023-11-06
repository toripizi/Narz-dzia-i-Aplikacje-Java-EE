package com.toripizi.farmhub.farmer.repository;

import com.toripizi.farmhub.configuration.ProjectPaths;
import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import com.toripizi.farmhub.datastore.DataStore;
import com.toripizi.farmhub.farmer.entity.Farmer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FarmerRepository {

    private final DataStore dataStore;
    private final String resourcePath;

    public FarmerRepository(DataStore dataStore, String resourcePath){
        this.dataStore = dataStore;
        this.resourcePath = resourcePath;
    }

    public List<Farmer> findAll() {
        return dataStore.findAllFarmers();
    }

    public Optional<Farmer> find(UUID id) {
        return dataStore.findAllFarmers().stream()
                .filter(farmer -> farmer.getId().equals(id))
                .findFirst();
    }

    public void create(Farmer farmer) {
        dataStore.createFarmer(farmer);
    }

    public void update(Farmer farmer) {
        dataStore.updateFarmer(farmer);
    }

    public void delete(Farmer farmer) {
        dataStore.deleteFarmer(farmer.getId());
    }

    public byte[] findAvatar(UUID id) {
        return getResource(id.toString() + ".png");
    }

    public void updateAvatar(UUID id, InputStream is) {
        putResource(id.toString(), is);
    }

    public void deleteAvatar(UUID id) {
        deleteResource(id.toString());
    }


    /**
     * Metthods for resource management.
     * @param name filename
     */

    private void deleteResource(String name) {
        try {
            Files.delete(
                    ProjectPaths.getAvatarPath(name, this.resourcePath)
            );
        } catch (IOException ex) {
            throw new NotFoundException(ex, "Could not find any resource of name: " + name);
        }
    }

    private byte[] getResource(String name) {
        try {
            return Files.readAllBytes(
                    ProjectPaths.getAvatarPath(name, this.resourcePath)
            );
        } catch (IOException ex) {
            throw new NotFoundException(ex, "Could not find any resource of name: " + name);
        }
    }

    private void putResource(String name, InputStream stream) {
        try {
            Files.write(
                    ProjectPaths.getAvatarPath(name, this.resourcePath),
                    stream.readAllBytes(),
                    new StandardOpenOption[]{StandardOpenOption.CREATE}
            );
        } catch (IOException ex) {
            throw new BadRequestException(ex, "Uploading avatar failed.");
        }
    }
}
