package com.example.order;

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

@WebServlet(name = "myOrdersServlet", value = "/myOrders-servlet")
public class MyOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email"); // get email parameter from request
        String url = "http://localhost:7082/order-1.0-SNAPSHOT/api/user/order/" + email; // specify API endpoint URL

        // create a new URL object and open a connection to the API endpoint
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // get the response code and response data from the API call
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();

        // set the orders attribute on the request object and forward to a JSP page for display
        request.setAttribute("orders", responseBuffer.toString());
        RequestDispatcher rd = request.getRequestDispatcher("myOrders.jsp");
        rd.forward(request, response);
    }
}
