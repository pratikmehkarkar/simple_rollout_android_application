package in.baselinesoft.mahindraro;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Pratik on 23/03/2018.
 */

public class AboutUs extends Fragment
{

    WebView webview;
    String text;
    TextView txt;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_about,container,false);
        webview = (WebView)v.findViewById(R.id.textContent);
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("About us");
        text = "<html><head>";
        text = "<style> .footer{position: fixed; left:0; bottom: 0; width: 100%; color: black; text-align: center;}";
        text = "</head><body>";
        text+="<center><h2>M-RO </h2><h3><i>Complete Roll-out</i></h3></center><br>";
        text+="<center>Version 1.0(beta)</center><br>";
        text+="<hr>";
        text+="<p align=\"justify\"><br>";
        text+="MRO is mahindra complete roll-out native android application.It includes Rollout management,offline rollout alert, employee account and admin panel for system management.\n" +
                "\n</p>";
        text+="<br><div class= \"footer\"> Design  and Developed by Avinash & Team<br><center> \t&copy; All rights reserved<center></div> ";
        text+="</body></html>";
        webview.loadData(text,"text/html", "utf-8");
        return v;
    }
}