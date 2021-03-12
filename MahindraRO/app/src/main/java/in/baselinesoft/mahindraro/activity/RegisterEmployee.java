package in.baselinesoft.mahindraro.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class RegisterEmployee extends AppCompatActivity {
    private static final String TAG = RegisterEmployee.class.getSimpleName();
    MyEditText edid,edname,edpass,edmail,edmob;
    ImageView submit;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        edid = (MyEditText)findViewById(R.id.empid);
        edname = (MyEditText)findViewById(R.id.empname);
        edpass = (MyEditText)findViewById(R.id.emppass);
        edmail = (MyEditText)findViewById(R.id.empmail);
        edmob = (MyEditText)findViewById(R.id.empmobile);
        submit = (ImageView) findViewById(R.id.sub);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eeid = edid.getText().toString().trim();
                String ename = edname.getText().toString().trim();
                String eemail = edmail.getText().toString().trim();
                String epassword = edpass.getText().toString().trim();
                String emobil = edmob.getText().toString().trim();


                if (!eeid.isEmpty() && !ename.isEmpty() && !eemail.isEmpty() && !epassword.isEmpty() && !emobil.isEmpty())
                {
                    registerUser(eeid,ename, eemail, epassword,emobil);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn())
        {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterEmployee.this, MainNavigation.class);
            startActivity(intent);
            finish();
        }


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

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
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
}
