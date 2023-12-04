package com.toripizi.farmhub.farmer.view;

import com.toripizi.farmhub.farmer.model.FarmersModel;
import com.toripizi.farmhub.farmer.model.function.FarmersToModelFunction;
import com.toripizi.farmhub.farmer.service.FarmerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class FarmerList {
    private final FarmerService service;
    private FarmersModel farmers;

    @Inject
    public FarmerList(FarmerService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all farmers
     */
    public FarmersModel getFarmers() {
        if (farmers == null) {
            farmers = new FarmersToModelFunction().apply(service.findAll());
        }
        return farmers;
    }

    /**
     * Action for clicking delete action.
     *
     * @param farmer farmer to be removed
     * @return navigation case to list_farmers
     */
    public void deleteAction(FarmersModel.Farmer farmer) {
        service.delete(farmer.getId());
        farmers.setFarmers(null);
    }

}
