package com.baxi.android.rft_kocsmapp;

import android.app.Activity;

/**
 * Created by rendgazd on 2017. 11. 28..
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baxi.android.rft_kocsmapp.MainActivity;

/**
 * Created by rendgazd on 2017. 11. 28..
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
