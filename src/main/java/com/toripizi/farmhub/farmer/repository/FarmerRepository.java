package com.toripizi.farmhub.farmer.repository;

import com.toripizi.farmhub.farmer.entity.Farmer;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmerRepository {

    List<Farmer> findAll();

    Optional<Farmer> find(UUID id);

    Optional<Farmer> findByLogin(String login);

    void create(Farmer farmer);

    void update(Farmer farmer);

    void delete(Farmer farmer);

    byte[] findAvatar(UUID id);

    void updateAvatar(UUID id, InputStream is);

    void deleteAvatar(UUID id);

    void detach(Farmer user);
}
