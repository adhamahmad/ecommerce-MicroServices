package com.example.product;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@WebServlet(name = "getCompanyProductsServlet", value = "/getCompanyProducts-servlet")
public class CompanyProductsServlet extends HttpServlet {
    @Path("/{sellingName}")
    public void doGet(HttpServletRequest request, HttpServletResponse response,@PathParam("sellingName") String sellingName) throws IOException {

        String url = "http://localhost:6082/product-1.0-SNAPSHOT/api/selling/products/sellingName";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(con.getInputStream());
            String responseString = scanner.useDelimiter("\\A").next();
            scanner.close();
            PrintWriter out = response.getWriter();
            out.println("<h1> Customer accounts </h1>");
            out.println(responseString);
            out.close();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
