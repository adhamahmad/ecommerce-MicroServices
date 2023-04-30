<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sold Products</title>
</head>
<body>
<h1>Sold Products</h1>
<table>
    <thead>
    <tr>
        <th>Order ID</th>
        <th>Product ID</th>
        <th>Shipping Name</th>
        <th>User Email</th>
        <th>User Name</th>
        <th>Location</th>
        <th>Product Details</th>
    </tr>
    </thead>
    <tbody>
    <%
        JSONArray orderDetails = new JSONArray(request.getAttribute("orderDetails").toString());
        for (int i = 0; i < orderDetails.length(); i++) {
            JSONObject orderDetail = orderDetails.getJSONObject(i);
    %>
    <tr>
        <td><%= orderDetail.getInt("order-id") %></td>
        <td><%= orderDetail.getInt("product-id") %></td>
        <td><%= orderDetail.getString("shipping-name") %></td>
        <td><%= orderDetail.getString("user-email") %></td>
        <td><%= orderDetail.getString("user-name") %></td>
        <td><%= orderDetail.getString("location") %></td>
        <td><%= orderDetail.getString("product-details") %></td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>