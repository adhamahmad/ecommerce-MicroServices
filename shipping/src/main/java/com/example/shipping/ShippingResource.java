package com.example.shipping;

import jakarta.ejb.EJB;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/shipping")
public class ShippingResource {
    @EJB
    private ShippingRepository shippingRepository = new ShippingRepository();

    @POST
    @Path("/register/{shipping-name}/{email}/{supported-regions}")
    public Response registerShipping(
            @PathParam("shipping-name") String shippingName,
            @PathParam("email") String email,
            @PathParam("supported-regions") String supportedRegions
    ){
        ShippingCompany shippingCompany = new ShippingCompany();
        shippingCompany.setEmail(email);
        shippingCompany.setShippingName(shippingName);
        shippingCompany.setSupportedRegions(supportedRegions);
        try {
            shippingRepository.createShipping(shippingCompany);
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
    public JsonArray getShippingAccounts(){
        List<ShippingCompany> companies = shippingRepository.getShippingCompanies();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (ShippingCompany company : companies) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("shipping-name", company.getShippingName());
            jsonObjectBuilder.add("email", company.getEmail());
            jsonObjectBuilder.add("supported-regions", company.getSupportedRegions());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }

    @GET
    @Path("/accounts/{region}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getShippingCompanies(
            @PathParam("region") String region
    ){
        List<ShippingCompany> companies = shippingRepository.getShippingCompanies(region);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (ShippingCompany company : companies) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("shipping-name", company.getShippingName());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }
}