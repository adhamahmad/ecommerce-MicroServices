package com.example.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "processShippingServlet", value = "/processShipping-servlet")
public class ProcessShippingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String shippingName = request.getParameter("shippingName");
        // API retrieve productIds
        String apiUrl = "http://localhost:7082/order-1.0-SNAPSHOT/api/user/cart/" + email;
        String jsonStr;
        List<Integer> ids;
        String productIds;
        try {
            jsonStr = getJsonString(apiUrl);
            productIds = extractProductIds(jsonStr);
            ids = extractIntProductIds(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //calculate order price
        double totalPrice;
        try {
            totalPrice = calculateTotalPrice(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //JSP page to show all this info
        request.setAttribute("totalPrice", (int) totalPrice);
        request.setAttribute("shippingName", shippingName);
        request.setAttribute("productIds",productIds);
        request.setAttribute("status", "on hold");
        //get product details
        List<List<Object>> productDetailsList = new ArrayList<>();
        Client client = ClientBuilder.newClient();

        for (int productId : ids) {
            JsonObject productDetails = client.target("http://localhost:6082/product-1.0-SNAPSHOT/api/selling/product/" + productId)
                    .request(MediaType.APPLICATION_JSON)
                    .get(JsonObject.class);
            String sellingName = productDetails.getString("selling-name");
            String productName = productDetails.getString("product-name");
            Double price = productDetails.getJsonNumber("price").doubleValue();
            List<Object> productDetailsRow = Arrays.asList(sellingName, productName, price);
            productDetailsList.add(productDetailsRow);
        }
        request.setAttribute("productDetailsList", productDetailsList);
        request.getRequestDispatcher("/order-summary.jsp").forward(request, response);
    }
    private static String getJsonString(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static String extractProductIds(String jsonStr) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonStr);
        StringBuilder builder = new StringBuilder();
        for (JsonNode node : rootNode) {
            int productId = node.get("product-id").asInt();
            builder.append(productId);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1); // Remove the trailing comma
        return builder.toString();
    }

    private static List<Integer> extractIntProductIds(String jsonStr) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonStr);
        List<Integer> productIds = new ArrayList<>();
        for (JsonNode node : rootNode) {
            int productId = node.get("product-id").asInt();
            productIds.add(productId);
        }
        return productIds;
    }

    private static double calculateTotalPrice(List<Integer> ids) throws Exception {
        double totalPrice = 0.0;
        String apiUrl = "http://localhost:6082/product-1.0-SNAPSHOT/api/selling/price/";
        for (int id : ids) {
            String jsonStr = getJsonString(apiUrl + id);
            double price = extractPrice(jsonStr);
            totalPrice += price;
        }
        return totalPrice;
    }
    private static double extractPrice(String jsonStr) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonStr);
        JsonNode priceNode = rootNode.get("price");
        if (priceNode == null || !priceNode.isNumber()) {
            throw new Exception("Invalid JSON response: missing or invalid 'price' field");
        }
        return priceNode.asDouble();
    }
}
