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
import com.mobicash.utils.Pj;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
import nl.strohalm.cyclos.webservices.payments.PaymentStatus;
import nl.strohalm.cyclos.webservices.payments.PaymentWebService;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.xmlsoap.schemas.soap.encoding.Array;
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
import za.co.freepaid.dev.ws.airtimeplus.Product;
import za.co.freepaid.dev.ws.airtimeplus.Products;
import za.co.freepaid.dev.ws.airtimeplus.QueryOrderIn;
import za.co.freepaid.dev.ws.airtimeplus.QueryOrderOut;
import za.co.freepaid.dev.ws.airtimeplus.Voucher;
import za.co.freepaid.dev.ws.airtimeplus.Vouchers;

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
        
        
            //int userFreepaid =926920;//dev user
            int userFreepaid =3751523;
            String pass ="480720";

       try{ 
           

                        Airtimeplus aService= new Airtimeplus() ;
                            FetchProductsIn fetchProductsIn = new FetchProductsIn();
                            fetchProductsIn.setPass(pass);
                            fetchProductsIn.setUser(userFreepaid);

                            
                            FetchProductsOut fetchProductOut = aService.getAirtimeplusPort().fetchProducts(fetchProductsIn);
                            
                            if(fetchProductOut.getStatus() == 1){
                                
                                //fetchProductOut.setProducts(fetchProductOut.getProducts()); 
                                //reply.fetchProductsOut = fetchProductOut;
                                //reply.prdcts=fetchProductOut.getProducts();
                                //System.out.println("======"+reply.prdcts.getOtherAttributes().size());
                                //reply.any=fetchProductOut.getProducts().getAny();
                                List<Object> oo=fetchProductOut.getProducts().getAny();
                                //Products pdts=fetchProductOut.getProducts();
                                //Object obj=pdts.getAny().get(81);
                                //System.out.println(" -"+obj.getClass());
                               // System.out.println(" -"+obj.toString());
                                
                                List<Product> ps = new ArrayList<>();
                                
                                
                                System.out.println("======="+fetchProductOut.getProducts().getAny().size());
                                for(Object o:oo){
                                    if(o instanceof Product){
                                        Product p = (Product)o;
                                        System.out.println("----prods------"+p.getDescription());
                                        System.out.println("----------"+p.getNetwork());
                                        System.out.println("----------"+p.getCostprice());
                                        System.out.println("----------"+p.getSellvalue());
                                    }else if(o instanceof Products){
                                        Products p = (Products)o;
                                        System.out.println("----prods------"+p.getAny().size());
                                    }else if(o instanceof Vouchers){
                                        Vouchers p = (Vouchers)o;
                                        System.out.println("----Vouchers------"+p.getAny().size());
                                    }else if(o instanceof Voucher){
                                        Voucher p = (Voucher)o;
                                        System.out.println("----Voucher------"+p.getNetwork());
                                        System.out.println("----Voucher------"+p.getPin());
                                        System.out.println("----Voucher------"+p.getSerial());
                                        System.out.println("----Voucher------"+p.getCostprice());
                                        System.out.println("----Voucher------"+p.getSellvalue());
                                    }else if(o instanceof Array){
                                        Array p = (Array)o;
                                        System.out.println("----Array------"+p.getAny().size());
                                    }else if(o instanceof  com.sun.org.apache.xerces.internal.dom.ElementNSImpl){
                                        Product pp= new Product();
                                         com.sun.org.apache.xerces.internal.dom.ElementNSImpl p = (com.sun.org.apache.xerces.internal.dom.ElementNSImpl)o;
                                         pp.setDescription(p.getFirstChild().getFirstChild().getTextContent());
                                         pp.setNetwork(p.getChildNodes().item(1).getTextContent());
                                         pp.setSellvalue(Float.valueOf(p.getChildNodes().item(2).getTextContent()));
                                         pp.setCostprice(Float.valueOf(p.getLastChild().getTextContent()));
                                        System.out.println("----ElementNSImpl---1---"+p.getFirstChild().getFirstChild().getTextContent());
                                        System.out.println("----ElementNSImpl---2---"+p.getChildNodes().item(1).getTextContent());                                     
                                        System.out.println("----ElementNSImpl---3---"+p.getChildNodes().item(2).getTextContent());
                                        System.out.println("----ElementNSImpl---4---"+p.getLastChild().getTextContent());
                                        ps.add(pp);
                                    }
                                }
                                
                                reply.products=ps;
                                //reply.str=fetchProductOut.getProducts().getAny().get(0).toString();
                                //reply.product=(Product)fetchProductOut.getProducts().getAny().get(0);
                                //reply.arrayType=fetchProductOut.getProducts().getArrayType();
                                //reply.otherAttributes=fetchProductOut.getProducts().getOtherAttributes().get(1);
                                
                                 //log.info("Products "+fetchProductOut.getProducts());
                                 reply.setSucc("Products Found");
                               
                            
                            }else{
                                log.info("+++++++++ERROR "+fetchProductOut.getErrorcode()+" "+fetchProductOut.getMessage());
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
        
        
            //int userFreepaid =926920;//dev user
            int userFreepaid =3751523;
            String pass ="480720";

       try{ 
           
           
          Airtimeplus aService= new Airtimeplus() ;

           
                minput = gson.fromJson(json, Minput.class);
                if(minput.amount >= 1.0 && minput.phoneNumber.length()!=0 && minput.pin.length()!=0){
           
                    //create cyclos webservice
                    CyclosWebServicesClientFactory factory = new CyclosWebServicesClientFactory();
                    //factory.setServerRootUrl("http://sadev.mobicash.rw/south_africa/do/login");
                    factory.setServerRootUrl("http://test.mobicash.co.za/south_africa/");
                    //factory.setServerRootUrl("http://test.mcash.rw/rwanda/");
                    //factory.setServerRootUrl("http://54.149.233.142:8080/mobicore");
                    //payment endpoint
                    PaymentWebService paymentWebService = factory.getPaymentWebService();
                    AccessWebService accessWebservice =  factory.getAccessWebService();
                    
                    //agent credentials
                    String loginPassword =minput.pin;// "1122";//
                    String phone =minput.phoneNumber;// "agent1";//
                    String user = "airtime";
                    float amount = minput.amount;
                    
                    
                    //check cyclos credentials to debit client
                    CheckCredentialsParameters ccp = new CheckCredentialsParameters();
                    ccp.setCredentials(loginPassword);
                    ccp.setPrincipalType("mobilePhone");
                    ccp.setPrincipal(phone);
                    
                    CredentialsStatus cs = accessWebservice.checkCredentials(ccp);
                    switch (cs) {
                        case VALID:
                            //55 111 77 122 

                            //convert to big decimal & construct payment parameters
                            BigDecimal bd = new BigDecimal(minput.amount * Float.valueOf(minput.quantity));


                            log.info("amount"+bd);
                            PaymentParameters params = new PaymentParameters();

                            params.setFromMemberPrincipalType("mobilePhone");//for client buying directly
                            //params.setFromMemberPrincipalType("USER");//for Agent buying on behalf of client
                            params.setFromMember(phone);
                            //params.setFromMember("isaact");
                            params.setAmount(bd);
                            params.setCredentials(loginPassword);
                            //params.setCredentials("isaact123");
                            //params.setFromSystem(true);
                            //params.setToMemberPrincipalType("USER");
                            //params.setToMember(user);

                            params.setToSystem(true);
                            //params.setCurrency("Uganda Shillings");
                            params.setDescription("Client FREEPAID credit - airtime");

        //                    if("yes".equalsIgnoreCase(minput.isAgent)){
        //                        //params.setTransferTypeId(new Long(44));//for agent buying on behalf of client
        //                    }else{
        //                         //params.setTransferTypeId(new Long(41));//for client 
        //                    }

                            log.info("got there--------payment params set------------------");

                            // Perform the payment & check result
                            PaymentResult result = new PaymentResult();//paymentWebService.doPayment(params);
                            result.setStatus(PaymentStatus.PROCESSED);
                            switch (result.getStatus()) {
                                case PROCESSED:
        //                            String transactionNumber = result.getTransfer().getTransactionNumber();
        //                            log.info("The payment was successful. The transaction number is " + transactionNumber);
                                    //check Mobicah balance on FreePaid-to confirm if neccesary
                                    Pj.freepaidDoYourThing(userFreepaid,pass,amount);
                                    //reply.setSucc("Tax deduction:"+result.getStatus().toString());
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

                                        //Vouchers oo=fetchOrderOut.getVouchers();
                                        List<Object> oo=fetchOrderOut.getVouchers().getAny();

                                        List<Voucher> vs = new ArrayList<>();


                                        System.out.println("======="+fetchOrderOut.getVouchers().getAny().size());
                                        for(Object o:oo){
                                            if(o instanceof  com.sun.org.apache.xerces.internal.dom.ElementNSImpl){
                                                Voucher vv= new Voucher();
                                                 com.sun.org.apache.xerces.internal.dom.ElementNSImpl v = (com.sun.org.apache.xerces.internal.dom.ElementNSImpl)o;
                                                 vv.setNetwork(v.getFirstChild().getFirstChild().getTextContent());
                                                 vv.setSellvalue(Float.valueOf(v.getChildNodes().item(1).getTextContent()));
                                                 vv.setPin(v.getChildNodes().item(2).getTextContent());
                                                 vv.setCostprice(Float.valueOf(v.getChildNodes().item(3).getTextContent()));
                                                 vv.setCostprice(Float.valueOf(v.getLastChild().getTextContent()));
                                                System.out.println("----ElementNSImpl---1---"+v.getFirstChild().getFirstChild().getTextContent());
                                                System.out.println("----ElementNSImpl---2---"+v.getChildNodes().item(1).getTextContent());                                     
                                                System.out.println("----ElementNSImpl---3---"+v.getChildNodes().item(2).getTextContent());
                                                System.out.println("----ElementNSImpl---4---"+v.getChildNodes().item(3).getTextContent());
                                                System.out.println("----ElementNSImpl---5---"+v.getLastChild().getTextContent());
                                                vs.add(vv);
                                            }
                                        }

                                        reply.vouchers=vs;
                                        reply.totalCost=bd;

                                            //reply.fetchOrderOut = fetchOrderOut;

                                            log.info("Mobicash Charged Order No.: "+fetchOrderOut.getOrderno()+" Status: "+fetchOrderOut.getStatus());

                                            log.info("VOUCHER "+fetchOrderOut.getOrderno());
                                            reply.setSucc("Success purchase");


                                    }else{
                                        //reverse transaction
                                        ChargebackResult chargeResult = paymentWebService.chargeback(result.getTransfer().getId());

                                        switch(chargeResult.getStatus()){
                                            case SUCCESS:
                                                reply.message ="Payment reversed";
                                            case INVALID_PARAMETERS:
                                                reply.message ="Given transfer id or amount are invalid";
                                            case TRANSFER_NOT_FOUND:
                                                reply.message ="transfer to chargedback/reverse was not found";
                                            case TRANSFER_CANNOT_BE_CHARGEDBACK:
                                                reply.message ="Maximum allowed time for chargeback/reverse passed";
                                            case TRANSFER_ALREADY_CHARGEDBACK:
                                                reply.message ="Transfer was already charged back/reversed";
                                            default:
                                                log.info("CHARGEBACK fail"+chargeResult.getStatus());
                                        }
                                        log.error("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                                        reply.setError("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                                    }

                                    break;
                                case PENDING_AUTHORIZATION:
                                    log.info("The payment is awaiting authorization");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case INVALID_CHANNEL:
                                    log.info("The given user cannot access this channel");
                                    log.info(""+result.getStatus().toString());
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case INVALID_CREDENTIALS:
                                    log.info("You have entered an invalid PIN");
                                    log.info(""+result.getStatus().toString());
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case BLOCKED_CREDENTIALS:
                                    log.info("Your PIN is blocked by exceeding trials");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case INVALID_PARAMETERS:
                                    log.info("Please, check the given parameters");
                                    log.info(""+result.getStatus().toString());
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case NOT_ENOUGH_CREDITS:
                                    log.info("You don't have enough funds for this payment");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case MAX_DAILY_AMOUNT_EXCEEDED:
                                    log.info("You have already exceeded the maximum amount today");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                default:
                                    log.info("There was an error on the payment: " + result.getStatus());
                                    reply.setError(result.getStatus().toString());
                            }
                            
                             break;
                        case INVALID:
                            log.info("Wrong PIN: " + cs.toString());
                            reply.setError("Wrong PIN: " + cs.toString());
                            break;
                        case BLOCKED:
                            log.info("Blocked Account: " + cs.toString());
                            reply.setError("Wrong PIN: " + cs.toString());
                            break;
                        default:
                            log.info("error authentication: " + cs.toString());
                            reply.setError("authentication Error");
                                                       
                        }
                           
                }else{
                    reply.setError("Send amount,phoneNumber and pin");
                    log.error("Send amount,phoneNumber and pin");
                }
                         
          
                   
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
            //int userFreepaid =926920;//dev user
            int userFreepaid =3751523;
            String pass ="480720";

            //Live user ID
            //int userFreepaid =3751523;
              

       try{ 
           
           
           Airtimeplus aService= new Airtimeplus();

           
                minput = gson.fromJson(json, Minput.class);
                  if(minput.amount >= 1.0 && minput.phoneNumber.length()!=0 && minput.pin.length()!=0){
           
                    CyclosWebServicesClientFactory factory = new CyclosWebServicesClientFactory();
                    //factory.setServerRootUrl("http://sadev.mobicash.rw/south_africa/do/login");
                    factory.setServerRootUrl("http://test.mobicash.co.za/south_africa/");
                    //factory.setServerRootUrl("http://test.mcash.rw/rwanda/");
                    //factory.setServerRootUrl("http://test.mobicash.co.za/index.php/home/client");
                    
                    AccessWebService accessWebservice =  factory.getAccessWebService();
                    
                    String loginPassword =minput.pin;// "1122";//
                    String phone = Pj.checkPhoneNumberFormat(minput.phoneNumber);
                    log.info("+++++++++++++++++++++++"+phone);
                    String user = "airtime";
                    float amount = minput.amount;
                    String otherNumber ="";
                    String network =Pj.checkPhoneNetwork(phone);
                    String otherNetwork="";
                    
                    if(minput.otherNumber.length()>0){
                        otherNumber= Pj.checkPhoneNumberFormat(minput.otherNumber);
                        otherNetwork = Pj.checkPhoneNetwork(otherNumber);
                        
                    }
                    
                    //check cyclos credentials to debit client
                    CheckCredentialsParameters ccp = new CheckCredentialsParameters();
                    ccp.setCredentials(loginPassword);
                    ccp.setPrincipalType("mobilePhone");
                    ccp.setPrincipal(phone);
                    
                    CredentialsStatus cs = accessWebservice.checkCredentials(ccp);
                    switch (cs) {
                        case VALID:
                            PaymentWebService paymentWebService = factory.getPaymentWebService();
                            //convert to big decimal construct payment parameters
                            BigDecimal bd = new BigDecimal(minput.amount);
                            //BigDecimal taxAmount = new BigDecimal(jInput.taxedAmount);


                            log.info("amount"+bd);
                            PaymentParameters params = new PaymentParameters();


                            //params.setFromMemberPrincipalType("mobilePhone");//for client paying tax directly
                            params.setFromMemberPrincipalType("mobilePhone");//for Agent paying on behalf of client
                            params.setFromMember(phone);
                            
                            params.setAmount(bd);
                            //params.setCredentials(loginPassword);
                            //params.setFromSystem(true);
                            //params.setToMemberPrincipalType("USER");
                            //params.setToMember(user);
                            params.setToSystem(true);
                            //params.setCurrency("Uganda Shillings");
                            params.setDescription("Client FREEPAID credit - airtime");
//                            
//                            if("yes".equalsIgnoreCase(minput.isAgent)){
//                                 //params.setTransferTypeId(new Long(44));//for agent buying on behalf of client
//                            }else{
//                                 //params.setTransferTypeId(new Long(41));//for client 
//                             }
                            //params.setTransferTypeId(new Long(41));//for agent paying on behalf of client
                            //params.setTransferTypeId(new Long(41));//client SA to commission account
                            //params.setTransferTypeId(new Long(44));//for agent commission account

                            log.info("got there------------------------------");
                            // Perform the payment
                            PaymentResult result = paymentWebService.doPayment(params);
                            switch (result.getStatus()) {
                                case PROCESSED:
                                    String transactionNumber = result.getTransfer().getTransactionNumber();
                                    log.info("The payment was successful. The transaction number is " + transactionNumber+" Transaction Status: "+result.getStatus());
                                    Pj.freepaidDoYourThing(userFreepaid,pass,amount);
                                    reply.setSucc("Tax deduction:"+result.getStatus().toString());
                                    PlaceOrderIn placeOrderIn = new PlaceOrderIn();
                                    placeOrderIn.setUser(userFreepaid);
                                    placeOrderIn.setPass(pass);
                                    //placeOrderIn.setExtra("27843762255");//phone number of client to recharge
                                    if(otherNumber.length()<=0){
                                        placeOrderIn.setExtra(Pj.stripNumberFormat(phone));//phone number of other client to recharge
                                    }else{
                                        placeOrderIn.setExtra(Pj.stripNumberFormat(otherNumber));//phone number of client to recharge
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
                                                reply.totalCost=bd;
                                            }else{
                                                log.info("VOUCHER "+fetchOrderOut.getStatus());
                                                reply.setSucc("Success "+minput.amount+" sent to "+otherNumber+" Network: "+otherNetwork);
                                                reply.totalCost=bd;
                                            }
                                        }else{
                                            //reverse transaction
                                            ChargebackResult chargeResult = paymentWebService.chargeback(result.getTransfer().getId());

                                            switch(chargeResult.getStatus()){
                                                case SUCCESS:
                                                    reply.message ="Payment reversed";
                                                    break;
                                                case INVALID_PARAMETERS:
                                                    reply.message ="Given transfer id or amount are invalid";
                                                    break;
                                                case TRANSFER_NOT_FOUND:
                                                    reply.message ="Transfer to chargedback/reverse was not found";
                                                    break;
                                                case TRANSFER_CANNOT_BE_CHARGEDBACK:
                                                    reply.message ="Maximum allowed time for chargeback/reverse passed";
                                                    break;
                                                case TRANSFER_ALREADY_CHARGEDBACK:
                                                    reply.message ="transfer was already charged back/reversed";
                                                    break;
                                                default:
                                                    reply.message ="CHARGEBACK fail"+chargeResult.getStatus();
                                                    log.info("CHARGEBACK fail"+chargeResult.getStatus());
                                            }

                                            log.error("Error "+queryOrderOut.getErrorcode()+" "+queryOrderOut.getMessage());
                                            reply.setError("Error: "+queryOrderOut.getErrorcode()+" "+queryOrderOut.getMessage());
                                        }
//
                                    }else{
//
                                        //reverse transaction
                                            ChargebackResult chargeResult = paymentWebService.chargeback(result.getTransfer().getId());

                                            switch(chargeResult.getStatus()){
                                                case SUCCESS:
                                                    reply.message ="Payment reversed";
                                                    break;
                                                case INVALID_PARAMETERS:
                                                    reply.message ="Given transfer id or amount are invalid";
                                                    break;
                                                case TRANSFER_NOT_FOUND:
                                                    reply.message ="Transfer to chargedback/reverse was not found";
                                                    break;
                                                case TRANSFER_CANNOT_BE_CHARGEDBACK:
                                                    reply.message ="Maximum allowed time for chargeback/reverse passed";
                                                    break;
                                                case TRANSFER_ALREADY_CHARGEDBACK:
                                                    reply.message ="transfer was already charged back/reversed";
                                                    break;
                                                default:
                                                    log.info("CHARGEBACK fail"+chargeResult.getStatus());
                                            }

                                        log.error("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                                        reply.setError("+++++++ERROR "+placeOrderOut.getErrorcode()+" "+placeOrderOut.getMessage());
                                    }
                                    break;
                                case PENDING_AUTHORIZATION:
                                    log.info("The payment is awaiting authorization");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case INVALID_CHANNEL:
                                    log.info("The given user cannot access this channel");
                                    log.info(""+result.getStatus().toString());
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case INVALID_CREDENTIALS:
                                    log.info("You have entered an invalid PIN");
                                    log.info(""+result.getStatus().toString());
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case BLOCKED_CREDENTIALS:
                                    log.info("Your PIN is blocked by exceeding trials");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case INVALID_PARAMETERS:
                                    log.info("Please, check the given parameters");
                                    log.info(""+result.getStatus().toString());
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case NOT_ENOUGH_CREDITS:
                                    log.info("You don't have enough funds for this payment");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                case MAX_DAILY_AMOUNT_EXCEEDED:
                                    log.info("You have already exceeded the maximum amount today");
                                    reply.setError(result.getStatus().toString());
                                    break;
                                default:
                                    log.info("There was an error on the payment: " + result.getStatus());
                                    reply.setError("default"+result.getStatus().toString());
                            }
                            break;
                        case INVALID:
                            log.info("Wrong PIN: " + cs.toString());
                            reply.setError("Wrong PIN: " + cs.toString());
                            break;
                        case BLOCKED:
                            log.info("Blocked Account: " + cs.toString());
                            reply.setError("Wrong PIN: " + cs.toString());
                            break;
                        default:
                            log.info("error authentication: " + cs.toString());
                            reply.setError("authentication Error");
                                                       
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
