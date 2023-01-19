package io.micronaut.function.aws.proxy

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.lambda.runtime.Context
import example.Dummy
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.serde.ObjectMapper
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class BodySpec1 extends Specification {

    @Shared @AutoCleanup MicronautLambdaContainerHandler handler = new MicronautLambdaContainerHandler(
            ApplicationContext.builder().properties([
                    'micronaut.security.enabled': false,
                    'spec.name': 'BodySpec1'
            ])
    )
    @Shared Context lambdaContext = new MockLambdaContext()

    void "test singleValuesHeaders"() {

        given:
        def dummy = new Dummy()
        AwsProxyRequestBuilder builder = new AwsProxyRequestBuilder().fromJsonString(getSingleValueRequestJson())

        def objectMapper = handler.getApplicationContext().getBean(ObjectMapper)
        def bytes = objectMapper.writeValueAsBytes(builder.build())
        def output = new ByteArrayOutputStream()

        when:
        handler.proxyStream(new ByteArrayInputStream(bytes), output, lambdaContext)
        def response = objectMapper.readValue(output.toByteArray(), AwsProxyResponse)
        then:
        response.statusCode == 201
        response.getBody() == "Root=1-62e22402-3a5f246225e45edd7735c182"
    }

    private String getSingleValueRequestJson() {
        return """{
        "requestContext": {
            "elb": {
                "targetGroupArn": "arn:aws:elasticloadbalancing:us-east-2:123456789012:targetgroup/prod-example-function/e77803ebb6d2c24"
            }
        },
        "httpMethod": "GET",
        "path": "/response-body/singeHeaders",
        "queryStringParameters": {},
        "headers": {
            "accept": "*",
            "content-length": "17",
            "content-type": "application/json",
            "host": "stackoverflow.name",
            "user-agent": "curl/7.77.0",
            "x-amzn-trace-id": "Root=1-62e22402-3a5f246225e45edd7735c182",
            "x-forwarded-for": "24.14.13.186",
            "x-forwarded-port": "443",
            "x-forwarded-proto": "https",
            "x-jersey-tracing-accept": "true"
        },
        "body": null,
        "isBase64Encoded": false
}
"""
    }

    @Controller('/response-body')
    @Requires(property = 'spec.name', value = 'BodySpec1')
    static class BodyController {


        @Get(uri = "/singeHeaders")
        @Status(HttpStatus.CREATED)
        String singeHeaders(@Header String xAmznTraceId) {
            return xAmznTraceId
        }
    }

}
