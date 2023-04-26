package com.example.credentials;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

@Stateless
@Path("/users")
public class userResource {

    @EJB
    private UserRepository userRepository = new UserRepository();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("name") String name,
                                 @FormParam("location") String location,
                                 @FormParam("email") String email,
                                 @FormParam("password") String password) throws IOException, InterruptedException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccountType("customer");

        try {
            userRepository.createUser(user);
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/signup-servlet");
            return Response.seeOther(uri).build();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7082/order-1.0-SNAPSHOT/api/user/register/"+name+"/"+location+"/"+email))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(!(response.statusCode() == 201)){ // didn't create
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/signup-servlet");
            return Response.seeOther(uri).build();
        }
        return Response.status(Response.Status.CREATED).build();
       }
        @POST
        @Path("/login")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public Response loginUser(@FormParam("account-type") String accountType,
                                  @FormParam("email") String email,
                                  @FormParam("password") String password) throws IOException, InterruptedException {
            if(userRepository.login(email, password,accountType)) {
                //redirect based on account type
                URI uri = null;
                switch (accountType) {
                    case "customer":
                        //TODO
                        break;
                    case "selling":
                        String url = "http://localhost:6082/product-1.0-SNAPSHOT/api/selling/"+email;
                        HttpClient httpClient = HttpClient.newBuilder().build();
                        HttpRequest request = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(url))
                                .build();
                        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                        // Parse the response body into a JsonObject
                        JsonObject jsonObject = Json.createReader(new StringReader(response.body())).readObject();

                        // Extract the "selling-name" value
                        String sellingName = jsonObject.getString("selling-name");

                        uri = URI.create("http://localhost:6082/product-1.0-SNAPSHOT/sellingCompanyHomepage-servlet?sellingName="+sellingName);
                        return Response.seeOther(uri).build();
                    case "shipping":
                        //TODO
                        break;
                    default: //Admin
                        uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/adminHomepage-servlet");
                        return Response.seeOther(uri).build();
                }
            }
            //didn't login (incorrect info)
            URI uri = URI.create("http://localhost:8080/credentials-1.0-SNAPSHOT/login-servlet");
            return Response.seeOther(uri).build();
        }
    }
