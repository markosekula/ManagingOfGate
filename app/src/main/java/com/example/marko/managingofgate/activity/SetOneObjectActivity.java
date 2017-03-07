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

public class SetOneObjectActivity extends AppCompatActivity {
    EditText edObject;
    EditText edPhoneNumber;
    Button update;
    Button reset;
    Button delete;
    DataDB data = new DataDB();
    Context context;
    String object_set;
    String phone_set;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_one_object);
        context = this;

        Intent intent = getIntent();
        final String id_gate = (String) intent.getSerializableExtra("id_gate");
        id = Integer.parseInt(id_gate);

        final String nameObject = (String) intent.getSerializableExtra("name_object");
        final String phone = (String) intent.getSerializableExtra("phone");

        edObject = (EditText) findViewById(R.id.set_one_object);
        edObject.setText(nameObject);

        edPhoneNumber = (EditText) findViewById(R.id.set_number);
        edPhoneNumber.setText(phone);

        update = (Button) findViewById(R.id.edit_object);
        reset = (Button) findViewById(R.id.reset_object);
        delete = (Button) findViewById(R.id.delete_object);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    private void saveData() {
        object_set = edObject.getText().toString();
        phone_set = edPhoneNumber.getText().toString();

        if (object_set.length() > 0 && phone_set.length() > 0) {
            data.updateObject(context, object_set, phone_set, id);
            Toast.makeText(context, getResources().getString(R.string.updated_object), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, getResources().getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
        }
    }

    private void resetData() {
        String object = "";
        String phone = "";
        edObject.setText("");
        edPhoneNumber.setText("");

        data.resetObject(context, object, phone, id);
        Toast.makeText(context, getResources().getString(R.string.reset_object), Toast.LENGTH_SHORT).show();
    }

    private void deleteData() {
        edObject.setText("");
        edPhoneNumber.setText("");

        data.deleteObject(context, id);
        Toast.makeText(context, getResources().getString(R.string.deleted_object), Toast.LENGTH_SHORT).show();
    }

}
