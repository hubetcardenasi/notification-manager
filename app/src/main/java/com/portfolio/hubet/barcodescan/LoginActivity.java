package com.portfolio.hubet.barcodescan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText txtUser = null;
    EditText txtPassword = null;
    Button btnOK = null;
    String sMessage = null;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            txtUser = findViewById(R.id.txtUser);
            txtPassword = findViewById(R.id.txtPassword);
            btnOK = findViewById(R.id.btnOK);

            if (btnOK != null) btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sURL = "http://192.168.1.73/api/login.aspx";
                    sURL += "?UserID=";
                    sURL += txtUser.getText();
                    sURL += "&Password=";
                    sURL += txtPassword.getText();
                    new JsonTask().execute(sURL);
                }
            });
        } catch (Exception e) {
            sMessage = e.getMessage();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            //display in long period of time
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if(result != null) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonResponse = null;
                try {
                    assert json != null;
                    jsonResponse = json.getJSONObject("login");
                    String ValidAccess = jsonResponse.getString("ValidAccess");

                    Boolean bValidAccess = Boolean.valueOf(ValidAccess.toLowerCase());
                    if (bValidAccess) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Non valid access", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
