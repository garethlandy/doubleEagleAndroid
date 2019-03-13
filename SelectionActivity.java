package com.example.gareth.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv1,tv2;
    Button btTeeTime,btPlayRound,btLeaderbaord,btUpdateProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        tv1 = (TextView)findViewById(R.id.etSelectionUsername);
        tv2 = (TextView)findViewById(R.id.etSelectionHandyCap);
        final String name = (SharedPrefManager.getInstance(this).getUsername().toUpperCase());
        tv1.setText(name);

        final String guiNumber = (SharedPrefManager.getInstance(this).getUserGUI().toUpperCase());
        tv2.setText(guiNumber);

        btTeeTime = (Button)findViewById(R.id.button1);
        btTeeTime.setOnClickListener(this);
        btPlayRound = (Button)findViewById(R.id.button3);
        btPlayRound.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        if(view == btTeeTime)
            startActivity(new Intent(this,TeeTimeActivity.class));
        if(view == btPlayRound)
            startActivity(new Intent(this,ViewTeeTimesByUser.class));
        if(view == btLeaderbaord)
            Toast.makeText(this, "Method to implement", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,LoginActivity.class));
        if(view == btUpdateProfile)
            Toast.makeText(this, "Method to implement", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,LoginActivity.class));

    }
}
