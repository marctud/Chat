package CamelActiveMq;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Camel Application
 */
public class MainApp {
	private final static Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
    	CamelContext defaultCamelContext = new DefaultCamelContext();
		// adding the routes to the camel context is done one by one
		defaultCamelContext.addRoutes( new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("activemq:test_queue")
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						LOGGER.info(exchange.getIn().getBody(String.class));
					}
				});
			}
		});
		defaultCamelContext.start();
    }

}

