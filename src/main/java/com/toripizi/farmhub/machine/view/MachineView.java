package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineModel;
import com.toripizi.farmhub.machine.model.functions.MachineToModelFunction;
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
public class MachineView implements Serializable {
    private final MachineService machineService;

    @Inject
    public MachineView(MachineService machineService) {
        this.machineService = machineService;
    }

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MachineModel machine;

    public void init() throws IOException {
        Optional<Machine> machine = machineService.find(id);
        if (machine.isPresent()) {
            MachineToModelFunction function = new MachineToModelFunction();
            this.machine = function.apply(machine.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Machine not found");
        }
    }

}
