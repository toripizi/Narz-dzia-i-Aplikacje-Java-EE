package com.toripizi.farmhub.machine.view;

import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.model.MachineEditModel;
import com.toripizi.farmhub.machine.model.functions.MachineToEditModelFunction;
import com.toripizi.farmhub.machine.model.functions.UpdateMachineWithModelFunction;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class MachineEdit implements Serializable {
    private MachineService machineService;
    private final FacesContext facesContext;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MachineEditModel machine;

    @Inject
    public MachineEdit(MachineService machineService, FacesContext facesContext) {
        this.machineService = machineService;
        this.facesContext = facesContext;
    }

    public void init() throws IOException {
        Optional<Machine> machine = machineService.findForCallerPrincipal(id);
        if (machine.isPresent()) {
            MachineToEditModelFunction function = new MachineToEditModelFunction();
            this.machine = function.apply(machine.get());
        } else {
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Machine not found");
        }
    }

    public String saveAction() throws IOException {
        try {
            UpdateMachineWithModelFunction function = new UpdateMachineWithModelFunction();
            Machine entity = machineService.findForCallerPrincipal(id).orElseThrow();
            entity.updateCreationDateTime();
            machineService.update(function.apply(entity, machine));
            return FacesContext.getCurrentInstance().getViewRoot().getViewId() + "?faces-redirect=true&includeViewParams=true";
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null;
        }
    }
}
