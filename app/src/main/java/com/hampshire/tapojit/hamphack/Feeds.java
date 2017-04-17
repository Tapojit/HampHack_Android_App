package com.hampshire.tapojit.hamphack;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by tapojit on 2/16/17.
 */

public class Feeds extends Fragment {
    public Feeds() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView=inflater.inflate(R.layout.feed_fragment,container,false);


        ListView listView=(ListView) rootView.findViewById(R.id.feeds_forecast);




        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Feeds");
        DatabaseReference databaseReferenceurl=FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/FeedsURL");
        DatabaseReference icon=FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Icon");

        final FirebaseListAdapter<String> firebaseListAdapterurl=new FirebaseListAdapter<String>(
                getActivity(),
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReferenceurl
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);

            }
        };


        final FirebaseListAdapter<String> com=new FirebaseListAdapter<String>(
                getActivity(),
                String.class,
                android.R.layout.simple_list_item_1,
                icon
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

        final FirebaseListAdapter<String> firebaseListAdapter=new FirebaseListAdapter<String>(
                getActivity(),
                String.class,
                R.layout.feeds_list_item_layout,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView) v.findViewById(R.id.feeds_list_item_layout);
                textView.setText(model);
                ImageView imageView=(ImageView) v.findViewById(R.id.mediaIcon);
                if (com.getItem(position).equals("hh")){
                    imageView.setImageResource(R.drawable.hh_logo_bckgrnd1);
                }
                else if (com.getItem(position).equals("fb")) imageView.setImageResource(R.drawable.fb);
                else if (com.getItem(position).equals("tw")) imageView.setImageResource(R.drawable.twitter);
                else if (com.getItem(position).equals("inst")) imageView.setImageResource(R.drawable.inst);



            }
        };





        listView.setAdapter(firebaseListAdapter);


