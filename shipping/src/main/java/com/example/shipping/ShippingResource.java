package com.example.shipping;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

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
}