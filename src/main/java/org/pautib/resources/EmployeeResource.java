package org.pautib.resources;

import org.pautib.entities.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/employee")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EmployeeResource {

    @GET
    @Path("/{employee-id}")
    public Employee getEmployeeById(@PathParam("employee-id") Integer employeeId) {

        Employee employee = new Employee();
        employee.setFullName("Pau Torres i Bravo");

        return employee;
    }


}
