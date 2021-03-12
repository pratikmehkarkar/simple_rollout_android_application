package in.baselinesoft.mahindraro;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_font.MyEditText;
import custom_font.MyTextView_Lato_Light;

/**
 * Created by Pratik on 15/03/2018.
 */

public class Meeting_Manage extends Fragment implements View.OnClickListener {

    MyEditText ed1;
    MyTextView_Lato_Light txt1,txt2,txt3,txt4,txt5,txt6;
    Spinner spn;
    ImageButton upd;
    ProgressDialog progress;
    TextView txt;
    Toolbar toolbar;
    String[] stat = {"In-Progress","Training Over","Completed"};
    ArrayAdapter<String> adapter;
    String status;
    private String PRODUCT_DATA = "http://www.redo-it.com/rollout/mfetch.php";
    private String DATA_UPDATE = "http://www.redo-it.com/rollout/mupdate.php";
    ImageView submit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_meeting_manage, container, false);

        txt1 = (MyTextView_Lato_Light)v.findViewById(R.id.mloc);
        txt2 = (MyTextView_Lato_Light)v.findViewById(R.id.process);
        txt3 = (MyTextView_Lato_Light)v.findViewById(R.id.txstart);
        txt4 = (MyTextView_Lato_Light)v.findViewById(R.id.txend);
        txt5 = (MyTextView_Lato_Light)v.findViewById(R.id.txone);
        txt6 = (MyTextView_Lato_Light) v.findViewById(R.id.txtwo);
        ed1 = (MyEditText)v.findViewById(R.id.id);
        upd = (ImageButton)v.findViewById(R.id.updt);
        upd.setOnClickListener(this);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("Manage Roll-out");
        submit = (ImageView)v.findViewById(R.id.sub);
        submit.setOnClickListener(this);

        spn = (Spinner)v.findViewById(R.id.spinner2);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item ,stat);
        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading..");
        progress.setTitle("Please wait..");




        return v;

    }

    private void getData()
    {
        progress.show();
        StringRequest postrequest  = new StringRequest(Request.Method.POST, PRODUCT_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject j2 = new JSONObject(response);
                    JSONArray jsonArray = j2.getJSONArray("result");
                    if (jsonArray.length() == 0)
                    {
                        Toast.makeText(getActivity(), "Try Again.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject j1 = jsonArray.getJSONObject(i);
                            String m_loc = j1.getString("tlocation");
                            String m_start = j1.getString("start");
                            String m_end = j1.getString("end");
                            String m_tone = j1.getString("trainerone");
                            String m_ttwo = j1.getString("trainertwo");
                            String m_process = j1.getString("process");


                            //pname.setText(p_nam);
                            //pprice.setText(p_price);
                            //pquant.setText(p_quantity);

                            txt1.setText(m_loc);
                            txt2.setText(m_process);
                            txt3.setText(m_start);
                            txt4.setText(m_end);
                            txt5.setText(m_tone);
                            txt6.setText(m_ttwo);
                        }
                    }

                } catch (Exception ee)
                {
                    Toast.makeText(getActivity(), "Try again..", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.dismiss();
                Log.e("K1", error.toString());
                Toast.makeText(getActivity(),"Unable to Connect..", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map = new HashMap<String, String>();
                String pid = ed1.getText().toString().trim();
                map.put("meeting_id",""+pid);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(postrequest);

    }
    private void UpdateData()
    {
        progress.show();
        StringRequest postrequest  = new StringRequest(Request.Method.POST, DATA_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progress.dismiss();
                try
                {
                    JSONObject j2 = new JSONObject(response);

                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                }
                catch (Exception ee)
                {
                    Toast.makeText(getActivity(), "Try again..", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progress.dismiss();
                Log.e("K1", error.toString());
                Toast.makeText(getActivity(),"Unable to Connect..",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map = new HashMap<String, String>();
                String pmid = ed1.getText().toString().trim();
                //String ppr = pprice.getText().toString().trim();
                //String qty = pquant.getText().toString().trim();
                map.put("meeting_id",pmid);
                //map.put("price",ppr);
                map.put("status",status);
                //map.put("quantity",qty);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(postrequest);

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
        if(submit == v)
        {
            getData();
        }
        else if(upd == v)
        {
            UpdateData();
        }
    }
}
