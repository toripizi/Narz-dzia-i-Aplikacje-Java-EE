package com.toripizi.farmhub.machine.controller;

import com.toripizi.farmhub.machine.dto.CreateMachineRequest;
import com.toripizi.farmhub.machine.dto.GetMachineryResponse;
import com.toripizi.farmhub.machine.dto.GetMachineResponse;
import com.toripizi.farmhub.machine.dto.UpdateMachineRequest;

import java.util.UUID;

public interface MachineController {
    GetMachineryResponse getMachinery();

    GetMachineResponse getMachine(UUID id);

    void createMachine(CreateMachineRequest req);

    void updateMachine(UUID id, UpdateMachineRequest req);

    void deleteMachine(UUID id);

}
