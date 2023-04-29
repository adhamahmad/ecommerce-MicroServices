package com.example.product;

import jakarta.ejb.EJB;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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
    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getShippingAccounts(){
        List<SellingCompany> companies = sellingRepository.getSellingCompanies();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (SellingCompany company : companies) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("selling-company", company.getSellingName());
            jsonObjectBuilder.add("email", company.getEmail());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }
    @GET
    @Path("/products/{selling-name}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getCompanyProducts(
            @PathParam("selling-name") String sellingName
    ){
        List<Product> products = sellingRepository.getCompanyProducts(sellingName);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Product product : products) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("product-name", product.getProductName());
            jsonObjectBuilder.add("price", product.getPrice());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getCompanyProducts(
    ){
        List<Product> products = sellingRepository.getProducts();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Product product : products) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("product-name", product.getProductName());
            jsonObjectBuilder.add("price", product.getPrice());
            jsonObjectBuilder.add("product-id", product.getProductId());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return  jsonArray;
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getCompanyName(
            @PathParam("email") String email
    ){
        String sellingName = sellingRepository.getCompanyName(email);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("selling-name", sellingName);
        return  jsonObjectBuilder.build();
    }

    @GET
    @Path("/product/{product-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getProductDetails(
            @PathParam("product-id") int productId
    ){
        Product product = sellingRepository.getProductDetails(productId);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("selling-name", product.getSellingCompany().getSellingName());
        jsonObjectBuilder.add("product-name", product.getProductName());
        jsonObjectBuilder.add("price", product.getPrice());
        return  jsonObjectBuilder.build();
    }

    @GET
    @Path("/price/{product-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getProductPrice(
            @PathParam("product-id") int productId
    ){
        Product product = sellingRepository.getProductDetails(productId);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("price", product.getPrice());
        return  jsonObjectBuilder.build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/products/{selling-name}")
    public Response addProduct(
            @PathParam("selling-name") String sellingName,
            @FormParam("product-name") String productName,
            @FormParam("price") Double price,
            @FormParam("quantity") int quantity
    ){
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setSellingCompany( sellingRepository.getCompany(sellingName));
        try {
            sellingRepository.createProduct(product);
            Response response = Response.status(Response.Status.CREATED).build();
            return response;
        }
        catch (Exception e){
            //didn't create the user (duplicate emails)
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
