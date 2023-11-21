package com.toripizi.farmhub.machine.model.functions;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineCreateModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiFunction;

public class ModelToMachineFunction implements BiFunction<MachineCreateModel, Category, Machine>, Serializable {
    @Override
    public Machine apply(MachineCreateModel model, Category category) {
        return Machine.builder()
                .id(model.getId())
                .name(model.getName())
                .whenProduced(LocalDate.parse(model.getWhenProduced()))
                .horsepower(model.getHorsepower())
                .category(category)
                .build();
    }
}
