package couldmsu.project.nimbusio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
/**
 * Created by VishnuChaitanya on 11/2/2016.
 * Code for the first screen that launches in the beginning of the application.
 * Flash screen: Uses Timer from UTIL package to call the next activity from the intent of the current activity.
 */

public class Welcome extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Welcome.this, LoginActivity.class);
                startActivity(intent);
                finishScreen();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 4000);

    }
    private void finishScreen() {
        this.finish();
    }
}
