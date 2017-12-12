package com.baxi.android.rft_kocsmapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baxi.android.rft_kocsmapp.model.CustomerOpinion;
import com.baxi.android.rft_kocsmapp.model.Pub;
import com.baxi.android.rft_kocsmapp.util.JSONUtils;
import com.facebook.AccessToken;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rendgazd on 2017. 12. 06..
 */

public class ShowPubDialogTask extends AsyncTask<String, String, String> {

    private Pub pub;

    private Context context;


    private ArrayAdapter opinionAdapter;


    public ShowPubDialogTask(Pub pub, Context context){
        super();
        this.pub = pub;
        this.context = context;

    }

    @Override
    protected String doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urls[0])
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

    @Override
    public void onPostExecute(String result){
        System.out.println(result);

        final Dialog dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.pub_details_dialog);
        dialog.setTitle("Részletek");

        TextView nameTextView = (TextView) dialog.findViewById(R.id.pubNameTextView);
        nameTextView.setText(pub.getName());

        TextView addressTextView = (TextView) dialog.findViewById(R.id.opinionTextView);
        addressTextView.setText(pub.getAddress());

        TextView ratingTextView = (TextView) dialog.findViewById(R.id.ratingTextView);
        ratingTextView.setText(String.format("Értékelés: %.1f",pub.getCustomerOverallRatings()));

        final double pubLatitude = pub.getLatitude();
        final double pubLongitude = pub.getLongitude();
        final String pubName = pub.getName();

        JSONArray Jarray = null;
        List<CustomerOpinion> customerOpinions;
        try {
            Jarray = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Jarray != null){
            customerOpinions = JSONUtils.getOpinions(Jarray);
            this.pub.setCustomerOpinions(customerOpinions);
        }
        else{
            customerOpinions = this.pub.getCustomerOpinions();
        }

        final List<CustomerOpinion> opinions = pub.getCustomerOpinions();
        this.opinionAdapter = new CustomOpinionArrayAdapter(this.context, opinions);

        ListView opinionListView = (ListView) dialog.findViewById(R.id.opinionListView);
        opinionListView.setAdapter(opinionAdapter);
        opinionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerOpinion opinionToHandle = opinions.get(position);
                System.out.println(opinionToHandle);
            }
        });

        Button showOnMapButton = (Button) dialog.findViewById(R.id.showOnMapButton);
        showOnMapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPubDialogTask.this.context, ShowPubOnMapActivity.class);
                intent.putExtra("pubLatitude", pubLatitude);
                intent.putExtra("pubLongitude", pubLongitude);
                intent.putExtra("pubName", pubName);

                context.startActivity(intent);
            }
        });

        Button rateButton = (Button) dialog.findViewById(R.id.ratingButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessToken.getCurrentAccessToken() == null){
                    Toast.makeText(getApplicationContext(),
                            "Jelentkezz be!", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    showRatingDialog(pub);
                }

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

    public void showRatingDialog(final Pub pub){
        Log.d("showRatingDialog", "Showing dialog for posting opinions");
        final Dialog dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.post_opinion_dialog);
        dialog.setTitle("Mondd el a véleményed!");

        final EditText opinionText = (EditText) dialog.findViewById(R.id.OpinionText);

        Button rateButton = (Button) dialog.findViewById(R.id.postRatingButton);

        Button cancelButton = (Button) dialog.findViewById(R.id.cancelPostButton);

        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);



        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RateButton", "Rate Button clicked");

                    CustomerOpinion opinionToSend = new CustomerOpinion();
                    opinionToSend.setOpinion(opinionText.getText().toString());
                    opinionToSend.setRating(ratingBar.getRating());
                    opinionToSend.setCustomerName(Profile.getCurrentProfile().getName());
                    opinionToSend.setPubID(pub.getPubID());
                    opinionToSend.setCustomerID(AccessToken.getCurrentAccessToken().getUserId());
                    opinionToSend.setCustomerOpinionID(2);
                    System.out.println(opinionToSend);
                    String url = "http://rftpubnfun.azurewebsites.net/PubnFunCore.svc/postOpinion";
                    new PostOpinionTask(opinionToSend).execute(url);
                    dialog.dismiss();


            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
