package org.pautib.config;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@PreMatching
public class PreMatchingServerRequestFilter implements ContainerRequestFilter {

    @Inject
    private Logger logger;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String httpMethod = requestContext.getHeaderString("X-Http-Method-Override");

        if (httpMethod != null && !httpMethod.isEmpty()) {
            logger.log(Level.INFO, "Original http method " + requestContext.getMethod());

            requestContext.setMethod(httpMethod);

            logger.log(Level.INFO, "Altered http method now is " + requestContext.getMethod());
        }
    }
}
