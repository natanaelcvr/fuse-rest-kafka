package com.uscellular.service.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class KafkaPublisherRoute extends RouteBuilder {

	public static final String KAFKA_ENDPOINT = "kafkaEndpoint";
	public static final String KAFKA_ROUTE_NAME = "kafkaRoute";

	@Autowired
	@Qualifier("kafkaEndpoint")
	private KafkaEndpoint kafkaEndpoint;

	@Override
	public void configure() throws Exception {

		from("direct:kafkaPublisherRoute").routeId(KAFKA_ROUTE_NAME)
			.to(kafkaEndpoint).id(KAFKA_ENDPOINT)
			.log("message sent");
	}

}
