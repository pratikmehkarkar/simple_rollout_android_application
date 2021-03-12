package in.baselinesoft.mahindraro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NewMeeting extends MainNavigation implements AdapterView.OnItemSelectedListener {

    private DatePicker datePicker;
    char chars[];
    String randoms;
    private Calendar calendar, calendarend;
    private TextView mid,tst,tst2;
    private int year, month, day;
    private int yearend, monthend, dayend;
    static final int DATE_DIALOG_ID = 0;
    private Calendar activeDate;
    //Button start, end,submit;
    ImageView start,end,submit;
    int DATE_PICKER_TO = 0;
    int DATE_PICKER_FROM = 1;
    //EditText tloc, tprocess;
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
    private String KEY_TID = "meeting_id";
    private String KEY_TEMPONE = "trainerone";
    private String KEY_TEMPTWO = "trainertwo";
    private Spinner spinner,spin;
    private ArrayList<String> students;
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_new_meeting,frame);
        //setContentView(R.layout.activity_new_meeting);

        dtone = (MyEditText) findViewById(R.id.txstart);
        //dttwo = (MyEditText) findViewById(R.id.test);
        tst2 = (TextView) findViewById(R.id.trone);
        tst = (TextView) findViewById(R.id.trtwo);
        mid = (TextView) findViewById(R.id.trid);
        start = (ImageView) findViewById(R.id.mstart);
        dttwo = (MyEditText) findViewById(R.id.txend);
        end = (ImageView) findViewById(R.id.mend);
        location = (MyEditText) findViewById(R.id.mloc);
        process = (MyEditText) findViewById(R.id.process);
        students = new ArrayList<String>();
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spin = (Spinner)findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        calendar = Calendar.getInstance();

        getData();

        chars = "0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0;i<8;i++)
        {
            char c1 = chars[random.nextInt(chars.length)];
            stringBuilder.append(c1);
        }
        randoms = stringBuilder.toString();
        mid.setText(randoms+" ");

        //dateView1 = (TextView) findViewById(R.id.textView2);
        //end = (Button) findViewById(R.id.button);
        //calendarend = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        yearend = calendar.get(Calendar.YEAR);
        monthend = calendar.get(Calendar.MONTH);
        dayend = calendar.get(Calendar.DAY_OF_MONTH);
        showDateend(yearend, monthend + 1, dayend);

        submit = (ImageView) findViewById(R.id.sub);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadmeet();
            }
        });


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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

        spinner.setAdapter(new ArrayAdapter<String>(NewMeeting.this, android.R.layout.simple_spinner_dropdown_item,students));
        spin.setAdapter(new ArrayAdapter<String>(NewMeeting.this, android.R.layout.simple_spinner_dropdown_item,students));

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


    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };
    private DatePickerDialog.OnDateSetListener myendDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDateend(arg1, arg2 + 1, arg3);
                }
            };

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("deprecation")
    public void setDateend(View view) {
        showDialog(111);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if (id == 111) {
            return new DatePickerDialog(this, myendDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dtone.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    private void showDateend(int year, int month, int day) {
        dttwo.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    private void uploadmeet() {
        final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "uploading...", "Please wait", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getApplicationContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                params.put(KEY_TLOC, trnm);
                params.put(KEY_TSTART,tstart);
                params.put(KEY_TEND,tend);
                params.put(KEY_TPROCESS,trprocess);
                params.put(KEY_TID,tid);
                params.put(KEY_TEMPONE,one);
                params.put(KEY_TEMPTWO,two);

                //params.put(KEY_PRICE,pprice);
                //params.put(KEY_DESC,pdesc);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
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
    }
}