package in.baselinesoft.mahindraro;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.Calendar;
import java.util.HashMap;

import custom_font.MyTextView_Lato_Light;
import custom_font.MyTextView_Lato_Medium;
import in.baselinesoft.mahindraro.activity.Admin_Fragment;
import in.baselinesoft.mahindraro.activity.RegisterEmployee;
import in.baselinesoft.mahindraro.activity.Register_Fragment;
import in.baselinesoft.mahindraro.helper.SQLiteHandler;

/**
 * Created by Pratik on 12/03/2018.
 */

public class MainNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    NavigationView navigationView;
    MyTextView_Lato_Light cust,adm,emp,abt;
    FragmentManager fm;
    FragmentTransaction fx;
    FrameLayout frame;
    Fragment current =null;
    SQLiteHandler db;
    MyTextView_Lato_Medium name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        cust = (MyTextView_Lato_Light)findViewById(R.id.act);
        cust.setOnClickListener(this);
        adm = (MyTextView_Lato_Light)findViewById(R.id.adminp);
        adm.setOnClickListener(this);
        emp = (MyTextView_Lato_Light)findViewById(R.id.emppro);
        emp.setOnClickListener(this);
        abt = (MyTextView_Lato_Light)findViewById(R.id.about);
        abt.setOnClickListener(this);
        frame = (FrameLayout)findViewById(R.id.frame);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view1);
        name = (MyTextView_Lato_Medium)findViewById(R.id.ename);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String,String> user = db.getUserDetails();
        String uuname = user.get("name");
        name.setText(uuname);

        Calendar current = Calendar.getInstance();
        Calendar current1 = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,5);
        calendar.set(Calendar.SECOND,10);
        long cal = calendar.getTimeInMillis();

        Intent intent = new Intent(getApplicationContext(),Notification_receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal,AlarmManager.INTERVAL_DAY,pendingIntent);

        long curr1 = current1.getTimeInMillis();
        if(cal >= curr1)
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal,AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else
        {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            cal = calendar.getTimeInMillis();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal,AlarmManager.INTERVAL_DAY,pendingIntent);
        }

        /* notification two */
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY,17);
        calendar1.set(Calendar.MINUTE,5);
        calendar1.set(Calendar.SECOND,10);
        long cal1 = calendar1.getTimeInMillis();

        Intent intent2 = new Intent(getApplicationContext(),Notification_receivertwo.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),101,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager1 = (AlarmManager)getSystemService(ALARM_SERVICE);

        long curr = current.getTimeInMillis();
        if(cal1 >= curr)
        {
            alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,cal1,AlarmManager.INTERVAL_DAY,pendingIntent1);
        }
        else
        {
            calendar1.add(Calendar.DAY_OF_MONTH,1);
            cal1 = calendar1.getTimeInMillis();
            alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,cal1,AlarmManager.INTERVAL_DAY,pendingIntent1);
        }
        /*Intent intent1 = new Intent(MainNavigation.this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainNavigation.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainNavigation.this.getSystemService(MainNavigation.this.ALARM_SERVICE);

        Calendar intended = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        intended.set(Calendar.HOUR_OF_DAY, 16);
        intended.set(Calendar.MINUTE, 25);
        intended.set(Calendar.SECOND, 0);



        long intendedTime = intended.getTimeInMillis();
        long currentTime = current.getTimeInMillis();

        if(intendedTime >= currentTime)
        {

            if(Build.VERSION.SDK_INT >= 23)
            {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,intended.getTimeInMillis(),pendingIntent);
            }
            else if(Build.VERSION.SDK_INT >= 19)
            {
                am.setExact(AlarmManager.RTC_WAKEUP,intended.getTimeInMillis(),pendingIntent);
            }
            else if(Build.VERSION.SDK_INT >= 16)
            {
                am.setRepeating(AlarmManager.RTC_WAKEUP, intended.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
        else {
            // set from next day
            // you might consider using calendar.add() for adding one day to the current day
            intended.add(Calendar.DAY_OF_MONTH, 1);
            //intendedTime = intended.getTimeInMillis();

            //alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= 23) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, intended.getTimeInMillis(), pendingIntent);

            } else if (Build.VERSION.SDK_INT >= 19) {
                am.setExact(AlarmManager.RTC_WAKEUP, intended.getTimeInMillis(), pendingIntent);

            } else if (Build.VERSION.SDK_INT >= 16) {
                am.setRepeating(AlarmManager.RTC_WAKEUP, intended.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            }
        }*/




        fm=getFragmentManager();
        fx=fm.beginTransaction();
        fx.replace(R.id.frame,new MenuHome(),"home");
        fx.commit();

        setToolbar();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView)
            {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        invalidateOptionsMenu();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed()
    {
        fm = getFragmentManager();
        current= fm.findFragmentById(R.id.frame);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(Gravity.LEFT); //OPEN Nav Drawer!
        }
        else if(current.getTag().equals("home"))
        {

            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            MainNavigation.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(current.getTag().equals("admin"))
        {

            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new MenuHome(),"home");
            fx.commit();
        }
        else if(current.getTag().equals("addmeet"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Menu1(),"admin");
            fx.commit();
        }

        else if(current.getTag().equals("reguser"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Menu1(),"admin");
            fx.commit();
        }
        else if(current.getTag().equals("manmeet"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Menu1(),"admin");
            fx.commit();
        }
        else if(current.getTag().equals("meetdetail"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new MenuHome(),"home");
            fx.commit();
        }
        else if(current.getTag().equals("empprofile"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new MenuHome(),"home");
            fx.commit();
        }
        else if(current.getTag().equals("about"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new MenuHome(),"home");
            fx.commit();
        }
        else if(current.getTag().equals("empmeet"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Menu2(),"empprofile");
            fx.commit();
        }
        else if(current.getTag().equals("adminfrag"))
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new MenuHome(),"home");
            fx.commit();
        }
        else
            {
            finish();
        }

    }

    private void setToolbar()
    {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(false);

        actionBar.setDisplayShowTitleEnabled(false);
            //actionBar.setTitle("");

        toolbar.findViewById(R.id.navigation_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Click", "keryu");

                if (drawer.isDrawerOpen(navigationView)) {
                    drawer.closeDrawer(navigationView);
                } else {
                    drawer.openDrawer(navigationView);
                }
            }
        });

        //TextView txt = (TextView)findViewById(R.id.head);
        //txt.setText("Demo");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v)
    {

        if(v == cust)
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new MenuHome(),"home");
            fx.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        if(v == adm)
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Admin_Fragment(),"adminfrag");
            fx.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        if(v == emp)
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Menu2(),"empprofile");
            fx.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        if(v == abt)
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new AboutUs(),"about");
            fx.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
