package com.example.order;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@WebServlet(name = "checkoutServlet", value = "/checkout-servlet")
public class CheckoutServlet extends HttpServlet {
    @EJB
    OrderRepository orderRepository = new OrderRepository();
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
        } //reset cart
//        String url2 = "http://localhost:7082/order-1.0-SNAPSHOT/api/user/cart/" + email;
//        URL obj2 = new URL(url2);
//        HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
//        con2.setRequestMethod("PUT");
//        int responseCode2 = con2.getResponseCode();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request2 = new Request.Builder()
                .url("http://localhost:7082/order-1.0-SNAPSHOT/api/user/cart/"+email)
                .method("PUT", body)
                .build();
        Response response2 = client.newCall(request2).execute();

        // create shipping request
        int orderID = orderRepository.getLastOrderId(email);
        System.out.println(orderID);
        String url3 = "http://localhost:5090/shipping-1.0-SNAPSHOT/api/shipping/request/" + shippingName+"/"+orderID;
        URL obj3 = new URL(url3);
        HttpURLConnection con3 = (HttpURLConnection) obj3.openConnection();
        con3.setRequestMethod("POST");
        int responseCode3 = con3.getResponseCode();
        //pass email and redirect after 5 secs to product list again passing the email
        request.setAttribute("email", email);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}

