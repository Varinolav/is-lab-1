package ru.varino.lab1.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.varino.lab1.entity.Person;
import ru.varino.lab1.service.PersonService;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    private PersonService personService;

    @Inject
    private ChangeEvents events;

    @POST
    public Response create(@Valid Person person) {
        personService.save(person);
        events.publish();
        return Response.status(Response.Status.CREATED).entity(person).build();
    }

    @GET
    public Response getPersons() {
        return Response.ok(personService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        Person person = personService.findById(id);
        return person == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(person).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid Person person) {
        Person updatedPerson = personService.update(id, person);
        if (updatedPerson == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        events.publish();
        return Response.ok(updatedPerson).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (!personService.deleteById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        events.publish();
        return Response.noContent().build();
    }
}
