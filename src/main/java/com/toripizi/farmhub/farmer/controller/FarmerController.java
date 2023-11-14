package com.toripizi.farmhub.farmer.controller;

import com.toripizi.farmhub.farmer.dto.CreateFarmerRequest;
import com.toripizi.farmhub.farmer.dto.GetFarmerResponse;
import com.toripizi.farmhub.farmer.dto.GetFarmersResponse;
import com.toripizi.farmhub.farmer.dto.UpdateFarmerRequest;

import java.io.InputStream;
import java.util.UUID;

public interface FarmerController {
    GetFarmersResponse getFarmers();

    GetFarmerResponse getFarmer(UUID id);

    void createFarmer(CreateFarmerRequest req);

    void updateFarmer(UUID id, UpdateFarmerRequest req);

    void deleteFarmer(UUID id);

    byte[] getFarmerAvatar(UUID id);

    void putFarmerAvatar(UUID id, InputStream portrait);

    void deleteFarmerAvatar(UUID id);

}
