<%@ page import="com.example.shipping.ShippingRequest" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shipping Requests</title>
</head>
<body>
<h1>Shipping Requests for <%= request.getAttribute("shippingName") %></h1>
<table>
    <thead>
    <tr>
        <th>Request ID</th>
        <th>Order ID</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% List<ShippingRequest> shippingRequests = (List<ShippingRequest>) request.getAttribute("shippingRequests");
        for (int i = 0; i < shippingRequests.size(); i++) { %>
    <% ShippingRequest shippingRequest = shippingRequests.get(i); %>
    <tr>
        <td><%= shippingRequest.getRequestId() %></td>
        <td><%= shippingRequest.getOrderId()%></td>
        <td>
            <form method="post" action="acceptRequest-servlet">
                <input type="hidden" name="requestId" value="<%= shippingRequest.getRequestId() %>">
                <input type="hidden" name="orderId" value="<%= shippingRequest.getOrderId() %>">
                <button type="submit">Accept</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>