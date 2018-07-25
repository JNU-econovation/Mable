package org.techtown.iot3;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SMSActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    EditText textPhoneNo;
    Timer timer;
    TimerTask timerTask;
    String sms = "복약 하지 않음";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);

        TextView i;


      ImageButton btn = (ImageButton)findViewById(R.id.imageButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView i = (TextView)findViewById(R.id.textView5);
                i.setVisibility(TextView.VISIBLE);


            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }

        final Switch sw = (Switch) findViewById(R.id.switch1);

        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (sw.isChecked()) {
                    Toast.makeText(SMSActivity.this, "보호자 연락기능 ON" + isChecked, Toast.LENGTH_SHORT).show();
                    startTimer();
                } else {
                    Toast.makeText(SMSActivity.this, "보호자 연락기능 OFF" + isChecked, Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    public void startTimer() {
        Log.d("calc_thread", "in");
        final String phoneNo = textPhoneNo.getText().toString();

        SetTime s = new SetTime();

        final Calendar calendar = s.getCal1().getInstance();
        calendar.add(Calendar.MINUTE, 1);

        timer = new Timer();

        Handler handler = new Handler();
        handler.post(
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar now = Calendar.getInstance(); //현재 시간

                                if (calendar.get(Calendar.MINUTE) == now.get(Calendar.MINUTE)) {

                                    Log.d("cal", "들어왔다");
                                    try {
                                        Log.d("cal", "들어왔다2");

                                        SmsManager smsManager = SmsManager.getDefault();
                                        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT"), 0);
                                        PendingIntent recvPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED"), 0);

                                        registerReceiver(mSentReceiver, new IntentFilter("SMS_SENT"));
                                        registerReceiver(mRecvReceiver, new IntentFilter("SMS_DELIVERED"));

                                        smsManager.sendTextMessage(phoneNo, null, sms, sentPI, recvPI);
                                        Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
                    }
                });
        timer.scheduleAtFixedRate(timerTask, 0, 60000);
    }


    BroadcastReceiver mSentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case RESULT_OK:
                    Toast.makeText(SMSActivity.this, "SMS Send", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(SMSActivity.this, "ERROR_GENERIC_FAILURE", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(SMSActivity.this, "ERROR_NO_SERVICE", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(SMSActivity.this, "ERROR_NULL_PDU", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(SMSActivity.this, "ERROR_RADIO_OFF", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    BroadcastReceiver mRecvReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case RESULT_OK:
                    Toast.makeText(SMSActivity.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(SMSActivity.this, "SMS Delivered Fail", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}
