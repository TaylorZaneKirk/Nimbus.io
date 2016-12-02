package couldmsu.project.nimbusio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by VishnuChaitanya on 11/25/2016.
 */

public class BgWorkerPaymentUpdate extends AsyncTask<String,Void,String> {
    Context context;
    BgWorkerPaymentUpdate (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://104.236.47.53/paymentupdate.php";

        if(type.equals("updatepayment")){
            try {
                String ccn= params[1];
                String ccun= params[2];
                String cced= params[3];
                String cci = params[4];
                String cccvv = params[5];
                String ccadd1 = params[6];
                String ccadd2 = params[7];
                String cczip = params[8];
                String ccuid = params[9];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("cc_uid","UTF-8")+"="+URLEncoder.encode(ccuid,"UTF-8")+"&"
                        + URLEncoder.encode("cc_number","UTF-8")+"="+URLEncoder.encode(ccn,"UTF-8")+"&"
                        + URLEncoder.encode("cc_name","UTF-8")+"="+URLEncoder.encode(ccun,"UTF-8")+"&"
                        + URLEncoder.encode("cc_exp","UTF-8")+"="+URLEncoder.encode(cced,"UTF-8")+"&"
                        + URLEncoder.encode("cc_issuer","UTF-8")+"="+URLEncoder.encode(cci,"UTF-8")+"&"
                        + URLEncoder.encode("cc_security","UTF-8")+"="+URLEncoder.encode(cccvv,"UTF-8")+"&"
                        + URLEncoder.encode("cc_add1","UTF-8")+"="+URLEncoder.encode(ccadd1,"UTF-8")+"&"
                        + URLEncoder.encode("cc_add2","UTF-8")+"="+URLEncoder.encode(ccadd2,"UTF-8")+"&"
                        + URLEncoder.encode("cc_zip","UTF-8")+"="+URLEncoder.encode(cczip,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() { }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context,"Payment information updated successfully!",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
