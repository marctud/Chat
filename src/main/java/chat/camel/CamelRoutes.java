package chat.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelRoutes {

	private static ProducerTemplate template = createProducerTemplate();

	private static ProducerTemplate createProducerTemplate() {
		CamelContext defaultCamelContext = new DefaultCamelContext();
		// adding the routes to the camel context is done one by one
		try {
			defaultCamelContext.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("direct:test").to("activemq:test_queue");
				}
			});
			defaultCamelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();
		return producerTemplate;
	}
	
	public static void sendMessage(String message) {
		template.sendBody(message);
	}
	
	public static void main(String[] args) {
		template.sendBody("direct:test", "test");
	}

}
