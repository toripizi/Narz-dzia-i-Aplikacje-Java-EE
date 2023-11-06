package com.toripizi.farmhub.configuration.listener;

import com.toripizi.farmhub.datastore.DataStore;
import com.toripizi.farmhub.serialization.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateDataStore implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("dataStore", new DataStore(new CloningUtility()));
    }
}
