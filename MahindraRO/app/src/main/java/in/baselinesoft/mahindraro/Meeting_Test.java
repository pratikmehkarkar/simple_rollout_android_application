package in.baselinesoft.mahindraro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import custom_font.MyEditText;
import in.baselinesoft.mahindraro.activity.Admin_Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pratik on 14/03/2018.
 */

public class Meeting_Test extends Fragment implements  View.OnClickListener {

    char chars[];
    String randoms;
    private TextView mid,tst,tst2;
    private int year, month, day;
    ImageView start,end,submit;
    MyEditText location,process,dtone,dttwo;
    private String UPLOAD_URL = "http://www.redo-it.com/rollout/meetingadd.php";
    public static final String DATA_URL = "http://www.redo-it.com/rollout/employee.php";
    private String KEY_ENAME = "name";
    private String KEY_EID = "employee_id";
    public static final String JSON_ARRAY = "result";
    private String KEY_TLOC = "tlocation";
    private String KEY_TSTART = "start";
    private String KEY_TEND = "end";
    private String KEY_TPROCESS = "process";
    private String KEY_STATUS = "status";
    private String KEY_TID = "meeting_id";
    private String KEY_TEMPONE = "trainerone";
    private String KEY_TEMPTWO = "trainertwo";
    private Spinner spinner,spin;
    FragmentManager fm;
    FragmentTransaction fx;
    TextView txt;
    Toolbar toolbar;
    //String status;
    private ArrayList<String> students,stud;

    private JSONArray result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_new_meeting, container, false);

        dtone = (MyEditText) v.findViewById(R.id.txstart);
        //dttwo = (MyEditText) findViewById(R.id.test);
        tst2 = (TextView) v.findViewById(R.id.trone);
        tst = (TextView) v.findViewById(R.id.trtwo);
        mid = (TextView) v.findViewById(R.id.trid);
        start = (ImageView) v.findViewById(R.id.mstart);
        dttwo = (MyEditText) v.findViewById(R.id.txend);
        end = (ImageView) v.findViewById(R.id.mend);
        submit = (ImageView) v.findViewById(R.id.sub);
        location = (MyEditText) v.findViewById(R.id.mloc);
        process = (MyEditText) v.findViewById(R.id.process);



        students = new ArrayList<String>();
        stud = new ArrayList<String>();

        spinner = (Spinner)v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                tst.setText(getName(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                tst.setText("");
            }
        });

        spin = (Spinner)v.findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                tst2.setText(getName(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                tst2.setText("");
            }
        });

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("Create New Roll-Out ");


        chars = "0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0;i<4;i++)
        {
            char c1 = chars[random.nextInt(chars.length)];
            stringBuilder.append(c1);
        }
        randoms = stringBuilder.toString();
        mid.setText(randoms+" ");

        start.setOnClickListener(this);
        end.setOnClickListener(this);
        submit.setOnClickListener(this);
        getData();
        return v;

    }
    private void getData()
    {

        StringRequest stringRequest = new StringRequest(DATA_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                JSONObject j = null;
                try
                {
                    j = new JSONObject(response);
                    result = j.getJSONArray(JSON_ARRAY);
                    getEmployee(result);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void getEmployee(JSONArray j)
    {

        for (int i=0;i<j.length();i++)
        {
            try
            {
                JSONObject json = j.getJSONObject(i);
                students.add(json.getString(KEY_ENAME));
                stud.add(json.getString(KEY_ENAME));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,students));
        spin.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,stud));

    }
    private  String getName(int position)
    {
        String name = "";
        try
        {
            JSONObject json = result.getJSONObject(position);
            name = json.getString(KEY_ENAME);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return name;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setTitle("Dashboard");
    }




    private void uploadmeet()
    {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "uploading...", "Please wait", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        fm=getFragmentManager();
                        fx=fm.beginTransaction();
                        fx.replace(R.id.frame,new Admin_Fragment(),"adminfrag");
                        fx.commit();
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                String trnm = location.getText().toString().trim();
                String trprocess = process.getText().toString().trim();
                String tstart = dtone.getText().toString().trim();
                String tend = dttwo.getText().toString().trim();
                String tid = mid.getText().toString().trim();
                String one = tst.getText().toString().trim();
                String two = tst2.getText().toString().trim();


                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_TLOC,trnm);
                params.put(KEY_TSTART,tstart);
                params.put(KEY_TEND,tend);
                params.put(KEY_TPROCESS,trprocess);
                params.put(KEY_TID,tid);
                params.put(KEY_TEMPONE,one);
                params.put(KEY_TEMPTWO,two);
                //params.put(KEY_STATUS,status);
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        tst.setText(getName(position));
        tst2.setText(getName(position));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

        tst.setText("");
        tst2.setText("");
    }*/

    @Override
    public void onClick(View v)
    {
        if(v == submit)
        {
            uploadmeet();
        }
        else if(v == start)
        {
            showDatePicker();
        }
        else if(v == end)
        {
            showDatePicker2();
        }


    }

    private void showDatePicker()
    {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dtone.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(year));
        }
    };

    private void showDatePicker2()
    {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate2);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate2 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dttwo.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };
}
