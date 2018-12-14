# aws-java-products-api

Simple AWS REST api created using Serverless Framework. The Api is deployed in AWS and data is stored in a DynamoDB table.

# API Endpoints

* `POST /products`
* `GET /products`
* `GET /products/{id}`
* `DELETE /products/{id}`

# Pre-requisites
* `node` and `npm`.
* Serverless Framework with an AWS acount set up.
* JDK.
* Apache Maven.

# Build the project

```
mvn clean install
```

# Deploy with serverless

```
sls deploy
```

# Test API with the API Gateway Console
* Sign in to the API Gateway console at https://console.aws.amazon.com/apigateway.
* In the box that contains the name of the API for the method, choose **Resources**.
* In the Resources pane, choose the method you want to test.
* In the Method Execution pane, in the Client box, choose **TEST**.
* Choose **Test**.
