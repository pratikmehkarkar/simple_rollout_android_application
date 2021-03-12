package in.baselinesoft.mahindraro;

/**
 * Created by Pratik on 05/03/2018.
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import custom_font.MyTextView_Lato_Medium;
import in.baselinesoft.mahindraro.activity.LoginActivity;
import in.baselinesoft.mahindraro.helper.SQLiteHandler;
import in.baselinesoft.mahindraro.helper.SessionManager;


public class Menu2 extends Fragment implements View.OnClickListener {

    MyTextView_Lato_Medium txt1,txt2,txt3,txt4;
    SQLiteHandler db;
    TextView txt;
    Toolbar toolbar;
    ImageView img,img2;
    FragmentManager fm;
    FragmentTransaction fx;
    private SessionManager session;
    //private SQLiteHandler db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_employee_profile, container, false);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("Employee Profile");
        txt1 = (MyTextView_Lato_Medium)v.findViewById(R.id.uname);
        txt2 = (MyTextView_Lato_Medium)v.findViewById(R.id.uemail);
        txt3 = (MyTextView_Lato_Medium)v.findViewById(R.id.umob);
        txt4 = (MyTextView_Lato_Medium)v.findViewById(R.id.uid);


        // SQLite database handler

        db = new SQLiteHandler(getActivity());
        // Check if user is already logged in or not
        /*if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        }*/
        img = (ImageView)v.findViewById(R.id.mymeet);
        img.setOnClickListener(this);
        img2 = (ImageView)v.findViewById(R.id.myman);
        img2.setOnClickListener(this);



        HashMap<String,String> user = db.getUserDetails();

        String nam = user.get("name");
        txt1.setText(nam);
        String mal = user.get("email");
        txt2.setText(mal);
        String mb = user.get("mobile");
        txt3.setText(mb);
        String di = user.get("id");
        txt4.setText(di);


        return v;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");
    }

    @Override
    public void onClick(View v)
    {
     if(img == v)
     {
         fm=getFragmentManager();
         fx=fm.beginTransaction();
         fx.replace(R.id.frame,new Employee_Meetings(),"empmeet");
         fx.commit();

     }
        if(img2 == v)
        {
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new EmployeeMeet(),"empmeet");
            fx.commit();

        }
    }
}
