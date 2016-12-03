package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

public class PresetServersList extends AppCompatActivity {
    String uid;
    ListView presetlv;
    ArrayList<HashMap<String, String>> presetList;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presetservers);
        Intent intent = getIntent();
        uid = intent.getExtras().getString("uid");
        presetList = new ArrayList<>();
        presetlv = (ListView) findViewById(R.id.presetlist);

        getServerList();
    };

    private void getServerList(){

        String url = ConfigPreset.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PresetServersList.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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
            JSONArray result = jsonObject.getJSONArray(ConfigPreset.JSON_ARRAY);
            for(int i = 0; i<result.length(); i++) {
                JSONObject accData = result.getJSONObject(i);

                ipaddress = accData.getString(ConfigPreset.KEY_IPA);
                servername = accData.getString(ConfigPreset.KEY_SNAME);
                serverdesc = accData.getString(ConfigPreset.KEY_SDESC);
                serverid = accData.getString(ConfigPreset.KEY_SID);
                serverprice = accData.getString(ConfigPreset.KEY_PRICE);
                servermemory = accData.getString(ConfigPreset.KEY_MEMORY);
                serverstorage = accData.getString(ConfigPreset.KEY_STORAGE);
                serverprocessor = accData.getString(ConfigPreset.KEY_PROCESSOR);

                HashMap<String, String> server = new HashMap<>();

                server.put("name","Server Name: " +servername);
                //server.put("ipaddress", "Server IP: " +ipaddress);
                server.put("description", "Software Stack: " +serverdesc);
                server.put("price", "Price: " +serverprice);

                presetList.add(server);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
        PresetServersList.this, presetList,
        R.layout.presetlist, new String[]{"name", "description", "price"}, new int[]{R.id.sn, R.id.sd, R.id.sp});

        presetlv.setAdapter(adapter);

        presetlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedPosition = position+1;
                String ts = Integer.toString(selectedPosition);
                BgWorkerAddPreset bgWorkerAddPreset = new BgWorkerAddPreset(PresetServersList.this);
                bgWorkerAddPreset.execute("addPreset", ts, uid);
                //Toast.makeText(PresetServersList.this, ts, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
