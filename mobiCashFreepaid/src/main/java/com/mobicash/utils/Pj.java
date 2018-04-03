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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import za.co.freepaid.dev.ws.airtimeplus.Airtimeplus;
import za.co.freepaid.dev.ws.airtimeplus.FetchBalanceIn;
import za.co.freepaid.dev.ws.airtimeplus.FetchBalanceOut;

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
    
    
   //strip number of country code b4 sending to FreePaid 
   public static String stripNumberFormat(String number){

        String country_code = "0";
        String correctNumber ="";

        correctNumber = country_code + number.substring(2); // e.g. 27767610645 ->  (country_code) 767610645
        
      return correctNumber;
  }
    
    //check Network for which to buy airtime from phone number
    public static String checkPhoneNetwork(String number){
    String network="";
    String code = "";
    
    code = number.substring(2,4);
    
    if(code.equalsIgnoreCase("71")){
        code = number.substring(2,5);
        switch(code){
                case "710":
                     network ="p-mtn";
                     break;
                case "711":
                     network ="p-vodacom";
                     break;
                case "712":
                     network ="p-vodacom";
                     break;
                case "713":
                     network ="p-vodacom";
                     break;
                case "714":
                     network ="p-vodacom";
                     break;
                case "715":
                     network ="p-vodacom";
                     break;
                case "716":
                     network ="p-vodacom";
                     break;
                case "717":
                     network ="p-mtn";
                     break;
                case "718":
                     network ="p-mtn";
                     break;
                case "719":
                     network ="p-mtn";
                     break;
                default:
                    log.info("failed to pass network");
                    network = "NOT_FOUND";
            }
        
    
    }else{

            switch(code){
                case "83":
                     network ="p-mtn";
                     break;
                 case "84":
                     network ="p-cellc";
                     break;   
                 case "82":
                     network ="p-vodacom";
                     break;
                 case "72":
                     network ="p-vodacom";
                     break;
                 case "73":
                     network ="p-mtn";
                     break;
                 case "74":
                     network ="p-cellc";
                     break; 
                 case "76":
                     network ="p-vodacom";
                     break; 
                 case "78":
                     network ="p-mtn";
                     break; 
                 case "79":
                     network ="p-vodacom";
                     break; 
                 case "81":
                     network ="p-heita";
                     break; 
                 default:
                     log.info("failed to pass network");
                     network = "NOT_FOUND";
             }
   
    }
   
   return network;
  }
    
    //check if formt of number is 0 or +
    public static String checkPhoneNumberFormat(String number){
    String sPhoneNumber = "27119785313";
    String correctNumber = null;
    
    log.info("=================================="+number+"=====================================");


      Pattern pattern = Pattern.compile("\\d{11}");
      Matcher matcher = pattern.matcher(number);

      if (matcher.matches()) {
    	  log.info("Phone Number Valid");
          correctNumber = number;
          
      }
      else
      {
        String country_code = "27";
    	log.info("Phone Number must be in the form 27xxxxxxxxx");
        correctNumber = number.replaceAll("[^0-9]", "");
          
        if (number.substring(0, 1).compareTo("0") == 0/* && number.substring(1, 2).compareTo("0") != 0*/) {
            correctNumber = country_code + number.substring(1); // e.g. 0172 12 34 567 -> + (country_code) 172 12 34 567
        }
          
      }
      log.info("=================================="+correctNumber+"=====================================");
      return correctNumber;
  }

    
        //check balance of MobiCash account at freepaid
    public static String freepaidDoYourThing(int user, String pass, float amount){
            Mreply reply=new Mreply("freepaidDoYourThing");
        
        try {
            FetchBalanceIn fetchBalanceIn = new FetchBalanceIn();
            fetchBalanceIn.setUser(user);
            fetchBalanceIn.setPass(pass);
            
            
            Airtimeplus aService1= new Airtimeplus();
           
            
            try{
                        log.info("================================================================");
                        JAXBContext jaxbContext = JAXBContext.newInstance(FetchBalanceIn.class);
                        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                        // output pretty printed
                        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                        //jaxbMarshaller.marshal(customer, file);
                        jaxbMarshaller.marshal(fetchBalanceIn, System.out);

                      } catch (JAXBException e) {
                          log.info("*****************"+e.toString());
                      }
            log.info("================================================================");
            
            FetchBalanceOut fetchBalanceOut=aService1.getAirtimeplusPort().fetchBalance(fetchBalanceIn);
            log.info("DDDDDDDDDDDDDD"+fetchBalanceOut.getBalance());
            reply.setSucc("DDDDDDDDDDDDDD"+fetchBalanceOut.getBalance());
            
                try{
                        //File file = new File("C:\\file.xml");
                        JAXBContext jaxbContext = JAXBContext.newInstance(FetchBalanceOut.class);
                        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                        // output pretty printed
                        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                        //jaxbMarshaller.marshal(customer, file);
                        jaxbMarshaller.marshal(fetchBalanceOut, System.out);

                      } catch (JAXBException e) {
                          log.info("*******+++++++ERROR "+e.toString());
                      }
            
            
        } catch (Exception ex) {
            log.error(ex.toString());
            reply.setError(ex.toString());
        }
        return reply.toString();
    }

}
