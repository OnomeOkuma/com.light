/*This is the Bean used to handle the information provided by the user
 */
package com.light;

import javax.inject.Named;
import javax.enterprise.inject.Default;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
//import javax.faces.bean.ManagedBean;

/**
 *
 * @author onome
 */
@Default
@Named(value = "infoBean")
//@ManagedBean
@SessionScoped
public class InfoBean implements Serializable {
        
    private String meter_number;
    private String phone_number;
    private String email_or_name;
    private String amount;
    /**
     * Creates a new instance of InfoBean
     */
    public InfoBean() {
    }
    //Getter and Setter methods for the private variables
    public String getMeter_number(){
        return this.meter_number;
    }
    public void setMeter_number(String meter_number){
        this.meter_number = meter_number;
    }
    public String getPhone_number(){
        return this.phone_number;
    }
    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }
    public String getEmail_or_name(){
        return this.email_or_name;
    }
    public void setEmail_or_name(String email_or_name){
        this.email_or_name = email_or_name;
    }
    public String getAmount(){
        return this.amount;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }
}
