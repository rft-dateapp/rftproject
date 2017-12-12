package com.baxi.android.rft_kocsmapp;

import android.os.AsyncTask;

import com.baxi.android.rft_kocsmapp.model.CustomerOpinion;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PostOpinionTask extends AsyncTask<String, String, String>{

    private CustomerOpinion opinion;

    private boolean isNetworkConnected;

    OkHttpClient client;


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public PostOpinionTask(CustomerOpinion opinion){
        super();
        this.opinion = opinion;
        client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = null;
        try {
            response = post(urls[0], createJsonFromOpinion(this.opinion));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void onPostExecute(String result){
        System.out.println(result);


    }

    private String post(String url, String json) throws IOException {
        System.out.println(json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println(request);
        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    private String createJsonFromOpinion(CustomerOpinion opinion){

        String json = "{\"customerId\":\"" + opinion.getCustomerID()
                + "\" ,\"customerName\":\"" + opinion.getCustomerName()
                + "\" ,\"opinion\":\"" + opinion.getOpinion()
                +"\" ,\"pubID\":\"" + Integer.toString(opinion.getPubID())
                +"\",\"rating\":\"" + Double.toString(opinion.getRating())
                +"\"}";

        return json;
    }
}
