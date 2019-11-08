package com.example.android.audiodemo;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;


import static com.example.android.audiodemo.R.id.tvd;

public class MainActivity extends AppCompatActivity {
    //private MediaPlayer mediaPlayer;
    //for mediaPlayer purpose
    private int length;
    private int min;
    private int sec;
    //for setting date
    private Calendar calendar = Calendar.getInstance();
    private int cd;

     static String dateC="";
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),this, year, month, day);
        }

        public  void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //TextView textview =  (TextView) findViewById(R.id.cal);
            // textview.setText(day+month+year);
            dateC = day+"-"+month+"-"+year;

            }

    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.siathegreatest);
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(MainActivity.this, R.raw.gtasanandreas);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        Button playbutton = (Button) findViewById(R.id.playid);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer1.isPlaying() == true) {
                    Toast.makeText(MainActivity.this, "wait bro", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "play", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                    length = mediaPlayer.getDuration();

                    sec = length / 1000;
                    min = sec / 60;
                    sec = sec % 60;
                    vibe.vibrate(1000);


                    Toast.makeText(MainActivity.this, "duration: " + min + ":" + sec, Toast.LENGTH_SHORT).show();
                    //  Log.d("Position",Integer.toString(lenght));
                }
            }

        });

        Button pausebutton = (Button) findViewById(R.id.pauseid);
        pausebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MainActivity.this,"pause",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                vibe.vibrate(1000);

            }
        });


        Button forward = (Button) findViewById(R.id.forwardid);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = mediaPlayer.getCurrentPosition();
                cd = cd + 5000;
                mediaPlayer.seekTo((int) cd);
                sec = cd / 1000;
                min = sec / 60;
                sec = sec % 60;

                Toast.makeText(MainActivity.this, "duration: " + min + ":" + sec, Toast.LENGTH_SHORT).show();
                vibe.vibrate(1000);
            }
        });

        Button back = (Button) findViewById(R.id.backid);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                vibe.vibrate(1000);

            }
        });

        Button gtam = (Button) findViewById(R.id.gtaid);
        gtam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(1000);
                if (mediaPlayer.isPlaying()) {
                    Toast.makeText(MainActivity.this, "SIA¬GREATEST is much better \uD83D\uDE0A", Toast.LENGTH_LONG).show();
                } else {
                    mediaPlayer1.start();
                }
            }
        });

        Button batteryButton = (Button) findViewById(R.id.tvbid);
        batteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = MainActivity.this.registerReceiver(null, ifilter);
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float batteryPct = level / (float) scale;
                batteryPct *= 100;
                TextView view1 = (TextView) findViewById(R.id.tvb);
                view1.setText(String.valueOf(batteryPct) + "%");
                vibe.vibrate(1000);
            }
        });


        Button dateButton = (Button) findViewById(R.id.tvdid);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    https://www.tutorialspoint.com/android/android_datepicker_control.htm

                //   dateView = (TextView) findViewById(R.id.tvd);

                //private TextView dateView;
                final int year, month, day;

                year = calendar.get(Calendar.YEAR);

                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                // showDate(year, month+1, day);
                //showDate nethod is outside onCreate method
                TextView view2 = (TextView) findViewById(tvd);
                view2.setText(day + "-" + month + "-" + year);
                vibe.vibrate(1000);
        }
        });


        Button timeButton = (Button) findViewById(R.id.tvtid);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TextView view3 = (TextView) findViewById(R.id.tvt);
                view3.setText(hour + ":" + minute);
                vibe.vibrate(1000);

            }
        });

        Button deviceButton = (Button) findViewById(R.id.tvddid);
        deviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myDeviceModel = "Model: " + android.os.Build.MODEL;
                System.getProperty("os.version"); // OS version
                myDeviceModel += "\nAPI Level: " + android.os.Build.VERSION.SDK;     // API Level
                myDeviceModel += " \nDevice: " + android.os.Build.DEVICE;           // Device
                myDeviceModel += " \nModel: " + android.os.Build.MODEL;            // Model
                myDeviceModel += " \nProduct: " + android.os.Build.PRODUCT;          // Product
                TextView view4 = (TextView) findViewById(R.id.tvdd);
                view4.setText(myDeviceModel);
                vibe.vibrate(1000);
            }
        });

        Button datepicker = (Button) findViewById(R.id.calid);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

                TextView view2 = (TextView) findViewById(R.id.tvd);
                view2.setText(dateC);


            }
              /*  Intent intent = new Intent(MainActivity.this, DatePicker.class);
                startActivity(intent);
                Toast.makeText(view.getContext(),"test begin",Toast.LENGTH_SHORT).show();*/

        });
    }

  /* public void displaydate(String datev){
       // TextView view2 = (TextView) findViewById(R.id.tvd);
        //view2.setText(""+datev);

    }*/

    }
