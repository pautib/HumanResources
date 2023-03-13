package org.pautib.config;

import io.jsonwebtoken.Jwts;
import org.pautib.services.ApplicationState;
import org.pautib.services.SecurityUtil;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Secure
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {
    private static final String BEARER = "Bearer";

    @Inject
    private Logger logger;

    @Inject
    private ApplicationState applicationState;

    @Inject
    private SecurityUtil securityUtil;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            logger.log(Level.SEVERE, "Wrong or no authorization header found {0}", authHeader);
            throw new NotAuthorizedException("No authorization header provided");
        }

        String token = authHeader.substring(BEARER.length()).trim();

        try {
            Key key = securityUtil.generateKey(applicationState.getEmail());
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            SecurityContext securityContext = requestContext.getSecurityContext();

            requestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return () -> Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
                }

                @Override
                public boolean isUserInRole(String s) { return securityContext.isUserInRole(s); }

                @Override
                public boolean isSecure() { return securityContext.isSecure(); }

                @Override
                public String getAuthenticationScheme() { return securityContext.getAuthenticationScheme(); }
            });

            logger.info("Token parsed successfully");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Invalid {0}", token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
