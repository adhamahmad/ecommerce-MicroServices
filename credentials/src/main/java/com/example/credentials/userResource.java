package com.example.credentials;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/users")
public class userResource {

    @EJB
    private UserRepository userRepository = new UserRepository();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("account-type") @DefaultValue("") String accountType,
                                 @FormParam("email") String email,
                                 @FormParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        if(!accountType.isEmpty()){ // not customer signup
            user.setAccountType(accountType);
        }else{
            user.setAccountType("customer");
        }

        try {
            userRepository.createUser(user);
            Response response = Response.status(Response.Status.CREATED).build();
            return response;
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/signup-servlet");
            return Response.seeOther(uri).build();
        }
       }
        @POST
        @Path("/login")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public Response loginUser(@FormParam("account-type") String accountType,
                                  @FormParam("email") String email,
                                  @FormParam("password") String password){
            if(userRepository.login(email, password,accountType)) {
                //TODO redirect based on account type
                return Response.ok().build();
            }
            //didn't login (incorrect info)
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/login-servlet");
            return Response.seeOther(uri).build();
        }
    }
