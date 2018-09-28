package com.dialursearch.gyanpatra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref1 = this.getSharedPreferences("studentLogin", 0); // 0 - for private mode
        final SharedPreferences.Editor editor1 = pref1.edit();

        SharedPreferences pref2 = this.getSharedPreferences("teacherLogin", 0); // 0 - for private mode
        final SharedPreferences.Editor editor2 = pref2.edit();

        editor1.putString("login","0");
        editor2.putString("login","0");

        editor1.commit();
        editor2.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        if(connected) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.home, homeFragment, homeFragment.getTag()).commit();
        }
        else
        {
            Intent intent=new Intent(MainActivity.this,CheckInternetActivity.class);
            startActivity(intent);
            finish();
        }

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences pref = MainActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        clearApplicationData();

    }

    public void setValue(String n,String c,String s,String r)
    {
        //Toast.makeText(MainActivity.this,"Shubham",Toast.LENGTH_SHORT).show();
        //txtName.setText(n);
        //txtClass.setText(c);
        //txtSec.setText(s);
        //txtRoll.setText(r);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.tab_home)
            {
                HomeFragment homeFragment=new HomeFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.home,homeFragment,homeFragment.getTag()).commit();
                return true;
            }
            else if(item.getItemId()==R.id.tab_career)
            {
                editor.putString("headText","Career");
                editor.putString("url","http://dpsdhanbad.org/career.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                return true;
            }
            else if(item.getItemId()==R.id.tab_alumni)
            {
                editor.putString("headText","Alumni");
                editor.putString("url","http://dpsdhanbad.org/alumniportal.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                return true;
            }
            else if(item.getItemId()==R.id.tab_gallery)
            {
                editor.putString("headText","Gallery");
                editor.putString("url","http://dpsdhanbad.org/gallerys.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                return true;
            }
            else if(item.getItemId()==R.id.tab_verification)
            {
                editor.putString("headText","TC Verification");
                editor.putString("url","http://dpsdhanbad.org/tclogin.aspx");
                editor.commit();
                WebFragment webFragment=new WebFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
                return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"SETTING",Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.action_admin)
        {
            AdminLoginFragment adminLoginFragment=new AdminLoginFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home,adminLoginFragment,adminLoginFragment.getTag()).addToBackStack("").commit();
        }
        else if(id == R.id.action_contact)
        {
            editor.putString("headText","Contact Us");
            editor.putString("url","http://dpsdhanbad.org/contact.aspx");
            editor.commit();
            WebFragment webFragment=new WebFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home,webFragment,webFragment.getTag()).addToBackStack("").commit();
            return true;
        }
        else if(id == R.id.action_exit)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
                }
            });
            builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.home, homeFragment, homeFragment.getTag()).commit();
        } else if (id == R.id.nav_studentCorner) {
            editor.putString("headText", "Student Corner");
            editor.putString("url", "http://dpsdhanbad.org/StudentCorner/default2.aspx");
            editor.commit();
            WebFragment webFragment = new WebFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home, webFragment, webFragment.getTag()).addToBackStack("").commit();

        } else if (id == R.id.nav_teacherCorner) {
            editor.putString("headText", "Teacher Corner");
            editor.putString("url", "http://dpsdhanbad.org/StaffCorner/Login.aspx");
            editor.commit();
            WebFragment webFragment = new WebFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home, webFragment, webFragment.getTag()).addToBackStack("").commit();

        } else if (id == R.id.nav_onlineStudyCenter) {
            editor.putString("headText", "Gyan Patra");
            editor.putString("url", "http://www.gyanpatra.com/");
            editor.commit();
            WebFragment webFragment = new WebFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home, webFragment, webFragment.getTag()).addToBackStack("").commit();

        } else if (id == R.id.nav_onlineAdmission) {
            editor.putString("headText", "Admission");
            editor.putString("url", "http://adm.dpsdhanbad.org/Default.aspx");
            editor.commit();
            WebFragment webFragment = new WebFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home, webFragment, webFragment.getTag()).addToBackStack("").commit();

        } else if (id == R.id.nav_onlineFee) {
            editor.putString("headText", "Fee");
            editor.putString("url", "http://dpsdhanbad.org/StudentCorner/feereceipt.aspx");
            editor.commit();
            WebFragment webFragment = new WebFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.home, webFragment, webFragment.getTag()).addToBackStack("").commit();

        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
