<!DOCTYPE html>
<html>
<head>
    <title>Your Order Status</title>
    <meta http-equiv="refresh" content="5;url=/order-1.0-SNAPSHOT/products?email=<%= request.getParameter("email") %>">
<%--    <meta http-equiv="refresh" content="5;url=http://localhost:9999/notifications?email=<%= request.getParameter("email") %>">--%>
</head>
<body>
<h1><%= request.getAttribute("message") %></h1>
<p>You will be redirected to products page in 5 seconds...</p>
</body>
</html>