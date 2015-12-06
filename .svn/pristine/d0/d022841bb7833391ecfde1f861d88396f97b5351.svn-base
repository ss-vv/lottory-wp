package com.unison.lottery.api.protocol;

import java.io.File;
import java.io.IOException;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.unison.lottery.api.protocol.common.Constants;

public class XmlProtocolTest {
	
	public static void main(String[] args){
		String strURL = args[0];
		// Get file to be posted
		String strXMLFilename = args[1];
		
		String seed=args[2];
		File input = new File(strXMLFilename);
		// Prepare HTTP post
		HttpPost post = new HttpPost(strURL);
		// Request content will be retrieved directly
		// from the input stream
		FileEntity entity = new FileEntity(input, "text/plain; charset=\"UTF-8\"");
		post.setEntity(entity);
		// Get HTTP client
		HttpClient httpclient = new DefaultHttpClient();
		post.setHeader(Constants.SEED_PARAMETER_NAME, seed);
		// Execute request
		try {
			HttpResponse response = httpclient.execute(post);
			// Display status code
			System.out.println("Response status code: " +response.getStatusLine().getStatusCode());
			// Display response
			System.out.println("Response body: ");
			response.getEntity().writeTo(System.out);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Release current connection to the connection pool once you are done
			post.abort();
		}
			  
	}

}
