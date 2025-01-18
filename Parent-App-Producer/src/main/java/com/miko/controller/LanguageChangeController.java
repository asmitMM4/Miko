package com.miko.controller;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miko.service.LanChangeDatabase;
import com.miko.service.LanguageChangeProducer;

@RestController
public class LanguageChangeController {

   //private final LanguageChangeProducer languageChangeProducer = new LanguageChangeProducer();
    @Autowired
	LanguageChangeProducer languageChangeProducer;
     
    private final LanChangeDatabase service;
    
    @Autowired
    public LanguageChangeController(LanChangeDatabase service) {
        this.service = service;
       
    }


    @PostMapping("/change-language")
    public String changeLanguage(@RequestParam String userId, @RequestParam String language) {
    	
    	
    	try {
    		
    		ExecutorService executorService = Executors.newSingleThreadExecutor();
    			
    		
    		executorService.execute(new Runnable() {

				@Override
				public void run() {
					languageChangeProducer.sendLanguageChangeNotification(userId, language);
				}
    			
    		});
    		executorService.shutdown();
    			
        	 
  
        	
    		
    	}catch(Exception e) {
    		
    	}
    	
        try {
			Thread.sleep(2);
		     //cheak in database if langause changed
        	//update here Use Yugabyte database here
		 	String status=service.getAcknowledgmentStatus(userId);
	    	
	    	if(status.equalsIgnoreCase("success")) {
	    		return "Langauge has been changed on children App"; 
	    	}else {
	    		return "Langauge has not been changed on children App";
	    	}
	    	
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			System.out.println("Exception occured during changing the langauge");
		}
		return " ";
   
    }
}
