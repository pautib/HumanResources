package org.pautib.resources;

import org.pautib.config.MaxAge;
import org.pautib.entities.Department;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/v1/department")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface DepartmentResource {

    @GET
    @Produces({"application/json; qs=0.9", "application/xml; qs=0.7"})
    @Path("/all")
    Response getDepartments();

    @GET
    @Path("/{id: \\d+}")
    @MaxAge(age = 200)
    Response getDepartmentById(@PathParam("id") @DefaultValue("0") Long id);

    @GET
    @Path("/byCode/{code}")
    Response getDepartmentByCode(@PathParam("code") @NotNull String code);

    @POST
    Response createDepartment(@Valid Department d);

    @PUT
    @Path("{id: \\d+}")
    Response updateDepartment(@PathParam("id") @NotNull Long id, @Valid Department inputDept);

    @DELETE
    @Path("{code}")
    Response deleteDepartment(@PathParam("code") @NotNull String code);
}
