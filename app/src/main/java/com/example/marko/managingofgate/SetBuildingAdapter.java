package com.example.marko.managingofgate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SetBuildingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<GateObject> gateObjects = new ArrayList<>();
    Context mContext;

    public SetBuildingAdapter (Context context) {
        this.mContext = context;
    }

    public void setGateObjects (ArrayList<GateObject> gateObjects) {
        this.gateObjects.addAll(gateObjects);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gate_object_row, parent, false);
        return new MyGateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyGateHolder myGateHolder = (MyGateHolder) holder;


        GateObject gateObject = gateObjects.get(position);

        String id = String.valueOf(gateObject.getId());

        Log.d("id" , "id_gate: " + id);

        myGateHolder.id_gate.setText(id);


    }

    @Override
    public int getItemCount() {
        return gateObjects.size();
    }

    public class MyGateHolder extends RecyclerView.ViewHolder {
        public TextView id_gate, year, genre;

        public MyGateHolder(View view) {
            super(view);
            id_gate = (TextView) view.findViewById(R.id.id_gate);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }
}
