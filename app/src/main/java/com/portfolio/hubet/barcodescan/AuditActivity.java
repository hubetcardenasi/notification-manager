package com.portfolio.hubet.barcodescan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AuditActivity extends AppCompatActivity {
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        String sURL = "http://192.168.1.73/api/audit.aspx";
        sURL += "?Option=AuditList";
        new AuditActivity.JsonTask().execute(sURL);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AuditActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(AuditActivity.this);
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
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    List<String> values= new ArrayList<>();
                    values.add("Audit ID");values.add("Name");values.add("Total Asset Qty");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String AuditID=jsonObject1.getString("AuditID");
                        String Name=jsonObject1.getString("Name");
                        String TotalAssetQty=jsonObject1.getString("TotalAssetQty");

                        values.add(AuditID);values.add(Name);values.add(TotalAssetQty);
                    }
                    GridView myGrid = findViewById(R.id.gvAudit);
                    myGrid.setAdapter(new ArrayAdapter<>(AuditActivity.this, R.layout.gvcell, values));
                    myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            // Get the GridView selected/clicked item text

                            int row_no=position/3;
                            int col_no=position%3;
                            position = position - col_no;
                            String selectedItem = parent.getItemAtPosition(position).toString();
                            //String selectedItem = parent.getSelectedItem().toString();
                            //Toast.makeText(AuditActivity.this, "Value: " + selectedItem + "Position: " + position + " ID:" + id + " row_no:" + row_no + " col_no:" + col_no, Toast.LENGTH_SHORT).show();
                            Toast.makeText(AuditActivity.this, "Value: " + selectedItem, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
