package com.practice.ar.gcpsender;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;


import org.slf4j.LoggerFactory;
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
import com.practice.ar.gcpsender.PolicyClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableScheduling
@EnableBinding(Source.class)
public class GcpFileReader{

	@Autowired
	private Source source;

	private String[] users = {"Glenn", "Sabby", "Mark", "Janne", "Ilaya"};
	private static final Logger logger = LoggerFactory.getLogger(GcpSenderApplication.class);

	@Scheduled(fixedDelay = 10000)
	public void sendEvents() {
	//public static void main(String args[]) 
	{
		try {
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
	    
	   for(int i=2;i<100;i++)
	   {
	String s=lines.get(i);
	    	String [] message =s.split(",");
	    	System.out.println(message[0]);
	    	PolicyClass p = new PolicyClass(message[0],message[1],message[2]);
		System.out.println(p.getPolicyId());
		this.source.output().send(MessageBuilder.withPayload(p).build());
		}
		}
		catch(Exception e)
		{ 
			logger.debug("exception thrown");
			
		}
		
	}
}}