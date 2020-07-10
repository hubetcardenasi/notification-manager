package com.portfolio.hubet.barcodescan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MaintenanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MaintenanceActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }
}
