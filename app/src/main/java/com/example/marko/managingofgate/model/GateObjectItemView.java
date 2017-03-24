package com.example.marko.managingofgate.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.marko.managingofgate.R;
import com.example.marko.managingofgate.activity.SetOneObjectActivity;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.gate_object_row)
public class GateObjectItemView extends LinearLayout {

    @ViewById
    TextView title_object;

    Context context;

    public GateObjectItemView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind (final GateObject gateObject, int position) {
        String title = context.getResources().getString(R.string.object_title);
        final String nameObject = gateObject.getNameObject();
        final String id = String.valueOf(gateObject.getId());
        final String phone = gateObject.getPhoneNumber();

        String nameOb = "";
        if (nameObject.length() > 0) {
            nameOb =  " - " + nameObject;
        }

        title_object.setText(title + position + nameOb);

        title_object.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SetOneObjectActivity.class);
                intent.putExtra("id_gate", id);
                intent.putExtra("name_object", nameObject);
                intent.putExtra("phone", phone);
                context.startActivity(intent);
            }
        });

    }
}
