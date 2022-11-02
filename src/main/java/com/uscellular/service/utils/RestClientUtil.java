package com.uscellular.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClientUtil {
	
	
	public static String executeGet(String url) throws IOException {
		
		String result = null;
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);
            
            CloseableHttpResponse httpResponse = client.execute(request);
            InputStream is = httpResponse.getEntity().getContent();
            result = IOUtils.toString(is, StandardCharsets.UTF_8);
        }
		
		return result;
	}
	
}
