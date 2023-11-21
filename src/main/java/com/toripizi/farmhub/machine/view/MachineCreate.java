package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.model.CategoryModel;
import com.toripizi.farmhub.category.model.function.CategoryToModelFunction;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.machine.model.MachineCreateModel;
import com.toripizi.farmhub.machine.model.functions.ModelToMachineFunction;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class MachineCreate implements Serializable {
    private final MachineService machineService;
    private final CategoryService categoryService;

    @Getter
    private MachineCreateModel machine;

    @Getter
    private List<CategoryModel> categories;

    private final Conversation conversation;

    @Inject
    public MachineCreate(MachineService machineService, CategoryService categoryService, Conversation conversation) {
        this.machineService = machineService;
        this.categoryService = categoryService;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            categories = categoryService.findAll().stream()
                    .map(new CategoryToModelFunction())
                    .collect(Collectors.toList());
            machine = MachineCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/category/category_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        ModelToMachineFunction function = new ModelToMachineFunction();
        Optional<Category> o = categoryService.find(machine.getCategoryId());
        machineService.create(function.apply(machine, o.get()));
        conversation.end();
        return "/category/category_list.xhtml?faces-redirect=true";
    }

    public String goToCategoryAction() {
        return "/machine/machine_create__category.xhtml?faces-redirect=true";
    }
}
