package com.uscellular.service.route.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.entity.MvnProperties;



/**
 * 
 * 
 * @author nvasc001
 *
 */
@Component
public class CreateArtifectProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

        MvnProperties mvnProperties = exchange.getIn().getBody(MvnProperties.class);

		exchange.setProperty("request", mvnProperties);
        exchange.setProperty("producerType", mvnProperties.getProducerType());
		exchange.setProperty("consumerType", mvnProperties.getConsumerType());

		//exchange.setProperty("finalProjectPath", finalProjectPath);

	}
}
