package in.baselinesoft.mahindraro;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import custom_font.MyTextView_Lato_Bold;
import custom_font.MyTextView_Lato_Light;
import custom_font.MyTextView_Lato_Regular;

/**
 * Created by Pratik on 14/03/2018.
 */

public class MeetingDetail extends Fragment
{
    MyTextView_Lato_Bold tid;
    MyTextView_Lato_Light tstart,tend,tone,ttwo;
    MyTextView_Lato_Regular tprocess,tstatus;
    TextView txt;
    Toolbar toolbar;

    public  String locationv = MenuHome.vloc;
    public String startv = MenuHome.vstart;
    public  String endv = MenuHome.vend;
    public  String tonev = MenuHome.vtone;
    public  String twov = MenuHome.vttwo;
    public  String processv = MenuHome.vprocess;
    public  String statusv = MenuHome.vstatus;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.meeting_details, container, false);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("RollOut");
        tid = (MyTextView_Lato_Bold)v.findViewById(R.id.txtvid);
        tstart = (MyTextView_Lato_Light)v.findViewById(R.id.txtvstart);
        tend = (MyTextView_Lato_Light)v.findViewById(R.id.txtvend);
        tone = (MyTextView_Lato_Light)v.findViewById(R.id.txtvtone);
        ttwo = (MyTextView_Lato_Light)v.findViewById(R.id.txtvttwo);
        tprocess = (MyTextView_Lato_Regular) v.findViewById(R.id.txtvprocess);
        tstatus = (MyTextView_Lato_Regular) v.findViewById(R.id.txtvstatus);


        tid.setText(locationv);
        tstart.setText(startv);
        tend.setText(endv);
        tone.setText(tonev);
        ttwo.setText(twov);
        tprocess.setText(processv);
        tstatus.setText(statusv);



        return v;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setTitle("Dashboard");
        //txt.setText("RollOut");
    }
}
