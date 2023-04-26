package com.example.product;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "newProductServlet", value = "/newProduct-servlet")
public class NewProductServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sellingName = request.getParameter("sellingName");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //TODO call the correct api
        out.println("<form action=\"/product-1.0-SNAPSHOT/api/selling/products/" + sellingName + "\" method=\"POST\">"+
                "  <label for=\"product-name\">Product name:</label>\n" +
                "  <input id=\"product-name\" name=\"product-name\" type=\"product-name\" required>\n" +
                "  <br>\n" +
                "  <label for=\"price\">Price:</label>\n" +
                "  <input id=\"price\" name=\"price\" type=\"number\" step=\"any\" min=\"0\" required>\n" +
                "  <br>\n" +
                "  <label for=\"quantity\">Quantity:</label>\n" +
                "  <input id=\"quantity\" name=\"quantity\" type=\"number\" step=\"1\" min=\"0\" required>\n" +
                "  <br>\n" +
                "  <button type=\"submit\">Add product</button>\n" +
                "</form>");
    }
}
