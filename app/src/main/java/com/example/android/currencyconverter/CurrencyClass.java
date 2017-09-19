package com.example.android.currencyconverter;

/**
 * Created by globe_000 on 9/14/2017.
 */

//import com.example.android.currencyconverter.Network;

import java.io.IOException;
import java.net.URL;

public class CurrencyClass {

    private double currencyAmount;
    private String country;
    private double currencyRate;

    public CurrencyClass(double ca, String c){

        this.currencyAmount = ca;
        this.country = c;
       // this.requestRate(c);
    }

    public CurrencyClass(double ca, String c, double r){

        this.currencyAmount = ca;
        this.country = c;
        this.currencyRate = r;
    }

    public double getCurrencyAmount() { return currencyAmount; }

    public String getCountry() { return country; }

    public double getRate() { return currencyRate; }

    public void setRate(double r) { this.currencyRate = r;}

    public void setCurrencyAmount(double ca) { this.currencyAmount = ca; }

    public void setCountry(String c) { this.country = c;}

    public double calculateConversion(){
        return this.currencyRate * this.currencyAmount;
    }


}
