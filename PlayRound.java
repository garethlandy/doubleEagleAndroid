package com.example.gareth.androidphpmysql;

import com.example.gareth.androidphpmysql.TeeTimeActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gareth.androidphpmysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayRound extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private TextView textView,textViewPlayRound;
    private EditText editText;
    private Button btn1,btn2,btn3,btn4,btnBack9;
    int f9,b9,total,currenthc;
    private ProgressDialog progressDialog;
    String course;
    ListItem listItem= new ListItem();
    String h1PAR,h2PAR,h3PAR,h4PAR,h5PAR,h6PAR,h7PAR,h8PAR,h9PAR,h10PAR,h11PAR,h12PAR,h13PAR,h14PAR,h15PAR,h16PAR,h17PAR,h18PAR;
    String h1Index,h2Index,h3Index,h4Index,h5Index,h6Index,h7Index,h8Index,h9Index,h10Index,h11Index,h12Index,h13Index,h14Index,h15Index,h16Index,h17Index,h18Index;
    String h1Len,h2Len,h3Len,h4Len,h5Len,h6Len,h7Len,h8Len,h9Len,h10Len,h11Len,h12Len,h13Len,h14Len,h15Len,h16Len,h17Len,h18Len;
    TextView ph1,ph2,ph3,ph4,ph5,ph6,ph7,ph8,ph9,ph10,ph11,ph12,ph13,ph14,ph15,ph16,ph17,ph18;
    int index1,index2,index3,index4,index5,index6,index7,index8,index9,index10,index11,index12,index13,index14,index15,index16,index17,index18;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);


        textViewPlayRound = (TextView) findViewById(R.id.textViewPlayRound);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Object name = extras.get("name");
        String courseName = String.valueOf(name);
        Object userHandicap = extras.get("userHandicap");
        String userHC = String.valueOf(userHandicap);

        currenthc = Integer.parseInt(userHC);

        Toast.makeText(getApplicationContext(),courseName + " " + userHC + " " + currenthc,Toast.LENGTH_LONG).show();

        textViewPlayRound.setText(name.toString());

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        progressDialog = new ProgressDialog(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        //btn2 = (Button)findViewById(R.id.btnTotalIn);
        // btn2.setOnClickListener(this);

        // btn3 = (Button)findViewById(R.id.btnTotal);
        // btn3.setOnClickListener(this);

         btn4 = (Button)findViewById(R.id.btn4);
         btn4.setOnClickListener(this);




        getJSON(Constants.URL_GET_COURSE_INFO_BY_ID+1);
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
               // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                try {
                    loadHoleInfo(s);
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

    private void loadHoleInfo(String json) throws JSONException{
        final JSONArray jsonArray = new JSONArray(json);
       // String[] holeInfo = new String[jsonArray.length()];

        JSONObject obj = jsonArray.getJSONObject(0);

        course = obj.getString("name");

        h1PAR = obj.getString("hole1par");
        TextView tvHolePar1 = (TextView)findViewById(R.id.hole1Par);
        tvHolePar1.setText(h1PAR);

        h1Index = obj.getString("hole1index");
        TextView tvHoleIndex1 = (TextView)findViewById(R.id.hole1Index);
        tvHoleIndex1.setText(h1Index);
        index1 = Integer.parseInt(tvHoleIndex1.getText().toString());

        h1Len = obj.getString("hole1length");
        TextView tvHoleLength1 = (TextView)findViewById(R.id.hole1Length);
        tvHoleLength1.setText(h1Len);

        h2PAR = obj.getString("hole2par");
        TextView tvHolePar2 = (TextView)findViewById(R.id.hole2Par);
        tvHolePar2.setText(h2PAR);

        h2Index = obj.getString("hole2index");
        TextView tvHoleIndex2 = (TextView)findViewById(R.id.hole2Index);
        tvHoleIndex2.setText(h2Index);
        index2 = Integer.parseInt(tvHoleIndex2.getText().toString());

        h2Len = obj.getString("hole2length");
        TextView tvHoleLength2 = (TextView)findViewById(R.id.hole2Length);
        tvHoleLength2.setText(h2Len);

        h3PAR = obj.getString("hole3par");
        TextView tvHolePar3 = (TextView)findViewById(R.id.hole3Par);
        tvHolePar3.setText(h3PAR);

        h3Index = obj.getString("hole3index");
        TextView tvHoleIndex3 = (TextView)findViewById(R.id.hole3Index);
        tvHoleIndex3.setText(h3Index);
        index3 = Integer.parseInt(tvHoleIndex3.getText().toString());

        h3Len = obj.getString("hole3length");
        TextView tvHoleLength3 = (TextView)findViewById(R.id.hole3Length);
        tvHoleLength3.setText(h3Len);

        h4PAR = obj.getString("hole4par");
        TextView tvHolePar4 = (TextView)findViewById(R.id.hole4Par);
        tvHolePar4.setText(h4PAR);

        h4Index = obj.getString("hole4index");
        TextView tvHoleIndex4 = (TextView)findViewById(R.id.hole4Index);
        tvHoleIndex4.setText(h4Index);
        index4 = Integer.parseInt(tvHoleIndex4.getText().toString());

        h4Len = obj.getString("hole4length");
        TextView tvHoleLength4 = (TextView)findViewById(R.id.hole4Length);
        tvHoleLength4.setText(h4Len);

        h5PAR = obj.getString("hole5par");
        TextView tvHolePar5 = (TextView)findViewById(R.id.hole5Par);
        tvHolePar5.setText(h5PAR);

        h5Index = obj.getString("hole5index");
        TextView tvHoleIndex5 = (TextView)findViewById(R.id.hole5Index);
        tvHoleIndex5.setText(h5Index);
        index5 = Integer.parseInt(tvHoleIndex5.getText().toString());

        h5Len = obj.getString("hole5length");
        TextView tvHoleLength5 = (TextView)findViewById(R.id.hole5Length);
        tvHoleLength5.setText(h5Len);

        h6PAR = obj.getString("hole6par");
        TextView tvHolePar6 = (TextView)findViewById(R.id.hole6Par);
        tvHolePar6.setText(h6PAR);

        h6Index = obj.getString("hole6index");
        TextView tvHoleIndex6 = (TextView)findViewById(R.id.hole6Index);
        tvHoleIndex6.setText(h6Index);
        index6 = Integer.parseInt(tvHoleIndex6.getText().toString());

        h6Len = obj.getString("hole6length");
        TextView tvHoleLength6 = (TextView)findViewById(R.id.hole6Length);
        tvHoleLength6.setText(h6Len);

        h7PAR = obj.getString("hole7par");
        TextView tvHolePar7 = (TextView)findViewById(R.id.hole7Par);
        tvHolePar7.setText(h7PAR);

        h7Index = obj.getString("hole7index");
        TextView tvHoleIndex7 = (TextView)findViewById(R.id.hole7Index);
        tvHoleIndex7.setText(h7Index);
        index7 = Integer.parseInt(tvHoleIndex7.getText().toString());

        h7Len = obj.getString("hole7length");
        TextView tvHoleLength7 = (TextView)findViewById(R.id.hole7Length);
        tvHoleLength7.setText(h7Len);

        h8PAR = obj.getString("hole8par");
        TextView tvHolePar8 = (TextView)findViewById(R.id.hole8Par);
        tvHolePar8.setText(h8PAR);

        h8Index = obj.getString("hole8index");
        TextView tvHoleIndex8 = (TextView)findViewById(R.id.hole8Index);
        tvHoleIndex8.setText(h8Index);
        index8 = Integer.parseInt(tvHoleIndex8.getText().toString());

        h8Len = obj.getString("hole8length");
        TextView tvHoleLength8 = (TextView)findViewById(R.id.hole8Length);
        tvHoleLength8.setText(h8Len);

        h9PAR = obj.getString("hole9par");
        TextView tvHolePar9 = (TextView)findViewById(R.id.hole9Par);
        tvHolePar9.setText(h9PAR);

        h9Index = obj.getString("hole9index");
        TextView tvHoleIndex9 = (TextView)findViewById(R.id.hole9Index);
        tvHoleIndex9.setText(h9Index);
        index9 = Integer.parseInt(tvHoleIndex9.getText().toString());

        h9Len = obj.getString("hole9length");
        TextView tvHoleLength9 = (TextView)findViewById(R.id.hole9Length);
        tvHoleLength9.setText(h9Len);

        h10PAR = obj.getString("hole10par");
        TextView tvHolePar10 = (TextView)findViewById(R.id.hole10Par);
        tvHolePar10.setText(h10PAR);

        h10Index = obj.getString("hole10index");
        TextView tvHoleIndex10 = (TextView)findViewById(R.id.hole10Index);
        tvHoleIndex10.setText(h10Index);
        index10 = Integer.parseInt(tvHoleIndex10.getText().toString());

        h10Len = obj.getString("hole10length");
        TextView tvHoleLength10 = (TextView)findViewById(R.id.hole10Length);
        tvHoleLength10.setText(h10Len);

        h11PAR = obj.getString("hole11par");
        TextView tvHolePar11 = (TextView)findViewById(R.id.hole11Par);
        tvHolePar11.setText(h11PAR);

        h11Index = obj.getString("hole11index");
        TextView tvHoleIndex11 = (TextView)findViewById(R.id.hole11Index);
        tvHoleIndex11.setText(h11Index);
        index11 = Integer.parseInt(tvHoleIndex11.getText().toString());

        h11Len = obj.getString("hole11length");
        TextView tvHoleLength11 = (TextView)findViewById(R.id.hole11Length);
        tvHoleLength11.setText(h11Len);

        h12PAR = obj.getString("hole12par");
        TextView tvHolePar12 = (TextView)findViewById(R.id.hole12Par);
        tvHolePar12.setText(h12PAR);

        h12Index = obj.getString("hole12index");
        TextView tvHoleIndex12 = (TextView)findViewById(R.id.hole12Index);
        tvHoleIndex12.setText(h12Index);
        index12 = Integer.parseInt(tvHoleIndex12.getText().toString());

        h12Len = obj.getString("hole12length");
        TextView tvHoleLength12 = (TextView)findViewById(R.id.hole12Length);
        tvHoleLength12.setText(h12Len);

        h13PAR = obj.getString("hole13par");
        TextView tvHolePar13 = (TextView)findViewById(R.id.hole13Par);
        tvHolePar13.setText(h13PAR);

        h13Index = obj.getString("hole13index");
        TextView tvHoleIndex13 = (TextView)findViewById(R.id.hole13Index);
        tvHoleIndex13.setText(h13Index);
        index13 = Integer.parseInt(tvHoleIndex13.getText().toString());

        h13Len = obj.getString("hole13length");
        TextView tvHoleLength13 = (TextView)findViewById(R.id.hole13Length);
        tvHoleLength13.setText(h13Len);

        h14PAR = obj.getString("hole14par");
        TextView tvHolePar14 = (TextView)findViewById(R.id.hole14Par);
        tvHolePar14.setText(h14PAR);

        h14Index = obj.getString("hole14index");
        TextView tvHoleIndex14 = (TextView)findViewById(R.id.hole14Index);
        tvHoleIndex14.setText(h14Index);
        index14 = Integer.parseInt(tvHoleIndex14.getText().toString());

        h14Len = obj.getString("hole14length");
        TextView tvHoleLength14 = (TextView)findViewById(R.id.hole14Length);
        tvHoleLength14.setText(h14Len);

        h15PAR = obj.getString("hole15par");
        TextView tvHolePar15 = (TextView)findViewById(R.id.hole15Par);
        tvHolePar15.setText(h15PAR);

        h15Index = obj.getString("hole15index");
        TextView tvHoleIndex15 = (TextView)findViewById(R.id.hole15Index);
        tvHoleIndex15.setText(h15Index);
        index15 = Integer.parseInt(tvHoleIndex15.getText().toString());

        h15Len = obj.getString("hole15length");
        TextView tvHoleLength15 = (TextView)findViewById(R.id.hole15Length);
        tvHoleLength15.setText(h15Len);

        h16PAR = obj.getString("hole16par");
        TextView tvHolePar16 = (TextView)findViewById(R.id.hole16Par);
        tvHolePar16.setText(h16PAR);

        h16Index = obj.getString("hole16index");
        TextView tvHoleIndex16 = (TextView)findViewById(R.id.hole16Index);
        tvHoleIndex16.setText(h16Index);
        index16 = Integer.parseInt(tvHoleIndex16.getText().toString());

        h16Len = obj.getString("hole16length");
        TextView tvHoleLength16 = (TextView)findViewById(R.id.hole16Length);
        tvHoleLength16.setText(h16Len);

        h17PAR = obj.getString("hole17par");
        TextView tvHolePar17 = (TextView)findViewById(R.id.hole17Par);
        tvHolePar17.setText(h17PAR);

        h17Index = obj.getString("hole17index");
        TextView tvHoleIndex17 = (TextView)findViewById(R.id.hole17Index);
        tvHoleIndex17.setText(h17Index);
        index17 = Integer.parseInt(tvHoleIndex17.getText().toString());

        h17Len = obj.getString("hole17length");
        TextView tvHoleLength17 = (TextView)findViewById(R.id.hole17Length);
        tvHoleLength17.setText(h17Len);

        h18PAR = obj.getString("hole18par");
        TextView tvHolePar18 = (TextView)findViewById(R.id.hole18Par);
        tvHolePar18.setText(h18PAR);

        h18Index = obj.getString("hole18index");
        TextView tvHoleIndex18 = (TextView)findViewById(R.id.hole18Index);
        tvHoleIndex18.setText(h18Index);
        index18 = Integer.parseInt(tvHoleIndex18.getText().toString());

        h18Len = obj.getString("hole18length");
        TextView tvHoleLength18 = (TextView)findViewById(R.id.hole18Length);
        tvHoleLength18.setText(h18Len);



    }


    public int getPoints(int par, int score, int handicap, int index){
        int result;

        //Player has and extra shot on each hole
        if(index <= handicap) {

            if (score == par)
                result = 3;
            else if (score == (par - 1))
                result = 4;
            else if (score == (par + 1))
                result = 2;
            else if (score == (par - 2))
                result = 5;
            else if (score == (par + 2))
                result = 1;
            else if (score == (par - 3))
                result = 6;
            else
                result = 0;
        }
        else
        {
            //Player has NO extra shot on each hole
            if (score == par)
                result = 2;
            else if (score == (par - 1))
                result = 3;
            else if (score == (par + 1))
                result = 1;
            else if (score == (par - 2))
                result = 4;
            else if (score == (par + 2))
                result = 0;
            else if (score == (par - 3))
                result = 5;
            else
                result = 0;

        }
        return result;
    }





    public void calculateScore(){
        int h1=0,h2=0,h3=0,h4=0,h5=0,h6=0,h7=0,h8=0,h9=0,h10=0,h11=0,h12=0,h13=0,h14=0,h15=0,h16=0,h17=0,h18=0;
        EditText HOLE1 = (EditText)findViewById(R.id.scoreHole1);
        EditText HOLE2 = (EditText)findViewById(R.id.scoreHole2);
        EditText HOLE3 = (EditText)findViewById(R.id.scoreHole3);
        EditText HOLE4 = (EditText)findViewById(R.id.scoreHole4);
        EditText HOLE5 = (EditText)findViewById(R.id.scoreHole5);
        EditText HOLE6 = (EditText)findViewById(R.id.scoreHole6);
        EditText HOLE7 = (EditText)findViewById(R.id.scoreHole7);
        EditText HOLE8 = (EditText)findViewById(R.id.scoreHole8);
        EditText HOLE9 = (EditText)findViewById(R.id.scoreHole9);
        EditText HOLE10 = (EditText)findViewById(R.id.scoreHole10);
        EditText HOLE11 = (EditText)findViewById(R.id.scoreHole11);
        EditText HOLE12 = (EditText)findViewById(R.id.scoreHole12);
        EditText HOLE13 = (EditText)findViewById(R.id.scoreHole13);
        EditText HOLE14 = (EditText)findViewById(R.id.scoreHole14);
        EditText HOLE15 = (EditText)findViewById(R.id.scoreHole15);
        EditText HOLE16 = (EditText)findViewById(R.id.scoreHole16);
        EditText HOLE17 = (EditText)findViewById(R.id.scoreHole17);
        EditText HOLE18 = (EditText)findViewById(R.id.scoreHole18);


        String hole1 = HOLE1.getText().toString();
        String hole2 = HOLE2.getText().toString();
        String hole3 = HOLE3.getText().toString();
        String hole4 = HOLE4.getText().toString();
        String hole5 = HOLE5.getText().toString();
        String hole6 = HOLE6.getText().toString();
        String hole7 = HOLE7.getText().toString();
        String hole8 = HOLE8.getText().toString();
        String hole9 = HOLE9.getText().toString();
        String hole10 = HOLE10.getText().toString();
        String hole11 = HOLE11.getText().toString();
        String hole12 = HOLE12.getText().toString();
        String hole13 = HOLE13.getText().toString();
        String hole14 = HOLE14.getText().toString();
        String hole15 = HOLE15.getText().toString();
        String hole16 = HOLE16.getText().toString();
        String hole17 = HOLE17.getText().toString();
        String hole18 = HOLE18.getText().toString();

        if(        hole1.isEmpty() || hole1.length() == 0 || hole1.equals("") || hole1 == null
                || hole2.isEmpty() || hole2.length() == 0 || hole2.equals("") || hole2 == null
                || hole3.isEmpty() || hole3.length() == 0 || hole3.equals("") || hole3 == null
                || hole4.isEmpty() || hole4.length() == 0 || hole4.equals("") || hole4 == null
                || hole5.isEmpty() || hole5.length() == 0 || hole5.equals("") || hole5 == null
                || hole6.isEmpty() || hole6.length() == 0 || hole6.equals("") || hole6 == null
                || hole7.isEmpty() || hole7.length() == 0 || hole7.equals("") || hole7 == null
                || hole8.isEmpty() || hole8.length() == 0 || hole8.equals("") || hole8 == null
                || hole9.isEmpty() || hole9.length() == 0 || hole9.equals("") || hole9 == null
                || hole10.isEmpty() || hole10.length() == 0 || hole10.equals("") || hole10 == null
                || hole11.isEmpty() || hole11.length() == 0 || hole11.equals("") || hole11 == null
                || hole12.isEmpty() || hole12.length() == 0 || hole12.equals("") || hole12 == null
                || hole13.isEmpty() || hole13.length() == 0 || hole13.equals("") || hole13 == null
                || hole14.isEmpty() || hole14.length() == 0 || hole14.equals("") || hole14 == null
                || hole15.isEmpty() || hole15.length() == 0 || hole15.equals("") || hole15 == null
                || hole16.isEmpty() || hole16.length() == 0 || hole16.equals("") || hole16 == null
                || hole17.isEmpty() || hole17.length() == 0 || hole17.equals("") || hole17 == null
                || hole18.isEmpty() || hole18.length() == 0 || hole18.equals("") || hole18 == null

                )
        {
            Toast.makeText(this, "You Missed a Hole Please recheck or if no score enter 8", Toast.LENGTH_LONG).show();
        }

        else
        {
            h1 = Integer.parseInt(hole1) ;
            int hole1par = Integer.parseInt(h1PAR);
            int getHP1 = getPoints(hole1par,h1,currenthc,index1);
            String HP1SCORE = String.valueOf(getHP1);
            ph1 = (TextView)findViewById(R.id.pointsHole1);
            ph1.setText(HP1SCORE);

            h2 = Integer.parseInt(hole2) ;
            int hole2par = Integer.parseInt(h2PAR);
            int getHP2 = getPoints(hole2par,h2,currenthc,index2);
            String HP2SCORE = String.valueOf(getHP2);
            ph2 = (TextView)findViewById(R.id.pointsHole2);
            ph2.setText(HP2SCORE);

            h3 = Integer.parseInt(hole3) ;
            int hole3par = Integer.parseInt(h3PAR);
            int getHP3 = getPoints(hole3par,h3,currenthc,index3);
            String HP3SCORE = String.valueOf(getHP3);
            ph3 = (TextView)findViewById(R.id.pointsHole3);
            ph3.setText(HP3SCORE);

            h4 = Integer.parseInt(hole4) ;
            int hole4par = Integer.parseInt(h4PAR);
            int getHP4 = getPoints(hole4par,h4,currenthc,index4);
            String HP4SCORE = String.valueOf(getHP4);
            ph4 = (TextView)findViewById(R.id.pointsHole4);
            ph4.setText(HP4SCORE);

            h5 = Integer.parseInt(hole5) ;
            int hole5par = Integer.parseInt(h5PAR);
            int getHP5 = getPoints(hole5par,h5,currenthc,index5);
            String HP5SCORE = String.valueOf(getHP5);
            ph5 = (TextView)findViewById(R.id.pointsHole5);
            ph5.setText(HP5SCORE);

            h6 = Integer.parseInt(hole6) ;
            int hole6par = Integer.parseInt(h6PAR);
            int getHP6 = getPoints(hole6par,h6,currenthc,index6);
            String HP6SCORE = String.valueOf(getHP6);
            ph6 = (TextView)findViewById(R.id.pointsHole6);
            ph6.setText(HP6SCORE);

            h7 = Integer.parseInt(hole7) ;
            int hole7par = Integer.parseInt(h7PAR);
            int getHP7 = getPoints(hole7par,h7,currenthc,index7);
            String HP7SCORE = String.valueOf(getHP7);
            ph7 = (TextView)findViewById(R.id.pointsHole7);
            ph7.setText(HP7SCORE);

            h8 = Integer.parseInt(hole8) ;
            int hole8par = Integer.parseInt(h8PAR);
            int getHP8 = getPoints(hole8par,h8,currenthc,index8);
            String HP8SCORE = String.valueOf(getHP8);
            ph8 = (TextView)findViewById(R.id.pointsHole8);
            ph8.setText(HP8SCORE);

            h9 = Integer.parseInt(hole9) ;
            int hole9par = Integer.parseInt(h9PAR);
            int getHP9 = getPoints(hole9par,h9,currenthc,index9);
            String HP9SCORE = String.valueOf(getHP9);
            ph9 = (TextView)findViewById(R.id.pointsHole9);
            ph9.setText(HP9SCORE);

            h10 = Integer.parseInt(hole10) ;
            int hole10par = Integer.parseInt(h10PAR);
            int getHP10 = getPoints(hole10par,h10,currenthc,index10);
            String HP10SCORE = String.valueOf(getHP10);
            ph10 = (TextView)findViewById(R.id.pointsHole10);
            ph10.setText(HP10SCORE);

            h11 = Integer.parseInt(hole11) ;
            int hole11par = Integer.parseInt(h11PAR);
            int getHP11 = getPoints(hole11par,h11,currenthc,index11);
            String HP11SCORE = String.valueOf(getHP11);
            ph11 = (TextView)findViewById(R.id.pointsHole11);
            ph11.setText(HP11SCORE);

            h12 = Integer.parseInt(hole12) ;
            int hole12par = Integer.parseInt(h12PAR);
            int getHP12 = getPoints(hole12par,h12,currenthc,index12);
            String HP12SCORE = String.valueOf(getHP12);
            ph12 = (TextView)findViewById(R.id.pointsHole12);
            ph12.setText(HP12SCORE);

            h13 = Integer.parseInt(hole13) ;
            int hole13par = Integer.parseInt(h13PAR);
            int getHP13 = getPoints(hole13par,h13,currenthc,index13);
            String HP13SCORE = String.valueOf(getHP13);
            ph13 = (TextView)findViewById(R.id.pointsHole13);
            ph13.setText(HP13SCORE);

            h14 = Integer.parseInt(hole14) ;
            int hole14par = Integer.parseInt(h14PAR);
            int getHP14 = getPoints(hole14par,h14,currenthc,index14);
            String HP14SCORE = String.valueOf(getHP14);
            ph14 = (TextView)findViewById(R.id.pointsHole14);
            ph14.setText(HP14SCORE);

            h15 = Integer.parseInt(hole15) ;
            int hole15par = Integer.parseInt(h15PAR);
            int getHP15 = getPoints(hole15par,h15,currenthc,index15);
            String HP15SCORE = String.valueOf(getHP15);
            ph15 = (TextView)findViewById(R.id.pointsHole15);
            ph15.setText(HP15SCORE);

            h16 = Integer.parseInt(hole16) ;
            int hole16par = Integer.parseInt(h16PAR);
            int getHP16 = getPoints(hole16par,h16,currenthc,index16);
            String HP16SCORE = String.valueOf(getHP16);
            ph16 = (TextView)findViewById(R.id.pointsHole16);
            ph16.setText(HP16SCORE);

            h17 = Integer.parseInt(hole17) ;
            int hole17par = Integer.parseInt(h17PAR);
            int getHP17 = getPoints(hole17par,h17,currenthc,index17);
            String HP17SCORE = String.valueOf(getHP17);
            ph17 = (TextView)findViewById(R.id.pointsHole17);
            ph17.setText(HP17SCORE);

            h18 = Integer.parseInt(hole18) ;
            int hole18par = Integer.parseInt(h18PAR);
            int getHP18 = getPoints(hole18par,h18,currenthc,index18);
            String HP18SCORE = String.valueOf(getHP18);
            ph18 = (TextView)findViewById(R.id.pointsHole18);
            ph18.setText(HP18SCORE);

            int totalPoints = getHP1+getHP2+getHP3+getHP4+getHP5+getHP6+getHP7+getHP8+getHP9
                    +getHP10+getHP11+getHP12+getHP13+getHP14+getHP15+getHP16+getHP17+getHP18;
            TextView tvTotalPoints = (TextView)findViewById(R.id.scoreToatlPointsOut);
            tvTotalPoints.setText(String.valueOf(totalPoints));
        }

        int totalScore =(h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18);
        f9 = totalScore;
        total = f9;
        TextView viewOutScore = (TextView)findViewById(R.id.scoreTotalOut);
        viewOutScore.setText(String.valueOf(totalScore));




        /*try {
            Thread.sleep(5000);
            registerScore();
            //setContentView(R.layout.activity_play_round_2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    }


    public void registerScore(){

        final String v = (SharedPrefManager.getInstance(this).getUsername());
        final String x  = (SharedPrefManager.getInstance(this).getUserGUI());
        final String userscore = String.valueOf(total);

        progressDialog.setMessage("Registering Score...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER_SCORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("username"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",v);
                params.put("score",userscore);
                params.put("userid",x);
                params.put("course",course);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        //finish();
        //startActivity(new Intent(this,ProfileActivity.class));
    }



    @Override
    public void onClick(View v) {
        if(v== btn1)
            calculateScore();
        if(v== btn4)
            registerScore();
        if(v== btn2)
           // calculateBack9();
        if(v== btn3)
           // calculateFinalScore();

        if(v==btnBack9) {
            btnBack9 = (Button)findViewById(R.id.btnBack9);
            btnBack9.setOnClickListener(this);
            setContentView(R.layout.activity_play_round);
        }




    }
}
