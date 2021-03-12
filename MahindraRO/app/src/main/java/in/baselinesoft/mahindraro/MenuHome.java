package in.baselinesoft.mahindraro;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** Created by Pratik on 11/03/2018.*/

public class MenuHome  extends Fragment implements RecyclerViewClick
{
    private static final String URL_PRODUCTS = "http://www.redo-it.com/rollout/allmeet.php";
    List<Meeting> meetingList;
    RecyclerView recyclerView;
    FragmentManager fm;
    TextView txt;
    Toolbar toolbar;
    ProgressDialog progress;
    FragmentTransaction fx;
    public  static String vloc,vstart,vend,vtone,vttwo,vprocess,vstatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_menu_home, container, false);

        recyclerView = v.findViewById(R.id.recylcerView);
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        txt = (TextView)toolbar.findViewById(R.id.head);
        txt.setText("");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        meetingList = new ArrayList<>();

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading..");
        progress.setTitle("Please wait..");
        loadmeet();

        return v;

    }
//created by pratik mehkarkar
    private void loadmeet()
    {
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                meetingList.add(new Meeting(
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
                            MeetingAdapter adapter = new MeetingAdapter(meetingList,MenuHome.this);
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
                        progress.dismiss();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public void getData(String aaloc, String aastart, String aaend, String aaone, String aatwo, String aaprocess, String aastatus)
    {
        vloc = aaloc;
        vstart = aastart;
        vend = aaend;
        vtone = aaone;
        vttwo = aatwo;
        vprocess = aaprocess;
        vstatus = aastatus;

        fm=getFragmentManager();
        fx=fm.beginTransaction();
        fx.replace(R.id.frame,new MeetingDetail(),"meetdetail");
        fx.commit();

    }
}
