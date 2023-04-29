package com.example.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    @EJB
    private OrderRepository orderRepository = new OrderRepository();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        // Retrieve the product ID from the form data
        double productId = Double.parseDouble(request.getParameter("productId"));
        int productIdInt = (int) productId;

//        // Retrieve the product details from the product service API
//        String apiUrl = "http://localhost:6082/product-1.0-SNAPSHOT/api/selling/product/" + productIdInt;
//        URL url = new URL(apiUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("Accept", "application/json");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String json = reader.readLine();
//        reader.close();
//        connection.disconnect();
//
//        // Parse the JSON response into a Map<String, Object> representing the product
//        Gson gson = new Gson();
//        Type productType = new TypeToken<Map<String, Object>>(){}.getType();
//        Map<String, Object> product = gson.fromJson(json, productType);

        // Add the product to the user's cart (entry in db with product id and user email)
        // TODO
            Cart cart = new Cart();
            cart.setProductId(productIdInt);
            cart.setUser(orderRepository.getUserByemail(email));
            orderRepository.createCart(cart);
        // Redirect the user back to the product list page in the product service
        response.sendRedirect("http://localhost:7082/order-1.0-SNAPSHOT/products?email="+email);
    }
}