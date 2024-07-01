package nl.meubelreview.com.webservices;

import nl.meubelreview.com.model.User;
import nl.meubelreview.com.repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UserResource {

    private UserRepository userRepository = new UserRepository();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        if (userRepository.getUserByName(user.getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
        }
        userRepository.saveUser(user);
        return Response.ok(user).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return Response.ok(users).build();
    }
}
