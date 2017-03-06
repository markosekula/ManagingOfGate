package com.example.marko.managingofgate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {
    private Spinner objectNameSpinner;
    Button openGate;
    private Button closeGate;
    Context context;
    ArrayList<GateObject> listGateObject = new ArrayList<>();
    private String nameObject;
    private String phoneNumber;
    private boolean isOpenGate = false;
    private boolean isClicked;
    private String selectedObject;
    private String selectedPhone;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String gate;

    TextView databaseName;
    DataDB data = new DataDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        databaseName = (TextView)findViewById(R.id.databaseName);

        objectNameSpinner = (Spinner) findViewById(R.id.object_name_spinner);
        objectNameSpinner.setOnItemSelectedListener(new CustomOnGateObjectSelectedListener());

        openGate = (Button) findViewById(R.id.open_gate);
        closeGate = (Button) findViewById(R.id.close_gate);

        getGateList();

        openGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameObject != null && phoneNumber != null) {
                    selectedObject = nameObject;
                    selectedPhone = phoneNumber;
                    isClicked = true;

                    if (checkPermission()){
                        isOpenGate = true;
                        sendSMSForOpenOrCloseGate();
                    } else {
                        isOpenGate = true;
                        requestPermission();
                    }
                    openGate.setEnabled(false);
                    closeGate.setEnabled(true);

                }  else {
                    Toast.makeText(context,"You need to choose your object", Toast.LENGTH_SHORT).show();
                }
            }
        });

        closeGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameObject != null && phoneNumber != null) {
                    isClicked = false;

                    if (checkPermission()){
                        isOpenGate = false;
                        sendSMSForOpenOrCloseGate();
                    } else {
                        isOpenGate = false;
                        requestPermission();
                    }

                    openGate.setEnabled(true);
                    closeGate.setEnabled(false);
                }  else {
                    Toast.makeText(context,"You need to choose your object", Toast.LENGTH_SHORT).show();
                }

            }
        });

        databaseName.setText(data.getNameDB(this));

    }

    private void getGateList() {
            listGateObject =  data.getExistObject(this);

            if (listGateObject != null) {
                for (GateObject gb : listGateObject) {
//                    Log.d("arrayObject" , "nameObject: " +  gb.getNameObject());
//                    Log.d("arrayObject" , "isIsFill: " +  gb.isIsFill());
                }

                GateObject object = new GateObject();
                object.setNameObject("-Select your object-");
                listGateObject.add(0, object);

                ArrayAdapter<GateObject> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listGateObject);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                objectNameSpinner.setAdapter(adapter);
            }
    }

    public class CustomOnGateObjectSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            GateObject gateObject = (GateObject) parent.getItemAtPosition(pos);

            Log.v("GATE_OBJECT", "number: Item Selected number: " + gateObject.getPhoneNumber() + ", name: " + gateObject.getNameObject());
            nameObject = gateObject.getNameObject();
            phoneNumber = gateObject.getPhoneNumber();

            if (selectedObject != null && selectedPhone != null && nameObject != null && phoneNumber != null){
                if (!nameObject.equals(selectedObject) && !phoneNumber.equals(selectedPhone)){
                    openGate.setEnabled(true);
                    closeGate.setEnabled(true);
                } else {
                    if (isOpenGate){
                        openGate.setEnabled(false);
                        closeGate.setEnabled(true);
                    } else {
                        openGate.setEnabled(true);
                        closeGate.setEnabled(false);
                    }
                }
            }

            if (nameObject != null) {
                if (nameObject.equals("-Select your object-")){
                    openGate.setEnabled(true);
                    closeGate.setEnabled(true);
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    public void sendSMSForOpenOrCloseGate() {
        Log.v("GATE_OBJECT", "number: " + phoneNumber);
        Log.v("GATE_OBJECT", "name: " + nameObject);
        if (nameObject != null && phoneNumber != null){
            Log.v("GATE_OBJECT", "number: SEND_SMS: " + phoneNumber + ", name: " + nameObject);
            if (isClicked) {
                gate = "open gate";
                callSMSManager();
            } else {
                gate = "close gate";
                callSMSManager();
            }
        }
    }

    private void callSMSManager() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, nameObject + "\n" + gate , null, null);
            Toast.makeText(context,"Sms sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults.length > 0) {
                    boolean smsAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (smsAccepted) {
                        sendSMSForOpenOrCloseGate();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(SEND_SMS)) {
                                showMessageOKCancel(getResources().getString(R.string.you_need_to_permission),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ SEND_SMS},
                                                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                                                }
                                            }
                                        });
                                return;
                            } else {
                                showMessageOKCancel(getResources().getString(R.string.never_ask_again),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), okListener)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.set_button) {
            goToSetBuildingActivity();
        }

        return super.onOptionsItemSelected(item);

    }

    private void goToSetBuildingActivity() {
        Intent intent = new Intent(MainActivity.this, SetBuildingActivity.class);
        startActivity(intent);
        finish();
    }

}
