package ru.varino.lab1.resource;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;

@Provider
public class ForeignKeyConflictMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        if (!"23503".equals(exception.getSQLState())) {
            return Response.serverError().build();
        }

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of("message", "Объект используется другими записями. Сначала измените связанные записи."))
                .build();
    }
}
