package com.dialursearch.gyanpatra;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

    ListView listView;
    LinearLayout linearLayout;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
            builder.setTitle("Info");
            builder.setMessage("Internet not available, Cross check your internet connectivity and try again");
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        if (connected) {
            new JSONAsyncTask().execute("http://app.dpsdhanbad.org/read.php");
            listView = (ListView) view.findViewById(R.id.listView);
            linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        }

        return view;
    }

    class CustomAdapter extends BaseAdapter {
        String[] alpha;
        String[] alpha1;
        String[] alpha2;

        public CustomAdapter(String[] alpha, String[] alpha1, String[] alpha2) {
             this.alpha = alpha;
             this.alpha1 = alpha1;
             this.alpha2 = alpha2;

        }

        @Override
        public int getCount() {
            return alpha.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View row, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(R.layout.a_notice, parent, false);
            TextView title, detail, download;
            title = (TextView) row.findViewById(R.id.textView2);
            detail = (TextView) row.findViewById(R.id.textView3);
                title.setText(alpha[i]);
                detail.setText(alpha1[i]);
                if (alpha2[i].isEmpty()) {

                } else {
                    download = (TextView) row.findViewById(R.id.txt_download);
                    download.setVisibility(View.VISIBLE);
                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = ""+alpha2[i];
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });
                }
                return (row);
        }

    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog;

        String[] date, message, download,type;

        JSONArray jarray;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//for displaying progress bar
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
//establishing http connection
                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();

                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);


                JSONObject jsono = new JSONObject(data);
                jarray = jsono.getJSONArray("notice");
                date = new String[jarray.length()];
                message = new String[jarray.length()];
                download = new String[jarray.length()];
                type = new String[jarray.length()];
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject o = jarray.getJSONObject(i);
                    date[i] = o.get("Dt").toString();
                    message[i] = o.getString("Description");
                    download[i] = o.getString("Link");
                    type[i] = o.getString("Type");
                }

                return true;

                //------------------>>

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {

            dialog.cancel();
            CustomAdapter adapter = new CustomAdapter(date, message, download);
            listView.setAdapter(adapter);
            if (result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}
