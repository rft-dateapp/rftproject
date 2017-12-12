package com.baxi.android.rft_kocsmapp.util;

import com.baxi.android.rft_kocsmapp.model.CustomerOpinion;
import com.baxi.android.rft_kocsmapp.model.Pub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rendgazd on 2017. 11. 27..
 */

public class JSONUtils {

    public static Pub createPubFromJson(JSONObject object){

        Pub pub = new Pub();

        try {
            String name = object.getString("name");
            double customerOverallRating = 0.0;
            if(object.get("customerOverallRatings").toString() != "null"){
                customerOverallRating = object.getDouble("customerOverallRatings");
            }
            int pubID = object.getInt("pubID");
            String address = object.getString("address");
            JSONArray array = object.getJSONArray("customerOpinions");
            double latitude = object.getDouble("latitude");
            double longitude = object.getDouble("longitude");

            List<CustomerOpinion> opinions = getOpinions(array);

            pub.setAddress(address);
            pub.setName(name);
            pub.setCustomerOverallRatings(customerOverallRating);
            pub.setPubID(pubID);
            pub.setCustomerOpinions(opinions);
            pub.setLatitude(latitude);
            pub.setLongitude(longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pub;
    }

    public static CustomerOpinion createOpinionFromJson(JSONObject object){

        CustomerOpinion customerOpinion = new CustomerOpinion();

        try {
            String customerName = object.getString("customerName");
            String opinion = object.getString("opinion");

            double rating = 0.0;
            if(object.get("rating").toString() != "null"){
                rating = object.getDouble("rating");
            }
            int opinionID = 0;
            if(object.get("opinionID").toString() != "null"){
                opinionID = object.getInt("opinionID");
            }

            String customerID = object.getString("customerId");

            customerOpinion.setCustomerName(customerName);
            customerOpinion.setOpinion(opinion);
            customerOpinion.setCustomerOpinionID(opinionID);
            customerOpinion.setRating(rating);
            customerOpinion.setCustomerID(customerID);
            //customerOpinion.setPubID(pubID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerOpinion;
    }

    public static List<CustomerOpinion> getOpinions(JSONArray Jarray){
        List<CustomerOpinion> opinions = new ArrayList<>();

        for(int i = 0; i < Jarray.length(); i++){
            try {
                JSONObject object = Jarray.getJSONObject(i);
                CustomerOpinion opinion = createOpinionFromJson(object);
                opinions.add(opinion);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return opinions;
    }

}
