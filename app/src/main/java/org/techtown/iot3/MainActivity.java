package org.techtown.iot3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final String ALARM_ALERT_ACTION = "com.android.alarmclock.ALARM_ALERT";
    Button bt1,bt2,bt3;

    TextView  tvHour1, tvMin1,si1,bun1,tvHour2,tvMin2,si2,bun2,tvHour3, tvMin3,si3,bun3;
    SetTime s = new SetTime();

    ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final String[] items = {"메인", "캘린더", "보호자 알림"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.drawer_menulist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0 : break;
                    case 1 : Intent intent = new Intent(MainActivity.this, Calendar_Activity.class);
                    startActivity(intent);break;
                    case 2:  Intent intent2 = new Intent(MainActivity.this, SMSActivity.class);
                        startActivity(intent2);break;
                }
            }
        });

        bt1= findViewById(R.id.bt1);

        tvHour1  =  findViewById(R.id.tvHour1);
        tvMin1   =  findViewById(R.id.tvMin1);
        si1 =  findViewById(R.id.si1);
        bun1 =  findViewById(R.id.bun1);

        bt2= findViewById(R.id.bt2);

        tvHour2  =  findViewById(R.id.tvHour2);
        tvMin2   =  findViewById(R.id.tvMin2);
        si2 =  findViewById(R.id.si2);
        bun2 =  findViewById(R.id.bun2);

        bt3= findViewById(R.id.bt3);

        tvHour3  =  findViewById(R.id.tvHour3);
        tvMin3   =  findViewById(R.id.tvMin3);
        si3 =  findViewById(R.id.si3);
        bun3 =  findViewById(R.id.bun3);

        bt1.setOnClickListener(new View.OnClickListener()  {

            TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

                @Override

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    Calendar calNow = Calendar.getInstance();   // 현재 시간을 위한 Calendar 객체를 구한다.
                    Calendar calSet = (Calendar)calNow.clone();   // 바로 위에서 구한 객체를 복제 한다.

                    calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);   // 시간 설정
                    calSet.set(Calendar.MINUTE, minute);        // 분 설정
                    calSet.set(Calendar.SECOND, 0);               // 초는 '0'으로 설정
                    calSet.set(Calendar.MILLISECOND, 0);       // 밀리 초도  '0' 으로 설정

                    String hour_of_day1 =Integer.toString(calSet.get(Calendar.HOUR_OF_DAY));
                    String minute1 = Integer.toString(calSet.get(Calendar.MINUTE));
                    tvHour1.setText(hour_of_day1);
                    tvMin1.setText(minute1);

                    s.setCal1(calSet);

                    if(calSet.compareTo(calNow) <= 0){            // 설정한 시간과 현재 시간 비교
                        // 만약 설정한 시간이 현재 시간보다 이전이면
                        calSet.add(Calendar.DATE, 1);  // 설정 시간에 하루를 더한다.
                    }
                    setAlarm(calSet);  // 주어진 시간으로 알람을 설정한다.
                }
            };

            public void onClick(View v) {
                Calendar c = Calendar.getInstance();    // Calendar 객체를 구한다.

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this, timeListener,                                           // 리스너 추가
                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),false);  //현재 시간과 분을 TimePickerDialog에 설정

                timePickerDialog.setTitle("알람 시간 설정");  // TimePickerDialog 제목을 정한다.
                timePickerDialog.show();   // TimePickerDialog를 화면에 보인다.
            }
        });






        bt2.setOnClickListener(new View.OnClickListener() {

            TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

                @Override

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    Calendar calNow = Calendar.getInstance();   // 현재 시간을 위한 Calendar 객체를 구한다.
                    Calendar calSet = (Calendar)calNow.clone();   // 바로 위에서 구한 객체를 복제 한다.

                    calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);   // 시간 설정
                    calSet.set(Calendar.MINUTE, minute);        // 분 설정
                    calSet.set(Calendar.SECOND, 0);               // 초는 '0'으로 설정
                    calSet.set(Calendar.MILLISECOND, 0);       // 밀리 초도  '0' 으로 설정

                    String hour_of_day2 =Integer.toString(calSet.get(Calendar.HOUR_OF_DAY));
                    String minute2 = Integer.toString(calSet.get(Calendar.MINUTE));
                    tvHour2.setText(hour_of_day2);
                    tvMin2.setText(minute2);

                    s.setCal2(calSet);

                    if(calSet.compareTo(calNow) <= 0){            // 설정한 시간과 현재 시간 비교
                        // 만약 설정한 시간이 현재 시간보다 이전이면
                        calSet.add(Calendar.DATE, 1);  // 설정 시간에 하루를 더한다.
                    }
                    setAlarm(calSet);  // 주어진 시간으로 알람을 설정한다.
                }
            };

            public void onClick(View v) {
                Calendar c = Calendar.getInstance();    // Calendar 객체를 구한다.

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this, timeListener,                                           // 리스너 추가
                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),false);  //현재 시간과 분을 TimePickerDialog에 설정

                timePickerDialog.setTitle("알람 시간 설정");  // TimePickerDialog 제목을 정한다.
                timePickerDialog.show();   // TimePickerDialog를 화면에 보인다.
            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {

            TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

                @Override

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    Calendar calNow = Calendar.getInstance();   // 현재 시간을 위한 Calendar 객체를 구한다.
                    Calendar calSet = (Calendar)calNow.clone();   // 바로 위에서 구한 객체를 복제 한다.

                    calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);   // 시간 설정
                    calSet.set(Calendar.MINUTE, minute);        // 분 설정
                    calSet.set(Calendar.SECOND, 0);               // 초는 '0'으로 설정
                    calSet.set(Calendar.MILLISECOND, 0);       // 밀리 초도  '0' 으로 설정

                    String hour_of_day3 =Integer.toString(calSet.get(Calendar.HOUR_OF_DAY));
                    String minute3 = Integer.toString(calSet.get(Calendar.MINUTE));
                    tvHour3.setText(hour_of_day3);
                    tvMin3.setText(minute3);

                    s.setCal3(calSet);

                    if(calSet.compareTo(calNow) <= 0){            // 설정한 시간과 현재 시간 비교
                        // 만약 설정한 시간이 현재 시간보다 이전이면
                        calSet.add(Calendar.DATE, 1);  // 설정 시간에 하루를 더한다.
                    }
                    setAlarm(calSet);  // 주어진 시간으로 알람을 설정한다.
                }
            };

            public void onClick(View v) {
                Calendar c = Calendar.getInstance();    // Calendar 객체를 구한다.

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this, timeListener,                                           // 리스너 추가
                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),false);  //현재 시간과 분을 TimePickerDialog에 설정

                timePickerDialog.setTitle("알람 시간 설정");  // TimePickerDialog 제목을 정한다.
                timePickerDialog.show();   // TimePickerDialog를 화면에 보인다.
            }
        });

    }





    private void setAlarm(Calendar targetCal) {

        Context context = getApplicationContext();

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // PendingIntent

        Intent intent = new Intent(ALARM_ALERT_ACTION);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // AlarmManager에 알람 시간 설정

        am.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), sender);

        // 상태바에 알람 아이콘 표시

        setStatusBarIcon(context, true);

        Toast.makeText(context, targetCal.getTime()+"에 알람이 설정되었습니다.", Toast.LENGTH_SHORT).show();

    }

    public static void setStatusBarIcon(Context context, boolean enabled) {

        Intent alarmChanged = new Intent("android.intent.action.ALARM_CHANGED");     // intent 생성

        alarmChanged.putExtra("alarmSet", enabled); // intent에 데이터 추가 (true => 아이콘 표시,  false => 아이콘 삭제)

        context.sendBroadcast(alarmChanged); // 주어진 intent를 Boardcast 한다.

    }



}