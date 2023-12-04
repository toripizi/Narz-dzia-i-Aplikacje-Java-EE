package com.toripizi.farmhub.configuration.observer;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.repository.CategoryRepository;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.FarmerRoles;
import com.toripizi.farmhub.farmer.repository.FarmerRepository;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.repository.MachineRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private final FarmerRepository farmerRepository;
    private final CategoryRepository categoryRepository;
    private final MachineRepository machineRepository;
    private final Pbkdf2PasswordHash passwordHash;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(FarmerRepository farmerRepository,
                           CategoryRepository categoryRepository,
                           MachineRepository machineRepository,
                           @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash,
                           RequestContextController requestContextController) {
        this.farmerRepository = farmerRepository;
        this.categoryRepository = categoryRepository;
        this.machineRepository = machineRepository;
        this.passwordHash = passwordHash;
        this.requestContextController = requestContextController;
    }
    @Transactional
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    @SneakyThrows
    private void init() {
        requestContextController.activate();

        if (farmerRepository.findByLogin("farmer1").isEmpty()) {

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

            farmerRepository.findAll().forEach(farmerRepository::delete);
            categoryRepository.findAll().forEach(categoryRepository::delete);
            machineRepository.findAll().forEach(machineRepository::delete);


            farmerRepository.create(farmer1);
            farmerRepository.create(farmer2);
            farmerRepository.create(farmer3);
            farmerRepository.create(farmer4);

            categoryRepository.create(ciagniki);
            categoryRepository.create(kombajny);
            categoryRepository.create(plugi);
            categoryRepository.create(kultywatory);

            machineRepository.create(ursus_1634);
            machineRepository.create(zetor);
            machineRepository.create(gruber);
            machineRepository.create(new_holland_5070);

        }
        requestContextController.deactivate();
    }
}
