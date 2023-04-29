<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orders</title>
</head>
<body>
<table id="ordersTable">
    <thead>
    <tr>
        <th>Order ID</th>
        <th>Products ID</th>
        <th>User Email</th>
        <th>Shipping Name</th>
        <th>Order Amount</th>
        <th>Order Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<script>
    var orders = ${orders}; // get orders JSON data from JSP attribute
    var ordersTableBody = document.getElementById("ordersTable").getElementsByTagName("tbody")[0];

    // iterate over orders array and append a row to the table for each order
    orders.forEach(function(order) {
        var row = ordersTableBody.insertRow(-1);
        row.insertCell(0).innerHTML = order["order-id"];
        row.insertCell(1).innerHTML = order["products-id"];
        row.insertCell(2).innerHTML = order["user-email"];
        row.insertCell(3).innerHTML = order["shipping-name"];
        row.insertCell(4).innerHTML = order["order-amount"];
        row.insertCell(5).innerHTML = order["order-status"];
    });
</script>
</body>
</html>