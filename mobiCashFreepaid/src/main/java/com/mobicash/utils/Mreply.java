/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobicash.utils;


import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigDecimal;



import java.util.List;
import za.co.freepaid.dev.ws.airtimeplus.FetchOrderOut;
import za.co.freepaid.dev.ws.airtimeplus.FetchProductsOut;
import za.co.freepaid.dev.ws.airtimeplus.Products;


/**
 *
 * @author Mac
 */
public class Mreply {

    //public Gson gson;
    static YaGson toStringGson = new YaGsonBuilder().setPrettyPrinting().create();

    public String transType;
    public boolean result;
    public String errorMessage;
    public String message;
    public BigDecimal amount;
    
    public FetchProductsOut fetchProductsOut;
    public FetchOrderOut fetchOrderOut;
    public List<FetchProductsOut> fetchProductsOutList;
    public Products products;
    public List<Products> productsList;
   

    public Mreply(String type) {
        System.out.println("+++++++++++: TRANS INIT:" + type);
        this.transType = type;
      //  gson = new Gson();
    }

    public Mreply() {
    }

    //UTILS FOR PROCESSING
    //SET ERROR
    public void setError(String error) {
        result = false;
        errorMessage = error;
    }

    //SET SUCCESS
    public void setSucc(String message) {
        result = true;
        this.message = message;
    }

    @Override
    public String toString() {
        return toStringGson.toJson(this);
    }

}
