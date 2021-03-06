/*
 *This is the Bean handling the transactions.
 *
 *
 */
package com.light;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.Clock;
import javax.faces.bean.ManagedBean;
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
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author onome
 */
@Named(value = "processorBean")
@ManagedBean
public class ProcessorBean implements Serializable {
     /*All the objects needed for processing the request.
         declared with the private final qualifier to keep with the OO
         model.
      */
    private final Client pay = ClientBuilder.newClient();
    private final WebTarget paytarget = pay.target("https://api.paystack.co/transaction/initialize");
    private final MultivaluedHashMap<String,String> form = new MultivaluedHashMap();
    private final MultivaluedMap<String,Object> https_head = new MultivaluedHashMap();
    private Response payresponse;
    private final  AmazonSNSClient messagesender = new AmazonSNSClient();
    //CDI bean injected here to manage the information provided by the user
    private @Inject InfoBean information;
    //getter method
    public InfoBean getInformation(){
        return this.information;
    }
    //setter method
    public void setInformation(InfoBean Information){
        this.information = Information;
    }
    
    //Creates a new instance of ProcessorBean
    public ProcessorBean() {
    }
    
    //Function for preparing and rendering the payment page.
    public void preparepaymentpage(){
        
                       //necessary information needed for the POST request.
                       form.add("amount", information.getAmount());
                       form.add("email", information.getEmail_or_name());
                       https_head.add("Authorization","Bearer sk_test_d97f963ffb78901675cb6d7c48497236e5101884");
                       
                       //The Request sent and Response received in one line.
                       payresponse = paytarget.request(MediaType.APPLICATION_JSON)
                                                      .headers(https_head)
                                                      .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
                       System.out.println(payresponse.getStatus());
                       System.out.println(payresponse.toString());
                       System.out.println(information.getEmail_or_name());
                       System.out.println(Clock.systemUTC());
                       
                    
                        if (payresponse.getStatus() == 200){
                            //Extraction of payment link sent from paystack.com
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
                        //Redirects to the Error page if there is a problem with the information given.
                        else{
                                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                                    try {
                                externalContext.redirect("Error.xhtml");
                         } catch (IOException ex) {
                            System.err.printf("invalid link", ex.getMessage());
                                    }
                        }
                                 
                        }
    
    //Function for generating te utility pin
    public void generatepin(){
                        PublishResult result = messagesender.publish(new PublishRequest()
                                                                                   .withMessage("This is the pin 12334213322113")
                                                                                   .withPhoneNumber("+234" + information.getPhone_number().substring(1)));
                        System.out.println(result.getMessageId());
                        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect("Presentation.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ProcessorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

