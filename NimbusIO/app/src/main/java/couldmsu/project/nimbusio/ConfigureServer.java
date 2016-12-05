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
 * Description: Activity that starts when Configure Server button is pressed in Add Servers activity
 * Displays dropdowns (Spinners) memory, storage, processor and Edit text for server name, server description
 * Fetches data from the form and stores to a string and passes these strings to BgWorkerConfigureServer.
 *
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
    // Method called when Add Server is pushed on Configure Server activity
    public void addCustomServer(View view){
        smemory = memory.getSelectedItem().toString();
        sstorage = storage.getSelectedItem().toString();
        sprocessor = processor.getSelectedItem().toString();
        sservername = servername.getText().toString();
        sserverdesc = serverdesc.getText().toString();

        BgWorkerConfigureServer bgWorkerConfigureServer = new BgWorkerConfigureServer(this);
        bgWorkerConfigureServer.execute("addCustom", sservername, sserverdesc, uid, smemory, sstorage, sprocessor);

        //Toast.makeText(ConfigureServer.this, "Service has been registered with your account.",Toast.LENGTH_LONG).show();
    }

}
