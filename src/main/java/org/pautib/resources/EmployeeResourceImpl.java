package org.pautib.resources;


import org.pautib.entities.Department;
import org.pautib.entities.Employee;
import org.pautib.services.PersistenceService;
import org.pautib.services.PersistenceServiceImpl;
import org.pautib.services.QueryService;
import org.pautib.services.QueryServiceImpl;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class EmployeeResourceImpl implements EmployeeResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private QueryService queryService;

    @Inject
    private PersistenceService persistenceService;

    @Inject
    private Logger logger;

    public Response getEmployees(HttpHeaders httpHeaders) {

        logger.info("Get all the employees");
        httpHeaders.getAcceptableMediaTypes().get(0);

        List<Employee> employees = queryService.getEmployees();
        logger.warning("Employees: \n" + employees);

        return Response
                .ok(employees)
                .status(Response.Status.FOUND)
                .build();
    }


    public Response getEmployeeById(Long id, Request request) {

        Employee employeeById = queryService.findEmployeeById(id);

        if (employeeById == null ) throw new NoResultException("Employee with id " + id + " not found");

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(1000);

        EntityTag entityTag = new EntityTag(UUID.randomUUID().toString());
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);

        if (responseBuilder != null) {
            responseBuilder.cacheControl(cacheControl);
            responseBuilder.build();
        }

        responseBuilder = Response.ok(employeeById);
        responseBuilder.tag(entityTag);
        responseBuilder.cacheControl(cacheControl);

        return responseBuilder.build();
    }


    public Response createEmployee(Employee employee) {

        validateDepartmentIfProvided(employee);
        validateManagerIfProvided(employee);
        validateIfNIEDuplicated(employee);

        persistenceService.saveEmployee(employee);

        URI uri = getUri(employee);
        URI others = getAllUri();
        JsonObject links = getLinksJsonObject(uri, others);

        return Response
                .ok(links.toString())
                .status(Response.Status.CREATED)
                .build();
    }

    public Response updateEmployee(Employee employee) {

        Employee eByNIE = queryService.findEmployeeByNIE(employee.getSocialSecurityNumber());

        if (eByNIE != null ) persistenceService.updateEmployee(employee);
        else throw new NoResultException("Employee with NIE " + employee.getSocialSecurityNumber() + " not found");

        URI uri = getUri(employee);
        URI others = getAllUri();
        JsonObject links = getLinksJsonObject(uri, others);

        return Response
                .ok(links.toString())
                .status(Response.Status.ACCEPTED)
                .build();
    }

    public Response upload(File picture, Long id) throws FileNotFoundException {

        Employee e = queryService.findEmployeeById(id);

        try (Reader reader = new FileReader(picture)) {

            e.setPicture(Files.readAllBytes(Paths.get(picture.toURI())));
            persistenceService.updateEmployee(e);

            return Response.ok(picture.length()).build();

        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }


    public Response download(Long id) throws IOException {

        NewCookie userId = new NewCookie("userId", id.toString()); // Send a cookie back to the client
        Employee e = queryService.findEmployeeById(id);

        if (e != null) {
            File file = Files.write(Paths.get("picture.png"), e.getPicture()).toFile();
            return Response.ok(file).cookie(userId).build();
        }

        return Response.noContent().cookie(userId).build();
    }

    /**
     * The request hits this method after going through PreMatchingServerRequestFilter
     *
     * @param id
     * @return
     */
    //api/v1/employee/34 - DELETE
    public Response terminateEmployeeById(@PathParam("employee-id") @NotNull Long id) {

        //Integer dummyStaticValue = QueryServiceImpl.getDummyStaticValue();

        Employee employee = queryService.findEmployeeById(id);

        if (employee == null) throw new NoResultException("Employee with id " + id + " not found");

        persistenceService.deleteEntity(employee);

        return Response.ok().build();
    }

    /**
     * The request hits this method after going through PreMatchingServerRequestFilter
     *
     * @param
     * @return
     */
    //api/v1/employee/38875513Q - DELETE
    public Response terminateEmployeeByNIE(@PathParam("nie") @NotNull String nie) {

        Employee employee = queryService.findEmployeeByNIE(nie);

        if (employee == null) throw new NoResultException("Employee with NIE " + nie + " not found");

        persistenceService.deleteEmployee(employee);

        return Response.ok().build();
    }

    private URI getUri(Employee employee) {
        return uriInfo
                .getAbsolutePathBuilder()
                .path(employee.getId().toString())
                .build();
    }

    private URI getAllUri() {
        return uriInfo
                .getBaseUriBuilder()
                .path(EmployeeResource.class)
                .path(EmployeeResource.class, "getEmployees")
                .build();
    }

    private JsonObject getLinksJsonObject(URI uri, URI others) {
        return Json
                .createObjectBuilder()
                .add("_links", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("_others", others.toString())
                                .add("_self", uri.toString()).build()
                        )
                ).build();
    }

    private void validateDepartmentIfProvided(Employee employee) {

        if (employee.getDepartment() != null) {
            String code = employee.getDepartment().getDepartmentCode();

            Department departmentByCode = queryService.findDepartmentByCode(code);

            if (departmentByCode == null)
                throw new NoResultException("Department specified does not exist: " + code);

            employee.setDepartment(departmentByCode);
        }
    }

    private void validateManagerIfProvided(Employee employee) {

        if (employee.getReportsTo() != null) {

            String nie = employee.getReportsTo().getSocialSecurityNumber();
            Employee employeeByNIE = queryService.findEmployeeByNIE(nie);

            if (employeeByNIE == null)
                throw new NoResultException("Manager employee with NIE " + nie + " does not exist");

            employee.setReportsTo(employeeByNIE);
        }
    }

    private void validateIfNIEDuplicated(Employee employee) {

        String nie = employee.getSocialSecurityNumber();
        Employee employeeByNIE = queryService.findEmployeeByNIE(nie);

        if (employeeByNIE != null)
            throw new NoResultException("NIE provided already exists: " + nie);
    }


    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }


    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    public void setPersistenceService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
