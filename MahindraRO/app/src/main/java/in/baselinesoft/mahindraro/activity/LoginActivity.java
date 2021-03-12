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
import in.baselinesoft.mahindraro.SplashScreen;
import in.baselinesoft.mahindraro.app.AppConfig;
import in.baselinesoft.mahindraro.app.AppController;
import in.baselinesoft.mahindraro.helper.SQLiteHandler;
import in.baselinesoft.mahindraro.helper.SessionManager;


public class LoginActivity extends AppCompatActivity
{
    MyEditText emp_mail,emp_pass;
    private static final String TAG = LoginActivity.class.getSimpleName();
    ImageView next;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next = (ImageView)findViewById(R.id.next);
        emp_mail = (MyEditText) findViewById(R.id.email);
        emp_pass = (MyEditText)findViewById(R.id.password);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn())
        {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
            startActivity(intent);
            finish();
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = emp_mail.getText().toString().trim();
                String password = emp_pass.getText().toString().trim();
                if(!email.isEmpty() && !password.isEmpty())
                {
                    checklogin(email,password);
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Please enter the credentials!", Toast.LENGTH_LONG)
                                .show();
                    }
                //Intent it = new Intent(LoginActivity.this, Home.class);
                //startActivity(it);
            }
        });
    }

    private void checklogin(final String email,final String password)
    {
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error)
                    {
                        session.setLogin(true);
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String eid = user.getString("employee_id");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        String mobil = user.getString("mobile");

                        db.addUser(name, email, uid, created_at,mobil,eid);

                        Intent intent = new Intent(LoginActivity.this, MainNavigation.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
