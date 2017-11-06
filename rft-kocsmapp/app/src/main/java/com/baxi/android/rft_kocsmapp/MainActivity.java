package com.baxi.android.rft_kocsmapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showPubs(View view) {
        Intent intent = new Intent(this, ShowPubsAcivity.class);
        startActivity(intent);
    }

    public void showMap(View view) {
        Intent intent = new Intent(this, ShowMapActivity.class);
        startActivity(intent);
    }

    public void suggestOne(View view) {
        Intent intent = new Intent(this, SuggestOneActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view) {
        showExitDialog();
    }

    public void facebookLogin(View view) {
        Intent intent = new Intent(this, FacebookLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
    public void showExitDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setTitle("");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_beer_spill);

        TextView tv = (TextView) dialog.findViewById(R.id.exittext);
        tv.setText("Biztosan ki akarsz lépni?");

        Button yesButton = (Button) dialog.findViewById(R.id.dialogButtonYes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        Button noButton = (Button) dialog.findViewById(R.id.dialogButtonNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}


