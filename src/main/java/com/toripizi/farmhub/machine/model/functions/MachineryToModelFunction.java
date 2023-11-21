package com.toripizi.farmhub.machine.model.functions;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineryModel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MachineryToModelFunction implements Function<List<Machine>, MachineryModel> {

    @Override
    public MachineryModel apply(List<Machine> entity) {
        return MachineryModel.builder()
                .machinery(entity.stream()
                        .map(machine -> MachineryModel.Machine.builder()
                                .id(machine.getId())
                                .name(machine.getName())
                                .horsepower(machine.getHorsepower())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
