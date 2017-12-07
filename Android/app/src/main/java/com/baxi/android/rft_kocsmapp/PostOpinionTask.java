package com.baxi.android.rft_kocsmapp;

import android.os.AsyncTask;

import com.baxi.android.rft_kocsmapp.model.CustomerOpinion;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PostOpinionTask extends AsyncTask<String, String, String>{
    private int pubId;

    private CustomerOpinion opinion;

    private boolean isNetworkConnected;

    private String url = "";

    OkHttpClient client;


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public PostOpinionTask(int pubId, CustomerOpinion opinion){
        super();
        this.pubId = pubId;
        this.opinion = opinion;
        client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(String... urls) {


        return null;
    }

    @Override
    public void onPostExecute(String result){
        System.out.println(result);


    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    private String createJsonFromOpinion(CustomerOpinion opinion){
        return null;
    }
}
