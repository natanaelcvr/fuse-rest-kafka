package com.uscellular.service.route.processors;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.CreateArtifectRoute;
import com.uscellular.service.route.entity.MvnProperties;
import com.uscellular.service.utils.GenerateProjectUtil;
import com.uscellular.service.utils.RestClientUtil;



/**
 * Processor to store information for logging purpose and build message to send
 * to Kafka Topic
 * 
 * @author nvasc001
 *
 */
@Component
public class RestProducerProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

        MvnProperties mvnProperties = exchange.getProperty("request", MvnProperties.class);

        //request apicurio json
        String restProducerSpec = RestClientUtil.executeGet(mvnProperties.getProducerSpec());
        String baseDir = exchange.getProperty(CreateArtifectRoute.ARTIFACT_FOLDER, String.class);

        List<String> schemas = getSchemas(restProducerSpec, baseDir, mvnProperties.getArtifactPackage());
	}

    private List<String> getSchemas(String jsonString, String finalProjectPath, String packageId) throws IOException{
		
		JSONObject json = new JSONObject(jsonString);
		
		String basePathToJsonSchema = finalProjectPath + "/src/main/resources/interfaces/rest/";
		
		List<String> results = null;
		
		JSONObject jsonDefinitions = json.getJSONObject("definitions");
		if(jsonDefinitions != null) {
			System.out.println("definition Type");
			
			jsonDefinitions.keySet().forEach(keyStr ->
		    {
			    Object keyvalue = jsonDefinitions.get(keyStr);
		        String pathToJsonSchema = basePathToJsonSchema + keyStr + ".json";
		        try {
					GenerateProjectUtil.writeJsonSchemaFile(pathToJsonSchema, keyvalue.toString());
					
					File f = new File(pathToJsonSchema);

                    String packageName = packageId.concat(".service.consumer.dto");
			        
			        URL url = f.toURI().toURL();
			        
			        String javaClassName = keyStr;
			        
			        String outputJavaClassDirectory = finalProjectPath.concat("/src/main/java");
			        
			        GenerateProjectUtil.convertJsonToJavaClass(url, new File(outputJavaClassDirectory), packageName, javaClassName);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		    });
			
//			for (String keyStr : jsonDefinitions.keySet()) {
//		        Object keyvalue = jsonDefinitions.get(keyStr);
//		        String pathToJsonSchema = basePathToJsonSchema + keyStr + ".json";
//		        GenerateProjectUtil.writeJsonSchemaFile(pathToJsonSchema, keyvalue.toString());
//		    }
			
			
		}
		else {
			JSONObject components = json.getJSONObject("components");
			if(components != null) {
				System.out.println("components Type");
			}
		}
		
		return results;
	}

}
