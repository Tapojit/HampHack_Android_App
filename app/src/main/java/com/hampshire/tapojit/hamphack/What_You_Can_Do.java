package com.hampshire.tapojit.hamphack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hampshire.tapojit.hamphack.R.id.container;

public class What_You_Can_Do extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what__you__can__do);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(container, new OrganizerFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class OrganizerFragment extends android.support.v4.app.Fragment {
        public ArrayAdapter<String> mViews;
        public OrganizerFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_what__you__can__do, container, false);

            String[] views={
                    "QR Scan",
                    "Update feeds"
                    };
            List<String> viewPrime= new ArrayList<String>(Arrays.asList(views));

            mViews=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,viewPrime){

            };

            ListView listView=(ListView) rootView.findViewById(R.id.organizerList);
            listView.setAdapter(mViews);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position==0){
                        IntentIntegrator intentIntegrator=new IntentIntegrator(getActivity());
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                        intentIntegrator.setPrompt("Scan");
                        intentIntegrator.setCameraId(0);
                        intentIntegrator.initiateScan();
                    }
                    else if(position==1){
                        String[] f2={
                                "URL",
                                "Media",
                                "Details"
                        };
                        List<String> f=new ArrayList<String>(Arrays.asList(f2));
                        View view1=(LayoutInflater.from(getActivity())).inflate(R.layout.enterpopulator,null);
                        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
                        alertBuilder.setView(view1);
                        final ListView l1=(ListView) view1.findViewById(R.id.list);
                        ArrayAdapter<String> mF=new ArrayAdapter<String>(getActivity(), R.layout.input, R.id.label,f);
                        l1.setAdapter(mF);

                        alertBuilder.setCancelable(true)
                                .setTitle("Enter Input For Feeds: ")
                                .setNegativeButton("Cancel",null)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref");
                                        DatabaseReference databaseReferenceurl= FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref");
                                        final DatabaseReference icon= FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref");
                                        icon.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Double d1=Double.parseDouble(dataSnapshot.getChildren().iterator().next().getKey());
                                                EditText t1=(EditText)l1.getChildAt(1).findViewById(R.id.fill);
                                                String s=t1.getText().toString();
                                                FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref").child(Double.toString(d1-1)).setValue(s);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        databaseReferenceurl.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Double d1=Double.parseDouble(dataSnapshot.getChildren().iterator().next().getKey());
                                                EditText t1=(EditText)l1.getChildAt(0).findViewById(R.id.fill);
                                                String s=t1.getText().toString();
                                                FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref").child(Double.toString(d1-1)).setValue(s);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Double d1=Double.parseDouble(dataSnapshot.getChildren().iterator().next().getKey());
                                                EditText t1=(EditText)l1.getChildAt(2).findViewById(R.id.fill);
                                                String s=t1.getText().toString();
                                                FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref").child(Double.toString(d1-1)).setValue(s);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

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
                    }

                }
            });






            return rootView;
        }
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_LONG).show();

            } else {
                if(result.getContents().equals("Ticket2")||result.getContents().equals("Ticket3")) Toast.makeText(this,"Approved!",Toast.LENGTH_LONG).show();
                else {
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref");
                    DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReferenceFromUrl("DB_ref");

                    FirebaseListAdapter<String> participants=new FirebaseListAdapter<String>(this,String.class,android.R.layout.simple_list_item_1,databaseReference) {
                        @Override
                        protected void populateView(View v, String model, int position) {
                            TextView txt1=(TextView)v.findViewById(android.R.id.text1);
                            txt1.setText(model);
                        }
                    };
                    for (int i=0;i<participants.getCount();i++){
                        if(participants.getItem(i).equals(result.getContents())) {
                            Toast.makeText(this,"Registered Participant",Toast.LENGTH_SHORT);
                            databaseReference2.runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    Double p=mutableData.getValue(Double.class);
                                    p++;
                                    mutableData.setValue(p);
                                    return Transaction.success(mutableData);
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                                }
                            });
                            break;
                        }
                        else if(i==participants.getCount()-1) Toast.makeText(this,"Not Registered",Toast.LENGTH_LONG);
                    }
                }
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }

    }


}
