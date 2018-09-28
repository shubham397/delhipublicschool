package com.dialursearch.gyanpatra;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    WebView webView;
    private ProgressDialog progressBar;

    public WebFragment() {
        // Required empty public constructor
    }

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            switch (message.what)
            {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        webView =(WebView) view.findViewById(R.id.web_page);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode

        String str1,str2;

        str1=pref.getString("headText","");
        str2=pref.getString("url","");

        if(str1.equals("GYAN PATRA"))
        {
//            Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();

        }

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        progressBar = ProgressDialog.show(getActivity(), "", "Loading...");
        progressBar.setCancelable(true);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                //Toast.makeText(getActivity(), "Oh no! ", Toast.LENGTH_SHORT).show();
                //progressBar.cancel();
                if (progressBar.isShowing()) {
                    progressBar.cancel();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Log.e(TAG, "Error: " + description);
                //Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        webView.loadUrl(str2);

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack())
                {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        return view;
    }
    private void webViewGoBack()
    {
        webView.goBack();
    }

}