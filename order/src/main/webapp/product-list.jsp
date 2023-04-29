        <%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<!-- product-list.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Product List</title>
</head>
<body>
<h1>Product List</h1>
<ul>
  <% String email = null;
    for (Map<String, Object> product : (List<Map<String, Object>>) request.getAttribute("productList")) { %>
  <%
    email = (String) request.getAttribute("email");
  %>
  <li>
    Product Name: <%=product.get("product-name")%> - Price: <%=product.get("price")%>
    <form method="post" action="http://localhost:7082/order-1.0-SNAPSHOT/cart?email=<%=email%>">
      <input type="hidden" name="productId" value="<%=product.get("product-id")%>">
      <button type="submit">Add to Cart</button>
    </form>
  </li>
  <% } %>
</ul>
<a href="http://localhost:7082/order-1.0-SNAPSHOT/chooseShipping-servlet?email=<%=email%>">Choose shipping company</a>
</body>
</html>