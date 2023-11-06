package com.toripizi.farmhub.configuration.listener;

import com.toripizi.farmhub.farmer.service.FarmerService;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.Role;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.UUID;

@WebListener
public class DataInitializer implements ServletContextListener {

    private FarmerService farmerService;

    public void contextInitialized(ServletContextEvent sce) {
        farmerService = (FarmerService) sce.getServletContext().getAttribute("farmerService");
        init();
    }

    private void init() {
        /* create Farmers */
        Farmer farmer1 = Farmer.builder()
                .id(UUID.fromString("ba735cb3-a7e6-44b3-87da-19a47ea4202b"))
                .login("farmer1")
                .password("password1")
                .role(Role.ADMIN)
                .build();

        Farmer farmer2 = Farmer.builder()
                .id(UUID.fromString("80f176e8-cfb5-45df-a592-61bba7e1de25"))
                .login("farmer2")
                .password("password2")
                .role(Role.USER)
                .build();

        Farmer farmer3 = Farmer.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be27"))
                .login("farmer3")
                .password("password3")
                .role(Role.USER)
                .build();

        Farmer farmer4 = Farmer.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be29"))
                .login("farmer4")
                .password("password4")
                .role(Role.USER)
                .build();

        this.farmerService.create(farmer1);
        this.farmerService.create(farmer2);
        this.farmerService.create(farmer3);
        this.farmerService.create(farmer4);
    }
}
