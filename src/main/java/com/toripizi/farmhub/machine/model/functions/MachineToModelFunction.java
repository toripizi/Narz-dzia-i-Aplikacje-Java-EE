package com.toripizi.farmhub.machine.model.functions;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineModel;

import java.io.Serializable;
import java.util.function.Function;

public class MachineToModelFunction implements Function<Machine, MachineModel>, Serializable {
    @Override
    public MachineModel apply(Machine machine) {
        return MachineModel.builder()
                .name(machine.getName())
                .whenProduced(machine.getWhenProduced())
                .horsepower(machine.getHorsepower())
                .category(machine.getCategory())
                .build();
    }
}
