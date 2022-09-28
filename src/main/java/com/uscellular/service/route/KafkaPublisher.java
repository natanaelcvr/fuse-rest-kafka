package com.uscellular.service.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class KafkaPublisher extends RouteBuilder {
    public static final String KAFKA_ENDPOINT = "kafkaPublisher";
	public static final String KAFKA_ROUTE_NAME = "kafkaPublisherRoute";

	@Autowired
	@Qualifier("kafkaEndpoint")
	private KafkaEndpoint kafkaEndpoint;

	@Override
	public void configure() throws Exception {

		from("direct:findPets").routeId(KAFKA_ROUTE_NAME)
            .setHeader("kafka.TOPIC", simple("webcheck-notification_events-dev01"))
			.setHeader("kafka.KEY", simple("order-details-event-listener-v1-0_${exchangeId}"))
			.to(kafkaEndpoint).id(KAFKA_ENDPOINT)
			.to("notificationEventResponseProcessor");
	}
}
