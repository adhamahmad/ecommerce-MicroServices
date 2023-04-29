package com.example.order;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "customerHomepageServlet", value = "/customerHomepage-servlet")
public class CustomerHomepageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Home</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Welcome to the customer Home page</h1>");
        out.println("<p>Please choose one of the following options:</p>");
        out.println("<ul>");
        out.println("<li><a href=\"" + request.getContextPath() + "/products?email=" + email + "\">Make a new order</a></li>");
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }
}
