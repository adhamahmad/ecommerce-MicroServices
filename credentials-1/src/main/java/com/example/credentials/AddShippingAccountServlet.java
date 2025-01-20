package com.example.credentials;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "addShippingAccountServlet", value = "/addShippingAccount-servlet")
public class AddShippingAccountServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<form action=\"/credentials-1.0-SNAPSHOT/api/admin/shipping\" method=\"POST\">\n" +
                "  <label for=\"shipping-name\">Shipping-name:</label>\n" +
                "  <input id=\"shipping-name\" name=\"shipping-name\" type=\"shipping-name\" required>\n" +
                "  <br>\n" +
                "  <label for=\"email\">Email:</label>\n" +
                "  <input id=\"email\" name=\"email\" type=\"email\" required>\n" +
                "  <br>\n" +
                "  <label for=\"password\">Password:</label>\n" +
                "  <input id=\"password\" name=\"password\" type=\"password\" required>\n" +
                "  <br>\n" +
                "  <label for=\"supported-regions\">supported-regions(enter them comma seperated):</label>\n" +
                "  <input id=\"supported-regions\" name=\"supported-regions\" type=\"supported-regions\" required>\n" +
                "  <br>\n" +
                "  <button type=\"submit\">Add shipping account</button>\n" +
                "</form>");
    }

    public void destroy() {
    }
}