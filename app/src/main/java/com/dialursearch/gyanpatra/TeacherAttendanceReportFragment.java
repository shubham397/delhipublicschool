package com.dialursearch.gyanpatra;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherAttendanceReportFragment extends Fragment {


    String str_class,str_section,str_date,str_month,str_year,str_day;

    TextView textView;

    ListView listView;

    public TeacherAttendanceReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher_attendance_report, container, false);

        textView=(TextView)view.findViewById(R.id.txt_head);

        SharedPreferences pref = getActivity().getSharedPreferences("Attendance", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        str_class = pref.getString("Class", "I");
        str_section = pref.getString("Section", "A");
        str_date = pref.getString("Date", "A");
        str_month = pref.getString("Month", "A");
        str_year = pref.getString("Year", "A");

        str_day=str_date+"-"+str_month+"-"+str_year;

        listView=(ListView)view.findViewById(R.id.listView_report);

        new SubmitAsyncTask().execute("http://app.dpsdhanbad.org/report.php");

        return view;
    }

    class SubmitAsyncTask extends AsyncTask<String, Void, Boolean> {

        JSONArray jarray;

        String[] stu_name,stu_phone;

        int[] stu_roll;

        int c;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("class", "" + str_class));
            list.add(new BasicNameValuePair("section", "" + str_section));
            list.add(new BasicNameValuePair("date", "" + str_day));

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/report.php");

                httpPost.setEntity(new UrlEncodedFormEntity(list));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity entity = httpResponse.getEntity();
                String data = EntityUtils.toString(entity);

                JSONObject jsono = new JSONObject(data);
                jarray = jsono.getJSONArray("report");
                stu_name = new String[jarray.length()];
                stu_roll = new int[jarray.length()];
                stu_phone= new String[jarray.length()];
                c=jarray.length();
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject o = jarray.getJSONObject(i);
                    stu_name[i] = o.get("Name").toString();
                    stu_roll[i] = Integer.parseInt(o.get("Roll_no").toString());
                    stu_phone[i] = o.get("Phone_no").toString();
                }


            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //Toast.makeText(getActivity(),name[0]+"   "+roll[0],Toast.LENGTH_SHORT).show();
            CustomAdapter adapter = new CustomAdapter(stu_name,stu_roll,stu_phone);
            listView.setAdapter(adapter);

            textView.setText("(Date -> "+str_day+") (Class -> "+str_class+" "+str_section+") (Total ->"+c+")");
        }
    }

    class CustomAdapter extends BaseAdapter {

        String[] name,phone;

        int [] roll;

        public CustomAdapter(String[] name, int[] roll, String[] phone) {
            this.roll = roll;
            this.name = name;
            this.phone = phone;
        }

        @Override
        public int getCount() {
            return roll.length;
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
            row = inflater.inflate(R.layout.a_student_attendance,parent , false);

            final TextView txtroll,txtname,txtphone;

            txtroll = (TextView) row.findViewById(R.id.textView1);
            txtname = (TextView) row.findViewById(R.id.textView2);
            txtphone = (TextView) row.findViewById(R.id.textView3);

            txtroll.setText("Roll : " + roll[i]);
            txtname.setText("Name : " + name[i]);
            txtphone.setText("Phone : " + phone[i]);

            return (row);
        }
    }

}
