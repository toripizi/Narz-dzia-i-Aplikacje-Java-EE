package com.toripizi.farmhub.configuration.observer;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.farmer.service.FarmerService;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.Role;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class DataInitializer implements ServletContextListener {

    private final FarmerService farmerService;
    private final CategoryService categoryService;
    private final MachineService machineService;

    @Inject
    public DataInitializer(FarmerService farmerService, CategoryService categoryService, MachineService machineService) {
        this.farmerService = farmerService;
        this.categoryService = categoryService;
        this.machineService = machineService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
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

        Category ciagniki = Category.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be11"))
                .name("ciagniki")
                .isDrivable(true)
                .build();

        Category kombajny = Category.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be12"))
                .name("kombajny")
                .isDrivable(true)
                .build();

        Category plugi = Category.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be13"))
                .name("plugi")
                .isDrivable(false)
                .build();

        Category kultywatory = Category.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be14"))
                .name("kultywatory")
                .isDrivable(false)
                .build();


        Machine ursus_1634 = Machine.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8b311"))
                .name("ursus_1634")
                .whenProduced(LocalDate.of(1990, 1, 1))
                .horsepower(120)
                .category(ciagniki)
                .farmer(farmer1)
                .build();

        Machine zetor = Machine.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8b312"))
                .name("zetor")
                .whenProduced(LocalDate.of(1992, 1, 1))
                .horsepower(80)
                .category(ciagniki)
                .farmer(farmer2)
                .build();

        Machine gruber = Machine.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8b313"))
                .name("ursus_1634")
                .whenProduced(LocalDate.of(2023, 1, 1))
                .horsepower(0)
                .category(kultywatory)
                .farmer(farmer1)
                .build();

        Machine new_holland_5070 = Machine.builder()
                .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8b314"))
                .name("new_holland_5070")
                .whenProduced(LocalDate.of(2020, 1, 1))
                .horsepower(200)
                .category(kombajny)
                .farmer(farmer3)
                .build();

        farmerService.create(farmer1);
        farmerService.create(farmer2);
        farmerService.create(farmer3);
        farmerService.create(farmer4);

        categoryService.create(ciagniki);
        categoryService.create(kombajny);
        categoryService.create(plugi);
        categoryService.create(kultywatory);

        machineService.create(ursus_1634);
        machineService.create(zetor);
        machineService.create(gruber);
        machineService.create(new_holland_5070);
    }
}
