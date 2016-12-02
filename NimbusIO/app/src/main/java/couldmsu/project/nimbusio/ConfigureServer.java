package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by VishnuChaitanya on 11/3/2016.
 */

public class ConfigureServer extends AppCompatActivity {
    Spinner memory, storage, processor;
    EditText servername, serverdesc;
    String uid;
    String smemory, sstorage, sprocessor, sservername, sserverdesc; //Selected memory, storage and other fields in the activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configureservers);

        Intent intent = getIntent();
        uid = intent.getExtras().getString("uid");

        memory = (Spinner)findViewById(R.id.spMemory);
        storage = (Spinner) findViewById(R.id.spStorage);
        processor = (Spinner) findViewById(R.id.spProcessor);

        servername = (EditText) findViewById(R.id.etServerName);
        serverdesc = (EditText) findViewById(R.id.etServerDesc);
    };

    public void addCustomServer(View view){
        smemory = memory.getSelectedItem().toString();
        sstorage = storage.getSelectedItem().toString();
        sprocessor = processor.getSelectedItem().toString();
        sservername = servername.getText().toString();
        sserverdesc = serverdesc.getText().toString();

        Toast.makeText(ConfigureServer.this, uid+" --- "+smemory+" "+sstorage+" "+sprocessor,Toast.LENGTH_LONG).show();
    }

}
