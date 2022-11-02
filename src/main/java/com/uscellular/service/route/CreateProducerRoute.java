package com.uscellular.service.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.processors.RestProducerProcessor;

@Component
public class CreateProducerRoute extends RouteBuilder{

	@Autowired 
	RestProducerProcessor restProducerProcessor;
	
	@Override
	public void configure() throws Exception {

		from("direct:createProducerRoute")
		.log(":: USCC :: CreateProducerRoute :: IN")
		.choice()
			.when(exchangeProperty("producerType").isEqualTo("kafka-consumer"))
				.to("direct:createKafkaProducerEndpointRoute")
			.when(exchangeProperty("producerType").isEqualTo("rest"))
				//.to("direct:createRestProducerRoute")
				.log(":: USCC :: RestProducerProcessor :: IN")
				.process(restProducerProcessor)
				.log(":: USCC :: RestProducerProcessor :: OUT")
		.end()
		.log(":: USCC :: CreateProducerRoute :: OUT")
		;
		
	}

}
