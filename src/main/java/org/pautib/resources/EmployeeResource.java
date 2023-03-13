package org.pautib.resources;

import org.pautib.config.Secure;
import org.pautib.entities.Employee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Path("/v1/employee")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface EmployeeResource {

    @GET
    @Path("/all")
    @Secure
    Response getEmployees(@Context HttpHeaders httpHeaders);

    @GET
    @Path("/{employee-id: \\d+}")
    Response getEmployeeById(@PathParam("employee-id") @DefaultValue("0") Long id, @Context Request request);

    @POST
    Response createEmployee(@Valid Employee employee);

    @PUT
    Response updateEmployee(Employee employee);

    @POST
    @Path("/upload") //v1/employee/upload?id=1
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA, "image/jpg", "image/png", "image/jpeg"})
    @Produces({MediaType.TEXT_PLAIN})
    Response upload(File picture, @QueryParam("id") @NotNull Long id) throws FileNotFoundException;

    @GET
    @Path("/download")
    @Produces({MediaType.APPLICATION_OCTET_STREAM, "image/jpg", "image/png", "image/jpeg"})
    Response download(@QueryParam("id") @NotNull Long id) throws IOException;

    @DELETE
    @Path("/{employee-id: \\d+}")   //api/v1/employee/34 - DELETE
    Response terminateEmployeeById(@PathParam("employee-id") @NotNull Long id);

    @DELETE
    @Path("/{nie}")   //api/v1/employee/38875513Q - DELETE
    Response terminateEmployeeByNIE(@PathParam("nie") @NotNull String nie);

}
