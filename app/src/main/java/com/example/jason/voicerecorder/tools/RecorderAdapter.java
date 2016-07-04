package com.example.jason.voicerecorder.tools;

import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * Created by Jason on 6/25/2016.
 */
public class RecorderAdapter extends ArrayAdapter<Recorder> {
    public RecorderAdapter(Context context, int resource, Recorder[] objects) {
        super(context, resource, objects);
    }
}
