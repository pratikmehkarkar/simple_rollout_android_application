package in.baselinesoft.mahindraro.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.baselinesoft.mahindraro.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_font.MyEditText;
import in.baselinesoft.mahindraro.MainNavigation;
import in.baselinesoft.mahindraro.R;
import in.baselinesoft.mahindraro.app.AppConfig;
import in.baselinesoft.mahindraro.app.AppController;
import in.baselinesoft.mahindraro.helper.SQLiteHandler;
import in.baselinesoft.mahindraro.helper.SessionManager;

/**
 * Created by Pratik on 14/03/2018.
 */

public class Register_Fragment extends Fragment
{

    private static final String TAG = RegisterEmployee.class.getSimpleName();
    MyEditText edid,edname,edpass,edmail,edmob;
    ImageView submit;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    TextView txt;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_register_employee, container, false);

        edid = (MyEditText)v.findViewById(R.id.empid);
        edname = (MyEditText)v.findViewById(R.id.empname);
        edpass = (MyEditText)v.findViewById(R.id.emppass);
        edmail = (MyEditText)v.findViewById(R.id.empmail);
        edmob = (MyEditText)v.findViewById(R.id.empmobile);
        submit = (ImageView) v.findViewById(R.id.sub);
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("Register Employee");
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String eeid = edid.getText().toString().trim();
                String ename = edname.getText().toString().trim();
                String eemail = edmail.getText().toString().trim();
                String epassword = edpass.getText().toString().trim();
                String emobil = edmob.getText().toString().trim();


                if(eeid.length() == 0)
                {
                    edid.requestFocus();
                    edid.setError("Field cannot be Empty");
                }
                else if(!ename.matches("[a-zA-Z ]+"))
                {
                    edname.requestFocus();
                    edname.setError("Only alphabetical characters are allowed");
                }
                else if(eemail.length() == 0)
                {
                    edmail.requestFocus();
                    edmail.setError("Field cannot be Empty");
                }
                else if(!eemail.matches("[a-zA-Z0-9+._%-+]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "." + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+"))
                {
                    edmail.requestFocus();
                    edmail.setError("Enter Valid Email address");
                }
                else if(emobil.length()==0)
                {
                    edmob.requestFocus();
                    edmob.setError("Field cannot be Empty");
                }
                else if(!emobil.matches("^([+][9][1]|[9][1]|[0]){0,1}([7-9]{1})([0-9]{9})$"))
                {
                    edmob.requestFocus();
                    edmob.setError("Enter valid Mobile Number");
                }
                else if(epassword.length() == 0)
                {
                    edpass.requestFocus();
                    edpass.setError("Field cannot be Empty");
                }
                else if(epassword.length()<8)
                {
                    edpass.requestFocus();
                    edpass.setError("Password is too short");
                }
                else if(epassword.length()>12)
                {
                    edpass.requestFocus();
                    edpass.setError("Password must be 8-12 characters");
                }
                else
                    {
                        registerUser(eeid,ename, eemail, epassword,emobil);
                    }


                /*if (!eeid.isEmpty() && !ename.isEmpty() && !eemail.isEmpty() && !epassword.isEmpty() && !emobil.isEmpty())
                {
                    registerUser(eeid,ename, eemail, epassword,emobil);
                }
                else
                {
                    Toast.makeText(getActivity(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }*/
            }
        });
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        session = new SessionManager(getActivity());
        db = new SQLiteHandler(getActivity());
        // SQLite database handler
        /*db = new SQLiteHandler(getActivity());
        // Check if user is already logged in or not
        if (session.isLoggedIn())
        {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), MainNavigation.class);
            startActivity(intent);
            getActivity().finish();
        }
        */


        return v;

    }
    private void registerUser(final String eeid,final String ename,final String eemail,final String epassword,final String emobil)
    {
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String eid = user.getString("employee_id");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        String mobil = user.getString("mobile");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at,mobil,eid);

                        Toast.makeText(getActivity(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("employee_id", eeid);
                params.put("name", ename);
                params.put("email", eemail);
                params.put("password", epassword);
                params.put("mobile",emobil);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");
    }
}
