package nl.meubelreview.com.webservices;

import nl.meubelreview.com.model.Reactie;
import nl.meubelreview.com.model.Review;
import nl.meubelreview.com.model.User;
import nl.meubelreview.com.service.ReviewService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/reviews")
public class ReviewController {
    private ReviewService reviewService = new ReviewService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(Review review, @Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        if (user != null) {
            review.setUserId(user.getId());
            System.out.println("Gebruiker ID ingesteld in review: " + user.getId());
        } else {
            System.out.println("Geen gebruiker gevonden in security context");
        }
        Review savedReview = reviewService.saveReview(review);
        return Response.status(Response.Status.CREATED).entity(savedReview).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return Response.ok(reviews).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewById(@PathParam("id") Long id) {
        return reviewService.getReviewById(id)
                .map(review -> Response.ok(review).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchReviews(@QueryParam("productName") String productName) {
        List<Review> reviews = reviewService.searchReviews(productName);
        return Response.ok(reviews).build();
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewsByUserId(@PathParam("userId") Long userId) {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return Response.ok(reviews).build();
    }

    @GET
    @Path("/{id}/reacties")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReactiesByReviewId(@PathParam("id") Long id) {
        List<Reactie> reacties = reviewService.getReactiesByReviewId(id);
        return Response.ok(reacties).build();
    }

    @POST
    @Path("/{id}/reacties")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReactieToReview(@PathParam("id") Long reviewId, Reactie reactie) {
        Reactie savedReactie = reviewService.addReactieToReview(reviewId, reactie);
        return Response.status(Response.Status.CREATED).entity(savedReactie).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReview(@PathParam("id") Long id) {
        boolean isDeleted = reviewService.deleteReview(id);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}


