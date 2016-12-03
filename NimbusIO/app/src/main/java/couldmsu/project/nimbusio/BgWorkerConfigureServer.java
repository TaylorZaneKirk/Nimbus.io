package couldmsu.project.nimbusio;

import android.app.AlertDialog;
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
 * Created by VishnuChaitanya on 12/2/2016.
 */

public class BgWorkerConfigureServer extends AsyncTask <String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String uid;

    BgWorkerConfigureServer(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://104.236.47.53/addservers.php";

        if (type.equals("addCustom")) {
            try {
                String usn = params[1]; //user given server name
                String usd = params[2]; // user given server description
                uid = params[3];
                String umemory = params[4];
                String ustorage = params[5];
                String uprocessor = params[6];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8") + "&"
                        + URLEncoder.encode("servicename", "UTF-8") + "=" + URLEncoder.encode(usn, "UTF-8") + "&"
                        + URLEncoder.encode("servicedesc", "UTF-8") + "=" + URLEncoder.encode(usd, "UTF-8") + "&"
                        + URLEncoder.encode("Memory", "UTF-8") + "=" + URLEncoder.encode(umemory, "UTF-8") + "&"
                        + URLEncoder.encode("Processor", "UTF-8") + "=" + URLEncoder.encode(uprocessor, "UTF-8") + "&"
                        + URLEncoder.encode("Storage", "UTF-8") + "=" + URLEncoder.encode(ustorage, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
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
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "Service Added to the account!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
