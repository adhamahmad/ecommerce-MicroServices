# Microservices Shopping Web Application using Java EE EJBs and Spring Boot 

## This project is an online shopping application built using microservices architecture with a fully functional web interface. 
The services are
built using Java EE EJB, Spring Boot, MySQL databases and a RabbitMQ
queue. All Services are exposed as REST APIs The web interface was made
using Java servlets, JSP pages, HTML and JavaScript.

Each service is implemented as its own project. This entails it has its
own codebase and its own DB. If you have S1 Service and S2 Service then
S1 shouldn't be able to get any information from the DB of S2, but
instead should request it from the S2 Service through REST calls.

**Functional Requirements**

The shopping application will need products to be added by their selling
companies, and will need shipping companies for delivering the products,
some administrative activities need to be supported. The application
should support the following features:

**Admin Features**

-   Creation of product selling companies representative accounts.

    -   Given a range of company unique names

    -   Password for each company is auto generated

-   Creation of Shipping companies

    -   Companies should have a geographic coverage including specific
        regions (i.e., a customer cannot request shipping from a company
        that doesn't cover his geographic location)

-   Listing of customer accounts

-   Listing of shipping companies

-   Listing of selling companies representative accounts

**Selling Company Representative Features**

-   Login into the system using the generated credentials as sent by the
    admin

-   View products that are currently offered for sale.

-   View previously sold products, including information about the
    customers who bought each product and the shipping company.

-   Add new products.

**Shipping Company Features**

-   Process shipping requests as long as the customer who purchased that
    order falls within its supported geographic region(s).

-   Customers should be notified, once the shipping request is
    processed.

**Customer Features**

-   Register as a new customer through the system.

-   Login into the system using the credentials used during
    registration.

-   View current and past purchase orders.

-   Make new purchase orders. Orders should be handled in a special way
    to avoid situations of server failure.

-   Both orders processing and their shipping should be confirmed back
    to customers.

**Technical Requirements**

-   Use of 4 different bean types to fulfill the above functional
    requirements:

    1.  Stateless

    2.  Stateful

    3.  Singleton

    4.  Message Driven

-   Interface should be a web-based interface using any technology of
    your choice to simulate a functioning online shopping application
    with different users as per the above-mentioned functional
    requirements.

-   Service should be exposed as REST APIs, and you should expose your
    beans using REST to fulfill the web service REST API as appropriate.
