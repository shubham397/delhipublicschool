package com.dialursearch.gyanpatra;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherAttendanceMainFragment extends Fragment {

    String str_class, str_section;

    ListView listView;

    TextView txtCount,textView;

    Button btn;

    int c=0;

    String a[];

    String[] stu_name, stu_phone,stu_admission,stu_class,stu_section,stu_illness;

    int[] stu_roll;

    String str_date,str_teacherCode,str_teacherName;

    public TeacherAttendanceMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_attendance_main, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("Attendance", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        str_class = pref.getString("Class", "I");
        str_section = pref.getString("Section", "A");

        SharedPreferences pref2 = getActivity().getSharedPreferences("teacherLogin", 0); // 0 - for private mode
        final SharedPreferences.Editor editor2 = pref2.edit();

        str_teacherCode = pref2.getString("ncode", "");
        str_teacherName = pref2.getString("name", "");

        listView = (ListView) view.findViewById(R.id.listView);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
        String str = sdf.format(cal.getTime());

        str_date = str;

        txtCount = (TextView) view.findViewById(R.id.txt_count);

        btn = (Button) view.findViewById(R.id.btn_submit);

        //Toast.makeText(getActivity(),"asdasdasd"+str_class,Toast.LENGTH_SHORT).show();
        a = new String[65];

        Arrays.fill(a,"1");

        new SubmitAsyncTask().execute("http://app.dpsdhanbad.org/student.php");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckTimeAsyncTask().execute("http://app.dpsdhanbad.org/CheckTime.php");
            }
        });

        textView=(TextView)view.findViewById(R.id.head);
        textView.setText(str_class+" "+str_section);
        return view;
    }

    class CheckTimeAsyncTask extends AsyncTask<String, Void, String> {

        String str;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/CheckTime.php");

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                str = EntityUtils.toString(httpResponse.getEntity());


            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int l = stu_roll.length - c;
            String absenteeList="";
            int roll[]=new int[a.length];
            for(int i=0;i<a.length;i++) {
                if (a[i].equals("1")) {
                    continue;
                } else {
                    for (int j = 0; j < stu_admission.length; j++) {
                        if (stu_admission[j].equals(a[i])) {
                            roll[j]=stu_roll[j];
//                            absenteeList=absenteeList+roll[j]+"  -  "+stu_name[j]+"\n";
                        }
                    }
                }
            }
            for(int i=0;i<roll.length;i++)
            {
                if(roll[i]==0)
                {
                    continue;
                }
                else
                {
                    if (stu_roll[i]==roll[i]) {
                            absenteeList=absenteeList+roll[i]+"  -  "+stu_name[i]+"\n";
                    }
                }
            }

//            Toast.makeText(getActivity(),""+Arrays.toString(roll),Toast.LENGTH_SHORT).show();
            String[] str1=str.split("=");
            if(str1[1].equals("GOT")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Do you want to Send Absentee?");
                builder.setMessage(absenteeList+"\nTotal Absent : " + c + "\nTotal Present : " + l + "\nTotal Student : " + stu_roll.length);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        set();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setCancelable(true);
                        builder1.setTitle("Success");
                        builder1.setMessage("Absentee Send Successfully");
                        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if user pressed "yes", then he is allowed to exit from application
                                HomeFragment homeFragment = new HomeFragment();
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                manager.beginTransaction().replace(R.id.home, homeFragment, homeFragment.getTag()).commit();
                            }
                        });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Warning");
                builder.setMessage("You Are Not Allowed To Give Attendance After "+str1[0]);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.home, homeFragment, homeFragment.getTag()).commit();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    public void set() {
        new SubmitAbsenteeAsyncTask().execute("http://app.dpsdhanbad.org/absentee.php");

    }

    class SubmitAbsenteeAsyncTask extends AsyncTask<String, Void, String> {

        String str;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for(int i=0;i<a.length;i++) {
                if(a[i].equals("1"))
                {
                    continue;
                }
                else {
                    for(int j=0;j<stu_admission.length;j++) {
                        if(stu_admission[j].equals(a[i])) {
                            list.add(new BasicNameValuePair("admission", "" + stu_admission[j]));
                            list.add(new BasicNameValuePair("date", "" + str_date));
                            list.add(new BasicNameValuePair("roll", "" + stu_roll[j]));
                            list.add(new BasicNameValuePair("name", "" + stu_name[j]));
                            list.add(new BasicNameValuePair("class", "" + stu_class[j]));
                            list.add(new BasicNameValuePair("section", "" + stu_section[j]));
                            list.add(new BasicNameValuePair("phone", "" + stu_phone[j]));
                            list.add(new BasicNameValuePair("teacher", "" + str_teacherName));
                            list.add(new BasicNameValuePair("tcode", "" + str_teacherCode));
                        }
                    }
                    try {
                        HttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/absentee.php");

                        httpPost.setEntity(new UrlEncodedFormEntity(list));

                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        HttpEntity httpEntity = httpResponse.getEntity();

                        str = EntityUtils.toString(httpResponse.getEntity());


                    } catch (ClientProtocolException e) {

                    } catch (IOException e) {

                    }
                }
            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(getActivity(),""+str,Toast.LENGTH_SHORT).show();
        }
    }

    class SubmitAsyncTask extends AsyncTask<String, Void, Boolean> {

        JSONArray jarray;

        int c;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("class", "" + str_class));
            list.add(new BasicNameValuePair("section", "" + str_section));

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/student.php");

                httpPost.setEntity(new UrlEncodedFormEntity(list));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity entity = httpResponse.getEntity();
                String data = EntityUtils.toString(entity);

                JSONObject jsono = new JSONObject(data);
                jarray = jsono.getJSONArray("student");
                stu_name = new String[jarray.length()];
                stu_roll = new int[jarray.length()];
                stu_phone= new String[jarray.length()];
                stu_admission= new String[jarray.length()];
                stu_class= new String[jarray.length()];
                stu_section= new String[jarray.length()];
                stu_illness= new String[jarray.length()];
                c=jarray.length();
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject o = jarray.getJSONObject(i);
                    stu_name[i] = o.get("stud_name").toString();
                    stu_roll[i] = Integer.parseInt(o.get("roll_no").toString());
                    stu_phone[i] = o.get("contact_current").toString();
                    stu_admission[i] = o.get("admission_no").toString();
                    stu_class[i]=o.get("Class").toString();
                    stu_section[i]=o.get("Section").toString();
                    stu_illness[i]=o.get("illness").toString();
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
            CustomAdapter adapter = new CustomAdapter(stu_name,stu_roll,stu_phone,stu_class,stu_section,stu_admission,stu_illness);
            listView.setAdapter(adapter);
            if(c==0)
            {
                Toast.makeText(getActivity(), "Please Go Back And Select Correct Class & Section", Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.INVISIBLE);
            }
        }
    }
    class CustomAdapter extends BaseAdapter {

        String[] name,phone,stuclass,section,admission,illness;

        int [] roll;

        public CustomAdapter(String[] name, int[] roll, String[] phone,String[] stuclass,String[] section,String[] admission,String[] illness ) {
            this.roll = roll;
            this.name = name;
            this.phone = phone;
            this.stuclass=stuclass;
            this.section=section;
            this.admission=admission;
            this.illness=illness;
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
            row = inflater.inflate(R.layout.a_student_attendance_erp,parent , false);

            final TextView textClassSecRoll,textadm,textname,textmobile,textcat;

            final LinearLayout linearLayout;

            textClassSecRoll = (TextView) row.findViewById(R.id.txtClass_Section_Roll);
            textadm = (TextView) row.findViewById(R.id.txtAdm);
            textname = (TextView) row.findViewById(R.id.txtName);
            textmobile = (TextView) row.findViewById(R.id.txtMobile);
            textcat = (TextView) row.findViewById(R.id.txtCategory);

            textClassSecRoll.setText("Roll No. : "+roll[i]);
            textadm.setText("                               AdmNo. : " + admission[i]);
            textname.setText("Name : " + name[i]);
            textmobile.setText("Mobile : " + phone[i]);
            textcat.setText("    Cat. : " + illness[i]);

            linearLayout=(LinearLayout)row.findViewById(R.id.layout);

            final ColorDrawable viewColor = (ColorDrawable) linearLayout.getBackground();


            for(int j=0;j<admission.length;j++)
            {
                if(a[j].equals(admission[i]))
                {
                    linearLayout.setBackgroundColor(Color.RED);
                }
            }

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int colorId = viewColor.getColor();
                    int d=0;
//                    Toast.makeText(getActivity(), "" + admission[2979], Toast.LENGTH_SHORT).show();
                    if(colorId==-65536)
                    {
                        //Toast.makeText(getActivity(), ""+a[i], Toast.LENGTH_SHORT).show();
                        for(int j=0;j<admission.length;j++)
                        {
                            if(a[j].equals(admission[i]))
                            {
                                a[j]="1";
                            }
                        }
                        linearLayout.setBackgroundColor(Color.TRANSPARENT);
                        c--;
                        txtCount.setText("Count = "+c);


                        //Toast.makeText(getActivity(), "Roll Number Exist", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //linearLayout.setBackgroundColor(Color.RED);
                        for (int j = 0; j < admission.length; j++) {
                            if (a[j].equals(admission[i])) {
                                //linearLayout.setBackgroundColor(Color.RED);
                                //Toast.makeText(getActivity(), "Roll Number Exist", Toast.LENGTH_SHORT).show();
                                d = 0;
                                break;
                            } else {
                                d = 1;
                            }
                        }
                        if (d == 1) {
                            for (int j = 0; j < admission.length; j++) {
                                if (a[j].equals("1")) {
                                    linearLayout.setBackgroundColor(Color.RED);
                                    a[j] = admission[i];
                                    c++;
                                    break;
                                }
                            }
                        }
                    }
                    //Toast.makeText(getActivity(),""+txt.getText().toString(),Toast.LENGTH_SHORT).show();
                    txtCount.setText("Count = "+c);
                }
            });

            /*btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c++;
                    Toast.makeText(getActivity(),""+c,Toast.LENGTH_SHORT).show();
                    txtCount.setText("Count = "+c);
                }
            });*/

            return (row);
        }
    }
}
