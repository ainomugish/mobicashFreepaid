/*
 * Copyright 2016 i.
 *
 * Licensed under the Village Power License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.village-power.ch

 */
package com.mobicash.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;

/**
 *
 * @author Isaac Tumusiime isaac@village-power.ug
 */
public class Pj {
    
    final static Logger log = Logger.getLogger(Pj.class.getName());
    
    public static String printJ(String json){
        
        String prettyJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(json));
        log.info(prettyJson);
    
        return prettyJson;
    
    }
    
    /*
         public void printXml(Class<T> tClass){
             
           try {

		//File file = new File("C:\\file.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(FormFreeTranLookupResponse.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		//jaxbMarshaller.marshal(customer, file);
		jaxbMarshaller.marshal(fReceiveResponse1, System.out);

	      } catch (JAXBException e) {
                  System.out.println("*****************"+e.toString());
                  
                  reply.setError("Marshaling failed: "+e.toString());
	      }
         }*/
}
