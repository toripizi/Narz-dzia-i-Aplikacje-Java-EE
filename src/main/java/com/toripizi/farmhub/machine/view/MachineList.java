package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.machine.model.MachineryModel;
import com.toripizi.farmhub.machine.model.functions.MachineryToModelFunction;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@RequestScoped
@Named
public class MachineList implements Serializable {
    private MachineService machineService;

    private MachineryModel machinery;

    @Inject
    public MachineList(MachineService machineService, MachineryModel machinery) {
        this.machineService = machineService;
        this.machinery = machinery;
    }

    public MachineryModel getMachinery() {
        if (machinery.getMachinery() == null) {
            MachineryToModelFunction function = new MachineryToModelFunction();
            machinery = function.apply(machineService.findAllForCallerPrincipal());
        }
        return machinery;
    }

    public void deleteAction(MachineryModel.Machine machine) {
        machineService.delete(machine.getId());
        machinery.setMachinery(null);
    }
}
