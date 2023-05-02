package com.example.shipping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
@WebServlet(name = "/acceptRequestServlet", value = "/acceptRequest-servlet")
public class AcceptRequestServlet extends HttpServlet {
    @EJB
    ShippingRepository shippingRepository = new ShippingRepository();
    private final static String QUEUE_NAME = "myQueue";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String email = "";
        try {
            // Make a GET request to the API
            URL url = new URL("http://localhost:7082/order-1.0-SNAPSHOT/api/user/"+orderId);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(url);

            // Extract the email value from the response
             email = rootNode.get("email").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String message = "Order id is " +orderId+" Shipping request accepted for email:" + email;
        // Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri("amqp://guest:guest@localhost:5672");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // Declare a queue for our messages
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // Send the message to the queue
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("Sent message: " + message);

            // Set the response status to 200 OK
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TimeoutException e) {
            // Handle exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        //TODO delete shipping request
        shippingRepository.deleteRequest(requestId);

    }
}