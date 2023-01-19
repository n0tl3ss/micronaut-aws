package example;

import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport(AwsProxyRequest.class)
@Introspected(classes = {
    AwsProxyRequest.class
})
public class Dummy {
}
