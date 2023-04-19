package com.example.credentials;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<form action=\"/credentials-1.0-SNAPSHOT/api/users/register\" method=\"POST\">\n" +
                "  <label for=\"email\">Email:</label>\n" +
                "  <input id=\"email\" name=\"email\" type=\"email\" required>\n" +
                "  <br>\n" +
                "  <label for=\"password\">Password:</label>\n" +
                "  <input id=\"password\" name=\"password\" type=\"password\" required>\n" +
                "  <br>\n" +
                "  <button type=\"submit\">Register</button>\n" +
                "</form>");
    }

    public void destroy() {
    }
}