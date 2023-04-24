package com.example.credentials;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(name = "signupServlet", value = "/signup-servlet")
public class SignupServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<form action=\"/credentials-1.0-SNAPSHOT/api/users/register\" method=\"POST\">\n" +
                "  <label for=\"name\">Name:</label>\n" +
                "  <input id=\"name\" name=\"name\" type=\"name\" required>\n" +
                "  <br>\n" +
                "  <label for=\"email\">Email:</label>\n" +
                "  <input id=\"email\" name=\"email\" type=\"email\" required>\n" +
                "  <br>\n" +
                "  <label for=\"password\">Password:</label>\n" +
                "  <input id=\"password\" name=\"password\" type=\"password\" required>\n" +
                "  <br>\n" +
                "  <label for=\"location\">Location:</label>\n" +
                "  <input id=\"location\" name=\"location\" type=\"location\" required>\n" +
                "  <br>\n" +
                "  <button type=\"submit\">Sign up</button>\n" +
                "</form>");
    }

}
