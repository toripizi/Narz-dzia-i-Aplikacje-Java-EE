package com.toripizi.farmhub.farmer.model.function;

import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.model.FarmersModel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts {@link List<Farmer>} to {@link FarmersModel}.
 */
public class FarmersToModelFunction implements Function<List<Farmer>, FarmersModel> {

    @Override
    public FarmersModel apply(List<Farmer> entity) {
        return FarmersModel.builder()
                .farmers(entity.stream()
                        .map(character -> FarmersModel.Farmer.builder()
                                .id(character.getId())
                                .login(character.getLogin())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
