/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobicashfreepaid;

import com.gilecode.yagson.YaGson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mobicash.utils.Minput;
import com.mobicash.utils.MyTypeAdapter;
import com.mobicash.utils.Mreply;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.ws.soap.SOAPFaultException;
import nl.strohalm.cyclos.webservices.CyclosWebServicesClientFactory;
import nl.strohalm.cyclos.webservices.access.AccessWebService;
import nl.strohalm.cyclos.webservices.access.CheckCredentialsParameters;
import nl.strohalm.cyclos.webservices.access.CredentialsStatus;
import nl.strohalm.cyclos.webservices.payments.ChargebackResult;
import nl.strohalm.cyclos.webservices.payments.PaymentParameters;
import nl.strohalm.cyclos.webservices.payments.PaymentResult;
import nl.strohalm.cyclos.webservices.payments.PaymentWebService;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import za.co.freepaid.dev.ws.airtimeplus.Airtimeplus;
import za.co.freepaid.dev.ws.airtimeplus.FetchBalanceIn;
import za.co.freepaid.dev.ws.airtimeplus.FetchBalanceOut;
import za.co.freepaid.dev.ws.airtimeplus.FetchOrderIn;
import za.co.freepaid.dev.ws.airtimeplus.FetchOrderLatestIn;
import za.co.freepaid.dev.ws.airtimeplus.FetchOrderOut;
import za.co.freepaid.dev.ws.airtimeplus.FetchProductsIn;
import za.co.freepaid.dev.ws.airtimeplus.FetchProductsOut;
import za.co.freepaid.dev.ws.airtimeplus.PlaceOrderIn;
import za.co.freepaid.dev.ws.airtimeplus.PlaceOrderOut;
import za.co.freepaid.dev.ws.airtimeplus.Products;
import za.co.freepaid.dev.ws.airtimeplus.QueryOrderIn;
import za.co.freepaid.dev.ws.airtimeplus.QueryOrderOut;

/**
 * REST Web Service
 *
 * @author iainomugisha
 */
@Path("Airtime")
public class AirtimeResource {

    @Context
    private UriInfo context;
    
        final static Logger log = Logger.getLogger(AirtimeResource.class.getName());
    
    Mreply reply;
    Minput minput;
        final static YaGson gson= new YaGson();/*new GsonBuilder()
               .registerTypeAdapter(Products.class, new MyTypeAdapter<Products>())
               .create();*/
    /**
     * Creates a new instance of AirtimeResource
     */
    public AirtimeResource() {
    }
    
     //**************endpoint for retrieving products*****************
    @GET
    @Path("fetchProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public String productList() throws ParseException, IOException, NotSupportedException, SystemException, SOAPException,AxisFault,ServiceException,Exception {
        
        reply = new Mreply("productList");
        minput = new Minput();
        
        
            int userFreepaid =926920;
            String pass ="480720";

       try{ 
           

                        Airtimeplus aService= new Airtimeplus() ;
                            FetchProductsIn fetchProductsIn = new FetchProductsIn();
                            fetchProductsIn.setPass(pass);
                            fetchProductsIn.setUser(userFreepaid);

                            
                            FetchProductsOut fetchProductOut = aService.getAirtimeplusPort().fetchProducts(fetchProductsIn);
                            
                            if(fetchProductOut.getStatus() == 1){
                                
                                //fetchProductOut.setProducts(fetchProductOut.getProducts()); 
                                reply.fetchProductsOut = fetchProductOut;
                                 //log.info("Products "+fetchProductOut.getProducts());
                                 reply.setSucc("Products Found");
                               
                            
                            }else{
                                System.out.println("+++++++++ERROR "+fetchProductOut.getErrorcode()+" "+fetchProductOut.getMessage());
                                reply.setError("+++++++ERROR "+fetchProductOut.getErrorcode()+" "+fetchProductOut.getMessage());
                            }
              
          
                   
       }catch(JsonSyntaxException | SOAPFaultException e){
           log.error("something went wrong on server "+e.toString());
           reply.setError("Server error "+e.toString());
       
       }
        
         return reply.toString();
  
}
    
