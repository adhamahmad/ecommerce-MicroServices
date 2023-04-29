package com.example.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.RequestDispatcher;
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
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        // Retrieve the list of products from the product service API
        String apiUrl = "http://localhost:6082/product-1.0-SNAPSHOT/api/selling/products";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String json = reader.readLine();
        reader.close();
        connection.disconnect();

        // Parse the JSON response into a list of Map<String, Object> objects
        Gson gson = new Gson();
        Type productListType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> productList = gson.fromJson(json, productListType);

        // Store the list of products in a request attribute
        request.setAttribute("productList", productList);

        request.setAttribute("email", email); // set the email as an attribute

        // Forward the request to the JSP page for rendering
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list.jsp");
        dispatcher.forward(request, response);
    }
}