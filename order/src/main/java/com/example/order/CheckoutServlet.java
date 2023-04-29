package com.example.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@WebServlet(name = "checkoutServlet", value = "/checkout-servlet")
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String shippingName = request.getParameter("shipping-name");
        String productIds = request.getParameter("productIds");
        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));

        String url = "http://localhost:7082/order-1.0-SNAPSHOT/api/orders/checkout/" + email + "/" + productIds + "/" + shippingName + "/" + totalPrice;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            request.setAttribute("message", "Order confirmed successfully.");
        } else {
            request.setAttribute("message", "Server error occurred. Please try again later.");
        } //TODO reset cart
        String url2 = "http://localhost:7082/order-1.0-SNAPSHOT/api/user/cart/" + email;
        URL obj2 = new URL(url2);
        HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
        con2.setRequestMethod("PUT");
        int responseCode2 = con2.getResponseCode();
        //TODO pass email and redirect after 5 secs to product list again passing the email
        request.setAttribute("email", email);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}

