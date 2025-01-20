package com.example.credentials;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@WebServlet(name = "getSellingAccountServlet", value = "/getSellingAccount-servlet")
public class GetSellingAccountServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String url = "http://localhost:8080/credentials-1.0-SNAPSHOT/api/admin/selling";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(con.getInputStream());
            String responseString = scanner.useDelimiter("\\A").next();
            scanner.close();
            PrintWriter out = response.getWriter();
            out.println("<h1> Selling accounts </h1>");
            out.println(responseString);
            out.close();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
