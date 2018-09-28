package com.dialursearch.gyanpatra;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentScheduleFragment extends Fragment {

    ListView listView;

    public StudentScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_schedule, container, false);


        new JSONAsyncTask().execute("http://app.dpsdhanbad.org/schedule.php");
        listView = (ListView) view.findViewById(R.id.listView);

        return view;
    }
        class JSONAsyncTask extends AsyncTask<String, Void, String> {

            String[] examName, fileName;

            JSONArray jarray;


            String str;

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... urls) {
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/schedule.php");

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();

                    str = EntityUtils.toString(httpResponse.getEntity());

                    JSONObject jsono = new JSONObject(str);
                    jarray = jsono.getJSONArray("schedule");
                    examName = new String[jarray.length()];
                    fileName = new String[jarray.length()];

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject o = jarray.getJSONObject(i);
                        examName[i] = o.getString("Subject");
                        fileName[i] = o.getString("FileNm");
                    }

                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return str;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
//                Toast.makeText(getActivity(), ""+result, Toast.LENGTH_SHORT).show();
                CustomAdapter adapter = new CustomAdapter(examName,fileName);
                listView.setAdapter(adapter);
            }
        }

        class CustomAdapter extends BaseAdapter {

            String[] examName, fileName;

            public CustomAdapter(String[] examName, String[] fileName) {
                this.examName = examName;
                this.fileName = fileName;
            }

            @Override
            public int getCount() {
                return examName.length;
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
                row = inflater.inflate(R.layout.a_student_schedule, parent, false);

                final TextView txtexam, txtfile;

                txtexam = (TextView) row.findViewById(R.id.textView1);
                txtfile = (TextView) row.findViewById(R.id.textView2);

                txtexam.setText("Examination Name : " + examName[i]);
                txtfile.setText("View/Download");

                txtfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "http://www.dpsdhanbad.org/ExamSchedule/"+fileName[i];
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                return (row);
            }

        }
    }
