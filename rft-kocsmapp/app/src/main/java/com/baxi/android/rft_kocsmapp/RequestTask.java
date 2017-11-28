package com.baxi.android.rft_kocsmapp;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.StatusLine;

/**
 * Created by rendgazd on 2017. 11. 19..
 */

class RequestTask extends AsyncTask<String, String, String> {

    private AsyncResponse delegate;

    public RequestTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url[0])
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    protected void onPostExecute(String result) {
        System.out.println(result);
        delegate.processFinish(result);
    }

}
