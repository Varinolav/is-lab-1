package ru.varino.lab1.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.varino.lab1.entity.Coordinates;
import ru.varino.lab1.service.CoordinatesService;

@Path("/coordinates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoordinatesResource {

    @Inject
    private CoordinatesService coordinatesService;

    @Inject
    private ChangeEvents events;

    @POST
    public Response create(@Valid Coordinates coordinates) {
        coordinatesService.save(coordinates);
        events.publish();
        return Response.status(Response.Status.CREATED).entity(coordinates).build();
    }

    @GET
    public Response getCoordinates() {
        return Response.ok(coordinatesService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCoordinates(@PathParam("id") Long id) {
        Coordinates coordinates = coordinatesService.findById(id);
        return coordinates == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(coordinates).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid Coordinates coordinates) {
        Coordinates updatedCoordinates = coordinatesService.update(id, coordinates);
        if (updatedCoordinates == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        events.publish();
        return Response.ok(updatedCoordinates).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (!coordinatesService.deleteById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        events.publish();
        return Response.noContent().build();
    }
}
