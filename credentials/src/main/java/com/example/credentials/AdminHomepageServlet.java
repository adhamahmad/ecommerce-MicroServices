package com.example.credentials;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "adminHomepageServlet", value = "/adminHomepage-servlet")
public class AdminHomepageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Home</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Welcome to the Home page</h1>");
        out.println("<p>Please choose one of the following options:</p>");
        out.println("<ul>");
        out.println("<li><a href=\"" + request.getContextPath() + "/addSellingAccount-servlet\">Add a selling account</a></li>");
        out.println("<li><a href=\"" + request.getContextPath() + "/addShippingAccount-servlet\">Add a shipping account</a></li>");
        out.println("<li><a href=\"" + request.getContextPath() + "/getShippingAccount-servlet\">Get shipping accounts</a></li>");
        out.println("<li><a href=\"" + request.getContextPath() + "/getSellingAccount-servlet\">Get selling accounts</a></li>");
        out.println("<li><a href=\"" + request.getContextPath() + "/getCustomerAccount-servlet\">Get customer accounts</a></li>");
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }
}
