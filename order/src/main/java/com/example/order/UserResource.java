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
    ) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setLocation(location);
        try {
            orderRepository.createUser(user);
            Response response = Response.status(Response.Status.CREATED).build();
            return response;
        } catch (Exception e) {
            //didn't create the user (duplicate emails)
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getCustomerAccounts() {
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
        return jsonArray;
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getUserEmail(
            @PathParam("orderId") int orderId
    ) {
        String email = orderRepository.getUserEmail(orderId);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("email", email);
        return jsonObjectBuilder.build();
    }

    @GET
    @Path("/location/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getUserEmail(
            @PathParam("email") String email
    ) {
        String location = orderRepository.getUserByemail(email).getLocation();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("location", location);
        return jsonObjectBuilder.build();
    }

    @GET
    @Path("/order/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getUserOrders(
            @PathParam("email") String email
    ) {
//        List<Order> orders = orderRepository.getUserByemail(email).getOrders();
        List<Order> orders = orderRepository.getUserOrders(email);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Order order : orders) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("order-id", order.getOrderId());
            jsonObjectBuilder.add("products-id", order.getProductId());
            jsonObjectBuilder.add("user-email", email);
            jsonObjectBuilder.add("shipping-name", order.getShippingName());
            jsonObjectBuilder.add("order-amount", order.getOrderAmount());
            jsonObjectBuilder.add("order-status", order.getOrderStatus());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return jsonArray;
    }

    @GET
    @Path("/company/{product-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getCompanyOrders(
            @PathParam("product-id")  int productId
    ) {
        List<Order> orders = orderRepository.getCompanyOrders(productId);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Order order : orders) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("order-id", order.getOrderId());
            jsonObjectBuilder.add("product-id", productId); //product id company already sent
            jsonObjectBuilder.add("shipping-name", order.getShippingName());
            jsonObjectBuilder.add("user-email", order.getUser().getEmail());
            jsonObjectBuilder.add("user-name", order.getUser().getName());
            jsonObjectBuilder.add("location", order.getUser().getLocation());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return jsonArray;
    }

    @GET
    @Path("/cart/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getUserCart(
            @PathParam("email") String email
    ) {
        List<Integer> productIds = orderRepository.getUserCart(email);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Integer id : productIds) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("product-id", id);
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return jsonArray;
    }


    @PUT
    @Path("/cart/{email}")
    public Response resetCart(
            @PathParam("email") String email
    ) {
        try {
            orderRepository.cartReset(email);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}