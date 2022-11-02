package com.uscellular.service.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.stereotype.Component;

import com.sun.codemodel.JCodeModel;


@Component
public class GenerateProjectUtil {
	
	private GenerateProjectUtil() {}
	
	public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
	    File sourceDirectory = new File(sourceDirectoryLocation);
	    File destinationDirectory = new File(destinationDirectoryLocation);
	    FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
	}
	
	public static void writeFile(String filePath, List<String> lines) throws IOException {
		
		File file = new File(filePath);
		Collection<String> collection = new ArrayList<String>(lines);
		FileUtils.writeLines(file, collection, true);
		
	}
	
	
	public static void writeJsonSchemaFile(String pathToJsonSchema, String content) throws IOException {
		
		File f = new File(pathToJsonSchema);
		f.getParentFile().mkdirs(); 
		f.createNewFile();
        
        FileWriter file = new FileWriter(pathToJsonSchema);
		
        file.write(content);
        file.close();
		
	}
	
	public static void convertJsonToJavaClass(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName) 
			  throws IOException {
	    JCodeModel jcodeModel = new JCodeModel();

	    GenerationConfig config = new DefaultGenerationConfig() {
	        @Override
	        public boolean isGenerateBuilders() {
	            return false;
	        }

	        @Override
	        public SourceType getSourceType() {
	            return SourceType.JSON;
	        }
	    };

	    SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
	    mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

	    jcodeModel.build(outputJavaClassDirectory);
	}
}
