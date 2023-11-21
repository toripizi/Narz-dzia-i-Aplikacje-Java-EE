package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineEditModel;
import com.toripizi.farmhub.machine.model.functions.MachineToEditModelFunction;
import com.toripizi.farmhub.machine.model.functions.UpdateMachineWithModelFunction;
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
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class MachineEdit implements Serializable {
    private final MachineService machineService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MachineEditModel machine;

    @Inject
    public MachineEdit(MachineService machineService) {
        this.machineService = machineService;
    }

    public void init() throws IOException {
        Optional<Machine> machine = machineService.find(id);
        if (machine.isPresent()) {
            MachineToEditModelFunction function = new MachineToEditModelFunction();
            this.machine = function.apply(machine.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Machine not found");
        }
    }

    public String saveAction() {
        UpdateMachineWithModelFunction function = new UpdateMachineWithModelFunction();
        machineService.update(function.apply(machineService.find(id).orElseThrow(), machine));
        return FacesContext.getCurrentInstance().getViewRoot().getViewId() + "?faces-redirect=true&includeViewParams=true";
    }
}
