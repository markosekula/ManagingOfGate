package com.example.marko.managingofgate.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.marko.managingofgate.R;
import com.example.marko.managingofgate.activity.SetOneObjectActivity;
import java.util.ArrayList;

public class SetBuildingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<GateObject> gateObjects = new ArrayList<>();
    private Context mContext;

    public SetBuildingAdapter (Context context) {
        this.mContext = context;
    }

    public void setGateObjects (ArrayList<GateObject> gateObjects) {
        this.gateObjects = gateObjects;
        notifyDataSetChanged();
    }

    public void clearGateObjects() {
        gateObjects.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gate_object_row, parent, false);
        return new MyGateHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyGateHolder myGateHolder = (MyGateHolder) holder;

        GateObject gateObject = gateObjects.get(position);

        final String id = String.valueOf(gateObject.getId());
        final String nameObject = gateObject.getNameObject();
        final String phone = gateObject.getPhoneNumber();

        int new_position = position + 1;
        String title = mContext.getResources().getString(R.string.object_title);

        String nameOb = "";
        if (nameObject.length() > 0) {
            nameOb =  " - " + nameObject;

            Log.d("MASTER" , "name: " + nameOb);
        }

        myGateHolder.titleObject.setText(title + new_position + nameOb);

        myGateHolder.titleObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SetOneObjectActivity.class);
                intent.putExtra("id_gate", id);
                intent.putExtra("name_object", nameObject);
                intent.putExtra("phone", phone);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gateObjects.size();
    }

    private class MyGateHolder extends RecyclerView.ViewHolder {
        private TextView titleObject;

        private MyGateHolder(View view) {
            super(view);
            titleObject = (TextView) view.findViewById(R.id.title_object);
        }
    }

}
