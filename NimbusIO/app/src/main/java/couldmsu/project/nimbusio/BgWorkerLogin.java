package couldmsu.project.nimbusio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;

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

import static android.R.id.message;

/**
 * Created by VishnuChaitanya on 11/7/2016.
 */
public class BgWorkerLogin extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    public static String un = "";
    BgWorkerLogin (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://104.236.47.53/login.php";
        String register_url = "http://104.236.47.53/register.php";
        if(type.equals("login")) {
            try {
                String user_name = params[1];
                un = user_name;
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {

        if (result.equals("success")) {
            Intent i = new Intent(context, Home.class);
            i.putExtra("unfromlogin",un);
            context.startActivity(i);
        }
        else if (result.equals("badPassword")){
            alertDialog.setMessage("Incorrect Password, Please try again!");
            alertDialog.show();
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }
        else if(result.equals("noUser")){
            alertDialog.setMessage("Username not found, please check your username or consider Sign Up.");
            alertDialog.show();

            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}