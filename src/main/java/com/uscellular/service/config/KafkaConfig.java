package com.uscellular.service.config;

import java.util.HashSet;

import org.apache.camel.CamelContext;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.apache.camel.component.kafka.KafkaHeaderFilterStrategy;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Kafka. It can contains common bean definitions for
 * the different Camel components.
 * 
 * @author jiler001
 *
 */
@Configuration
public class KafkaConfig {

	@Value("${kafka.brokers}")
	protected String brokers;
	@Value("${kafka.publisher.clientId}")
	protected String clientId;
	@Value("${ssl.truststore.secrets-path}")
	protected String secretPath;
	@Value("${ssl.truststore.jks-file}")
	protected String trustStoreFile;
	@Value("${ssl.truststore.jks-file-password}")
	protected String trustStorePassword;
	@Value("${kafka.saslJaasConfig.kerberos.keytabfile}")
	protected String kerberosKeytabFile;
	@Value("${kafka.saslKerberosServiceName}")
	protected String saslKerberosServiceName;
	@Value("${kafka.saslJaasConfig.kerberos.principal}")
	protected String kerberosPrincipal;
	@Value("${kafka.max.block.ms}")
	protected Integer maxBlockMs;


	@Bean(name = "kafkaEndpoint")
	protected KafkaEndpoint createKafkaEndpoint(CamelContext camelContext) {
		KafkaEndpoint kafka = camelContext.getEndpoint("kafka:webcheck-notification_events-dev01", KafkaEndpoint.class);

		String keyTab = "'" + secretPath + "/" + kerberosKeytabFile + "'";

		KafkaConfiguration config = new KafkaConfiguration();
		config.setBrokers(brokers);
		config.setClientId(clientId);
		/* config.setSecurityProtocol(SecurityProtocol.SASL_SSL.name());
		config.setSaslJaasConfig(
				"com.sun.security.auth.module.Krb5LoginModule required useKeyTab=true      storeKey=true keyTab="
						+ keyTab + "      principal='" + kerberosPrincipal
						+ "'      useTicketCache=false doNotPrompt=true debug=true;"); */
		config.setSslTruststoreLocation(secretPath + "/" + trustStoreFile);
		config.setSslTruststorePassword(trustStorePassword);
		config.setSaslKerberosServiceName(saslKerberosServiceName); 
		config.setPartitionAssignor("org.apache.kafka.clients.consumer.RangeAssignor");
		config.setMaxBlockMs(maxBlockMs);
		config.setRecordMetadata(true);

		KafkaHeaderFilterStrategy khfs = new KafkaHeaderFilterStrategy();
		khfs.setLowerCase(true);
		HashSet<String> inFilter = new HashSet<>();
		inFilter.add("accept");
		inFilter.add("accept-encoding");
		inFilter.add("cache-control");
		inFilter.add("connection");
		inFilter.add("content-length");
		inFilter.add("postman-token");
		inFilter.add("user-agent");
		khfs.setInFilter(inFilter);
		config.setHeaderFilterStrategy(khfs);

		config.setAutoOffsetReset("earliest");
		config.setAutoCommitEnable(false);
		config.setAllowManualCommit(true); 

		kafka.setConfiguration(config);
		kafka.setCamelContext(camelContext);
		return kafka;
	}

}
