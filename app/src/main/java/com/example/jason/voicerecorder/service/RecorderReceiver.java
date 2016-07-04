package com.example.jason.voicerecorder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jason on 6/26/2016.
 */
public class RecorderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            String otn = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Intent it = new Intent(context,RecorderService.class);
            it.putExtra("OTN",otn);
            context.startService(it);
        }else{
            context.startService(new Intent(context,RecorderService.class));
        }
    }
}
