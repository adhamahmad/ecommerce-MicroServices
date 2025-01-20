package com.example.order;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/orders")
@Stateful
public class OrderResource {
    @EJB
    private OrderRepository orderRepository = new OrderRepository();
    @POST
    @Path("/checkout/{email}/{productIds}/{shipping-name}/{totalPrice}")
    @Transactional
    public Response processOrder(
            @PathParam("productIds") String productIds,
            @PathParam("email") String email,
            @PathParam("shipping-name") String shippingName,
            @PathParam("totalPrice") int totalPrice
    ) {
        Order order = new Order();
        order.setOrderAmount(totalPrice);
        order.setShippingName(shippingName);
        order.setProductId(productIds);
        order.setUser(orderRepository.getUserByemail(email));
            try {
                processOrder(order);
            } catch (Exception e) {
                // log the exception
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        return Response.ok().build();
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void processOrder(Order order) throws Exception {
        order.setOrderStatus("pending shipment");
        orderRepository.createOrder(order);
        orderRepository.flushEntityManager();
    }
}
