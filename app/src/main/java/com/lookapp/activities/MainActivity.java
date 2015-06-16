package com.lookapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lookapp.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login_btn).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                Log.d("running", "hello world");
                try {
                    url = new URL("http://192.168.77.143:8080/LookAppServer/LoginServlet");
                    HttpURLConnection post = (HttpURLConnection) url.openConnection();
                    post.setConnectTimeout(15 * 1000);
                    post.setDoInput(true);
                    post.setDoOutput(true);
                    post.setRequestMethod("POST");
                    post.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    OutputStream stream = post.getOutputStream();
                    String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
                    String data = "{userName:" + username + ",\n" + "password: 123}";
                    stream.write(data.getBytes());
                    stream.close();
                    int status = post.getResponseCode();
                    Log.d("status", status + "");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();



    }
}
