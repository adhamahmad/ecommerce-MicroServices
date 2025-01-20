package com.example.shipping;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "/shippingRequestsServlet", value = "/shippingRequests-servlet")
public class ShippingRequestsServlet extends HttpServlet {
    @EJB
    ShippingRepository shippingRepository = new ShippingRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String shippingName = request.getParameter("shippingName");
        List<ShippingRequest> shippingRequests = shippingRepository.getCompanyShippingRequests(shippingName);

        request.setAttribute("shippingName", shippingName);
        request.setAttribute("shippingRequests", shippingRequests);
        RequestDispatcher dispatcher = request.getRequestDispatcher("shippingRequests.jsp");
        dispatcher.forward(request, response);
    }
}
