package com.toripizi.farmhub.machine.model.functions;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineEditModel;
import jakarta.ejb.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Function;

public class MachineToEditModelFunction implements Function<Machine, MachineEditModel>, Serializable {
    @Override
    public MachineEditModel apply(Machine entity) {
        return MachineEditModel.builder()
                .name(entity.getName())
                .whenProduced(String.valueOf(entity.getWhenProduced()))
                .horsepower(entity.getHorsepower())
                .version(entity.getVersion())
                .build();
    }
}
