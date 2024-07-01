package nl.meubelreview.com.webservices;

import nl.meubelreview.com.model.Reactie;
import nl.meubelreview.com.service.ReactieService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/reacties")
public class ReactieResource {
    private ReactieService reactieService = new ReactieService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReactie(Reactie reactie) {
        Reactie createdReactie = reactieService.createReactie(reactie);
        return Response.status(Response.Status.CREATED).entity(createdReactie).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReactieById(@PathParam("id") Long id) {
        Reactie reactie = reactieService.getReactieById(id);
        if (reactie != null) {
            return Response.ok(reactie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/review/{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReactiesByReviewId(@PathParam("reviewId") Long reviewId) {
        return Response.ok(reactieService.getReactiesByReviewId(reviewId)).build();
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReactiesByUserId(@PathParam("userId") Long userId) {
        List<Reactie> reacties = reactieService.getReactiesByUserId(userId);
        return Response.ok(reacties).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReacties() {
        return Response.ok(reactieService.getAllReacties()).build();
    }
}
