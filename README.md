import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class YourRouteBuilderTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new YourRouteBuilder(
            new ExceptionResponseBuilder(), 
            new APIErrorLogger(), 
            new RealtimeAPIAuditLogger()
        );
    }

    @Test
    public void testRoute() throws Exception {
        // AdviceWith to mock endpoints
        context.getRouteDefinition("APIProfilesSearchv3").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // Mock the endpoint to which the route sends messages
                mockEndpoints("direct:apiProfilesSearchV3");
            }
        });

        // Start the Camel context
        context.start();

        // Create a test message
        Exchange exchange = new DefaultExchange(context);
        exchange.setProperty(RealtimeAPIAuditLogger.PROP_API_MODULE_KEY, "testModule");
        exchange.setProperty(RealtimeAPIAuditLogger.PROP_API_REQUEST_ID_KEY, "testRequest");
        exchange.getIn().setBody("Test error message");

        // Send the test message to the input endpoint
        template.send("direct:apiProfilesSearchV3", exchange);

        // Assert that the mock endpoint received the message
        MockEndpoint mockEndpoint = getMockEndpoint("mock:direct:apiProfilesSearchV3");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.assertIsSatisfied();

        // Stop the Camel context
        context.stop();
    }
}
