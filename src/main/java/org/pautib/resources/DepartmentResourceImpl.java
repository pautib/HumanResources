package org.pautib.resources;

import org.pautib.entities.Department;
import org.pautib.services.PersistenceService;
import org.pautib.services.QueryService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class DepartmentResourceImpl implements DepartmentResource{

    @Context
    private UriInfo uriInfo;
    @Inject
    private QueryService queryService;
    @Inject
    private PersistenceService persistenceService;

    public Response getDepartments() {

        List<Department> departments = queryService.getDepartments();

        return Response
                    .ok(departments)
                    .status(Response.Status.OK)
                    .build();
    }


    public Response getDepartmentById(Long id) {

        Department departmentById = queryService.findDepartmentById(id);

        if (departmentById == null) throw new NoResultException("Department with id " + id + " does not exits");

        return Response.ok(departmentById)
                        .status(Response.Status.OK)
                        .build();
    }


    public Response getDepartmentByCode(String code) {

        Department departmentByCode = queryService.findDepartmentByCode(code);

        if (departmentByCode == null) throw new NoResultException("Department with code " + code + " does not exits");

        return Response.ok(departmentByCode)
                        .status(Response.Status.OK)
                        .build();
    }


    public Response createDepartment(Department d) {

        persistenceService.saveDepartment(d);

        return Response
                    .ok(d)
                    .status(Response.Status.CREATED)
                    .build();
    }


    public Response updateDepartment(Long id, Department inputDept) {

        Department dept = queryService.findDepartmentById(id);

        if (dept == null)
            throw new NoResultException("Department with id " + id + " not found");

        persistenceService.updateDepartment(inputDept);

        URI uri = uriInfo.getAbsolutePathBuilder().build();
        URI others = getAllUri();
        JsonObject links = getJsonLinks(uri, others);

        return Response
                .ok(links.toString())
                .status(Response.Status.ACCEPTED)
                .build();
    }


    public Response deleteDepartment(String code) {

        Response departmentByCode = getDepartmentByCode(code);

        Department entity = (Department) departmentByCode.getEntity();

        persistenceService.deleteEntity(entity);

        return Response
                .ok()
                .status(Response.Status.CREATED)
                .build();
    }

    private URI getAllUri() {
        return uriInfo
                .getBaseUriBuilder()
                .path(DepartmentResource.class)
                .path(DepartmentResource.class, "getDepartments")
                .build();
    }

    private URI getUri(Department d) {
        return uriInfo
                .getAbsolutePathBuilder()
                .path(d.getId().toString())
                .build();
    }

    private JsonObject getJsonLinks(URI uri, URI others) {
        return Json
                .createObjectBuilder()
                .add("_links", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("_others", others.toString())
                                .add("_self", uri.toString()).build()
                        )
                ).build();
    }
}
