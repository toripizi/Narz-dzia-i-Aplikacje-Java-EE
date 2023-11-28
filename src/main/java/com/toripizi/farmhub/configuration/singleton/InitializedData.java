package com.toripizi.farmhub.configuration.singleton;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.FarmerRoles;
import com.toripizi.farmhub.farmer.service.FarmerService;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({FarmerRoles.ADMIN, FarmerRoles.USER})
@RunAs(FarmerRoles.ADMIN)
@Log
public class InitializedData {

    private CategoryService categoryService;
    private FarmerService farmerService;
    private MachineService machineService;

//    @Inject
//    private RequestContextController requestContextController;

    private SecurityContext securityContext;
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializedData(SecurityContext securityContext, Pbkdf2PasswordHash passwordHash) {
        this.securityContext = securityContext;
        this.passwordHash = passwordHash;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @EJB
    public void setFarmerService(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @EJB
    public void setMachineService(MachineService machineService) {
        this.machineService = machineService;
    }


    @PostConstruct
    @SneakyThrows
    private void init() {

        if (farmerService.find("farmer1").isEmpty()) {
//            requestContextController.activate();

            /* create Farmers */
            Farmer farmer1 = Farmer.builder()
                    .id(UUID.fromString("ba735cb3-a7e6-44b3-87da-19a47ea4202b"))
                    .login("farmer1")
                    .password(passwordHash.generate("password1".toCharArray()))
                    .roles(List.of(FarmerRoles.ADMIN, FarmerRoles.USER))
                    .build();

            Farmer farmer2 = Farmer.builder()
                    .id(UUID.fromString("80f176e8-cfb5-45df-a592-61bba7e1de25"))
                    .login("farmer2")
                    .password(passwordHash.generate("password2".toCharArray()))
                    .roles(List.of(FarmerRoles.USER))
                    .build();

            Farmer farmer3 = Farmer.builder()
                    .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be27"))
                    .login("farmer3")
                    .password(passwordHash.generate("password3".toCharArray()))
                    .roles(List.of(FarmerRoles.USER))
                    .build();

            Farmer farmer4 = Farmer.builder()
                    .id(UUID.fromString("b345e185-688c-43b4-a132-8bc8c7d8be29"))
                    .login("farmer4")
                    .password(passwordHash.generate("password4".toCharArray()))
                    .roles(List.of(FarmerRoles.USER))
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
                    .name("gruber")
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

            farmerService.findAll().forEach(farmer -> farmerService.delete(farmer.getId()));
            categoryService.findAll().forEach(category -> categoryService.delete(category.getId()));
            machineService.findAll().forEach(machine -> machineService.delete(machine.getId()));


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

//            requestContextController.deactivate();
        }
    }
}
