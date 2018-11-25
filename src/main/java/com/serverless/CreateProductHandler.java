package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dao.ProductDao;
import com.serverless.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class CreateProductHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(GetProductHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

            Product newProduct = new Product();
            newProduct.setName(body.get("name").asText());
            newProduct.setPrice((float) body.get("price").asDouble());

            // Instantite DAO
            ProductDao productDao = new ProductDao();

            // Save new product
            productDao.save(newProduct);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(newProduct)
                    .build();

        } catch (Exception e) {
            LOG.error("Error in retrieving product: " + e);
            Response responseBody = new Response("Error in saving product: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
        }
}
