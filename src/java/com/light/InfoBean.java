/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import java.io.Serializable;

/**
 *
 * @author onome
 */
@Default
@Named(value = "infoBean")
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
    public String getmeter_number(){
        return this.meter_number;
    }
    public void setmeter_number(String meter_number){
        this.meter_number = meter_number;
    }
    public String getphone_number(){
        return this.phone_number;
    }
    public void setphone_number(String phone_number){
        this.phone_number = phone_number;
    }
    public String getemail_or_name(){
        return this.email_or_name;
    }
    public void setemail_or_name(String email_or_name){
        this.email_or_name = email_or_name;
    }
    public String getamount(){
        return this.amount;
    }
    public void setamount(String amount){
        this.amount = amount;
    }
}
