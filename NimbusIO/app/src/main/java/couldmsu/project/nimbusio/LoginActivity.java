package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by VishnuChaitanya on 11/2/2016.
 */

public class LoginActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        };

    public void buttonSignin(View view){
        Intent startSignin = new Intent(this, SignUp.class);
        startActivity(startSignin);
    }

    public void buttonLogin (View view) {
        if (view.getId() == R.id.loginbutton) {
            // getting username and password from the edit text fields
            EditText un = (EditText)findViewById(R.id.username);
            String uname = un.getText().toString().trim();
            EditText up = (EditText)findViewById(R.id.password);
            String upassword = up.getText().toString().trim();

            if (uname.isEmpty() || upassword.isEmpty())
            {
                Toast.makeText(LoginActivity.this, "Please enter username and password!", Toast.LENGTH_LONG).show();
            }
            else {
                BgWorkerLogin bglogin = new BgWorkerLogin(this);
                bglogin.execute("login", uname, upassword);

            }
        }
    }
}


