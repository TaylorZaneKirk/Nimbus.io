package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by VishnuChaitanya on 11/3/2016.
 */

public class SignUp extends AppCompatActivity {

    EditText fname, lname, uname, enterpass, confirmpass, eaddress, phone;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        fname = (EditText)findViewById(R.id.firstname);
        lname = (EditText)findViewById(R.id.lastname);
        uname = (EditText)findViewById(R.id.username_signup);
        enterpass = (EditText)findViewById(R.id.sign_up_pass);
        confirmpass = (EditText)findViewById(R.id.confirmPassword);
        eaddress = (EditText)findViewById(R.id.emailaddress);
        phone = (EditText)findViewById(R.id.phonenumber);

    };

    public void buttonSignup (View view){

        String f_name = fname.getText().toString();
        String l_name = lname.getText().toString();
        String u_name = uname.getText().toString();
        String e_pass = enterpass.getText().toString();
        String c_pass = confirmpass.getText().toString();
        String e_address = eaddress.getText().toString();
        String u_phone = phone.getText().toString();
        if (e_pass.equals(c_pass)) {
            Toast.makeText(SignUp.this, "Correct data entered", Toast.LENGTH_LONG).show();
            BgWorkerSignUp bgsignup = new BgWorkerSignUp(this);
            bgsignup.execute("register",f_name, l_name, u_name, e_pass, e_address, u_phone);
            //Intent startHome = new Intent(this, Home.class);
            //startActivity(startHome);
        }
        else
        {
            Toast.makeText(SignUp.this, "Passwords does not match. Please re-enter.", Toast.LENGTH_LONG).show();
        }

    }
}
