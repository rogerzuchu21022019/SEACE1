package com.example.asm.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.asm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
    }


}