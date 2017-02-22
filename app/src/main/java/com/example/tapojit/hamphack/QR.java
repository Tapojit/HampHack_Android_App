package com.example.tapojit.hamphack;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.support.v7.app.AlertDialog;

/**
 * Created by tapojit on 2/15/17.
 */

public class QR extends Fragment {
    public QR(){

    }






    public void qrCodeGenerator(String filePath,String data, int dimen) {
        int size = dimen;
        String fileType = "png";
        File f = new File(getActivity().getFilesDir(), filePath + "." + fileType);

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, size, size);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            FileOutputStream fos=null;
            try{
                fos=new FileOutputStream(f);
                bmp.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.close();
            } catch (Exception e){
                Log.e("Save_Image",e.getMessage(),e);
            };

        } catch (WriterException e) {
            e.printStackTrace();
        };
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_qr, container, false);


        final ArrayAdapter<String> mtickets;

        final DatabaseReference tickets= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/QR");
        Log.v("fb","firebase loaded");
        String[] ticketLabel={
                "Registration",
                "Ticket2",
                "Ticket3",
                "Ticket4"};

        List<String> normalTickets=new ArrayList<String>(
                Arrays.asList(ticketLabel)
        );
        mtickets=new ArrayAdapter<String>(getActivity(),R.layout.std_lv,normalTickets);

        ListView listView=(ListView) rootView.findViewById(R.id.feeds_forecast2);

        listView.setAdapter(mtickets);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String data=(String) mtickets.getItem(position);
                final ImageView frame=(ImageView) rootView.findViewById(R.id.qr);
                final File f=new File(getActivity().getFilesDir(), data+".png");

                if (data.equals("Registration")&&(!f.exists())){

                    final DatabaseReference fVal=tickets.child("Ticket1");
                    fVal.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double num=dataSnapshot.getValue(Double.class);
                            if (num>0){
                                num--;
                                FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/QR").child(data).setValue(num);
                                View view1=(LayoutInflater.from(getActivity())).inflate(R.layout.popup,null);
                                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
                                alertBuilder.setView(view1);
                                final EditText input=(EditText)view1.findViewById(R.id.input);
                                alertBuilder.setCancelable(true)
                                        .setTitle("Please enter your registered name:")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String name=input.getText().toString();
                                                qrCodeGenerator(data, name,frame.getMeasuredHeight());

                                                try {
                                                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                                                    frame.setImageBitmap(b);
                                                }
                                                catch (FileNotFoundException e)
                                                {
                                                    e.printStackTrace();
                                                }


                                            }
                                        });
                                final AlertDialog dialog=alertBuilder.create();
                                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface arg0) {
                                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Black));
                                    }
                                });
                                dialog.show();


                                Button positive=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                                        positive.setBackgroundColor(getResources().getColor(R.color.White));
//                                        positive.setGravity(Gravity.CENTER_HORIZONTAL);
//                                        LinearLayout.LayoutParams positiveButton=(LinearLayout.LayoutParams) positive.getLayoutParams();
//                                        positiveButton.gravity= Gravity.CENTER;
                            }else {
                                Toast.makeText(getActivity(),"Sorry, tickets are unavailable",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else if(!data.equals("Registration")&&(!f.exists())) {


                    final DatabaseReference fVal=tickets.child(data);


                    fVal.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double num=dataSnapshot.getValue(Double.class);
                            if (num>0){
                                qrCodeGenerator(data,data,frame.getMeasuredHeight());
                                num--;
                                FirebaseDatabase.getInstance().getReferenceFromUrl("https://hello-world-d1075.firebaseio.com/HampHack/QR").child(data).setValue(num);
                                try {
                                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                                    frame.setImageBitmap(b);
                                }
                                catch (FileNotFoundException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                Toast.makeText(getActivity(),"Sorry, tickets are unavailable",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });}

                else {
                    try {
                        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                        frame.setImageBitmap(b);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                };

            }
        });



//        Button web=(Button) rootView.findViewById(R.id.web);
//        web.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://hamphack.hampshire.edu"));
//                startActivity(browserIntent);
//            }
//        });
//        View v=(View) rootView.findViewById(R.id.v3);
//        v.setBackground(getResources().getDrawable(R.drawable.rec_undr));
//        Button feed=(Button) rootView.findViewById(R.id.feeds);
//        Button qr=(Button) rootView.findViewById(R.id.qrtickets);
//        qr.setBackground(getResources().getDrawable(R.drawable.qr_f));
//
//        feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent feedIntent=new Intent(getActivity(),MainActivity.class);
//                startActivity(feedIntent);
//                getActivity().overridePendingTransition(R.transition.backb1,R.transition.backb2);
//            }
//        });



        return rootView;
    }

}
