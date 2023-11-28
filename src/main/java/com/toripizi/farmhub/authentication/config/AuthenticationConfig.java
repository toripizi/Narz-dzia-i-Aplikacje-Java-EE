package com.toripizi.farmhub.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "farmhub")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/farmhub",
        callerQuery = "select password from farmers where login = ?",
        groupsQuery = "select role from farmers__roles where id = (select id from farmers where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
