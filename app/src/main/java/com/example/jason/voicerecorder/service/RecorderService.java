package com.example.jason.voicerecorder.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.jason.voicerecorder.tools.RecorderUtil;

import static android.telephony.TelephonyManager.CALL_STATE_RINGING;

public class RecorderService extends Service {
    private TelephonyManager tm;
    private String otn;
    private RecorderUtil recorderUtil;
    private Intent it;
    public RecorderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //create phone state listener;
    @Override
    public void onCreate() {
        this.tm = (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE);
        this.tm.listen(new PhoneStateListenerImpl(),PhoneStateListener.LISTEN_CALL_STATE);
        Log.e("RecorderService", "PhoneStateListener was created");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.otn = intent.getStringExtra("OTN");
        Toast.makeText(this, "PhoneStateListener was created", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    private class PhoneStateListenerImpl extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state){
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    RecorderService.this.recorderUtil = new RecorderUtil(RecorderService.this.otn,"OUTGOING");
                    RecorderService.this.recorderUtil.audio();
                    Toast.makeText(RecorderService.this, "recording", Toast.LENGTH_SHORT).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if (state == TelephonyManager.CALL_STATE_OFFHOOK){
                        RecorderService.this.recorderUtil = new RecorderUtil(incomingNumber,"INCOMING");
                        RecorderService.this.recorderUtil.audio();
                        Toast.makeText(RecorderService.this, "recording", Toast.LENGTH_SHORT).show();
                    }else {
                        if (RecorderService.this.recorderUtil!=null){
                            RecorderService.this.recorderUtil.stop();
                            Toast.makeText(RecorderService.this, "finished", Toast.LENGTH_SHORT).show();
                            RecorderService.this.recorderUtil=null;
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (RecorderService.this.recorderUtil!=null){
                        RecorderService.this.recorderUtil.stop();
                        Toast.makeText(RecorderService.this, "finished", Toast.LENGTH_SHORT).show();
                        RecorderService.this.recorderUtil=null;
                    }
                    break;

            }
        }
    }

    @Override
    public void onDestroy() {
        Log.e("RecorderService", "Service Destroyed");
        super.onDestroy();
    }
}
