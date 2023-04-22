package com.example.credentials;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Path("/admin")
public class AdminResource {
    @EJB
    private UserRepository userRepository = new UserRepository();
    @POST
    @Path("/shipping")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("shipping-name") String shippingName,
                                 @FormParam("email") String email,
                                 @FormParam("password") String password,
                                 @FormParam("supported-regions") String supportedRegions) throws IOException, InterruptedException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccountType("shipping");
        try{
            userRepository.createUser(user);
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/addShippingAccount-servlet");
            return Response.seeOther(uri).build();
        }

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:5090/shipping-1.0-SNAPSHOT/api/shipping/register/"+shippingName+"/"+email+"/"+supportedRegions))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(!(response.statusCode() == 201)){ // didn't create
            URI uri = URI.create("http://localhost:5090/credentials-1.0-SNAPSHOT/addShippingAccount-servlet");
            return Response.seeOther(uri).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/selling")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("selling-name") String sellingName,
                                 @FormParam("email") String email
                                 ) throws IOException, InterruptedException {
        User user = new User();
        user.setEmail(email);
        String password = sellingName+email; // auto generate password
        user.setPassword(password);
        user.setAccountType("selling");
        try{
            userRepository.createUser(user);
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/addSellingAccount-servlet");
            return Response.seeOther(uri).build();
        }
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:6082/product-1.0-SNAPSHOT/api/selling/register/"+sellingName+"/"+email))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(!(response.statusCode() == 201)){ // didn't create
            URI uri = URI.create("http://localhost:6082/product-1.0-SNAPSHOT/addSellingAccount-servlet");
            return Response.seeOther(uri).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/shipping")
    public String getShippingAccounts() throws IOException, InterruptedException {
        String url = "http://localhost:5090/shipping-1.0-SNAPSHOT/api/shipping/accounts";
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        List<User> credentials = userRepository.getCredentialsAccountType("shipping");
        String email ="";
        for (User user: credentials) {
            email = user.getEmail();
        }
        return responseBody+" "+credentials;
    }
}
