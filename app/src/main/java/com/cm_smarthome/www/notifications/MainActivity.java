package com.cm_smarthome.www.notifications;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    get g1 = new get();
    private int TIME_CHECK = 5000;
    public Timer timer ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new myAsyncTask().execute();//เรียก ใช้ ที่เราได้ทำการสร้างไว้
    }

    public class myAsyncTask extends AsyncTask<String , Void ,String>
    {

        @Override
        protected String doInBackground(String... params) {

            try
            {
                timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        g1.getS();

                        if(g1.x.equals("1"))
                        {
                            createNotification();
                        }
                        else
                        {
                            //ไม่มีการ respone ก็ ไม่ต้องทำอะไร หรือ จะ ทำ ก่อ ได้ ครับผม
                        }
                    }
                }, 0, TIME_CHECK);//ทำซ้ำทุกๆๆๆ 5 วินาที TIME_CHECK มี หน่วย ms (ms = 1000 = 1s)
            }
            catch (Exception e)
            {
                Log.e("ERROR","Cannot GetData");
            }
            return null;
        }
    }

    public void createNotification() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new Notification(android.R.drawable.ic_dialog_alert,
                                    "New! www.cm-smarthome.com", System.currentTimeMillis());

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        String Title = "ข้อความใหม่!";
        String Message = "มีการเรียกใช้การบริการใหม่";

        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse("http://www.cm-smarthome.com"));

        PendingIntent activity = PendingIntent.getActivity(this, 0,myWebLink, 0);
        notification.setLatestEventInfo(this, Title, Message, activity);
        notification.number += 1;

        notification.defaults = Notification.DEFAULT_LIGHTS; // Sound
        notification.defaults = Notification.DEFAULT_VIBRATE; // Vibrate

        notificationManager.notify(1, notification);
    }

}
