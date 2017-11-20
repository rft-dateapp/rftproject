package com.baxi.android.rft_kocsmapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowPubsAcivity extends AppCompatActivity implements AsyncResponse{

    private TextView testTextField;

    private RequestTask task;

    private String toShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pubs_acivity);

        this.task = new RequestTask(this);

        this.testTextField = (TextView) findViewById(R.id.testTextView);

        if(isNetworkConnected()){
            task.execute("http://pubnfun.azurewebsites.net/PubnFunCore.svc/GetAllPub");
        }

        testTextField.setText(toShow);


    }

    public void showPubs(){

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void processFinish(String output) {
        this.toShow = output;
        this.testTextField.setText(toShow);
    }
}
