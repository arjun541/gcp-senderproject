package com.practice.ar.gcpsender;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.contrib.nio.CloudStorageConfiguration;
import com.google.cloud.storage.contrib.nio.CloudStorageFileSystem;

@EnableScheduling
@EnableBinding(Source.class)
public class GoogleFileReader {
	
	
	@Autowired
	private Source source;

	

	@Scheduled(fixedRate = 10000)
	public void sendEvents() throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("credentials.json");
		
	    CloudStorageFileSystem fs =
	        CloudStorageFileSystem.forBucket(
	            "demo-arbucket",
	            CloudStorageConfiguration.DEFAULT,
	            StorageOptions.newBuilder()
	                .setCredentials(ServiceAccountCredentials.fromStream(
	                    is))
	                .build());
	    
	   
	    Path path = fs.getPath("/FL_insurance_sample.csv");
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    
	    System.out.println(lines.size());
	String s=lines.get(100);
	    	String [] message =s.split(",");
	    	PolicyClass pc=new PolicyClass(message[0],message[2],message[1]);
	    	fs.close(); 
	    this.source.output().send(MessageBuilder.withPayload(pc).build());
		
		
	}

}
