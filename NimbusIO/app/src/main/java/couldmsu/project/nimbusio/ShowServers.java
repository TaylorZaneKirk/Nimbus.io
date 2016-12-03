package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by VishnuChaitanya on 11/3/2016.
 */

public class ShowServers extends AppCompatActivity {
    String uid;
    ListView serverlv;
    ArrayList<HashMap<String, String>> serverList;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showservers);

        Intent intent = getIntent();
        uid = intent.getExtras().getString("uidaccinfo");

        serverList = new ArrayList<>();
        serverlv = (ListView) findViewById(R.id.list);

        getServerList();

    };

    private void getServerList(){

        String url = ConfigServer.DATA_URL+uid;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowServers.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response){

        String ipaddress= "";
        String servername= "";
        String serverdesc= "";
        String serverid = "";
        String serverprice = "";
        String servermemory = "";
        String serverstorage = "";
        String serverprocessor = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            //for(int i = 0; i<jsonObject.length(); i++) {
            JSONArray result = jsonObject.getJSONArray(ConfigPayment.JSON_ARRAY);
            for(int i = 0; i<result.length(); i++) {
                JSONObject accData = result.getJSONObject(i);

                ipaddress = accData.getString(ConfigServer.KEY_IPA);
                servername = accData.getString(ConfigServer.KEY_SNAME);
                serverdesc = accData.getString(ConfigServer.KEY_SDESC);
                serverid = accData.getString(ConfigServer.KEY_SID);
                serverprice = accData.getString(ConfigServer.KEY_PRICE);
                servermemory = accData.getString(ConfigServer.KEY_MEMORY);
                serverstorage = accData.getString(ConfigServer.KEY_STORAGE);
                serverprocessor = accData.getString(ConfigServer.KEY_PROCESSOR);

                HashMap<String, String> server = new HashMap<>();

                server.put("name","Server Name: " +servername);
                server.put("ipaddress", "Server IP: " +ipaddress);
                server.put("description", "Software Stack: " +serverdesc);
                server.put("price", "Price: " +serverprice);

                serverList.add(server);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (serverid != "null") {
            ListAdapter adapter = new SimpleAdapter(
                    ShowServers.this, serverList,
                    R.layout.serverlist, new String[]{"name", "ipaddress",
                    "description", "price"}, new int[]{R.id.servicename,
                    R.id.serviceip, R.id.servicedesc, R.id.serviceprice});

            serverlv.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(ShowServers.this, "You do not have any servers added.",Toast.LENGTH_LONG).show();
            Toast.makeText(ShowServers.this, "Go back to add servers.",Toast.LENGTH_LONG).show();
        }

    }

}
