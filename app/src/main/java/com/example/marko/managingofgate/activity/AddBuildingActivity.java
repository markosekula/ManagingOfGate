package com.example.marko.managingofgate.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.marko.managingofgate.R;
import com.example.marko.managingofgate.dao.DataDB;

public class AddBuildingActivity extends AppCompatActivity {
    EditText edNewBuilding;
    EditText edNewPhone;
    Button addObject;
    DataDB data = new DataDB();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_building);
        context = this;

        edNewBuilding = (EditText) findViewById(R.id.add_new_building);
        edNewPhone = (EditText) findViewById(R.id.add_new_number);
        addObject = (Button) findViewById(R.id.add_new_object);

        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewObject();
            }
        });
    }

    private void addNewObject() {
        String building = edNewBuilding.getText().toString();
        String phone = edNewPhone.getText().toString();

        if (building.length() > 0 && phone.length() > 0){
            data.addObject(this, building, phone);
            Toast.makeText(this, getResources().getString(R.string.add_object), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddBuildingActivity.this , MainActivity_.class);
        AddBuildingActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    }
}
