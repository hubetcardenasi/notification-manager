package com.portfolio.hubet.barcodescan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imgAuditory = null;
    private ImageView imgMaintenance = null;
    private ImageView imgReport = null;

    String sMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            imgAuditory = (ImageView) findViewById(R.id.imgAuditory);
            imgAuditory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, AuditoryActivity.class);
                startActivity(intent);
                finish();*/

                    gotoactivity(MainActivity.this, AuditActivity.class);
                }
            });

            imgMaintenance = (ImageView) findViewById(R.id.imgMaintenance);
            imgMaintenance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, MaintenanceActivity.class);
                startActivity(intent);
                finish();*/

                    gotoactivity(MainActivity.this, MaintenanceActivity.class);
                }
            });

            imgReport = (ImageView) findViewById(R.id.imgReport);
            imgReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
                finish();*/

                    gotoactivity(MainActivity.this, ReportActivity.class);
                }
            });
        } catch (Exception e) {
            sMessage = e.getMessage();
        }
    }

    private void gotoactivity(Context thisContext, Class NextActivity) {
        try {
            Intent intent = new Intent(thisContext, NextActivity);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            sMessage = e.getMessage();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }
}
