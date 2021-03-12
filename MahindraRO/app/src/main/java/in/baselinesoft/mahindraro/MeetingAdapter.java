package in.baselinesoft.mahindraro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import custom_font.MyTextView_Lato_Medium;
import custom_font.MyTextView_Lato_Regular;

/**
 * Created by Pratik on 12/03/2018.
 */

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>
{
    //private Context mCtx;
    MenuHome test;
    RecyclerViewClick recyclerViewClick;
    private List<Meeting> meetinglist;
    String aloc,astart,aend,aone,atwo,aprocess,astatus;


    public MeetingAdapter(List<Meeting> meetinglist,MenuHome test)
    {
        //this.mCtx = mCtx;
        this.test = test;
        this.meetinglist = meetinglist;
    }


    @Override
    public MeetingAdapter.MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meeting_list,null);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingAdapter.MeetingViewHolder holder, int position)
    {
        Meeting meeting = meetinglist.get(position);

        holder.txt1.setText("Meeting ID "+meeting.getTmid());
        holder.txt2.setText(meeting.getTmloc());
        holder.txt3.setText(meeting.getTmone());
        holder.txt4.setText(meeting.getTmtwo());
        holder.txt5.setText(meeting.getTmstart());
        holder.txt6.setText(meeting.getTmend());
        holder.txt7.setText(meeting.getTmprocess());
        holder.txt8.setText(meeting.getTmstatus());
    }

    @Override
    public int getItemCount() {
        return meetinglist.size();
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyTextView_Lato_Medium txt1,txt8;
        MyTextView_Lato_Regular txt2,txt5,txt7;
        TextView txt3,txt4,txt6;
        public MeetingViewHolder(View itemView)
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            //Toast.makeText(v.getContext(), "ok", Toast.LENGTH_LONG).show();
            if(test!= null)
            {
                aloc = txt2.getText().toString().trim();
                astart = txt5.getText().toString().trim();
                aend = txt6.getText().toString().trim();
                aone = txt3.getText().toString().trim();
                atwo = txt4.getText().toString().trim();
                aprocess = txt7.getText().toString().trim();
                astatus = txt8.getText().toString().trim();

                //Toast.makeText(get, "ok", Toast.LENGTH_LONG).show();

                test.getData(aloc,astart,aend,aone,atwo,aprocess,astatus);
            }
        }
    }
}
