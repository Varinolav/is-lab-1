package ru.varino.lab1.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.varino.lab1.entity.MovieGenre;
import ru.varino.lab1.service.MovieOperationService;

import java.util.Map;

@Path("/movie-operations")
@Produces(MediaType.APPLICATION_JSON)
public class MovieOperationResource {

    @Inject
    private MovieOperationService service;

    @Inject
    private ChangeEvents events;

    @POST
    @Path("/delete-one-by-genre/{genre}")
    public Response deleteOneByGenre(@PathParam("genre") MovieGenre genre) {
        Integer deletedId = service.deleteOneByGenre(genre);
        if (deletedId != null) {
            events.publish();
        }
        return deletedId == null
                ? Response.noContent().build()
                : Response.ok(Map.of("deletedId", deletedId)).build();
    }

    @GET
    @Path("/golden-palms-sum")
    public Response sumGoldenPalms() {
        return Response.ok(Map.of("sum", service.sumGoldenPalms())).build();
    }

    @GET
    @Path("/count-before-genre/{genre}")
    public Response countGenreBefore(@PathParam("genre") MovieGenre genre) {
        return Response.ok(Map.of("count", service.countGenreBefore(genre))).build();
    }

    @GET
    @Path("/without-oscars")
    public Response moviesWithoutOscars() {
        return Response.ok(service.moviesWithoutOscars()).build();
    }

    @POST
    @Path("/add-oscar-to-r")
    public Response addOscarToRMovies() {
        int updated = service.addOscarToRMovies();
        if (updated > 0) {
            events.publish();
        }
        return Response.ok(Map.of("updated", updated)).build();
    }
}
