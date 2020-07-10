package com.portfolio.hubet.barcodescan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ReportActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }
}
