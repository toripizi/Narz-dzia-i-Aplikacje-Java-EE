package com.toripizi.farmhub.configuration.listener;

import com.toripizi.farmhub.datastore.DataStore;
import com.toripizi.farmhub.farmer.repository.FarmerRepository;
import com.toripizi.farmhub.farmer.service.FarmerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        DataStore dataStore = (DataStore) sce.getServletContext().getAttribute("dataStore");
        String resourcePath = sce.getServletContext().getInitParameter("resourcePath");

        FarmerRepository farmerRepository = new FarmerRepository(dataStore, resourcePath);
        sce.getServletContext().setAttribute("farmerService", new FarmerService(farmerRepository));
    }
}
