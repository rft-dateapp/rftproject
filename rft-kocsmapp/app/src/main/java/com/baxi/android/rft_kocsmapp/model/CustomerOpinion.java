package com.baxi.android.rft_kocsmapp.model;

import java.io.Serializable;

/**
 * Created by rendgazd on 2017. 11. 21..
 */

public class CustomerOpinion implements Serializable{

    private String opinion;

    private double rating;

    private String customerName;

    private int customerOpinionID;

    private int pubID;

    public CustomerOpinion(){

    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerOpinionID() {
        return customerOpinionID;
    }

    public void setCustomerOpinionID(int customerOpinionID) {
        this.customerOpinionID = customerOpinionID;
    }

    public int getPubID() {
        return pubID;
    }

    public void setPubID(int pubID) {
        this.pubID = pubID;
    }
}
