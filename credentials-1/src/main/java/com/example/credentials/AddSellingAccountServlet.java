package com.example.credentials;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "addSellingAccountServlet", value = "/addSellingAccount-servlet")
public class AddSellingAccountServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<form action=\"/credentials-1.0-SNAPSHOT/api/admin/selling\" method=\"POST\">\n" +
                "<label for=\"selling-name\">Selling Name:</label>" +
                "<select id=\"selling-name\" name=\"selling-name\">" +
                "<option value=\"samsung\">Samsung</option>" +
                "<option value=\"apple\">Apple</option>" +
                "<option value=\"rolex\">Rolex</option>" +
                "<option value=\"nike\">Nike</option>" +
                "</select>" +
                "<br>\n" +
                "<label for=\"email\">Email:</label>\n" +
                "<input id=\"email\" name=\"email\" type=\"email\" required>\n" +
                "<br>\n" +
                "<button type=\"submit\">Add selling account</button>\n" +
                "</form>");
    }
}
