package org.pautib.config;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {

        String path = String.valueOf(e.getClass());

        JsonObject jsonResponse = Json
                                    .createObjectBuilder()
                                        .add("_class", path)
                                        .add("_error", e.getLocalizedMessage())
                                    .build();

        return Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(jsonResponse)
                .build();
    }
}
