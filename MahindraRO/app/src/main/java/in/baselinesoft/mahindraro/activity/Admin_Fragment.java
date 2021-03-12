package in.baselinesoft.mahindraro.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import custom_font.MyEditText;
import in.baselinesoft.mahindraro.Meeting_Manage;
import in.baselinesoft.mahindraro.Meeting_Test;
import in.baselinesoft.mahindraro.Menu1;
import in.baselinesoft.mahindraro.R;
import in.baselinesoft.mahindraro.helper.SQLiteHandle;
import in.baselinesoft.mahindraro.helper.SessionManage;
import in.baselinesoft.mahindraro.helper.SessionManager;

/**
 * Created by Pratik on 16/03/2018.
 */

public class Admin_Fragment extends Fragment implements View.OnClickListener {

    FragmentManager fm;
    FragmentTransaction fx;
    TextView txt;
    Toolbar toolbar;
    MyEditText edx1,edx2;
    ImageView login;
    SQLiteHandle db;
    SessionManage sessionManage;

    public String one,two;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_admin_login, container, false);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("M-RO Admin Login");


        edx1 = (MyEditText)v.findViewById(R.id.adminid);
        edx2 = (MyEditText)v.findViewById(R.id.adminpass);
        login = (ImageView)v.findViewById(R.id.log);
        login.setOnClickListener(this);


        sessionManage = new SessionManage(getActivity());
        //session = new SessionManager(getApplicationContext());
        if(sessionManage.isLogged())
        {

            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Menu1(),"admin");
            fx.commit();
        }



        return v;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
       // getActivity().setTitle("Home");
    }


    @Override
    public void onClick(View v)
    {
        if(login == v)
        {

            db = new SQLiteHandle(getActivity());
            HashMap<String,String> admin = db.getadminDetails();
            one = admin.get("aid");
            two = admin.get("pass");

            String ab = edx1.getText().toString().trim();
            String cd = edx2.getText().toString().trim();

            if(ab.equals(one) && cd.equals(two))
            {
                sessionManage.setLogin(true);
                fm=getFragmentManager();
                fx=fm.beginTransaction();
                fx.replace(R.id.frame,new Menu1(),"admin");
                fx.commit();
                Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_LONG).show();

            }
            else
             {
                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
             }


        }
    }
}
