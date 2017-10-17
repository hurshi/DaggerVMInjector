package com.github.hurshi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hurshi.github.com.daggervminjector_annotations.DaggerVMModule;


@DaggerVMModule(TempModule.class)
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
