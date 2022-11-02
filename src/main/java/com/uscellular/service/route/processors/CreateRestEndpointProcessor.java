package com.uscellular.service.route.processors;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.uscellular.service.utils.GenerateProjectUtil;
import com.uscellular.service.utils.RestClientUtil;

@Component
public class CreateRestEndpointProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String jsonString = exchange.getIn().getBody(String.class);
		JSONObject json = new JSONObject(jsonString);
		JSONObject jsonRestEndpoint = json.getJSONObject("restEndpoint");
		
		String apucurioLink = jsonRestEndpoint.getString("apicurioLink");
		
		//String finalProjectPath = exchange.getProperty("finalProjectPath").toString();
		File resourceFolder = new File("src/main/resources/");
		String finalProjectPath = resourceFolder.getAbsolutePath();

		String apicurioJson = RestClientUtil.executeGet(apucurioLink);
		
		List<String> schemas = getSchemas(apicurioJson, finalProjectPath);
		
		System.out.println(apicurioJson);
		
	}
	
	
	public List<String> getSchemas(String jsonString, String finalProjectPath) throws IOException{
		
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
			        
			        URL url = f.toURI().toURL();
			        
			        String packageName = "com.uscellular.service.consumer.dto";
			        
			        String javaClassName = keyStr;
			        
			        String outputJavaClassDirectory = finalProjectPath + "/src/main/java";
			        
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
