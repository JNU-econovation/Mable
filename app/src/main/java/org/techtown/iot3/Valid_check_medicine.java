package org.techtown.iot3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.techtown.iot3.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Valid_check_medicine extends AppCompatActivity{
    public final static String flaskUrl = "http://aef77ebe.ngrok.io/";
    String return_string;
    Network_work nt = new Network_work();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.valid_check_medicine);

        Button yesbtn = (Button)findViewById(R.id.yes);
        Button nobtn = (Button)findViewById(R.id.no);
        yesbtn.setOnClickListener(yesClick);
        nobtn.setOnClickListener(noClick);

    }

    private String netWork(final String token)
    {
        //네트워크 로직
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(flaskUrl + "set_medicine_open?token_="+token);
                    //post 방식으로 값 전달
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    Log.d("flask_connect", "Connected");

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    return_string = new String(nt.getStringFromInputStream(in));
                    Log.d("flask_return", return_string);

                } catch (MalformedURLException e) {
                    Log.d("flask_error", "Malformed URL");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return return_string;

    }
    Button.OnClickListener noClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //안먹었으면 check_medicine = 0 업데이트
            //값 보내기
            String string = netWork("false");
            if(string.equals("set false") || string =="set false")
            {
                //TODO 안먹었다 처리
            }else
            {
                Log.e("flask_error", "something wrong! check sql_setfalse");
            }

        }
    };

    Button.OnClickListener yesClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //먹었으면 check_medicine  = 1 업데이트
            String string = netWork("true");
            if(string.equals("set true") || string =="set true")
            {
                //TODO 먹었다 처리
            }else
            {
                Log.e("flask_error", "something wrong! check sql_settrue");
            }
        }
    };


}
