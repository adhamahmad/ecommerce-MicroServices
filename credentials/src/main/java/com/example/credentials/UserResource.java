package com.example.credentials;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @EJB
    private UserRepository userRepository;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("account-type") String accountType,
                                 @FormParam("email") String email,
                                 @FormParam("password") String password) {
        User user = new User();
        user.setAccountType(accountType);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.createUser(user);
        return Response.status(Response.Status.CREATED).build();
    }
}
