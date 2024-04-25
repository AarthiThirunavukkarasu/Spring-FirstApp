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








hello


import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class ProfileSearchRouteTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        // Add any additional setup for your Camel context here
        return context;
    }

    @Override
    protected void doPreSetup() throws Exception {
        super.doPreSetup();
        // Perform any pre-setup, such as mocking or endpoint replacement
        AdviceWithRouteBuilder.adviceWith(context, "apiProfileV2Search", a -> {
            // Replace endpoints or add mocking logic here
            a.mockEndpoints("direct:apiProfileV2SearchReturn");
        });
    }

    @Test
    void testProfileSearchRoute() throws Exception {
        // Define your test scenario and assertions here
        // For example, send a test message to the route
        // and verify the behavior using assertions
        context.start();

        // Send a test message to the route
        template.sendBody("direct-vm:apiProfileV2Search", "Test body");

        // Assert that the mock endpoint received the message
        getMockEndpoint("mock:direct:apiProfileV2SearchReturn").expectedMessageCount(1);

        // Add more assertions as needed

        // Wait for assertions to complete
        assertMockEndpointsSatisfied();

        context.stop();
    }
}

