package in.baselinesoft.mahindraro;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import custom_font.MyTextView_Lato_Medium;
import custom_font.MyTextView_Lato_Regular;

/**
 * Created by Pratik on 12/03/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>
{
    Employee_Meetings test;
    RecyclerViewClick recyclerViewClick;
    private List<Employee> employeelist;
    String aloc,astart,aend,aone,atwo,aprocess,astatus;

    public EmployeeAdapter(List<Employee> employeelist,Employee_Meetings test)
    {
        //this.mCtx = mCtx;
        this.test = test;
        this.employeelist = employeelist;
    }

    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employee_meeting_list,null);
        return new EmployeeAdapter.EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.EmployeeViewHolder holder, int position)
    {

        Employee employee = employeelist.get(position);

        holder.txt1.setText("Meeting ID "+employee.getTmid());
        holder.txt2.setText(employee.getTmloc());
        holder.txt3.setText(employee.getTmone());
        holder.txt4.setText(employee.getTmtwo());
        holder.txt5.setText("Date "+employee.getTmstart());
        holder.txt6.setText(employee.getTmend());
        holder.txt7.setText(employee.getTmprocess());
        holder.txt8.setText(employee.getTmstatus());
    }

    @Override
    public int getItemCount()
    {
        return employeelist.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder
    {
        MyTextView_Lato_Medium txt1,txt8;
        MyTextView_Lato_Regular txt2,txt5,txt7,txt3,txt4;
        TextView txt6;

        public EmployeeViewHolder(View itemView)
        {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txtid);
            txt2 = itemView.findViewById(R.id.txtloc);
            txt3 = itemView.findViewById(R.id.txtto);
            txt4 = itemView.findViewById(R.id.txtt);
            txt5 = itemView.findViewById(R.id.txtstart);
            txt6 = itemView.findViewById(R.id.txtend);
            txt7 = itemView.findViewById(R.id.txtpr);
            txt8 = itemView.findViewById(R.id.txtst);

            //itemView.setOnClickListener(this);

        }
    }
}