//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                List<String> d1=dataSnapshot.getValue(List.class);
//                DBHelper helper=new DBHelper(getActivity(),null,null,1);
//                if (helper.containsDetail(firebaseListAdapter.getItem(0))==false){
//                for(int i=0;i<firebaseListAdapter.getCount();i++){
//                    if (helper.containsDetail(firebaseListAdapter.getItem(i))==false){
//                        Notifications n1=new Notifications(firebaseListAdapter.getItem(i));
//                        helper.addDetail(n1);
//
//                        NotificationCompat.Builder notification=new NotificationCompat.Builder(getActivity());
//                        notification.setAutoCancel(true);
//                        notification.setSmallIcon(R.drawable.hh_logo_bckgrnd);
//                        notification.setTicker("This is the ticker");
//                        notification.setWhen(System.currentTimeMillis());
//                        notification.setContentTitle("You have a new notification!");
//                        notification.setContentText(firebaseListAdapter.getItem(i));
//                        notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//                        notification.setVibrate(new long[] {1000, 1000, 1000, 1000});
//
//                        if (firebaseListAdapterurl.getItem(i).equals("null")){
//                            Intent intent=new Intent(getActivity(),MainActivity.class);
//                            PendingIntent pendingIntent=PendingIntent.getActivity(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                            notification.setContentIntent(pendingIntent);
//                        }
//                        else {
//                            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(firebaseListAdapterurl.getItem(i)));
//                            PendingIntent pendingIntent=PendingIntent.getActivity(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                            notification.setContentIntent(pendingIntent);
//                        }
//                        Random r= new Random();
//                        int n=r.nextInt(1000)+1;
//                        NotificationManager nm=(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
//                        nm.notify(n,notification.build());
//
//
//
//                    }
//                    else break;
//                }}
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=(String) firebaseListAdapterurl.getItem(position);
                if (!url.equals("null")){
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }}
        });

        final TextView t1=(TextView) rootView.findViewById(R.id.announce1s);

        final TextView t2=(TextView) rootView.findViewById(R.id.announce1e);
        DatabaseReference ann=FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Announcements");

        ann.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String j1=dataSnapshot.getValue(String.class);
                if (j1.equals("null")){
                    j1="No announcements available now. Tune in for more later.";
                    t1.setText(j1);
                    t1.setTextColor(getResources().getColor(R.color.grey_dark));

                    t2.setText(j1);
                    t2.setTextColor(getResources().getColor(R.color.grey_dark));

                }
                else{
                    t1.setText(j1);
                    t1.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

                    t2.setText(j1);
                    t2.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }



                final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(9000L);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        final float progress = (float) animation.getAnimatedValue();
                        final float width = t1.getWidth();
                        final float translationX = width * progress;
                        t1.setTranslationX(0-translationX);
                        t2.setTranslationX(0-(translationX - width));
                    }
                });
                animator.start();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final ImageView emoji=(ImageView) rootView.findViewById(R.id.emoji);

        DatabaseReference pic=FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/Emoji");

        pic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val=dataSnapshot.getValue(String.class);

                if(val.equals("cool")){
                    emoji.setImageResource(R.drawable.cool);

                }

                if(val.equals("drink")){
                    emoji.setImageResource(R.drawable.drink);
                }

                if(val.equals("eyes")){
                    emoji.setImageResource(R.drawable.eyes);
                }

                if(val.equals("headph")){
                    emoji.setImageResource(R.drawable.headph);
                }

                if(val.equals("nerd")){
                    emoji.setImageResource(R.drawable.nerd);
                }

                if(val.equals("noodles")){
                    emoji.setImageResource(R.drawable.noodles);
                }

                if(val.equals("owl")){
                    emoji.setImageResource(R.drawable.owl);
                }

                if(val.equals("pizza")){
                    emoji.setImageResource(R.drawable.pizza);
                }

                if(val.equals("prize")){
                    emoji.setImageResource(R.drawable.prize);
                }

                if(val.equals("rocket")){
                    emoji.setImageResource(R.drawable.rocket);
                }

                if(val.equals("sleep")){
                    emoji.setImageResource(R.drawable.sleep);
                }

                if(val.equals("taco")){
                    emoji.setImageResource(R.drawable.taco);
                }

                if(val.equals("time")){
                    emoji.setImageResource(R.drawable.time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final TextView day1=(TextView) rootView.findViewById(R.id.day);
        final TextView hr1=(TextView) rootView.findViewById(R.id.hr);
        final TextView min1=(TextView) rootView.findViewById(R.id.min);
        final TextView sec1=(TextView) rootView.findViewById(R.id.sec);

        Time Hackend = new Time(Time.getCurrentTimezone());
        int houre = 18;
        int minutee = 30;
        int seconde = 0;
        int monthDaye = 15;
        // month is zero based...7 == August
        int monthe = 3;
        int yeare=2017;


        Hackend.set(seconde, minutee, houre, monthDaye, monthe,yeare);
        Hackend.normalize(true);
        long confMillis2 = Hackend.toMillis(true);

        Time nowTime2 = new Time(Time.getCurrentTimezone());
        nowTime2.setToNow();
        nowTime2.normalize(true);
        long nowMillis2 = nowTime2.toMillis(true);

        long milliDiff2 = confMillis2 - nowMillis2;

        final CountDownTimer countDownTimer2=new CountDownTimer(milliDiff2,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int mDisplayDays=(int) ((millisUntilFinished / 1000) / 86400);
                int mDisplayHours=(int) (((millisUntilFinished / 1000) - (mDisplayDays * 86400)) / 3600);
                int mDisplayMinutes=(int) (((millisUntilFinished / 1000) - ((mDisplayDays * 86400) + (mDisplayHours * 3600))) / 60);
                int mDisplaySeconds=(int) ((millisUntilFinished / 1000) % 60);

                day1.setText(String.valueOf(mDisplayDays));
                hr1.setText(String.valueOf(mDisplayHours));
                min1.setText(String.valueOf(mDisplayMinutes));
                sec1.setText(String.valueOf(mDisplaySeconds));
            }

            @Override
            public void onFinish() {
                day1.setText("OVER!");
                hr1.setText("OVER!");
                min1.setText("OVER!");
                sec1.setText("OVER!");


            }
        };



        Time Hackday = new Time(Time.getCurrentTimezone());
        int hour = 18;
        int minute = 30;
        int second = 0;
        int monthDay = 14;
        // month is zero based...7 == August
        int month = 3;
        int year=2017;



        Hackday.set(second, minute, hour, monthDay, month,year);
        Hackday.normalize(true);
        long confMillis = Hackday.toMillis(true);

        Time nowTime = new Time(Time.getCurrentTimezone());
        nowTime.setToNow();
        nowTime.normalize(true);
        long nowMillis = nowTime.toMillis(true);

        long milliDiff = confMillis - nowMillis;

        CountDownTimer countDownTimer=new CountDownTimer(milliDiff,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int mDisplayDays=(int) ((millisUntilFinished / 1000) / 86400);
                int mDisplayHours=(int) (((millisUntilFinished / 1000) - (mDisplayDays * 86400)) / 3600);
                int mDisplayMinutes=(int) (((millisUntilFinished / 1000) - ((mDisplayDays * 86400) + (mDisplayHours * 3600))) / 60);
                int mDisplaySeconds=(int) ((millisUntilFinished / 1000) % 60);

                day1.setText(String.valueOf(mDisplayDays));
                hr1.setText(String.valueOf(mDisplayHours));
                min1.setText(String.valueOf(mDisplayMinutes));
                sec1.setText(String.valueOf(mDisplaySeconds));
            }

            @Override
            public void onFinish() {
                countDownTimer2.start();


            }
        };
        countDownTimer.start();






        return rootView;
    }
}