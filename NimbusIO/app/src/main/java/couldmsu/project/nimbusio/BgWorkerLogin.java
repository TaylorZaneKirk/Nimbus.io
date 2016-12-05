package couldmsu.project.nimbusio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
 * Created by VishnuChaitanya on 11/7/2016.
 * Packages: java.net; java.IO
 * Description: Class works as background activity to Login Button pushed on LoginActivity class.
 * Gets the username and password from the app and connects to the php url. Send the username and password to php and
 * gets the response that is echoed on php.
 * Uses the result to decide on what needs to be done next.
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
        //Getting params from LoginActivity
        String type = params[0];
        String login_url = "http://104.236.47.53/login.php"; //Url to the php code on 104.236.47.53, which could handle uid and password
        if(type.equals("login")) {
            try {
                String user_name = params[1];
                un = user_name; //Made global so as the be available to the other methods in this class
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
        //Using the result from the url above to decide on what to do next.
        //The php above echos one of the below texts.
        if (result.equals("success")) {
            Intent i = new Intent(context, Home.class);
            i.putExtra("unfromlogin",un);
            context.startActivity(i);
        }
        else if (result.equals("badPassword")){
            alertDialog.setMessage("Incorrect Password, Please try again!");
            alertDialog.show();
        }
        else if(result.equals("noUser")){
            alertDialog.setMessage("Username not found, please check your username or consider Sign Up.");
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}