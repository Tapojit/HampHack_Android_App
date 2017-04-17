package com.hampshire.tapojit.hamphack;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tapojit on 1/13/17.
 */

public class NotificationService extends Service {
    private static final String TAG = "StickyService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Feeds");
        final DatabaseReference databaseReferenceurl= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/FeedsURL");
        DatabaseReference icon= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Icon");
        DatabaseReference announ= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Announcements");


        final List<String> d2=new ArrayList<String>();
        databaseReferenceurl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dm2:dataSnapshot.getChildren()){
                    d2.add(String.valueOf(dm2.getValue()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        announ.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d=dataSnapshot.getValue(String.class);
                DBHelper helper=new DBHelper(getApplicationContext(),null,null,1);
                if(!d.equals("null")){
                    if(helper.containsDetailA(d)==false){
                        Notifications n1=new Notifications(d);
                        helper.addAnnoun(n1);

                        NotificationCompat.Builder notification=new NotificationCompat.Builder(getApplicationContext());
                        notification.setAutoCancel(true);
                        notification.setSmallIcon(R.drawable.hh_logo_bckgrnd);
                        notification.setTicker("This is the ticker");
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle("Important Announcement!");
                        notification.setContentText(d);
                        notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pendingIntent);

                        Random r= new Random();
                        int n=r.nextInt(1000)+1;
                        NotificationManager nm=(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(n,notification.build());

                    }
                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> d1=new ArrayList<String>();
                for(DataSnapshot dm:dataSnapshot.getChildren()){
                    d1.add(String.valueOf(dm.getValue()));
                }

                DBHelper helper=new DBHelper(getApplicationContext(),null,null,1);
                if (helper.containsDetail(d1.get(0))==false){
                    for(int i=0;i<d1.size();i++){
                        if (helper.containsDetail(d1.get(i))==false){
                            Notifications n1=new Notifications(d1.get(i));
                            helper.addDetail(n1);

                            NotificationCompat.Builder notification=new NotificationCompat.Builder(getApplicationContext());
                            notification.setAutoCancel(true);
                            notification.setSmallIcon(R.drawable.hh_logo_bckgrnd);
                            notification.setTicker("This is the ticker");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle("You have a new notification!");
                            notification.setContentText(d1.get(i));
                            notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                            if (d2.get(i).equals("null")){
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                notification.setContentIntent(pendingIntent);
                            }
                            else {

                                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(d2.get(i)));
                                PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                notification.setContentIntent(pendingIntent);
                            }
                            Random r= new Random();
                            int n=r.nextInt(1000)+1;
                            NotificationManager nm=(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(n,notification.build());



                        }
                        else break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("NoDeath"));
    }
}


//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        sendNotification(remoteMessage.getNotification().getBody());
//    }
//
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.hh_logo_bckgrnd)
//                .setContentTitle("HampHack")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }