package com.toripizi.farmhub.machine.model.functions;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineEditModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiFunction;

public class UpdateMachineWithModelFunction implements BiFunction<Machine, MachineEditModel, Machine>, Serializable {

    @Override
    public Machine apply(Machine entity, MachineEditModel model) {
        return Machine.builder()
                .id(entity.getId())
                .name(model.getName())
                .whenProduced(LocalDate.parse(model.getWhenProduced()))
                .horsepower(model.getHorsepower())
                .category(entity.getCategory())
                .build();
    }
}
