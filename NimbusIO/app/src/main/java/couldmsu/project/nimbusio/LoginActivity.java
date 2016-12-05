package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by VishnuChaitanya on 11/2/2016.
 * Description: Activity that starts after the flash screen. Activity number 2.
 * Displays 2 edit text boxs, one for username and the other for password.
 * Displays 2 buttons, one for logging in with given username and password, which calls the background async task to check the database
 * Sign-up take to a form where the user can register
 */

public class LoginActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        };
//Method for Sign Up button on Login Activity
    //Takes the intent to the next activity
    public void buttonSignin(View view){
        Intent startSignin = new Intent(this, SignUp.class);
        startActivity(startSignin);
    }
//Method for Login button on Login Activity
    public void buttonLogin (View view) {
        if (view.getId() == R.id.loginbutton) {
            // getting username and password from the edit text fields
            // Stored the fetched values into a string
            EditText un = (EditText)findViewById(R.id.username);
            String uname = un.getText().toString().trim();
            EditText up = (EditText)findViewById(R.id.password);
            String upassword = up.getText().toString().trim();

            if (uname.isEmpty() || upassword.isEmpty())
            {
                Toast.makeText(LoginActivity.this, "Please enter username and password!", Toast.LENGTH_LONG).show();
            }
            else {
                //Sending the username and password as parameters to BgWorkerLogin
                BgWorkerLogin bglogin = new BgWorkerLogin(this);
                bglogin.execute("login", uname, upassword);

            }
        }
    }
}


