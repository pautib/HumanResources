package org.pautib.config;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<NoResultException> {

    @Override
    public Response toResponse(NoResultException e) {

        String path = String.valueOf(e.getClass());

        JsonObject jsonResponse = Json
                                    .createObjectBuilder()
                                        .add("_class", path)
                                        .add("_error", e.getLocalizedMessage())
                                    .build();

        return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(jsonResponse)
                .build();
    }
}
