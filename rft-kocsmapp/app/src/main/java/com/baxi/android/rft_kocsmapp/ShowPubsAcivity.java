package com.baxi.android.rft_kocsmapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baxi.android.rft_kocsmapp.model.Pub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ShowPubsAcivity extends AppCompatActivity implements AsyncResponse{

    private TextView testTextField;

    private RequestTask task;

    private List<Pub> publist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pubs_acivity);

        this.task = new RequestTask(this);

        this.publist = new ArrayList<Pub>();

        this.testTextField = (TextView) findViewById(R.id.testTextView);

        if(isNetworkConnected()){
            task.execute("http://pubnfun.azurewebsites.net/PubnFunCore.svc/GetAllPub");
        }
        else{
            this.publist = readList();
            String toShow = "";
            for(Pub pub : publist){
                toShow += pub;
                toShow += '\n';
            }
            this.testTextField.setText(toShow);
        }

    }

    public void showPubs(){

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONArray Jarray = new JSONArray(output);
            for(int i = 0; i < Jarray.length(); i++){
                if(Jarray.get(i).toString() != "null"){
                    JSONObject object = Jarray.getJSONObject(i);
                    Pub pub = new Pub();

                    String name = object.getString("Name");
                    double customerOverallRating = object.getDouble("CustomerOverallRatings");
                    int pubID = object.getInt("PubID");
                    String address = object.getString("Address");

                    pub.setAddress(address);
                    pub.setName(name);
                    pub.setCustomerOverallRatings(customerOverallRating);
                    pub.setPubID(pubID);

                    publist.add(pub);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String toShow = "";
        for(Pub pub : publist){
            toShow += pub;
            toShow += '\n';
        }

        this.testTextField.setText(toShow);

    }

    private void saveList(List<Pub> list){
        try {
            File file = Environment.getExternalStorageDirectory();
            File filename = new File(file, "pubs");
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<Pub> readList()
    {
        try {
            File file = Environment.getExternalStorageDirectory();
            File filename = new File(file, "pubs");
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            List<Pub> list= (List<Pub>) in.readObject();
            in.close();
            return list;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Pub>();
    }

    @Override
    public void onPause(){
        super.onPause();
        saveList(publist);
    }


}
