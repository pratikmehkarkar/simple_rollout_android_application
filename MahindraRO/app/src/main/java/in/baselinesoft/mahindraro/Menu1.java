package in.baselinesoft.mahindraro;

/**
 * Created by Pratik on 05/03/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import in.baselinesoft.mahindraro.activity.RegisterEmployee;
import in.baselinesoft.mahindraro.activity.Register_Fragment;
import in.baselinesoft.mahindraro.helper.SQLiteHandle;


public class Menu1 extends Fragment implements View.OnClickListener {
    ImageView add,newemp,man;
    FragmentManager fm;
    FragmentTransaction fx;
    TextView txt;
    Toolbar toolbar;
    SQLiteHandle db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_menu1, container, false);
        add = (ImageView) v.findViewById(R.id.addmeeting);
        newemp = (ImageView) v.findViewById(R.id.reg);
        man = (ImageView) v.findViewById(R.id.manage);
        //test = (ImageView)v.findViewById(R.id.verify);
        //test.setOnClickListener(this);
        add.setOnClickListener(this);
        newemp.setOnClickListener(this);
        man.setOnClickListener(this);
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("M-RO Admin");


        return v;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public void onClick(View v)
    {
        if(v == add)
        {
            /*Intent close = new Intent(getActivity(),NewMeeting.class);
            startActivity(close);
            getActivity().finish();*/
            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Meeting_Test(),"addmeet");
            fx.commit();
            /*fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.content_frame,new New_meeting(),"plist").addToBackStack("tag");
            fx.commit();*/
        }
        else if (v == newemp)
        {

            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Register_Fragment(),"reguser");
            fx.commit();
        }
        else if (v == man)
        {

            fm=getFragmentManager();
            fx=fm.beginTransaction();
            fx.replace(R.id.frame,new Meeting_Manage(),"manmeet");
            fx.commit();
        }
        //else if (v == test)
        //{
            //db = new SQLiteHandle(getActivity());
            //HashMap<String,String> admin = db.getadminDetails();
            //String txt = admin.get("aid");
            //Toast.makeText(getActivity(),"able"+txt, Toast.LENGTH_SHORT).show();
        //}
    }
}
