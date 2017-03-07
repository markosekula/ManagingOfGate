package com.example.marko.managingofgate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.marko.managingofgate.model.GateObject;
import com.example.marko.managingofgate.R;
import com.example.marko.managingofgate.model.SetBuildingAdapter;
import com.example.marko.managingofgate.dao.DataDB;

import java.util.ArrayList;

public class SetBuildingActivity extends AppCompatActivity {
    ArrayList<GateObject> gateObjects = new ArrayList<>();
    DataDB data = new DataDB();
    RecyclerView recyclerViewGateObject;
    private SetBuildingAdapter setBuildingAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_building);

        recyclerViewGateObject = (RecyclerView) findViewById(R.id.recycler_view_gate_objects);
        setBuildingAdapter = new SetBuildingAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewGateObject.setLayoutManager(linearLayoutManager);
        recyclerViewGateObject.setAdapter(setBuildingAdapter);
    }

    private void getAllObjects() {
        gateObjects = data.getAllObjects(this);

        if (gateObjects != null) {
            for (GateObject gb : gateObjects) {
                Log.d("arrayObject", "id: " + gb.getId());
                Log.d("arrayObject", "nameObject: " + gb.getNameObject());
                Log.d("arrayObject", "phoneNumber: " + gb.getPhoneNumber());
                Log.d("arrayObject", "isIsFill: " + gb.isIsFill());
            }
        }

        setBuildingAdapter.setGateObjects(gateObjects);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllObjects();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SetBuildingActivity.this , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
