package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.machine.model.MachineryModel;
import com.toripizi.farmhub.machine.model.functions.MachineryToModelFunction;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class MachineList {
    private final MachineService service;

    private MachineryModel machinery;

    @Inject
    public MachineList(MachineService service, MachineryModel machinery) {
        this.service = service;
        this.machinery = machinery;
    }

    public MachineryModel getMachinery() {
        if (machinery.getMachinery() == null) {
            MachineryToModelFunction function = new MachineryToModelFunction();
            machinery = function.apply(service.findAll());
        }
        return machinery;
    }

    public String deleteAction(MachineryModel.Machine machine) {
        service.delete(machine.getId());
        return "machine_list?faces-redirect=true";
    }
}
