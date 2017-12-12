package com.baxi.android.rft_kocsmapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baxi.android.rft_kocsmapp.model.CustomerOpinion;
import com.baxi.android.rft_kocsmapp.model.Pub;
import com.baxi.android.rft_kocsmapp.util.JSONUtils;

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


public class ShowPubsActivity extends AppCompatActivity implements AsyncResponse{

    private RequestTask task;

    private ShowPubDialogTask dialogTask;

    private List<Pub> pubList;
    private ListView pubListView;

    private ArrayAdapter pubAdapter;
    private ArrayAdapter opinionAdapter;

    private static boolean wasDownloaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pubs_acivity);

        this.task = new RequestTask(this);
        this.pubList = new ArrayList<Pub>();
        this.pubListView = (ListView) findViewById(R.id.pubList);

        if(isNetworkConnected() && !wasDownloaded){
            this.wasDownloaded = true;
            String url = "http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/GetAllPub";
            task.execute(url);
        }
        else{
            this.pubList = readList();
        }
        showPubs();
    }

    public void refresh(View view){
        if(isNetworkConnected()){
            pubList.clear();
            this.task = new RequestTask(this);
            task.execute("http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/GetAllPub");
        }
    }

    public void showPubs(){
        this.pubAdapter = new CustomPubArrayAdapter(ShowPubsActivity.this, pubList);
        pubListView.setAdapter(pubAdapter);
        pubListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
                Pub pubToHandle = pubList.get(position);
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
                    Pub pub = JSONUtils.createPubFromJson(object);
                    pubList.add(pub);
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
    private List<Pub> readList() {
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
        saveList(pubList);
    }


    public void showPubDetailsDialog(Pub pub){

        if(isNetworkConnected()){
            this.dialogTask = new ShowPubDialogTask(pub, this);
            String id = Integer.toString(pub.getPubID());
            String url = "http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/GetAllOpinionAboutPubByID/" + id;
            dialogTask.execute(url);
        }
        else{
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.pub_details_dialog);
            dialog.setTitle("Részletek");

            TextView nameTextView = (TextView) dialog.findViewById(R.id.pubNameTextView);
            nameTextView.setText(pub.getName());

            TextView addressTextView = (TextView) dialog.findViewById(R.id.opinionTextView);
            addressTextView.setText(pub.getAddress());

            TextView ratingTextView = (TextView) dialog.findViewById(R.id.ratingTextView);
            ratingTextView.setText(String.format("Értékelés: %.1f" ,pub.getCustomerOverallRatings()));

            final double pubLatitude = pub.getLatitude();
            final double pubLongitude = pub.getLongitude();
            final String pubName = pub.getName();

            final List<CustomerOpinion> opinions = pub.getCustomerOpinions();
            this.opinionAdapter = new CustomOpinionArrayAdapter(ShowPubsActivity.this, opinions);

            ListView opinionListView = (ListView) dialog.findViewById(R.id.opinionListView);
            opinionListView.setAdapter(opinionAdapter);
            opinionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(getApplicationContext(),
                            "Click ListItem Number " + position, Toast.LENGTH_LONG)
                            .show();
                    CustomerOpinion opinionToHandle = opinions.get(position);
                    System.out.println(opinionToHandle);
                }
            });

            Button showOnMapButton = (Button) dialog.findViewById(R.id.showOnMapButton);
            showOnMapButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),
                            "Showing on map", Toast.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(ShowPubsActivity.this, ShowPubOnMapActivity.class);
                    intent.putExtra("pubLatitude", pubLatitude);
                    intent.putExtra("pubLongitude", pubLongitude);
                    intent.putExtra("pubName", pubName);

                    startActivity(intent);
                }
            });

            Button rateButton = (Button) dialog.findViewById(R.id.ratingButton);
            rateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),
                            "Nincs internetkapcsolat az értékeléshez", Toast.LENGTH_LONG)
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
}
