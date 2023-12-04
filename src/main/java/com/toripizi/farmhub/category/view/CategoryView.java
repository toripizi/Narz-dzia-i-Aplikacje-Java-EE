package com.toripizi.farmhub.category.view;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.model.CategoryModel;
import com.toripizi.farmhub.category.model.function.CategoryToModelFunction;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineryModel;
import com.toripizi.farmhub.machine.model.functions.MachineryToModelFunction;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class CategoryView implements Serializable {
    private final CategoryService categoryService;
    private final MachineService machineService;

    @Setter
    @Getter
    private UUID id;

    @Setter
    @Getter
    private CategoryModel category;

    @Setter
    @Getter
    private MachineryModel machinery;

    @Inject
    public CategoryView(CategoryService categoryService, MachineService machineService) {
        this.categoryService = categoryService;
        this.machineService = machineService;
    }

    public void init() throws IOException {
        Optional<Category> category = categoryService.find(id);
        if (category.isPresent()) {
            CategoryToModelFunction function = new CategoryToModelFunction();
            this.category = function.apply(category.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public MachineryModel getMachinery() {
        if (machinery == null) {
            MachineryToModelFunction function = new MachineryToModelFunction();
            List<Machine> machineList = machineService.findAllByCategoryForCallerPrincipal(id);
            machinery = function.apply(machineList);
        }
        return machinery;
    }

    public String deleteMachineAction(MachineryModel.Machine machine) {
        machineService.delete(machine.getId());
        return FacesContext.getCurrentInstance().getViewRoot().getViewId() + "?faces-redirect=true&includeViewParams=true";
    }
}
