package nl.meubelreview.com.webservices;

import nl.meubelreview.com.model.Review;
import nl.meubelreview.com.service.ReviewService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/reviews")
public class ReviewController {
    private ReviewService reviewService = new ReviewService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(Review review) {
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
}
