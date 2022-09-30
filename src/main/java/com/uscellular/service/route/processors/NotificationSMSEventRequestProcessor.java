package com.uscellular.service.route.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uscellular.service.dto.NotificationEventRequestType;
import com.uscellular.service.dto.SmsInfo;



/**
 * Processor to store information for logging purpose and build message to send
 * to Kafka Topic
 * 
 * @author nvasc001
 *
 */
@Component
public class NotificationSMSEventRequestProcessor implements Processor {

	
	@Value("${notification.type.sms.value}")
	private String notificationType; 

	@Value("${notification.type.sms.activity-type}")
	private String smsNotificationActivityType;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public void process(Exchange exchange) throws Exception {


		// BUILD MESSAGE
		NotificationEventRequestType kafkaMessage = new NotificationEventRequestType();
		kafkaMessage.setNotificationMethod("SMS");
		kafkaMessage.setNotificationActivityType(smsNotificationActivityType);
		kafkaMessage.setCustomerId("Test_ID");

		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setNotificationType(notificationType);
		smsInfo.setRecipientMDN("7645582000");

		kafkaMessage.setSmsInfo(smsInfo);

		String bodyAsString = mapper.writeValueAsString(kafkaMessage);
		exchange.getIn().setBody(bodyAsString);

		//Logging information
		exchange.setProperty("mapName", smsNotificationActivityType);
	}

}
