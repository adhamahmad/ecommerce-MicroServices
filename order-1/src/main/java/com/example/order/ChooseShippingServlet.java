package com.example.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "chooseShippingServlet", value = "/chooseShipping-servlet")
public class ChooseShippingServlet extends HttpServlet {

    private String makeApiRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder responseBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
        }

        return responseBuilder.toString();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        //get user location
        String apiUrl = "http://localhost:7082/order-1.0-SNAPSHOT/api/user/location/"+email;
        String responseJson = makeApiRequest(apiUrl);
        // Parse the JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseJsonNode = mapper.readTree(responseJson);
        // Store the user location value as a string
        String userLocation = responseJsonNode.get("location").asText();
        //get covering shipping companies
        String apiUrl2 = "http://localhost:5090/shipping-1.0-SNAPSHOT/api/shipping/accounts/"+userLocation;
        String responseJson2 = makeApiRequest(apiUrl2);
        // Parse the JSON response
        JsonNode shippingCompaniesJsonNode = mapper.readTree(responseJson2);

        List<String> shippingNames = new ArrayList<>();
        for (JsonNode shippingCompanyJsonNode : shippingCompaniesJsonNode) {
            String shippingName = shippingCompanyJsonNode.get("shipping-name").asText();
            shippingNames.add(shippingName);
        }

        // Store the email and shipping names as request attributes
        request.setAttribute("email", email);
        request.setAttribute("shippingNames", shippingNames);
        // Forward the request to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/choose-shipping.jsp");
        dispatcher.forward(request, response);
    }}