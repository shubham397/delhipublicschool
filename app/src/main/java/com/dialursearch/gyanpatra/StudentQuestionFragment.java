package com.dialursearch.gyanpatra;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
public class StudentQuestionFragment extends Fragment {


    ListView listView;

    String url;

    Spinner spinner,spinner_m;

    String str;

    public StudentQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_question, container, false);
        spinner=(Spinner)view.findViewById(R.id.spinner_c);

        spinner_m=(Spinner)view.findViewById(R.id.spinner_m);

        ArrayAdapter<CharSequence> adp_date = ArrayAdapter.createFromResource(getActivity(),
                R.array.attendance_Class, android.R.layout.simple_list_item_1);

        adp_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp_date);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                str = spinner.getSelectedItem().toString();
                //Toast.makeText(getActivity(), str_section, Toast.LENGTH_SHORT).show();

                switch(str)
                {
                    case "NUR" : str="Nursery";
                        break;
                    case "PREP" : str="Prep";
                        break;
                    case "I" : str="1";
                        break;
                    case "II" : str="2";
                        break;
                    case "III" : str="3";
                        break;
                    case "IV" : str="4";
                        break;
                    case "V" : str="5";
                        break;
                    case "VI" : str="6";
                        break;
                    case "VII" : str="7";
                        break;
                    case "VIII" : str="8";
                        break;
                    case "IX" : str="9";
                        break;
                    case "X" : str="10";
                        break;
                    case "XI" : str="11";
                        break;
                    case "XII" : str="12";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<CharSequence> adp_month = ArrayAdapter.createFromResource(getActivity(),
                R.array.attendance_Month, android.R.layout.simple_list_item_1);

        adp_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_m.setAdapter(adp_month);

        spinner_m.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String str1 = spinner_m.getSelectedItem().toString();
                //Toast.makeText(getActivity(), str_section, Toast.LENGTH_SHORT).show();

                url="http://app.dpsdhanbad.org/question.php?c="+str+"&m="+str1;//DO

                new JSONAsyncTask().execute(""+url);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        listView = (ListView) view.findViewById(R.id.listView);

        return view;
    }
    class JSONAsyncTask extends AsyncTask<String, Void, String> {

        String[] subject, fileName;

        JSONArray jarray;


        String str;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(""+url);

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                str = EntityUtils.toString(httpResponse.getEntity());

                if(str.contains("null"))
                {
                    return str;
                }

                JSONObject jsono = new JSONObject(str);
                jarray = jsono.getJSONArray("question");
                subject = new String[jarray.length()];
                fileName = new String[jarray.length()];

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject o = jarray.getJSONObject(i);
                    subject[i] = o.getString("Subject");
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
            if(result.contains("null"))
            {
                listView.setAdapter(null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("SORRY");
                builder.setMessage("No Records....");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        dialog.cancel();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else
            {
                CustomAdapter adapter = new CustomAdapter(subject,fileName);
                listView.setAdapter(adapter);
            }

        }
    }

    class CustomAdapter extends BaseAdapter {

        String[] subject, fileName;

        public CustomAdapter(String[] subject, String[] fileName) {
            this.subject = subject;
            this.fileName = fileName;
        }

        @Override
        public int getCount() {
            return subject.length;
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

            txtexam.setText("Subject : " + subject[i]);
            txtfile.setText("View/Download");

            txtfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://www.dpsdhanbad.org/ModelQuestions/"+fileName[i];
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            return (row);
        }

    }
}
