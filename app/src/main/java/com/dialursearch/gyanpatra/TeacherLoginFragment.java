package com.dialursearch.gyanpatra;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherLoginFragment extends Fragment {

    EditText ed1,ed2;

    Button btn1,btn2;

    String adm,pass;

    SharedPreferences.Editor editor;


    public TeacherLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacher_login, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("teacherLogin", 0); // 0 - for private mode
        editor = pref.edit();

        ed1=(EditText)view.findViewById(R.id.edit_user_teacher);
        ed2=(EditText)view.findViewById(R.id.edit_pass_teacher);

        btn1=(Button)view.findViewById(R.id.btn_tea_submit);
        btn2=(Button)view.findViewById(R.id.btn_tea_reset);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();
                //Toast.makeText(getActivity(),"Please fill the form",Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setText("");
                ed2.setText("");
            }
        });



        return view;
    }
    public void set()
    {
        adm=ed1.getText().toString();
        pass=ed2.getText().toString();

        if(adm.isEmpty()||pass.isEmpty())
        {
            Toast.makeText(getActivity(),"Please fill the form",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getActivity(),""+adm+"="+pass,Toast.LENGTH_SHORT).show();
            new SubmitAsyncTask().execute("http://app.dpsdhanbad.org/teacherLogin.php");
        }
    }

    class SubmitAsyncTask extends AsyncTask<String, Void, String> {

        String str;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("teacher", ""+adm));
            list.add(new BasicNameValuePair("pass", ""+pass));

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/teacherLogin.php");

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
            String[] str1;
            str1=str.split("=");
            if (str1[0].equals("Done"))
            {
                editor.putString("login","1");
                editor.putString("user",str1[1]);
                editor.putString("ncode",str1[2]);
                editor.putString("name",str1[3]);
                editor.putString("wing",str1[4]);
                editor.putString("dept",str1[5]);
                editor.putString("mobile",str1[6]);
                editor.commit();
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                HomeFragment homeFragment=new HomeFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.home,homeFragment,homeFragment.getTag()).commit();
            }
            else
            {
                ed1.setError("Teacher Id. is incorrect");
                ed2.setError("Password is incorrect");
            }
        }
    }
}
