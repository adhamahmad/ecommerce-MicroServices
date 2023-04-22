package com.example.product;

import jakarta.ejb.EJB;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/selling")
public class SellingResource {
    @EJB
    private SellingRepository sellingRepository = new SellingRepository();
    @POST
    @Path("/register/{selling-name}/{email}")
    public Response registerShipping(
            @PathParam("selling-name") String sellingName,
            @PathParam("email") String email
    ){
        SellingCompany sellingCompany = new SellingCompany();
        sellingCompany.setEmail(email);
        sellingCompany.setSellingName(sellingName);
        try {
            sellingRepository.createSelling(sellingCompany);
            Response response = Response.status(Response.Status.CREATED).build();
            return response;
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
