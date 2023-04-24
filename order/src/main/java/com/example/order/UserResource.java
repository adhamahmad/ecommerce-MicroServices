package com.example.order;

import jakarta.ejb.EJB;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserResource {

    @EJB
    private OrderRepository orderRepository = new OrderRepository();
    @POST
    @Path("/register/{name}/{location}/{email}")
    public Response registerShipping(
            @PathParam("name") String name,
            @PathParam("email") String email,
            @PathParam("location") String location
    ){
        User user  = new User();
        user.setEmail(email);
        user.setName(name);
        user.setLocation(location);
        try {
            orderRepository.createUser(user);
            Response response = Response.status(Response.Status.CREATED).build();
            return response;
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
