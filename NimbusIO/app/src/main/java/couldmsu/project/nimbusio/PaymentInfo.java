package couldmsu.project.nimbusio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VishnuChaitanya on 11/15/2016.
 */

public class PaymentInfo extends AppCompatActivity {

    String uid = "";
    TextView id;
    EditText uccn, uccun, ucced, ucci, ucccvv, uccaddress1, uccaddress2, ucczip;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentinfo);

        Intent intent = getIntent();
        uid = intent.getExtras().getString("uidaccinfo");
        id = (TextView)findViewById(R.id.textView2);
        id.setText(uid);

        uccn= (EditText) findViewById(R.id.etcnumber);
        uccun= (EditText) findViewById(R.id.etcname);
        ucced= (EditText) findViewById(R.id.etcexp);
        ucci= (EditText) findViewById(R.id.etcissuer);
        ucccvv= (EditText) findViewById(R.id.etcsecurity);
        uccaddress1= (EditText) findViewById(R.id.etaddress1);
        uccaddress2= (EditText) findViewById(R.id.etaddress2);
        ucczip= (EditText) findViewById(R.id.etzipcode);

        getPaymentData();

    };

    private void getPaymentData() {


        String url = ConfigPayment.DATA_URL+uid;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentInfo.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        String ccn= "";
        String ccun= "";
        String cced= "";
        String cci = "";
        String cccvv = "";
        String ccadd1 = "";
        String ccadd2 = "";
        String cczip = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject accData = result.getJSONObject(0);

            ccn = accData.getString(ConfigPayment.KEY_CCN);
            ccun = accData.getString(ConfigPayment.KEY_CCUN);
            cced = accData.getString(ConfigPayment.KEY_CCED);
            cci = accData.getString(ConfigPayment.KEY_CCI);
            cccvv = accData.getString(ConfigPayment.KEY_CCCVV);
            ccadd1 = accData.getString(ConfigPayment.KEY_CCAddress1);
            ccadd2 = accData.getString(ConfigPayment.KEY_CCAddress2);
            cczip = accData.getString(ConfigPayment.KEY_CCZIP);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ccn != "null") {
            uccn.setText("" + ccn);
            uccun.setText("" + ccun);
            ucced.setText("" + cced);
            ucci.setText("" + cci);
            ucccvv.setText("" + cccvv);
            uccaddress1.setText("" + ccadd1);
            uccaddress2.setText("" + ccadd2);
            ucczip.setText("" + cczip);
        }
        else
        {
            Toast.makeText(PaymentInfo.this,"Enter your card details",Toast.LENGTH_LONG).show();
        }

    }

    public void updateCard(View view){
        uccn= (EditText) findViewById(R.id.etcnumber);
        uccun= (EditText) findViewById(R.id.etcname);
        ucced= (EditText) findViewById(R.id.etcexp);
        ucci= (EditText) findViewById(R.id.etcissuer);
        ucccvv= (EditText) findViewById(R.id.etcsecurity);
        uccaddress1= (EditText) findViewById(R.id.etaddress1);
        uccaddress2= (EditText) findViewById(R.id.etaddress2);
        ucczip= (EditText) findViewById(R.id.etzipcode);

        String ccn= uccn.getText().toString();
        String ccun= uccun.getText().toString();
        String cced= ucced.getText().toString();
        String cci = ucci.getText().toString();
        String cccvv = ucccvv.getText().toString();
        String ccadd1 = uccaddress1.getText().toString();
        String ccadd2 = uccaddress2.getText().toString();
        String cczip = ucczip.getText().toString();

        if (ccn.matches("[0-9]+") && ccn.length() >= 15 && ccn.length() <= 16) {
            if (ccun.matches("^.*[0-9].*$") == true) {
                Toast.makeText(PaymentInfo.this, "Error: Name cannot contain numbers.",Toast.LENGTH_LONG).show();
            }
            else
            {
                if (cccvv.length() == 3) {
                    if(cczip.length() == 5) {
                        BgWorkerPaymentUpdate bgupdatepayment = new BgWorkerPaymentUpdate(this);
                        bgupdatepayment.execute("updatepayment", ccn, ccun, cced, cci, cccvv, ccadd1, ccadd2, cczip, uid);
                    }
                    else {
                        Toast.makeText(PaymentInfo.this, "Error: Enter a valid Zipcode. Must be 5 numbers.",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(PaymentInfo.this, "Error: Security code cannot be more than 3 numbers.",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            Toast.makeText(PaymentInfo.this, "Enter a valid card number.",Toast.LENGTH_LONG).show();
        }

    }
}
