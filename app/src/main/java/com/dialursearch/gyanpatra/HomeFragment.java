package com.dialursearch.gyanpatra;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout slider;

    private TextView textView,textView_Student_Login,textView_Teacher_Login;

    private StringBuffer stringBuffer;

    ImageButton sbtn_1,sbtn_2,sbtn_3,sbtn_4,sbtn_5,sbtn_6,sbtn_7,sbtn_8,sbtn_9,sbtn_10,sbtn_11,sbtn_12,sbtn_13;
    TextView stxtnotice;

    ImageButton tbtn_1,tbtn_2,tbtn_3,tbtn_4,tbtn_5,tbtn_6,tbtn_7,tbtn_8;
    TextView ttxtnotice;

    ImageButton scbtn_1;
    TextView sctxtnotice;

    ImageButton abtn_1,abtn_2;
    TextView atxtnotice;

    ImageButton wbtn_1,wbtn_2,wbtn_3,wbtn_4,wbtn_5,wbtn_6,wbtn_7,wbtn_8;
    TextView wtxtnotice;

    SharedPreferences.Editor editor;

    String str,strname,strclass,strsection,strroll,strcontact,strcategory,strdob,strfathername;

    MainActivity mainActivity;

    String versionName;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        versionName = BuildConfig.VERSION_NAME;

        //new UpdateAsyncTask().execute("http://app.dpsdhanbad.org/checkUpdate.php");

        //Toast.makeText(getActivity(),""+versionName,Toast.LENGTH_SHORT).show();

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putString("headText","shubham");
        editor.putString("url","DO");
        editor.commit();

        mainActivity=new MainActivity();

        SharedPreferences pref1 = getActivity().getSharedPreferences("studentLogin", 0); // 0 - for private mode
        final SharedPreferences.Editor editor1 = pref1.edit();

        SharedPreferences pref2 = getActivity().getSharedPreferences("teacherLogin", 0); // 0 - for private mode
        final SharedPreferences.Editor editor2 = pref2.edit();

        slider=(SliderLayout)view.findViewById(R.id.slider);

        textView =(TextView) view.findViewById(R.id.txt_marquee);

        textView_Student_Login=(TextView)view.findViewById(R.id.txt_student_login);

        textView_Teacher_Login=(TextView)view.findViewById(R.id.txt_teacher_login);

        str=pref1.getString("login","");

        if(str.equals("1")) {
            textView_Student_Login.setText("VIEW PROFILE");
        }
        else {
            textView_Student_Login.setText("LOGIN");
        }

        final String str2=pref2.getString("login","");

        if(str2.equals("1")) {
            textView_Teacher_Login.setText("VIEW PROFILE");
        }
        else {
            textView_Teacher_Login.setText("LOGIN");
        }

        //Toast.makeText(getActivity(),""+str,Toast.LENGTH_SHORT).show();

        stringBuffer=new StringBuffer();

        textView.setSelected(true);

        stxtnotice=(TextView)view.findViewById(R.id.textView_notice);
        stxtnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeFragment noticeFragment=new NoticeFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home, noticeFragment, noticeFragment.getTag()).addToBackStack("").commit();
            }
        });

        sbtn_1=(ImageButton)view.findViewById(R.id.btn_stu);
        sbtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText", "Diary");
                    editor.putString("url", "http://dpsdhanbad.org/StudentCorner/IDiary.aspx");
                    editor.commit();
                    WebFragment webFragment = new WebFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, webFragment, webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
                }
        });

        sbtn_2=(ImageButton)view.findViewById(R.id.btn_stu1);
        sbtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","Suggestion Box");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/Suggestion.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_3=(ImageButton)view.findViewById(R.id.btn_stu2);

        sbtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","Fee");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/feereceipt.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }

            }
        });

        sbtn_4=(ImageButton)view.findViewById(R.id.btn_stu3);

        sbtn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    StudentSyllabusFragment studentSyllabusFragment=new StudentSyllabusFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,studentSyllabusFragment,studentSyllabusFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_5=(ImageButton)view.findViewById(R.id.btn_stu4);

        sbtn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    StudentQuestionFragment studentQuestionFragment=new StudentQuestionFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,studentQuestionFragment,studentQuestionFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }

            }
        });

        sbtn_6=(ImageButton)view.findViewById(R.id.btn_stu5);

        sbtn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    StudentAssignmentFragment studentQuestionFragment=new StudentAssignmentFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,studentQuestionFragment,studentQuestionFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_7=(ImageButton)view.findViewById(R.id.btn_stu6);

        sbtn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    StudentScheduleFragment studentScheduleFragment=new StudentScheduleFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,studentScheduleFragment,studentScheduleFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_8=(ImageButton)view.findViewById(R.id.btn_stu7);

        sbtn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","FeedBack");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/feedbackcv.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_9=(ImageButton)view.findViewById(R.id.btn_stu8);

        sbtn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","Scholarship");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/OnlineScholarshipForm.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_10=(ImageButton)view.findViewById(R.id.btn_stu9);

        sbtn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","Teacher Desk");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/VirtualClass.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_11=(ImageButton)view.findViewById(R.id.btn_stu10);

        sbtn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","Result");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/stresult.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_12=(ImageButton)view.findViewById(R.id.btn_stu11);

        sbtn_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","Permission Slip");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/StudentPermissionSlip.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sbtn_13=(ImageButton)view.findViewById(R.id.btn_stu12);

        sbtn_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str.equals("1")) {
                    editor.putString("headText","PTM");
                    editor.putString("url","http://dpsdhanbad.org/StudentCorner/Grandparentt.aspx");
                    editor.commit();
                    WebFragment webFragment=new WebFragment();
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        ttxtnotice=(TextView)view.findViewById(R.id.textView1_notice);
        ttxtnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeFragment noticeFragment=new NoticeFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home, noticeFragment, noticeFragment.getTag()).addToBackStack("").commit();
            }
        });

        tbtn_1=(ImageButton)view.findViewById(R.id.btn_teacher);

        tbtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str2.equals("1")) {
                    editor.putString("headText", "Attendance");
                    editor.putString("url", "http://dpsdhanbad.org/StaffCorner/Attentdance_Details.aspx");
                    editor.commit();
                    TeacherAttendanceFragment teacherAttendanceFragment = new TeacherAttendanceFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherAttendanceFragment, teacherAttendanceFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        tbtn_2=(ImageButton)view.findViewById(R.id.btn_teacher1);

        tbtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str2.equals("1")) {
                editor.putString("headText","Yellow Page");
                editor.putString("url","http://app.dpsdhanbad.org/new.php");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        tbtn_3=(ImageButton)view.findViewById(R.id.btn_teacher2);

        tbtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(str2.equals("1")) {
                /*editor.putString("headText","Permission Slip");
                editor.putString("url","http://dpsdhanbad.org/tclogin.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).commit();*/
                    }
                    else
                    {
                        TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                    }
            }
        });

        tbtn_4=(ImageButton)view.findViewById(R.id.btn_teacher3);

        tbtn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str2.equals("1")) {
                /*editor.putString("headText","Permission Slip");
                editor.putString("url","http://dpsdhanbad.org/tclogin.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).commit();*/
                }
                else
                {
                    TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        tbtn_5=(ImageButton)view.findViewById(R.id.btn_teacher4);

        tbtn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(str2.equals("1")) {
                editor.putString("headText","Upload Result");
                editor.putString("url","http://dpsdhanbad.org/StaffCorner/uploadresult.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                    }
                    else
                    {
                        TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                    }
            }
        });

        tbtn_6=(ImageButton)view.findViewById(R.id.btn_teacher5);

        tbtn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str2.equals("1")) {
                editor.putString("headText","Send Result");
                editor.putString("url","http://dpsdhanbad.org/StaffCorner/messagetemp.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        tbtn_7=(ImageButton)view.findViewById(R.id.btn_teacher6);

        tbtn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(str2.equals("1")) {
                /*editor.putString("headText","Permission Slip");
                editor.putString("url","http://dpsdhanbad.org/tclogin.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).commit();*/
                    }
                    else
                    {
                        TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                    }
            }
        });

        tbtn_8=(ImageButton)view.findViewById(R.id.btn_teacher7);

        tbtn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str2.equals("1")) {
                /*editor.putString("headText","Permission Slip");
                editor.putString("url","http://dpsdhanbad.org/tclogin.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).commit();*/
                }
                else
                {
                    TeacherLoginFragment teacherLoginFragment=new TeacherLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                }
            }
        });

        sctxtnotice=(TextView)view.findViewById(R.id.textView2_notice);
        sctxtnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeFragment noticeFragment=new NoticeFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home, noticeFragment, noticeFragment.getTag()).addToBackStack("").commit();
            }
        });

        scbtn_1=(ImageButton)view.findViewById(R.id.imageBtnGyan);
        scbtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","GYAN PATRA");
                editor.putString("url","http://www.gyanpatra.com/Default.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        atxtnotice=(TextView)view.findViewById(R.id.textView3_notice);
        atxtnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeFragment noticeFragment=new NoticeFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home, noticeFragment, noticeFragment.getTag()).addToBackStack("").commit();
            }
        });

        abtn_1=(ImageButton)view.findViewById(R.id.imageBtnDetail);
        abtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        abtn_2=(ImageButton)view.findViewById(R.id.imageBtnApply);
        abtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Apply Now");
                editor.putString("url","http://adm.dpsdhanbad.org/Default.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wtxtnotice=(TextView)view.findViewById(R.id.textView4_notice);
        wtxtnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeFragment noticeFragment=new NoticeFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home, noticeFragment, noticeFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_1=(ImageButton)view.findViewById(R.id.btn_website);

        wbtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","About Us");
                editor.putString("url","http://dpsdhanbad.org/philosphy.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_2=(ImageButton)view.findViewById(R.id.btn_website1);

        wbtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Events");
                editor.putString("url","http://dpsdhanbad.org/NewsandEvents.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_3=(ImageButton)view.findViewById(R.id.btn_website2);

        wbtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Facilities");
                editor.putString("url","http://dpsdhanbad.org/library.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_4=(ImageButton)view.findViewById(R.id.btn_website3);

        wbtn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Faculty");
                editor.putString("url","http://dpsdhanbad.org/Faculty.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_5=(ImageButton)view.findViewById(R.id.btn_website4);

        wbtn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Result");
                editor.putString("url","http://dpsdhanbad.org/Admission_Notice.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_6=(ImageButton)view.findViewById(R.id.btn_website5);

        wbtn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Info Links");
                editor.putString("url","http://dictionary.cambridge.org/");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_7=(ImageButton)view.findViewById(R.id.btn_website6);

        wbtn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Activity");
                editor.putString("url","http://dpsdhanbad.org/CulturalCoCulturalActivity.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        wbtn_8=(ImageButton)view.findViewById(R.id.btn_website7);

        wbtn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("headText","Contact Us");
                editor.putString("url","http://dpsdhanbad.org/contact.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            }
        });

        strname=pref1.getString("name","");
        strclass=pref1.getString("class","");
        strsection=pref1.getString("section","");
        strroll=pref1.getString("roll_no","");
        strcontact=pref1.getString("contact","");
        strcategory=pref1.getString("category","");
        strdob=pref1.getString("dob","");
        strfathername=pref1.getString("fatherName","");
        final String adm=pref1.getString("adm_no","");

        textView_Student_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView_Student_Login.getText().toString().equals("LOGIN")) {
                    StudentLoginFragment studentLoginFragment = new StudentLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, studentLoginFragment, studentLoginFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setMessage("Admission No. - "+adm+"\nName - "+strname+"\nClass - "+strclass+"\nSection - "+
                            strsection+"\nRoll No. - "+strroll+"\nMobile No. - "+strcontact+"\nCategory - "+strcategory+
                            "\nDate Of Birth - "+strdob+"\nFather Name - "+strfathername);
                    builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
                            editor1.putString("login","0");
                            editor1.commit();
                            textView_Student_Login.setText("LOGIN");
                            HomeFragment homeFragment=new HomeFragment();
                            FragmentManager manager=getActivity().getSupportFragmentManager();
                            manager.beginTransaction().add(R.id.home,homeFragment,homeFragment.getTag()).commit();
                        }
                    });
                    builder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user select "No", just cancel this dialog and continue with app
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                }
        });

        final String user=pref2.getString("user","");
        final String ncode=pref2.getString("ncode","");
        final String empname=pref2.getString("name","");
        final String wing=pref2.getString("wing","");
        final String dept=pref2.getString("dept","");
        final String mob=pref2.getString("mobile","");

        textView_Teacher_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView_Teacher_Login.getText().toString().equals("LOGIN")) {
                    TeacherLoginFragment teacherLoginFragment = new TeacherLoginFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherLoginFragment, teacherLoginFragment.getTag()).addToBackStack("").commit();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setMessage("User Name - "+user+"\nTeacher Code - "+ncode+"\nName - "+empname+
                            "\nWing - "+wing+"\nDepartment - "+dept+"\nMobile - "+mob);
                    builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
                            editor2.putString("login","0");
                            editor2.commit();
                            textView_Teacher_Login.setText("LOGIN");
                            HomeFragment homeFragment=new HomeFragment();
                            FragmentManager manager=getActivity().getSupportFragmentManager();
                            manager.beginTransaction().add(R.id.home,homeFragment,homeFragment.getTag()).commit();
                        }
                    });
                    builder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user select "No", just cancel this dialog and continue with app
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }
        });

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("1", "http://app.dpsdhanbad.org/image/slide1.jpg");
        hashMap.put("2", "http://app.dpsdhanbad.org/image/slide2.jpg");
        hashMap.put("3", "http://app.dpsdhanbad.org/image/slide3.jpg");
        hashMap.put("4", "http://app.dpsdhanbad.org/image/slide4.jpg");
        hashMap.put("5", "http://app.dpsdhanbad.org/image/slide6.jpg");

        for(String name : hashMap.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(hashMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            slider.addSlider(textSliderView);
        }

        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
        slider.addOnPageChangeListener(this);

        new JSONAsyncTask().execute("http://app.dpsdhanbad.org/read.php");

        return view;

    }

    @Override
    public void onStop()
    {
        slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView sliderView)
    {
        slider.moveNextPosition();
        slider.startAutoCycle();

    }

    @Override
    public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        String[] message,link,type;

        JSONArray jarray;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                message=new String[jarray.length()];
                type=new String[jarray.length()];
                //link=new String[jarray.length()];
                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject o=jarray.getJSONObject(i);
                    message[i]=o.get("Description").toString();
                    type[i]=o.get("Type").toString();
                    //link[i]=o.getString("link");
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

            for(int i=0;i<message.length;i++)
            {
                if(type[i].contains("r")) {
                    stringBuffer.append(message[i] + " || ");
                }
            }
            textView.setText(stringBuffer.toString());
            if (result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }

    class UpdateAsyncTask extends AsyncTask<String, Void, String> {

        String str;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("v", ""+versionName));

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/checkUpdate.php");

                httpPost.setEntity(new UrlEncodedFormEntity(list));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                str =  EntityUtils.toString(httpResponse.getEntity());


            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(getActivity(), ""+result, Toast.LENGTH_LONG).show();
            if(result.equals("NOT"))
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(false);
                builder1.setTitle("Update is available!");
                builder1.setMessage("Please Update your Android App.");
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        }
    }
}