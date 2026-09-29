package ru.varino.lab1.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.varino.lab1.entity.Location;
import ru.varino.lab1.service.LocationService;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    private LocationService locationService;

    @Inject
    private ChangeEvents events;

    @POST
    public Response create(@Valid Location location) {
        locationService.save(location);
        events.publish();
        return Response.status(Response.Status.CREATED).entity(location).build();
    }

    @GET
    public Response getLocations() {
        return Response.ok(locationService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getLocation(@PathParam("id") Long id) {
        Location location = locationService.findById(id);
        return location == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(location).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid Location location) {
        Location updatedLocation = locationService.update(id, location);
        if (updatedLocation == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        events.publish();
        return Response.ok(updatedLocation).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (!locationService.deleteById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        events.publish();
        return Response.noContent().build();
    }
}
