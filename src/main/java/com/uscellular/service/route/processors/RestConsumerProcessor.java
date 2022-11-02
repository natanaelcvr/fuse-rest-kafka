package com.uscellular.service.route.processors;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.CreateArtifectRoute;
import com.uscellular.service.route.entity.MvnProperties;
import com.uscellular.service.utils.RestClientUtil;



/**
 * Processor to store information for logging purpose and build message to send
 * to Kafka Topic
 * 
 * @author nvasc001
 *
 */
@Component
public class RestConsumerProcessor implements Processor {


	@Override
	public void process(Exchange exchange) throws Exception {
		MvnProperties mvnProperties = exchange.getProperty("request", MvnProperties.class);

        String openApiSpecFile = "/src/main/resources/openapi/openapi-spec.json";
        String artifectDirDir = (exchange.getProperty(CreateArtifectRoute.ARTIFACT_FOLDER, String.class));

        File baseDir = new File(artifectDirDir.concat(openApiSpecFile));

        File openApiSpecFolder = baseDir.getParentFile();

        if (openApiSpecFolder != null && !openApiSpecFolder.exists() && !openApiSpecFolder.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + openApiSpecFolder);
        }

		if(!baseDir.createNewFile()){
            //exception handleing 
            System.out.println("FILED TO CREATE FILE");
        }

         //request apicurio json
        String apicurioJson = RestClientUtil.executeGet(mvnProperties.getProducerSpec());
        FileUtils.writeStringToFile(baseDir, apicurioJson, StandardCharsets.UTF_8, false);

	}

}
