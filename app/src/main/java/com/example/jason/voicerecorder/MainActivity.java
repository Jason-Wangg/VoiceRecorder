package com.example.jason.voicerecorder;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jason.voicerecorder.service.RecorderService;


public class MainActivity extends AppCompatActivity {
    Switch recorderSwitch;
    PackageManager pm;
    int status=0;
    //    Intent it = new Intent(new Intent(MainActivity.this,RecorderService.class));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        requestMultiPermission();
        recorderSwitch = (Switch) findViewById(R.id.re_switch);
        Log.e("MainActivity", "switch  "+recorderSwitch.isChecked());
        final Context context;
        try {
            context = MainActivity.this;

            final ComponentName RecorderReceiver = new ComponentName(context, com.example.jason.voicerecorder.service.RecorderReceiver.class);
            pm = context.getPackageManager();
            status = pm.getComponentEnabledSetting(RecorderReceiver);
            if (status ==1){
                recorderSwitch.setChecked(true);
            }else {
                recorderSwitch.setChecked(false);
            }
            Log.e("MainActivity", "status "+ status);
        recorderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        pm.setComponentEnabledSetting(RecorderReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);

                        //  if service is running :
                        recorderSwitch.setChecked(true);
                        isChecked = true;
                        Toast.makeText(context, "Broadcast enabled "+status, Toast.LENGTH_SHORT).show();
                        // if service is not running :


                    }
                    //unchecked:
                    else {
                        pm.setComponentEnabledSetting(RecorderReceiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 2);
                        //if service stoped:
                        recorderSwitch.setChecked(false);
                        isChecked = false;
                        Toast.makeText(context, "Broadcast disabled "+status, Toast.LENGTH_SHORT).show();
                        // else service still running

                    }
                }

        });
        }catch (Exception e){
            e.printStackTrace();
        }
        /*recorderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context==null) {
                    context = MainActivity.this;
                    if (recorderSwitch.isChecked()) {
                        final ComponentName RecorderReceiver = new ComponentName(context, com.example.jason.voicerecorder.service.RecorderReceiver.class);
                        pm = context.getPackageManager();
                        pm.setComponentEnabledSetting(RecorderReceiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 2);
                        //if service stoped:
                        Toast.makeText(context, "Broadcast disabled", Toast.LENGTH_SHORT).show();
                        // else service still running

                    }
                    //unchecked:
                    else {
                        final ComponentName RecorderReceiver = new ComponentName(context, com.example.jason.voicerecorder.service.RecorderReceiver.class);
                        pm = context.getPackageManager();
                        pm.setComponentEnabledSetting(RecorderReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);
                        //  if service is running :
                        Toast.makeText(context, "Broadcast enabled", Toast.LENGTH_SHORT).show();
                        // if service is not running :

                    }
                }else {
                    //Check switch view's status,
                    //if is checked:
                    if (recorderSwitch.isChecked()) {
                        final ComponentName RecorderReceiver = new ComponentName(context, com.example.jason.voicerecorder.service.RecorderReceiver.class);
                        pm = context.getPackageManager();
                        pm.setComponentEnabledSetting(RecorderReceiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 2);
                        //if service stoped:
                        Toast.makeText(context, "Broadcast disabled", Toast.LENGTH_SHORT).show();
                        // else service still running

                    }
                    //unchecked:
                    else {
                        final ComponentName RecorderReceiver = new ComponentName(context, com.example.jason.voicerecorder.service.RecorderReceiver.class);
                        pm = context.getPackageManager();
                        pm.setComponentEnabledSetting(RecorderReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);
                        //  if service is running :
                        Toast.makeText(context, "Broadcast enabled", Toast.LENGTH_SHORT).show();
                        // if service is not running :

                    }
                }
            }
        });*/
        initRecorderList();
    }

    private void requestMultiPermission() {
        final int REQUEST_CODE = 1;
            String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.PROCESS_OUTGOING_CALLS,Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    private void initRecorderList() {
    }
}
