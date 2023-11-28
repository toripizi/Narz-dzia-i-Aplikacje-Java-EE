package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.machine.model.MachineryModel;
import com.toripizi.farmhub.machine.model.functions.MachineryToModelFunction;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class MachineList {
    private MachineService machineService;

    private MachineryModel machinery;

    @EJB
    public void setMachineService(MachineService machineService) {this.machineService = machineService;}

    @Inject
    public MachineList(MachineryModel machinery) {
        this.machinery = machinery;
    }

    public MachineryModel getMachinery() {
        if (machinery.getMachinery() == null) {
            MachineryToModelFunction function = new MachineryToModelFunction();
            machinery = function.apply(machineService.findAllForCallerPrincipal());
        }
        return machinery;
    }

    public String deleteAction(MachineryModel.Machine machine) {
        machineService.delete(machine.getId());
        return "machine_list?faces-redirect=true";
    }
}
