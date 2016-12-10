/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light;

import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

/**
 *
 * @author onome
 */
@Named(value = "processorBean")
@SessionScoped
public class ProcessorBean implements Serializable {
            
    
    private final Client pay = ClientBuilder.newClient();
    private final WebTarget paytarget = pay.target("https://api.paystack.co/transaction/initialize");
    private final MultivaluedHashMap<String,String> form = new MultivaluedHashMap();
    private final MultivaluedMap<String,Object> https_head = new MultivaluedHashMap();
    private Response payresponse;
    private @Inject InfoBean information;
    public InfoBean getInformation(){
        return this.information;
    }
    public void setInformation(InfoBean Information){
        this.information = Information;
    }
    /**
     * Creates a new instance of ProcessorBean
     */
    public ProcessorBean() {
    }
    
    public void preparepaymentpage(){
                       form.add("amount", information.getAmount());
                       form.add("email", information.getEmail_or_name());
                       https_head.add("Authorization","Bearer sk_test_d97f963ffb78901675cb6d7c48497236e5101884");
                       payresponse = paytarget.request(MediaType.APPLICATION_JSON)
                                                      .headers(https_head)
                                                      .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
                       System.out.println(payresponse.getStatus());
                       
                        if (payresponse.getStatus() == 200){
                                 JsonReader reader = Json.createReader(payresponse.readEntity(InputStream.class));
                                 JsonObject object = reader.readObject();
                                  reader.close();
                                 object = object.getJsonObject("data");
                                 JsonString object2 = object.getJsonString("authorization_url");
                                 String payment_link = object2.getString();
                                  ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        try {
                                externalContext.redirect(payment_link);
                         } catch (IOException ex) {
                            System.err.printf("invalid link", ex.getMessage());
                                    }
                         }
                        else{
             
                        }
                                 
                        }
    }
