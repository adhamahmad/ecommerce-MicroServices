package com.example.order;

import jakarta.ejb.EJB;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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

    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getCustomerAccounts(){
        List<User> users = orderRepository.getUsers();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (User user : users) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("name", user.getName());
            jsonObjectBuilder.add("email", user.getEmail());
            jsonObjectBuilder.add("location", user.getLocation());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }

    @GET
    @Path("/location/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getUserLocation(
            @PathParam("email") String email
    ){
        String location = orderRepository.getUserByemail(email).getLocation();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("location", location);
        return  jsonObjectBuilder.build();
    }
    @GET
    @Path("/cart/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getUserCart(
            @PathParam("email") String email
    ){
        List<Integer> productIds = orderRepository.getUserCart(email);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Integer id : productIds) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("product-id", id);
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }
}