            //**************endpoint for Pinned Order*****************
    @POST
    @Path("makePinnedOrder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String makePinned(@Context Request req, String json) throws ParseException, IOException, NotSupportedException, SystemException, SOAPException,Exception {
        
        reply = new Mreply("makePinned");
        minput = new Minput();
        
        
            int userFreepaid =926920;
            String pass ="480720";

       try{ 
           
           
          Airtimeplus aService= new Airtimeplus() ;

           
                minput = gson.fromJson(json, Minput.class);
                if(minput.amount >= 1.0 && minput.phoneNumber.length()!=0 && minput.pin.length()!=0){
           
                    //create cyclos webservice
                   /* CyclosWebServicesClientFactory factory = new CyclosWebServicesClientFactory();
                    factory.setServerRootUrl("http://test.mobicash.co.za/south_africa/");
                    //factory.setServerRootUrl("http://54.149.233.142:8080/mobicore");
                    //payment endpoint
                    PaymentWebService paymentWebService = factory.getPaymentWebService();
                    
                    //agent credentials
                    String loginPassword =minput.pin;// "1122";//
                    String phone =minput.phoneNumber;// "agent1";//
                    String user = "airtime";
                    float amount = minput.amount;
                   
   
                    //55 111 77 122 
                    
                    //convert to big decimal & construct payment parameters
                    BigDecimal bd = new BigDecimal(minput.amount * Float.valueOf(minput.quantity));
                                        
                    
                    System.out.println("amount"+bd);
                    PaymentParameters params = new PaymentParameters();
                                        
                    //params.setFromMemberPrincipalType("mobilePhone");//for client buying directly
                    params.setFromMemberPrincipalType("USER");//for Agent buying on behalf of client
                    params.setFromMember(phone);
                    params.setAmount(bd);
                    params.setCredentials(loginPassword);
                    //params.setFromSystem(true);
                    params.setToMemberPrincipalType("USER");
                    params.setToMember(user);
                    
                    //params.setToSystem(true);
                    //params.setCurrency("Uganda Shillings");
                    params.setDescription("Client FREEPAID credit - airtime");
                    params.setTransferTypeId(new Long(36));//for agent buying on behalf of client
                    //params.setTransferTypeId(new Long(46));//for client buying directly
                    
                    System.out.println("got there--------payment params set------------------");
                   
                                 
                
                    // Perform the payment & check result
                    PaymentResult result = paymentWebService.doPayment(params);
                    switch (result.getStatus()) {
                        case PROCESSED:
                            String transactionNumber = result.getTransfer().getTransactionNumber();
                            System.out.println("The payment was successful. The transaction number is " + transactionNumber);
                            //check Mobicah balance on FreePaid-to confirm if neccesary
                            freepaidDoYourThing(userFreepaid,pass,amount);
                            //reply.setSucc("Tax deduction:"+result.getStatus().toString());*/
                            PlaceOrderIn placeOrderIn = new PlaceOrderIn();
                            placeOrderIn.setUser(userFreepaid);
                            placeOrderIn.setExtra("");
                            placeOrderIn.setNetwork(minput.network);//e.g bela
                            placeOrderIn.setPass(pass);
                            placeOrderIn.setRefno("REF"+minput.phoneNumber);
                            placeOrderIn.setSellvalue(minput.amount);
                            placeOrderIn.setCount(Integer.valueOf(minput.quantity));
                            
                            PlaceOrderOut placeOrderOut = aService.getAirtimeplusPort().placeOrder(placeOrderIn);
                            
                            if(placeOrderOut.getStatus() == 1){
                                    
                                    FetchOrderIn fetchOrder = new FetchOrderIn();
                                    fetchOrder.setOrderno(placeOrderOut.getOrderno());
                                    fetchOrder.setPass(pass);
                                    fetchOrder.setUser(userFreepaid);
                                   
                                    
                                    FetchOrderOut fetchOrderOut = aService.getAirtimeplusPort().fetchOrder(fetchOrder);
                                    
                                    reply.fetchOrderOut = fetchOrderOut;
                                    
                                    log.info("Mobicash Charged Order No.: "+fetchOrderOut.getOrderno()+" Status: "+fetchOrderOut.getStatus());

                                    log.info("VOUCHER "+fetchOrderOut.getOrderno());
                                    reply.setSucc("Success purchase");

                            
                            }else{
                                //reverse transaction
//                                ChargebackResult chargeResult = paymentWebService.chargeback(result.getTransfer().getId());
//                                
//                                switch(chargeResult.getStatus()){
//                                    case SUCCESS:
//                                        reply.message ="Payment reversed";
//                                    case INVALID_PARAMETERS:
//                                        reply.message ="Given transfer id or amount are invalid";
//                                    case TRANSFER_NOT_FOUND:
//                                        reply.message ="transfer to chargedback/reverse was not found";
//                                    case TRANSFER_CANNOT_BE_CHARGEDBACK:
//                                        reply.message ="Maximum allowed time for chargeback/reverse passed";
//                                    case TRANSFER_ALREADY_CHARGEDBACK:
//                                        reply.message ="Transfer was already charged back/reversed";
//                                    default:
//                                        log.info("CHARGEBACK fail"+chargeResult.getStatus());
//                                }
                                log.error("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                                reply.setError("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                            }
                            
//                            break;
//                        case PENDING_AUTHORIZATION:
//                            System.out.println("The payment is awaiting authorization");
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        case INVALID_CHANNEL:
//                            System.out.println("The given user cannot access this channel");
//                            System.out.println(""+result.getStatus().toString());
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        case INVALID_CREDENTIALS:
//                            System.out.println("You have entered an invalid PIN");
//                            System.out.println(""+result.getStatus().toString());
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        case BLOCKED_CREDENTIALS:
//                            System.out.println("Your PIN is blocked by exceeding trials");
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        case INVALID_PARAMETERS:
//                            System.out.println("Please, check the given parameters");
//                            System.out.println(""+result.getStatus().toString());
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        case NOT_ENOUGH_CREDITS:
//                            System.out.println("You don't have enough funds for this payment");
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        case MAX_DAILY_AMOUNT_EXCEEDED:
//                            System.out.println("You have already exceeded the maximum amount today");
//                            reply.setError(result.getStatus().toString());
//                            break;
//                        default:
//                            System.out.println("There was an error on the payment: " + result.getStatus());
//                            reply.setError(result.getStatus().toString());
//                    }
                           
                }else{
                    reply.setError("Send amount,phoneNumber and pin");
                    log.error("Send amount,phoneNumber and pin");
                }
           //System.out.println("------------"+fReceiveRequest1.toString());
               /* log.info("FormFreeReceiveLookupResponse "+fReceiveResponse.getReceiverFirstName()+" :"+fReceiveResponse.getMgiTransactionSessionID());
                //if(fReceiveResponse.getReferenceNumber())
                
                reply.formFreeReceiveLookupResponse = fReceiveResponse;
                reply.setSucc("Please Confirm Transaction");*/
                
          
                   
      }catch(JsonSyntaxException | SOAPFaultException e){
          log.error("+++++++ERROR "+e.toString());
          reply.setError("+++++++ERROR  "+e.toString());
       
       }
        
         return reply.toString();
  
}
    
    
    
           //**************endpoint for pnless order*****************
    @POST
    @Path("makePinlessOrder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String makePinless(@Context Request req, String json) throws ParseException, IOException, NotSupportedException, SystemException, SOAPException,Exception {
        
        reply = new Mreply("makePinless");
        minput = new Minput();
        
            //Development user ID        
            int userFreepaid =926920;
            String pass ="480720";

            //Live user ID
            //int userFreepaid =3751523;
              

       try{ 
           
           
           Airtimeplus aService= new Airtimeplus();

           
                minput = gson.fromJson(json, Minput.class);
                  if(minput.amount >= 1.0 && minput.phoneNumber.length()!=0 && minput.pin.length()!=0){
           
//                    CyclosWebServicesClientFactory factory = new CyclosWebServicesClientFactory();
//                    factory.setServerRootUrl("http://test.mobicash.co.za/south_africa/");
//                    //factory.setServerRootUrl("http://test.mobicash.co.za/index.php/home/client");
//                    
//                    AccessWebService accessWebservice =  factory.getAccessWebService();
//                    
                    String loginPassword =minput.pin;// "1122";//
                    String phone = checkPhoneNumberFormat(minput.phoneNumber);
                    System.out.println("+++++++++++++++++++++++"+phone);
                    String user = "airtime";
                    float amount = minput.amount;
                    String otherNumber ="";
                    String network =checkPhoneNetwork(phone);
                    String otherNetwork="";
                    
                    if(minput.otherNumber.length()>0){
                        otherNumber= checkPhoneNumberFormat(minput.otherNumber);
                        otherNetwork = checkPhoneNetwork(otherNumber);
                        
                    }
                    
                    //check cyclos credentials to debit client
//                    CheckCredentialsParameters ccp = new CheckCredentialsParameters();
//                    ccp.setCredentials(loginPassword);
//                    ccp.setPrincipalType("mobilePhone");
//                    ccp.setPrincipal(phone);
//                    
//                    CredentialsStatus cs = accessWebservice.checkCredentials(ccp);
//                    switch (cs) {
//                        case VALID:
//                            PaymentWebService paymentWebService = factory.getPaymentWebService();
//                            //convert to big decimal construct payment parameters
//                            BigDecimal bd = new BigDecimal(minput.amount);
//                            //BigDecimal taxAmount = new BigDecimal(jInput.taxedAmount);
//
//
//                            System.out.println("amount"+bd);
//                            PaymentParameters params = new PaymentParameters();
//
//
//                            //params.setFromMemberPrincipalType("mobilePhone");//for client paying tax directly
//                            params.setFromMemberPrincipalType("mobilePhone");//for Agent paying on behalf of client
//                            params.setFromMember(phone);
//                            
//                            params.setAmount(bd);
//                            params.setCredentials(loginPassword);
//                            //params.setFromSystem(true);
//                            params.setToMemberPrincipalType("USER");
//                            params.setToMember(user);
//                            //params.setToSystem(true);
//                            //params.setCurrency("Uganda Shillings");
//                            params.setDescription("Client FREEPAID credit - airtime");
//                            params.setTransferTypeId(new Long(36));//for agent paying on behalf of client
//                            //params.setTransferTypeId(new Long(48));//for client paying directly
//
//                            System.out.println("got there------------------------------");
//                            // Perform the payment
//                            PaymentResult result = paymentWebService.doPayment(params);
//                            switch (result.getStatus()) {
//                                case PROCESSED:
//                                    String transactionNumber = result.getTransfer().getTransactionNumber();
//                                    System.out.println("The payment was successful. The transaction number is " + transactionNumber+" Transaction Status: "+result.getStatus());
//                                    freepaidDoYourThing(userFreepaid,pass,amount);
                                    //reply.setSucc("Tax deduction:"+result.getStatus().toString());*/
                                    PlaceOrderIn placeOrderIn = new PlaceOrderIn();
                                    placeOrderIn.setUser(userFreepaid);
                                    placeOrderIn.setPass(pass);
                                    //placeOrderIn.setExtra("27843762255");//phone number of client to recharge
                                    if(otherNumber.length()<=0){
                                        placeOrderIn.setExtra(stripNumberFormat(phone));//phone number of other client to recharge
                                    }else{
                                        placeOrderIn.setExtra(stripNumberFormat(otherNumber));//phone number of client to recharge
                                    }
                                    
                                    
                                    placeOrderIn.setPass(pass);
                                    if(otherNumber.length()<=0){
                                        placeOrderIn.setRefno("REF"+phone);
                                        placeOrderIn.setNetwork(network);//e.g p-vodacom, bela
                                    }else{
                                        placeOrderIn.setRefno("REF"+otherNumber);
                                        placeOrderIn.setNetwork(otherNetwork);//e.g p-vodacom, bela
                                    }
                                    placeOrderIn.setSellvalue(minput.amount);
                                    placeOrderIn.setCount(1);

                                    PlaceOrderOut placeOrderOut = aService.getAirtimeplusPort().placeOrder(placeOrderIn);

                                    if(placeOrderOut.getStatus() == 1){
                                        QueryOrderIn queryOrder = new QueryOrderIn();
                                        queryOrder.setOrderno(placeOrderOut.getOrderno());
                                        queryOrder.setUser(userFreepaid);
                                        queryOrder.setPass(pass);


                                        QueryOrderOut queryOrderOut =aService.getAirtimeplusPort().queryOrder(queryOrder);

                                        if(queryOrderOut.getStatus()==1){

                                            FetchOrderIn fetchOrder = new FetchOrderIn();
                                            fetchOrder.setOrderno(placeOrderOut.getOrderno());
                                            fetchOrder.setPass(pass);
                                            fetchOrder.setUser(userFreepaid);


                                            //FetchOrderOut fetchOrderOut = aService.getairtimeplusPort().fetchOrder(fetchOrder);

                                            FetchOrderLatestIn foli = new FetchOrderLatestIn();
                                            foli.setLast(placeOrderOut.getOrderno());
                                            foli.setPass(pass);
                                            foli.setUser(userFreepaid);

                                            FetchOrderOut fetchOrderOut = aService.getAirtimeplusPort().fetchOrderLatest(foli);

                                            log.info("Mobicash Charged: "+queryOrderOut.getCostprice()+" Status: "+queryOrderOut.getStatus());
                                            if(otherNumber.length()<=0){
                                                log.info("VOUCHER "+fetchOrderOut.getStatus());
                                                reply.setSucc("Success "+minput.amount+" sent to "+phone+" Network: "+network);
                                            }else{
                                                log.info("VOUCHER "+fetchOrderOut.getStatus());
                                                reply.setSucc("Success "+minput.amount+" sent to "+otherNumber+" Network: "+otherNetwork);
                                            }
                                        }else{
//                                            //reverse transaction
//                                            ChargebackResult chargeResult = paymentWebService.chargeback(result.getTransfer().getId());
//
//                                            switch(chargeResult.getStatus()){
//                                                case SUCCESS:
//                                                    reply.message ="Payment reversed";
//                                                    break;
//                                                case INVALID_PARAMETERS:
//                                                    reply.message ="Given transfer id or amount are invalid";
//                                                    break;
//                                                case TRANSFER_NOT_FOUND:
//                                                    reply.message ="Transfer to chargedback/reverse was not found";
//                                                    break;
//                                                case TRANSFER_CANNOT_BE_CHARGEDBACK:
//                                                    reply.message ="Maximum allowed time for chargeback/reverse passed";
//                                                    break;
//                                                case TRANSFER_ALREADY_CHARGEDBACK:
//                                                    reply.message ="transfer was already charged back/reversed";
//                                                    break;
//                                                default:
//                                                    reply.message ="CHARGEBACK fail"+chargeResult.getStatus();
//                                                    log.info("CHARGEBACK fail"+chargeResult.getStatus());
//                                            }
//
                                            log.error("Error "+queryOrderOut.getErrorcode()+" "+queryOrderOut.getMessage());
                                            reply.setError("Error: "+queryOrderOut.getErrorcode()+" "+queryOrderOut.getMessage());
                                        }
//
                                    }else{
//
//                                        //reverse transaction
//                                            ChargebackResult chargeResult = paymentWebService.chargeback(result.getTransfer().getId());
//
//                                            switch(chargeResult.getStatus()){
//                                                case SUCCESS:
//                                                    reply.message ="Payment reversed";
//                                                    break;
//                                                case INVALID_PARAMETERS:
//                                                    reply.message ="Given transfer id or amount are invalid";
//                                                    break;
//                                                case TRANSFER_NOT_FOUND:
//                                                    reply.message ="Transfer to chargedback/reverse was not found";
//                                                    break;
//                                                case TRANSFER_CANNOT_BE_CHARGEDBACK:
//                                                    reply.message ="Maximum allowed time for chargeback/reverse passed";
//                                                    break;
//                                                case TRANSFER_ALREADY_CHARGEDBACK:
//                                                    reply.message ="transfer was already charged back/reversed";
//                                                    break;
//                                                default:
//                                                    log.info("CHARGEBACK fail"+chargeResult.getStatus());
//                                            }
//
                                        log.error("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                                        reply.setError("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
//                                    }
//                                    break;
//                                case PENDING_AUTHORIZATION:
//                                    System.out.println("The payment is awaiting authorization");
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                case INVALID_CHANNEL:
//                                    System.out.println("The given user cannot access this channel");
//                                    System.out.println(""+result.getStatus().toString());
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                case INVALID_CREDENTIALS:
//                                    System.out.println("You have entered an invalid PIN");
//                                    System.out.println(""+result.getStatus().toString());
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                case BLOCKED_CREDENTIALS:
//                                    System.out.println("Your PIN is blocked by exceeding trials");
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                case INVALID_PARAMETERS:
//                                    System.out.println("Please, check the given parameters");
//                                    System.out.println(""+result.getStatus().toString());
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                case NOT_ENOUGH_CREDITS:
//                                    System.out.println("You don't have enough funds for this payment");
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                case MAX_DAILY_AMOUNT_EXCEEDED:
//                                    System.out.println("You have already exceeded the maximum amount today");
//                                    reply.setError(result.getStatus().toString());
//                                    break;
//                                default:
//                                    System.out.println("There was an error on the payment: " + result.getStatus());
//                                    reply.setError("default"+result.getStatus().toString());
//                            }
//                            break;
//                        case INVALID:
//                            System.out.println("Wrong PIN: " + cs.toString());
//                            reply.setError("Wrong PIN: " + cs.toString());
//                            break;
//                        case BLOCKED:
//                            System.out.println("Blocked Account: " + cs.toString());
//                            reply.setError("Wrong PIN: " + cs.toString());
//                            break;
//                        default:
//                            System.out.println("error authentication: " + cs.toString());
//                            reply.setError("authentication Error");
//                                                       
                        }
                           
                }else{
                    reply.setError("Send amount,phoneNumber and pin");
                    log.error("Send amount,phoneNumber and pin");
                }
          
                   
       }catch(JsonSyntaxException | SOAPFaultException e){
           log.error("+++++++ERROR  "+e.toString());
           reply.setError("+++++++ERROR  "+e.toString());
       
       }
        
         return reply.toString();
  
}
    
   //strip number of country code b4 sending to FreePaid 
   public String stripNumberFormat(String number){

        String country_code = "0";
        String correctNumber ="";

        correctNumber = country_code + number.substring(2); // e.g. 27767610645 ->  (country_code) 767610645
        
      return correctNumber;
  }
    
    //check Network for which to buy airtime from phone number
    public String checkPhoneNetwork(String number){
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
    public String checkPhoneNumberFormat(String number){
    String sPhoneNumber = "27119785313";
    String correctNumber = null;
    
    System.out.println("=================================="+number+"=====================================");


      Pattern pattern = Pattern.compile("\\d{11}");
      Matcher matcher = pattern.matcher(number);

      if (matcher.matches()) {
    	  System.out.println("Phone Number Valid");
          correctNumber = number;
          
      }
      else
      {
        String country_code = "27";
    	System.out.println("Phone Number must be in the form 27xxxxxxxxx");
        correctNumber = number.replaceAll("[^0-9]", "");
          
        if (number.substring(0, 1).compareTo("0") == 0/* && number.substring(1, 2).compareTo("0") != 0*/) {
            correctNumber = country_code + number.substring(1); // e.g. 0172 12 34 567 -> + (country_code) 172 12 34 567
        }
          
      }
      System.out.println("=================================="+correctNumber+"=====================================");
      return correctNumber;
  }

    
        //check balance of MobiCash account at freepaid
        public String freepaidDoYourThing(int user, String pass, float amount){
        
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


    /**
     * Retrieves representation of an instance of com.mobicashfreepaid.AirtimeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AirtimeResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
