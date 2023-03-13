package org.pautib.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.pautib.entities.ApplicationUser;
import org.pautib.services.ApplicationState;
import org.pautib.services.PersistenceService;
import org.pautib.services.PersistenceServiceImpl;
import org.pautib.services.SecurityUtil;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;


public class UsersResourceImpl implements UsersResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    PersistenceService persistenceService;

    @Inject
    private SecurityUtil securityUtil;

    @Inject
    private Logger logger;

    @Inject
    private ApplicationState applicationState;

    public Response createUser(ApplicationUser user) {

        persistenceService.saveUser(user);

        return Response
                .created(uriInfo.getAbsolutePathBuilder().path(user.getId().toString()).build())
                .status(Response.Status.OK)
                .build();
    }

    public Response login(String email, String password) {

        if (!securityUtil.authenticateUser(email, password)) {
            throw new SecurityException("Email or password incorrect");
        }

        applicationState.setEmail(email);
        String token = getToken(email);

        return Response
                .ok()
                .header(AUTHORIZATION, "Bearer " + token)
                .build();
    }

    private String getToken(String email) {

        Key key = securityUtil.generateKey(email);

        String token = Jwts
                        .builder()
                        .setSubject(email)
                        .setIssuer(uriInfo.getAbsolutePath().toString())
                        .setIssuedAt(new Date())
                        .setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(15)))
                        .signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString())
                        .compact();

        logger.log(Level.INFO, "Generated token is {0}", token);

        return token;
    }

    public Response createNewUser(String email, String password, String referer) {

        ApplicationUser user = new ApplicationUser();
        user.setEmail(email);
        user.setPassword(password);

        return createUser(user);
    }

    public Response createNewUser(MultivaluedMap<String, String> formMap, HttpHeaders httpHeaders) {

        List<String> referer = httpHeaders.getRequestHeader("Referer");

        if (referer.size() == 0) {
            throw new NoResultException("Referer header is needed here");
        }

        Stream.of(httpHeaders.getRequestHeaders().keySet()).forEach(h -> System.out.println("Header key set " + h));

        ApplicationUser user = new ApplicationUser();
        user.setEmail(formMap.getFirst("email"));
        user.setPassword(formMap.getFirst("password"));

        return createUser(user);
    }

    public Response createNewUser(ApplicationUser applicationUser, String user) {

        return createUser(applicationUser);
    }

}
