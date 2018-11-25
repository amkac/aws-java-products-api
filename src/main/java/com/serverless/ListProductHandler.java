package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.dao.ProductDao;
import com.serverless.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ListProductHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(ListProductHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        // Instatiate DAO
        ProductDao productDao = new ProductDao();
        try {
            List<Product> productList = productDao.list();

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(productList)
                    .build();
        } catch (Exception e) {
            LOG.error("Error in retrieving product: " + e);
            Response responseBody = new Response("Error in listing products: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }
}
