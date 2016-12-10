/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light;

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
    @Inject InfoBean Information;
    /**
     * Creates a new instance of ProcessorBean
     */
    public ProcessorBean() {
    }
    
}
