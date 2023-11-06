package com.toripizi.farmhub.configuration.listener;

import com.toripizi.farmhub.farmer.controller.FarmerController;
import com.toripizi.farmhub.farmer.service.FarmerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        FarmerService farmerService = (FarmerService) sce.getServletContext().getAttribute("farmerService");
        sce.getServletContext().setAttribute("farmerController", new FarmerController(farmerService));
    }
}
