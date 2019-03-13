

package com.example.gareth.androidphpmysql;


        import com.android.volley.AuthFailureError;
        import com.example.gareth.androidphpmysql.Constants;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.provider.Settings;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;


public class ViewTeeTimesByUser extends AppCompatActivity {


    ListView listView;
    String courseName;
    String userHandicap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tee_times_by_user);

        final String username = (SharedPrefManager.getInstance(this).getUsername());


        listView = (ListView) findViewById(R.id.listViewTeeTimes);
        //getJSON("http://192.168.1.46/Android/v1/getTeeTimesByUser.php?username="+username);
            getJSON(Constants.URL_TEE_TIME_BY_USER+username);
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
                    loadIntoListView(s);
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

    private void loadIntoListView(String json) throws JSONException {
        final JSONArray jsonArray = new JSONArray(json);
        String[] users = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            users[i] = obj.getString("t.gui") + "\n" +
                    obj.getString("t.course") + "\n" +
                    obj.getString("u.username") + "\n" +
                    obj.getString("g.handicap");

            courseName = obj.getString("t.course");
            userHandicap = obj.getString("g.handicap");




        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               //String o = listView.getItemAtPosition(i).toString().trim();
                String o = listView.getItemAtPosition(i).toString().trim();
               Object obj = listView.getAdapter().getItem(0);
               String str =obj.toString();

                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

               Bundle extras = new Bundle();

               extras.putString("name",courseName);
               extras.putString("userHandicap",userHandicap);
                final String course = (SharedPrefManager.getInstance(getApplicationContext()).getCourseById());
                extras.putString("course",course);
                Intent intent = new Intent(getApplicationContext(),PlayRound.class);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
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
            case R.id.playRound:
                startActivity(new Intent(this,PlayRound.class));
                break;
            case R.id.register:
                startActivity(new Intent(this,MainActivity.class));
                break;

        }
        return true;
    }
}


