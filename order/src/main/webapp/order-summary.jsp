<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Order Summary</title>
</head>
<body>
<h1>Order Summary</h1>
<p>Shipping Name: <%= request.getParameter("shippingName") %></p>
<p>Total Price: <%= request.getAttribute("totalPrice") %></p>
<p>Order Status: <%= request.getAttribute("status") %></p>
<table>
  <thead>
  <tr>
    <th>Selling Company</th>
    <th>Product Name</th>
    <th>Price</th>
  </tr>
  </thead>
  <tbody>
  <% List productDetailsList = (List) request.getAttribute("productDetailsList");
    for (Object productDetails : productDetailsList) {
      String sellingName = (String) ((List) productDetails).get(0);
      String productName = (String) ((List) productDetails).get(1);
      Double price = (Double) ((List) productDetails).get(2);
  %>
  <tr>
    <td><%= sellingName %></td>
    <td><%= productName %></td>
    <td><%= price %></td>
  </tr>
  <% } %>
  </tbody>
</table>
<form method="post" action="checkout-servlet">
  <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
  <input type="hidden" name="shipping-name" value="<%= request.getParameter("shippingName") %>">
  <input type="hidden" name="productIds" value="<%= request.getAttribute("productIds") %>">
  <input type="hidden" name="totalPrice" value="<%= request.getAttribute("totalPrice") %>">
  <input type="submit" value="Checkout">
</form>
</body>
</html>