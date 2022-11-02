package com.uscellular.service.route;

import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.processors.RestConsumerProcessor;

@Component
public class CreateConsumerRoute extends RouteBuilder{

	static final Logger logger = LoggerFactory.getLogger(CreateConsumerRoute.class);
	
    @Autowired 
	RestConsumerProcessor restConsumerProcessor;

	@Override
	public void configure() throws Exception {

		//configureHttp4(config.getTrustStoreFile(), config.getTrustStorePassword(), config.getSecretPath());

        from("direct:createConsumerRoute")
		.log(":: USCC :: CreateConsumerRoute :: IN")
		.choice()
			.when(exchangeProperty("consumerType").isEqualTo("kafka-consumer"))
				.to("direct:createKafkaProducerEndpointRoute")
			.when(exchangeProperty("consumerType").isEqualTo("rest"))
				//.to("direct:createRestConsumerRoute")
                .log(":: USCC :: RestConsumerProcessor :: IN")
                .process(restConsumerProcessor)
                .log(":: USCC :: RestConsumerProcessor :: OUT")
		.end()
		.log(":: USCC :: CreateConsumerRoute :: OUT")
		;


        /* 
		.setHeader(Exchange.HTTP_QUERY, constant("content=true"))
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
		//.setHeader(Exchange.HTTP_URI,  simple("/sharing/92dd20a7-601a-4a09-876a-decfdb64cfea?content=true"))
		.process(CreateRestEndpointRoute::printHeader)
        .process(CreateRestEndpointRoute::printBody)
		.to("https4:///apicurio-studio-ws.apps.ocpsqa.uscc.com/sharing/92dd20a7-601a-4a09-876a-decfdb64cfea?content=true"
			+ "&bridgeEndpoint=true"     
			//+ "&throwExceptionOnFailure=false")
			+ "&sslContextParameters=#clientSSLParameters" 
			+ "&x509HostnameVerifier=#noopHostnameVerifier") */
			
		//.process(createRestEndpointProcessor)
//		.setBody(jsonpath("$.restEndpoint.apicurioLink"))
//		.transform(simple("${body.replace('https:', 'https4:')}"))
//		.setProperty("apicurioLink", simple("${body}"))
//		.log("apicurioLink:  ${property.apicurioLink}")
//		.setHeader("content", simple("true"))
//		.setHeader(Exchange.HTTP_QUERY, simple("content=true"))
//		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
//		.toD("${exchangeProperty.apicurioLink}"
//				+ "&bridgeEndpoint=true"
//		)
//		.log("respuesta del servicio: ${body}")
	}

	private static void printHeader(final Exchange exchange){ 
        logger.info("\t:: REQUEST HEADERS :: ");
        final Message message = exchange.getMessage();
        Iterator<String> iName = message.getHeaders().keySet().iterator();
    
        while(iName.hasNext()) {
            String key = (String) iName.next();
            logger.info("\t[" +key+ "] - {"+message.getHeader(key)+"}");
        }
    }
    private static void printBody(final Exchange exchange){
        Object req = exchange.getIn().getBody(String.class);
    
        logger.info("\t:: REQUEST BODY :: ");
        logger.info("\t"+req);
    }

	private void configureHttp4(String keyStore, String pass, String secretPath) {
        KeyStoreParameters ksp = new KeyStoreParameters();
        
		ksp.setResource(secretPath + "/" + keyStore);
        ksp.setPassword(pass);

        TrustManagersParameters tmp = new TrustManagersParameters();
        tmp.setKeyStore(ksp);

        SSLContextParameters scp = new SSLContextParameters();
        scp.setTrustManagers(tmp);

        HttpComponent httpComponent = getContext().getComponent("https4", HttpComponent.class);
        httpComponent.setSslContextParameters(scp);
        httpComponent.setX509HostnameVerifier(new NoopHostnameVerifier());
    }


}
