package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by VishnuChaitanya on 11/3/2016.
 * Description:Activity that show the form to signup with this application.
 * Class called upon clicking the Sign Up button from LoginActivity
 * Task: Fetchs all the information inserted by the user and passes it to its respective background async task to write to the DB.
 *
 */

public class SignUp extends AppCompatActivity {

    //Global values for EditText fields in the activity.
    //Declared global as these values should be accessible to the entire program
    EditText fname, lname, uname, enterpass, confirmpass, eaddress, phone;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        //Linking the EditText fields to the Text boxes in the XML of this activity
        fname = (EditText)findViewById(R.id.firstname);
        lname = (EditText)findViewById(R.id.lastname);
        uname = (EditText)findViewById(R.id.username_signup);
        enterpass = (EditText)findViewById(R.id.sign_up_pass);
        confirmpass = (EditText)findViewById(R.id.confirmPassword);
        eaddress = (EditText)findViewById(R.id.emailaddress);
        phone = (EditText)findViewById(R.id.phonenumber);

    };
//Method that is called when the SignUp button is pressed
    // Fetches the information from the text fields and stores to the string which are passed to background task
    public void buttonSignup (View view){

        String f_name = fname.getText().toString();
        String l_name = lname.getText().toString();
        String u_name = uname.getText().toString();
        String e_pass = enterpass.getText().toString();
        String c_pass = confirmpass.getText().toString();
        String e_address = eaddress.getText().toString();
        String u_phone = phone.getText().toString();
        // Checking if the passwords match
        if (e_pass.equals(c_pass)) {
            Toast.makeText(SignUp.this, "Correct data entered", Toast.LENGTH_LONG).show();
            BgWorkerSignUp bgsignup = new BgWorkerSignUp(this);
            bgsignup.execute("register",f_name, l_name, u_name, e_pass, e_address, u_phone);

        }
        else
        {
            Toast.makeText(SignUp.this, "Passwords does not match. Please re-enter.", Toast.LENGTH_LONG).show();
        }

    }
}
