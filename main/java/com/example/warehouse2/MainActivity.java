package com.example.warehouse2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBarColor(R.color.blue4);
    }

    public void onClickToActivityWare(View v){
        Intent i = new Intent(this, Ware.class);
        startActivity(i);
    }

    public void onClickToActivityUnit(View v){
        Intent i = new Intent(this, AddUnit.class);
        startActivity(i);
    }

    public void onClickToActivityDoc1(View V){
        Intent i = new Intent(this, DocumentEnrollment.class);
        startActivity(i);
    }

    public void onClickToActivityDoc2(View V){
        Intent i = new Intent(this, DocumentOffs.class);
        startActivity(i);
    }

    public void onClickToActivityReport(View V){
        Intent i = new Intent(this, Report.class);
        startActivity(i);
    }

    @SuppressLint("ObsoleteSdkInt")
    public void setStatusBarColor(int color) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { return; }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, color));
    }
}