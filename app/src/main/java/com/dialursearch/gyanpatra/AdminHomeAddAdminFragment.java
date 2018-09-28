package com.dialursearch.gyanpatra;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class AdminHomeAddAdminFragment extends Fragment {

    EditText ed1,ed2;

    Button btn1,btn2;

    String user,pass;

    public AdminHomeAddAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home_add_admin, container, false);

        ed1=(EditText)view.findViewById(R.id.edit_user);
        ed2=(EditText)view.findViewById(R.id.edit_pass);

        btn1=(Button)view.findViewById(R.id.btn_admin_home_addAdmin_submit);
        btn2=(Button)view.findViewById(R.id.btn_admin_home_addAdmin_reset);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();
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
        user=ed1.getText().toString();
        pass=ed2.getText().toString();

        if(user.isEmpty()||pass.isEmpty())
        {
            Toast.makeText(getActivity(),"Please fill the form",Toast.LENGTH_SHORT).show();
        }
        else {
            new SubmitAsyncTask().execute("http://app.dpsdhanbad.org/addAdmin.php");
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
            list.add(new BasicNameValuePair("user", ""+user));
            list.add(new BasicNameValuePair("pass", ""+pass));

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://app.dpsdhanbad.org/addAdmin.php");

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
            if (str.equals("Done"))
            {
                Toast.makeText(getActivity(),"ADMIN ADD",Toast.LENGTH_SHORT).show();
                AdminHomeFragment adminHomeFragment=new AdminHomeFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.home,adminHomeFragment,adminHomeFragment.getTag()).commit();
            }
            else
            {
                Toast.makeText(getActivity(),"Try Again",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
