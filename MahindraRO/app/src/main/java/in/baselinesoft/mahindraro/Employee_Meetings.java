package in.baselinesoft.mahindraro;

/**
 * Created by Pratik on 05/03/2018.
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import custom_font.MyTextView_Lato_Medium;
import in.baselinesoft.mahindraro.helper.SQLiteHandler;


public class Employee_Meetings extends Fragment
{

    private static final String URL_PRODUCTS = "http://www.redo-it.com/rollout/empmeet.php";
    List<Employee> employeeList;
    RecyclerView recyclerView;
    FragmentManager fm;
    FragmentTransaction fx;
    SQLiteHandler db;
    TextView txt;
    String uname;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_employee_meetings, container, false);

        recyclerView = v.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        employeeList = new ArrayList<>();

        db = new SQLiteHandler(getActivity());
        HashMap<String,String> user = db.getUserDetails();
        uname = user.get("name");
        loadmeet();

        return v;

    }

    private void loadmeet()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                employeeList.add(new Employee(
                                        product.getString("meeting_id"),
                                        product.getString("tlocation"),
                                        product.getString("start"),
                                        product.getString("end"),
                                        product.getString("trainerone"),
                                        product.getString("trainertwo"),
                                        product.getString("process"),
                                        product.getString("status")
                                ));
                            }
                            EmployeeAdapter adapter = new EmployeeAdapter(employeeList,Employee_Meetings.this);
                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {



                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map = new HashMap<String, String>();
                map.put("name",uname);
                return map;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");
    }
}
