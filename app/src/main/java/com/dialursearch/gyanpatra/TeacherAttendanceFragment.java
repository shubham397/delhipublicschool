package com.dialursearch.gyanpatra;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherAttendanceFragment extends Fragment {

    Spinner spinner_class,spinner_section,spinner_date,spinner_month,spinner_year;

    SharedPreferences.Editor editor;

    Button btn,btn_report;

    String str_date,str_month,str_year;

    TextView txtbtn;

    String str_teacherCode,str_teacherName;


    public TeacherAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_attendance, container, false);

        spinner_class=(Spinner)view.findViewById(R.id.spinner_class);
        spinner_section=(Spinner)view.findViewById(R.id.spinner_section);

        SharedPreferences pref = getActivity().getSharedPreferences("Attendance", 0); // 0 - for private mode
        editor = pref.edit();

        SharedPreferences pref2 = getActivity().getSharedPreferences("teacherLogin", 0); // 0 - for private mode
        final SharedPreferences.Editor editor2 = pref2.edit();

        str_teacherCode = pref2.getString("ncode", "");
        str_teacherName = pref2.getString("name", "");

        ArrayAdapter<CharSequence> adp_class = ArrayAdapter.createFromResource(getActivity(),
                R.array.attendance_Class, android.R.layout.simple_list_item_1);

        adp_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_class.setAdapter(adp_class);

        spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String str_class = spinner_class.getSelectedItem().toString();
                //Toast.makeText(getActivity(), str_class, Toast.LENGTH_SHORT).show();
                editor.putString("Class",str_class);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<CharSequence> adp_section = ArrayAdapter.createFromResource(getActivity(),
                R.array.attendance_Section,android.R.layout.simple_list_item_1);

        adp_section.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(adp_section);

        spinner_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String str_section = spinner_section.getSelectedItem().toString();
                //Toast.makeText(getActivity(), str_section, Toast.LENGTH_SHORT).show();
                editor.putString("Section",str_section);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        btn=(Button)view.findViewById(R.id.btn_submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(getActivity(),AttendanceActivity.class);
                //startActivity(intent);
                TeacherAttendanceMainFragment teacherAttendanceMainFragment=new TeacherAttendanceMainFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,teacherAttendanceMainFragment,teacherAttendanceMainFragment.getTag()).addToBackStack("").commit();
            }
        });

        btn_report=(Button)view.findViewById(R.id.btn_report);

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.dialog_report, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                spinner_date=(Spinner)promptsView.findViewById(R.id.spinner_date);
                spinner_month=(Spinner)promptsView.findViewById(R.id.spinner_month);
                spinner_year=(Spinner)promptsView.findViewById(R.id.spinner_year);

                ArrayAdapter<CharSequence> adp_date = ArrayAdapter.createFromResource(getActivity(),
                        R.array.attendance_Date, android.R.layout.simple_list_item_1);

                adp_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_date.setAdapter(adp_date);

                ArrayAdapter<CharSequence> adp_month = ArrayAdapter.createFromResource(getActivity(),
                        R.array.attendance_Month, android.R.layout.simple_list_item_1);

                adp_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_month.setAdapter(adp_month);

                ArrayAdapter<CharSequence> adp_year = ArrayAdapter.createFromResource(getActivity(),
                        R.array.attendance_year, android.R.layout.simple_list_item_1);

                adp_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_year.setAdapter(adp_year);

                spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        // TODO Auto-generated method stub
                        str_date = spinner_date.getSelectedItem().toString();
                        //Toast.makeText(getActivity(), str_section, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        // TODO Auto-generated method stub
                        str_month = spinner_month.getSelectedItem().toString();
                        //Toast.makeText(getActivity(), str_section, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        // TODO Auto-generated method stub
                        str_year = spinner_year.getSelectedItem().toString();
                        //Toast.makeText(getActivity(), str_section, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Day Wise Report",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        editor.putString("Date",str_date);
                                        editor.commit();
                                        editor.putString("Month",str_month);
                                        editor.commit();
                                        editor.putString("Year",str_year);
                                        editor.commit();
                                        TeacherAttendanceReportFragment teacherAttendanceReportFragment=new TeacherAttendanceReportFragment();
                                        FragmentManager manager = getActivity().getSupportFragmentManager();
                                        manager.beginTransaction().add(R.id.home, teacherAttendanceReportFragment, teacherAttendanceReportFragment.getTag()).addToBackStack("").commit();
                                    }
                                })
                        .setNegativeButton("Month Wise Report",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        editor.putString("Month",str_month);
                                        editor.commit();
                                        TeacherAttendanceReportMonthFragment teacherAttendanceReportFragment=new TeacherAttendanceReportMonthFragment();
                                        FragmentManager manager = getActivity().getSupportFragmentManager();
                                        manager.beginTransaction().add(R.id.home, teacherAttendanceReportFragment, teacherAttendanceReportFragment.getTag()).addToBackStack("").commit();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        if(str_teacherCode.equals("ERP")) {
            txtbtn = (TextView) view.findViewById(R.id.txt_attendance);
            txtbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("Class", "ALL");
                    editor.putString("Section", "ALL");
                    editor.commit();
                    TeacherAttendance_erpFragment teacherAttendance_erpFragment = new TeacherAttendance_erpFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.home, teacherAttendance_erpFragment, teacherAttendance_erpFragment.getTag()).addToBackStack("").commit();
                }
            });
        }

        return view;


    }
}
