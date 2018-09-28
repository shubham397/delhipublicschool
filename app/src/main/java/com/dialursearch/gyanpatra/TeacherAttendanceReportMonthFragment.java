package com.dialursearch.gyanpatra;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
public class TeacherAttendanceReportMonthFragment extends Fragment {

    String str_class,str_section,str_month;

    ListView listView;

    TextView textView;

    String[] strTotal,strAbsent,strPresent;

    public TeacherAttendanceReportMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_teacher_attendance_report_month, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("Attendance", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        str_class = pref.getString("Class", "I");
        str_section = pref.getString("Section", "A");
        str_month = pref.getString("Month", "A");

        listView=(ListView)view.findViewById(R.id.listView_report);

        textView=(TextView)view.findViewById(R.id.txt_head);

        //Toast.makeText(getActivity(),str_class+" "+str_section+" "+str_month,Toast.LENGTH_SHORT).show();

        new SubmitMonthAsyncTask().execute("http://app.dpsdhanbad.org/report_month.php?c="+str_class+"&s="+str_section+"&d="+str_month);

        return view;
    }

    class SubmitMonthAsyncTask extends AsyncTask<String, Void, Boolean> {

        JSONArray jarray;

        String[] strName,strRoll,strPhone;

        String data;

        @Override
        protected void onPreExecute() {

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
                data = EntityUtils.toString(entity);


                JSONObject jsono = new JSONObject(data);
                jarray = jsono.getJSONArray("reportA");
                strName=new String[jarray.length()];
                strRoll=new String[jarray.length()];
                strPhone=new String[jarray.length()];
                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject o=jarray.getJSONObject(i);
                    strName[i]=o.getString("Name");
                    strRoll[i]=o.getString("Roll_no");
                    strPhone[i]=o.getString("Phone_no");
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

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
//            Toast.makeText(getActivity(),""+jarray.length(),Toast.LENGTH_SHORT).show();
            strTotal=data.split("=");
            strPresent=strTotal[2].split("\\.");
            strAbsent=strTotal[3].split("-");
//            Toast.makeText(getActivity(),strPresent[0],Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),strPresent[1],Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),strPresent[3],Toast.LENGTH_SHORT).show();

            CustomAdapter adapter = new CustomAdapter(strName,strRoll,strPhone);
            listView.setAdapter(adapter);

            textView.setText("(Date -> "+str_month+") (Class -> "+str_class+" "+str_section+") (Total ->"+jarray.length()+")");
        }
    }

    class CustomAdapter extends BaseAdapter {

        String[] name,phone,roll;

        public CustomAdapter(String[] name, String[] roll, String[] phone) {
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
            row = inflater.inflate(R.layout.a_report_month,parent , false);

            final TextView txtroll,txtname,txtphone,txtTotal,txtPresent,txtAbsent;

            txtroll = (TextView) row.findViewById(R.id.textView1);
            txtname = (TextView) row.findViewById(R.id.textView2);
            txtphone = (TextView) row.findViewById(R.id.textView3);
            txtTotal = (TextView) row.findViewById(R.id.textView4);
            txtPresent = (TextView) row.findViewById(R.id.textView5);
            txtAbsent = (TextView) row.findViewById(R.id.textView6);

            txtroll.setText("Roll : " + roll[i]);
            txtname.setText("Name : " + name[i]);
            txtphone.setText("Phone : " + phone[i]);
            txtPresent.setText("Total Present : " + strPresent[i]);
            txtAbsent.setText("Total Absent : " + strAbsent[i]);
            txtTotal.setText("Total Working Days : "+strTotal[1]);

            return (row);
        }
    }

}
