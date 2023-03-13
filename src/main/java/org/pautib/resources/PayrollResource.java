package org.pautib.resources;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/v1/payroll")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface PayrollResource {

    @POST
    @Path("/run")
    void run(@Suspended AsyncResponse asyncResponse);

    @POST
    @Path("run-cf")
    public void computePayrollCF(@Suspended AsyncResponse asyncResponse, @QueryParam("i") @DefaultValue("3") long number);
}
