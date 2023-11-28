package com.toripizi.farmhub.configuration.singleton;

import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.FarmerRoles;
import com.toripizi.farmhub.farmer.repository.FarmerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;

/**
 * EJB singleton can be forced to start automatically when application starts. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin farmer.
 */
@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final FarmerRepository farmerRepository;

    private final Pbkdf2PasswordHash passwordHash;

    /**
     * @param farmerRepository farmer repository
     * @param passwordHash   hash mechanism used for storing farmers' passwords
     */
    @Inject
    public InitializeAdminService(
            FarmerRepository farmerRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.farmerRepository = farmerRepository;
        this.passwordHash = passwordHash;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @PostConstruct
    @SneakyThrows
    private void init() {
        if (farmerRepository.findByLogin("admin-service").isEmpty()) {

            Farmer admin = Farmer.builder()
                    .id(UUID.fromString("ba735cb3-a7e6-44b3-87da-19a47ea4201b"))
                    .login("admin-service")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(FarmerRoles.ADMIN, FarmerRoles.USER))
                    .build();

            farmerRepository.create(admin);
        }
    }

}
