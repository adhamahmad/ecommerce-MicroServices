package com.example.credentials;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<form action=\"/credentials-1.0-SNAPSHOT/api/users/login\" method=\"POST\">\n" +
                "  <label for=\"account-type\">Account type:</label>\n" +
                "  <input id=\"account-type\" name=\"account-type\" type=\"account-type\" required>\n" +
                "  <br>\n" +
                "  <label for=\"email\">Email:</label>\n" +
                "  <input id=\"email\" name=\"email\" type=\"email\" required>\n" +
                "  <br>\n" +
                "  <label for=\"password\">Password:</label>\n" +
                "  <input id=\"password\" name=\"password\" type=\"password\" required>\n" +
                "  <br>\n" +
                "  <button type=\"submit\">Register</button>\n" +
                "</form>");
    }
}
