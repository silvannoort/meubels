package nl.meubelreview.com.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nl.meubelreview.com.model.Reactie;
import nl.meubelreview.com.model.Review;
import nl.meubelreview.com.model.User;
import nl.meubelreview.com.repository.UserRepository;
import nl.meubelreview.com.service.ReactieService;
import nl.meubelreview.com.service.ReviewService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("authentication")
public class AuthenticationResource {


    public static final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LogonRequest logonRequest) {
        System.out.println("Authenticating user: " + logonRequest.getUsername());
        try {
            Optional<User> optionalUser = UserRepository.validateLogin(logonRequest.getUsername(), logonRequest.getPassword());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String token = createToken(user.getUsername(), user.getRole());
                System.out.println("Authentication successful for user: " + user.getUsername());
                return Response.ok(Map.of("JWT", token)).build();
            } else {
                System.out.println("Authentication failed for user: " + logonRequest.getUsername());
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            System.out.println("Authentication exception for user: " + logonRequest.getUsername());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, String role) throws JwtException {
        java.util.Calendar expiration = java.util.Calendar.getInstance();
        expiration.add(java.util.Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(key)
                .compact();
    }
    @GET
    @Path("validate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUserToken(@javax.ws.rs.core.Context javax.ws.rs.core.SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        if (user != null) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("role", user.getRole());
            userInfo.put("profilePicture", user.getProfilePicture());

            return Response.ok(userInfo).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}

