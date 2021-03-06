package com.netcommlabs.greencontroller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netcommlabs.greencontroller.InterfaceValveAdapter;
import com.netcommlabs.greencontroller.R;
import com.netcommlabs.greencontroller.activities.DeviceDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/1/2017.
 */

public class ValvesListAdapter extends RecyclerView.Adapter<ValvesListAdapter.MyViewHolder> {

    private ArrayList<String> listValves;
    private DeviceDetails mContext;
    private List<View> listViewsCollection;

    public ValvesListAdapter(DeviceDetails mContext, ArrayList<String> listValves) {
        this.mContext = mContext;
        this.listValves = listValves;

        listViewsCollection = new ArrayList<>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llValveNameColor;
        TextView tvValveName;

        public MyViewHolder(View itemView) {
            super(itemView);


            llValveNameColor = itemView.findViewById(R.id.llValveNameColor);
            tvValveName = itemView.findViewById(R.id.tvValveName);

            listViewsCollection.add(llValveNameColor);
//---- First Item selected----
            listViewsCollection.get(0).setBackgroundResource(R.drawable.volve_bg_shadow_select);


            llValveNameColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TextView tvClickedValveName = v.findViewById(R.id.tvValveName);
                    String clickedValveName = tvClickedValveName.getText().toString();

                   // mContext.clickPassDataToAct(clickedValveName);

                            Log.e("@@@VALVE NAME", clickedValveName);

                    for (int i = 0; i < listViewsCollection.size(); i++) {
                        listViewsCollection.get(i).setBackgroundResource(R.drawable.volve_bg_shadow);
                    }

                    int pos = getAdapterPosition();
                    listViewsCollection.get(pos).setBackgroundResource(R.drawable.volve_bg_shadow_select);
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_valves_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvValveName.setText(listValves.get(position));
    }

    @Override
    public int getItemCount() {
        return listValves.size();
    }

}
