package org.pautib.resources;

import org.pautib.entities.ApplicationUser;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("/v1/users")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@RequestScoped
public interface UsersResource {

    @POST
    Response createUser(@Valid ApplicationUser user);

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response login(@FormParam("email") @NotEmpty(message = "Email must be set") String email,
                          @NotEmpty(message = "Password must be set") @FormParam("password") String password);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/form/add")
    Response createNewUser(@FormParam("email") String email, @FormParam("password") String password,
                           @HeaderParam("Referer") String referer);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/map/add")
    Response createNewUser(MultivaluedMap<String, String> formMap, @Context HttpHeaders httpHeaders);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/bean/add")
    Response createNewUser(@BeanParam ApplicationUser applicationUser, @CookieParam("user") String user);
}
