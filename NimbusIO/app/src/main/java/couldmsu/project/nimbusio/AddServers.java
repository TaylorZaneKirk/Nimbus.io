package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by VishnuChaitanya on 11/3/2016.
 */

public class AddServers extends AppCompatActivity {
    String uid;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addservers);

        Intent intent = getIntent();
        uid = intent.getExtras().getString("uid");
    };

    public void presetServers(View view){
        Intent startHome = new Intent(this, PresetServersList.class);
        startActivity(startHome);
    }

    public void configureServers(View view){
        Intent startHome = new Intent(this, ConfigureServer.class);
        startHome.putExtra("uid", uid);
        startActivity(startHome);
    }
}
