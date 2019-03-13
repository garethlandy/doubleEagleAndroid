package com.example.gareth.androidphpmysql;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gareth.androidphpmysql.Constants;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gareth.androidphpmysql.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TeeTimeActivity extends AppCompatActivity {

    TextView textView1, textView2;
    EditText editTextChooseDate;
    Spinner spinner5, spinner6;
    Button button;
    ImageView imageView;
    DatePickerDialog datePickerDialog;
    String name,gui,course,date,time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tee_time);


        //getJSON("http://192.168.1.46/Android/v1/getCourses.php");
        getJSON(Constants.URL_GET_COURSES);

        textView1 = (TextView) findViewById(R.id.teeTimeName);
        textView2 = (TextView) findViewById(R.id.teeTimeGUI);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner5.getBackground().setColorFilter(Color.parseColor("#871818"), PorterDuff.Mode.SRC_OUT);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner6.getBackground().setColorFilter(Color.parseColor("#871818"), PorterDuff.Mode.SRC_OUT);




        button = (Button) findViewById(R.id.btnCreateTeeTimeActivity);
        editTextChooseDate = (EditText)findViewById(R.id.editTextChooseDate);
        editTextChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(TeeTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                editTextChooseDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        final String name = (SharedPrefManager.getInstance(this).getUsername());
        textView1.setText(name);

        final String guiNumber = (SharedPrefManager.getInstance(this).getUserGUI());
        textView2.setText(guiNumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTeeTimeInformation();
            }
        });


    }

    public void getTeeTimeInformation(){
        name = String.valueOf(textView1.getText().toString());
        gui = String.valueOf(textView2.getText().toString());
        course = String.valueOf(spinner5.getSelectedItem());
        time = String.valueOf(spinner6.getSelectedItem());
        date = String.valueOf(editTextChooseDate.getText().toString());
        Log.i("date",date);

        String teeTime = name + " " + gui + " " + course + " " + time;

        if(name != null && gui != null && course != null && time != null){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            try {
                BitMatrix bitMatrix = multiFormatWriter.encode
                        (teeTime, BarcodeFormat.QR_CODE, 1000, 1000);

                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView = (ImageView)findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);

                createTeeTime();
                editTextChooseDate.setEnabled(false);
                button.setEnabled(false);
                spinner5.setEnabled(false);
                spinner6.setEnabled(false);



            }catch(WriterException e){
                e.printStackTrace();
            }
        }


    }


    public void createTeeTime(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_CREATE_TEETIME,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(getApplicationContext(),TeeTimeActivity.class));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }){
                public  Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("gui",gui);
                    params.put("course",course);
                    params.put("date",date);
                    params.put("time",time);
                    return params;
                }

            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }



    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                try {
                    loadCoursesIntoSpinner(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }


        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    public void loadCoursesIntoSpinner(String json) throws JSONException{

        JSONArray jsonArray = new JSONArray(json);
        String[] courses = new String[jsonArray.length()];

        for(int i = 0 ; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            courses[i] = obj.getString("name");
            Log.i("name",obj.getString("name"));

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courses);
        spinner5.setAdapter(arrayAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teetimemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                Toast.makeText(this,"See you soon",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.menuSettings:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;

            case R.id.register:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.showTeeTimes:
                startActivity(new Intent(this,ViewTeeTimesByUser.class));
                break;

        }
        return true;
    }
}
