package com.example.product;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "sellingCompanyHomepageServlet", value = "/sellingCompanyHomepage-servlet")
public class SellingCompanyHomepageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sellingName = request.getParameter("sellingName");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Home</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Welcome to the selling Home page</h1>");
        out.println("<p>Please choose one of the following options:</p>");
        out.println("<ul>");
        out.println("<li><a href=\"" + request.getContextPath() + "/yourProducts-servlet?sellingName=" + sellingName + "\">View your products</a></li>");
        out.println("<li><a href=\"" + request.getContextPath() + "/newProduct-servlet?sellingName=" + sellingName + "\">Add a new product</a></li>");
        out.println("<li><a href=\"" + request.getContextPath() + "/soldProducts-servlet?sellingName=" + sellingName + "\">View your sold products</a></li>");
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }
}
