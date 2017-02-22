package com.example.tapojit.hamphack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends ActionBarActivity implements View.OnClickListener
       {

    private static final String TAG="EmailPassword";

    //General
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignIn;
    private Button mSignOut;



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };

    }

   @Override
   public void onStart() {
       super.onStart();
       mAuth.addAuthStateListener(mAuthListener);
   }
   // [END on_start_add_listener]

   // [START on_stop_remove_listener]
   @Override
   public void onStop() {
       super.onStop();
       if (mAuthListener != null) {
           mAuth.removeAuthStateListener(mAuthListener);
       }
   }



   private void signIn(String email, String password) {
       Log.d(TAG, "signIn:" + email);
       if (!validateForm()) {
           return;
       }

       //showProgressDialog();

       // [START sign_in_with_email]
       mAuth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                       // If sign in fails, display a message to the user. If sign in succeeds
                       // the auth state listener will be notified and logic to handle the
                       // signed in user can be handled in the listener.
                       if (!task.isSuccessful()) {
                           Log.w(TAG, "signInWithEmail:failed", task.getException());
                           Toast.makeText(Authentication.this, R.string.auth_failed,
                                   Toast.LENGTH_SHORT).show();
                       }
                       if(task.isSuccessful()){
                           Intent intent=new Intent(getApplicationContext(),What_You_Can_Do.class);
                           startActivity(intent);
                       }

                       // [START_EXCLUDE]
                       if (!task.isSuccessful()) {
                           mStatusTextView.setText(R.string.auth_failed);
                       }
                       //hideProgressDialog();
                       // [END_EXCLUDE]
                   }
               });
       // [END sign_in_with_email]
   }

   private void signOut() {
       mAuth.signOut();
       updateUI(null);
   }

   private boolean validateForm() {
       boolean valid = true;

       String email = mEmailField.getText().toString();
       if (TextUtils.isEmpty(email)) {
           mEmailField.setError("Required.");
           valid = false;
       } else {
           mEmailField.setError(null);
       }

       String password = mPasswordField.getText().toString();
       if (TextUtils.isEmpty(password)) {
           mPasswordField.setError("Required.");
           valid = false;
       } else {
           mPasswordField.setError(null);
       }

       return valid;
   }

   private void updateUI(FirebaseUser user) {
       //hideProgressDialog();
       if (user != null) {
           mStatusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail()));
           mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

           findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
           findViewById(R.id.email_password_fields).setVisibility(View.GONE);
           findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
       } else {
           mStatusTextView.setText(R.string.signed_out);
           mDetailTextView.setText(null);

           findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
           findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
           findViewById(R.id.sign_out_button).setVisibility(View.GONE);
       }
   }
   @Override
   public void onClick(View v) {
       int i = v.getId();
       if (i == R.id.email_sign_in_button) {
           signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
       } else if (i == R.id.sign_out_button) {
           signOut();
       }
   }



}
