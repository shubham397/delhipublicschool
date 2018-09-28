package com.dialursearch.gyanpatra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment {

    Button btnAddAdmin;

    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_home, container, false);

        btnAddAdmin=(Button)view.findViewById(R.id.btn_admin_home_addAdmin);

        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminHomeAddAdminFragment adminHomeAddAdminFragment=new AdminHomeAddAdminFragment();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.home,adminHomeAddAdminFragment,adminHomeAddAdminFragment.getTag()).commit();
            }
        });

        return view;
    }

}
