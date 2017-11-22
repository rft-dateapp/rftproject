package com.baxi.android.rft_kocsmapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rendgazd on 2017. 11. 21..
 */

public class Pub implements Serializable{

    private String name;

    private double customerOverallRatings;

    private List<CustomerOpinion> customerCustomerOpinions;

    private String address;

    private int pubID;

    private LatLng position;

    public Pub(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCustomerOverallRatings() {
        return customerOverallRatings;
    }

    public void setCustomerOverallRatings(double customerOverallRatings) {
        this.customerOverallRatings = customerOverallRatings;
    }

    public List<CustomerOpinion> getCustomerCustomerOpinions() {
        return customerCustomerOpinions;
    }

    public void setCustomerCustomerOpinions(List<CustomerOpinion> customerCustomerOpinions) {
        this.customerCustomerOpinions = customerCustomerOpinions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPubID() {
        return pubID;
    }

    public void setPubID(int pubID) {
        this.pubID = pubID;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pub{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
