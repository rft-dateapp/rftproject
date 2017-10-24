package com.baxi.android.rft_kocsmapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showPubs(View view){
        Intent intent = new Intent(this, ShowPubsAcivity.class);
        startActivity(intent);
    }

    public void showMap(View view){
        Intent intent = new Intent(this, ShowMapActivity.class);
        startActivity(intent);
    }

    public void suggestOne(View view){
        Intent intent = new Intent(this, SuggestOneActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view){
        finish();
        System.exit(0);
    }

    public void facebookLogin(View view){
        Intent intent = new Intent(this, FacebookLoginActivity.class);
        startActivity(intent);
    }
}
