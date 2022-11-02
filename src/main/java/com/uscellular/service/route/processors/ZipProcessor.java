package com.uscellular.service.route.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.CreateArtifectRoute;



/**
 * Processor to store information for logging purpose and build message to send
 * to Kafka Topic
 * 
 * @author nvasc001
 *
 */
@Component
public class ZipProcessor implements Processor {

	
	List<String> filesListInDir = new ArrayList<String>();

	@Override
	public void process(Exchange exchange) throws Exception {

        File generatedArtifectDir = new File(exchange.getProperty(CreateArtifectRoute.ARTIFACT_FOLDER, String.class));
        File response = new File(generatedArtifectDir.getAbsolutePath().concat(".zip"));
        

        zipDirectory(generatedArtifectDir, response.getAbsolutePath());


        exchange.getMessage().setBody(response);
        exchange.getMessage().setHeader("Content-Disposition", "attachment; filename=".concat(response.getName()));
	}

	private void zipDirectory(File dir, String zipDirName) {
        try {
            populateFilesList(dir);
            //now compress files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                System.out.println("Zipping "+filePath);
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

	

}
