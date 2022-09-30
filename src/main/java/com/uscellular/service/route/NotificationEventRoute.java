package com.uscellular.service.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.processors.NotificationSMSEventRequestProcessor;


@Component
public class NotificationEventRoute extends RouteBuilder {

	@Value("${backend.service.name}")
	protected String backendServiceName;

	@Autowired
	NotificationSMSEventRequestProcessor notificationSMSEventRequestProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:findPets").routeId("notificationEventRoute")
			.setProperty(backendServiceName, simple("NotificationEvent"))	
			.removeHeaders("*", "CamelKafkaManualCommit")
			.setHeader(KafkaConstants.TOPIC, simple("{{kafka.topic}}"))
			.setHeader(KafkaConstants.KEY, simple("order-details-event-listener-v1-0_${exchangeId}"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/vnd.kafka.json.v2+json"))
			.process(notificationSMSEventRequestProcessor)
			.to("direct:kafkaPublisherRoute");
	}

}
