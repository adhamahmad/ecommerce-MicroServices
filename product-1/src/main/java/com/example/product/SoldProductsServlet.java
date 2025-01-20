package com.example.product;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "soldProductsServlet", value = "/soldProducts-servlet")
public class SoldProductsServlet extends HttpServlet {
    @EJB
    SellingRepository sellingRepository = new SellingRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sellingName = request.getParameter("sellingName");
        List<Integer> companyProductsIds = sellingRepository.getCompanyProductsIds(sellingName);

        List<JSONObject> orderDetails = new ArrayList<>();
        for (int companyProductId : companyProductsIds) {
            String url = "http://localhost:7082/order-1.0-SNAPSHOT/api/user/company/" + companyProductId;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseBuffer = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();
            String apiResponse = responseBuffer.toString();

            if (!apiResponse.isEmpty()) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(apiResponse);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int productId = jsonObject.getInt("product-id");
                        Product product = sellingRepository.getProductDetails(productId);
                        JSONObject productJson = new JSONObject();
//                        productJson.put("id", product.getProductId());
                        productJson.put("name", product.getProductName());
                        productJson.put("price", product.getPrice());
                        productJson.put("selling-name", product.getSellingCompany().getSellingName()); // add product details to order details
                        jsonObject.put("product-details", productJson);
                        orderDetails.add(jsonObject);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        request.setAttribute("orderDetails", orderDetails);
        request.getRequestDispatcher("/soldProducts.jsp").forward(request, response);
    }
}
