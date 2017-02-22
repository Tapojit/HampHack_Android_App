package com.example.tapojit.hamphack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by tapojit on 2/16/17.
 */

public class Frame3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.f3,container,false);
        WebView mwb=(WebView) rootView.findViewById(R.id.wv);
        mwb.loadUrl("http://hamphack.hampshire.edu");
        WebSettings webSettings = mwb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return rootView;
    }

}
