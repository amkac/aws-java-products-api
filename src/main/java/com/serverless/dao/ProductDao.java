package com.serverless.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.model.Product;
import com.serverless.utils.DynamoDBAdapter;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ProductDao {

    private Logger logger = Logger.getLogger(this.getClass());
    private static final String PRODUCTS_TABLE_NAME = System.getenv("PRODUCTS_TABLE_NAME");

    DynamoDBAdapter db_adapter;
    DynamoDBMapper mapper;
    AmazonDynamoDB client;

    public ProductDao() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
                .build();
        this.db_adapter = DynamoDBAdapter.getInstance();
        this.client = this.db_adapter.getDbClient();
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    public List<Product> list() throws IOException {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Product> results = this.mapper.scan(Product.class, scanExp);
        for (Product p : results) {
            logger.info("Products - list(): " + p.toString());
        }
        return results;
    }

    public Product get(String id) throws IOException {
        Product product = null;

        HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
        av.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Product> queryExp = new DynamoDBQueryExpression<Product>()
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(av);

        PaginatedQueryList<Product> result = this.mapper.query(Product.class, queryExp);
        if (result.size() > 0) {
            product = result.get(0);
            logger.info("Products - get(): product - " + product.toString());
            logger.info("product - name " + product.getName());

        } else {
            logger.info("Products - get(): product - Not Found.");
        }
        return product;
    }

    public void save(Product product) throws IOException {
        logger.info("Products - save(): " + product.toString());
        this.mapper.save(product);
    }

    public Boolean delete(String id) throws IOException {
        Product product = null;

        // get product if exists
        product = get(id);
        if (product != null) {
            logger.info("Products - delete(): " + product.toString());
            this.mapper.delete(product);
        } else {
            logger.info("Products - delete(): product - does not exist.");
            return false;
        }
        return true;
    }

}
