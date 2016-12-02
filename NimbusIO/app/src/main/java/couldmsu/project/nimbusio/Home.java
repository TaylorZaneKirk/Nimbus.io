package couldmsu.project.nimbusio;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by VishnuChaitanya on 11/3/2016.
 */

public class Home extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    public static String username;
    String uid = "";

    public TextView fn,ln, em, ph;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        Intent intent = getIntent();
        username = intent.getExtras().getString("unfromlogin");

        fn = (TextView) findViewById(R.id.fninsert);
        ln = (TextView) findViewById(R.id.lninsert);
        em = (TextView) findViewById(R.id.einsert);
        ph = (TextView) findViewById(R.id.pinsert);

        //Toast.makeText(Home.this, username, Toast.LENGTH_SHORT).show();

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getData();

    };

    private void getData() {


        String url = Config.DATA_URL+username;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String fname="";
        String lname="";
        String email="";
        String phone = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject accData = result.getJSONObject(0);
            uid = accData.getString(Config.KEY_UID);
            fname = accData.getString(Config.KEY_FNAME);
            lname = accData.getString(Config.KEY_LNAME);
            email = accData.getString(Config.KEY_EMAIL);
            phone = accData.getString(Config.KEY_PHONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
        fn.setText(fname);
        ln.setText(lname);
        em.setText(email);
        ph.setText(phone);
    }

    private void addDrawerItems() {
        String[] osArray = { "Account", "Show Servers", "Add Servers", "Payment info", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedPosition = position;
                String ts = Integer.toString(selectedPosition);
                Toast.makeText(Home.this, ts, Toast.LENGTH_SHORT).show();
                if (selectedPosition == 0){
                    showHome(view);
                }
                else if (selectedPosition == 1){
                    showServers(view);
                }
                else if (selectedPosition == 2){
                    addServers(view);
                }
                else if (selectedPosition == 3){
                    showPayment(view);
                }
                else if(selectedPosition == 4){
                    finish();
                    System.exit(0);
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showServers(View view){
        Intent startShowServers = new Intent(this, ShowServers.class);
        startShowServers.putExtra("uidaccinfo",uid);
        startActivity(startShowServers);
    }

    public void addServers (View view){
        Intent startAddServers = new Intent(this, AddServers.class);
        startAddServers.putExtra("uid",uid);
        startActivity(startAddServers);
    }

    public void showHome(View view){
        Intent reloadHome = new Intent(this, Home.class);
        reloadHome.putExtra("unfromlogin",username);
        startActivity(reloadHome);
    }

    public void showPayment(View view){
        Intent showpay = new Intent(this, PaymentInfo.class);
        showpay.putExtra("uidaccinfo",uid);
        startActivity(showpay);
    }




}

