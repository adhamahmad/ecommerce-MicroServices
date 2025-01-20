<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Choose Shipping</title>
</head>
<body>
<h1>Choose Shipping</h1>
<form action="processShipping-servlet?email=<%= request.getParameter("email") %>" method="post">
    <label for="shippingName">Select a shipping company:</label>
    <select id="shippingName" name="shippingName">
        <% for (String name : (List<String>) request.getAttribute("shippingNames")) { %>
        <option value="<%= name %>"><%= name %></option>
        <% } %>
    </select>
    <br>
    <input type="submit" value="Next">
</form>
</body>
</html>