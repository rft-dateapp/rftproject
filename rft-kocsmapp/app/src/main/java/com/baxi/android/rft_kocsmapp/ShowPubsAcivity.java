package com.baxi.android.rft_kocsmapp;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    private RequestTask task;

    private List<Pub> publist;

    private ListView pubListView;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pubs_acivity);

        this.task = new RequestTask(this);

        this.publist = new ArrayList<Pub>();

        this.pubListView = (ListView) findViewById(R.id.pubList);

        if(isNetworkConnected()){
            task.execute("http://pubnfun.azurewebsites.net/PubnFunCore.svc/GetAllPub");
        }
        else{
            this.publist = readList();
        }

        showPubs();

    }

    public void showPubs(){
        this.adapter = new CustomArrayAdapter(ShowPubsAcivity.this, publist);
        pubListView.setAdapter(adapter);
        pubListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
                Pub pubToHandle = publist.get(position);
                System.out.println(pubToHandle);
                showPubDetailsDialog(pubToHandle);
            }
        });
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
            showPubs();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


    public void showPubDetailsDialog(Pub pub){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pub_details_dialog);
        dialog.setTitle("Részletek");

        TextView nameTextView = (TextView) dialog.findViewById(R.id.nameTextView);
        nameTextView.setText(pub.getName());

        TextView addressTextView = (TextView) dialog.findViewById(R.id.addressTextView);
        addressTextView.setText(pub.getAddress());

        TextView ratingTextView = (TextView) dialog.findViewById(R.id.ratingTextView);
        ratingTextView.setText("Értékelés: " + Double.toString(pub.getCustomerOverallRatings()));

        Button rateButton = (Button) dialog.findViewById(R.id.ratingButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Rate button clicked", Toast.LENGTH_LONG)
                        .show();
            }
        });

        Button backButton = (Button) dialog.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
