package com.uscellular.service.route.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MavenProcessor implements Processor {

    //This will go to a Utils class
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();
    final Set<String> identifiers = new HashSet<>();

	ObjectMapper mapper = new ObjectMapper();
	List<String> filesListInDir = new ArrayList<>();

	@Override
	public void process(Exchange exchange) throws Exception {

        List<String> generateGoals = new ArrayList<>();

        generateGoals.addAll(Arrays.asList("archetype:generate", 
        "-DarchetypeGroupId=com.uscellular", 
        "-DarchetypeArtifactId=fuse-starter-archetype", 
        "-DarchetypeVersion=1.0.0"));

		InvocationRequest request = new DefaultInvocationRequest();

        MvnProperties mvnProperties = exchange.getIn().getBody(MvnProperties.class);

        generateGoals.add("-DgroupId="+mvnProperties.getArtifactGroupId());
        generateGoals.add("-Dversion="+mvnProperties.getArtifactVersion());
        generateGoals.add("-Dpackage="+mvnProperties.getArtifactPackage());
        generateGoals.add("-DartifactId="+mvnProperties.getArtifactName());

        request.setGoals(generateGoals);

		request.setBatchMode(true);
		request.setShowErrors(true);
		request.setQuiet(false);

		File baseDir = new File(randomIdentifier());

		baseDir.mkdir();

        request.setBaseDirectory(baseDir);
        Invoker invoker = new DefaultInvoker();
        InvocationResult result = invoker.execute( request );

        File generatedArtifact = new File(baseDir, mvnProperties.getArtifactName());

        exchange.setProperty(CreateArtifectRoute.ARTIFACT_BASE_DIR, baseDir.getAbsolutePath());
        exchange.setProperty(CreateArtifectRoute.ARTIFACT_FOLDER, generatedArtifact.getAbsolutePath());
        exchange.setProperty("request", mvnProperties);

        //TO DO - ERROR HANDLER
        if ( result.getExitCode() != 0 )
		{
			if ( result.getExecutionException() != null )
			{
				System.out.println(result.getExecutionException());
			}
			else
			{
				result.getExitCode();
			}
		}

        /* String fileName=generatedArtifact.getAbsolutePath().concat(".zip");

		zipDirectory(generatedArtifact, fileName);

        File response = new File(fileName);

		if ( result.getExitCode() != 0 )
		{
			if ( result.getExecutionException() != null )
			{
				System.out.println(result.getExecutionException());
			}
			else
			{
				result.getExitCode();
			}
		}else{
            exchange.getMessage().setBody(response);
            exchange.getMessage().setHeader("Content-Disposition", "attachment; filename=".concat(response.getName()));
        } */
	}

	private void zipDirectory(File dir, String zipDirName) {
        try {
            populateFilesList(dir);
            //now compress files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                System.out.println(" :: USCC :: Compressing :: ".concat(filePath));
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

	/**
     * This method populates all the files in a directory to a List
     * @param dir
     * @throws IOException
     */
    private void populateFilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
        }
    }

	public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public List<String> getSchemas(String jsonString, String finalProjectPath, String packageId) throws IOException{
		
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
