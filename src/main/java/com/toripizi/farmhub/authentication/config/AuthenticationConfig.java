package com.toripizi.farmhub.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

//@BasicAuthenticationMechanismDefinition(realmName = "farmhub")
@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/authentication/custom/login.xhtml",
                errorPage = "/authentication/custom/login_error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/farmhub",
        callerQuery = "select password from farmers where login = ?",
        groupsQuery = "select role from farmers__roles where id = (select id from farmers where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
