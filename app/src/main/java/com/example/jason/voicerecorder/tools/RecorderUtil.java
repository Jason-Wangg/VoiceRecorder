package com.example.jason.voicerecorder.tools;

import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jason on 6/26/2016.
 * AUDIO TOOLS CLASS
 */
public class RecorderUtil {
    private MediaRecorder mr;
    private String RE_PATH = "CallAudio";
    private File fileDir;
    private boolean cardEst = false;
    private boolean isRec = false;
    private String phoneNum ;
    private String callType;

    public RecorderUtil(String phoneNum, String callType) {
        this.phoneNum = phoneNum;
        this.callType = callType;
        //if ExternalStorage is exist:
        if (this.cardEst = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            this.fileDir = new File(Environment.getExternalStorageDirectory().toString()+File.separator+this.RE_PATH+File.separator);
            //if Directory is exist:
            if (!this.fileDir.exists()){
                this.fileDir.mkdir();
            }
        }
    }
    public File audio(){
        File audioFile = null;
        String fileName;
        fileName = this.fileDir.toString()+File.separator+this.callType+this.phoneNum+"_"+new SimpleDateFormat("yy/MM/dd/HH-mm-ss").format(new Date())+".aac";
        audioFile = new File(fileName);
        this.mr =  new MediaRecorder();
        try {
            this.mr.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.mr.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        this.mr.setOutputFile(fileName);
        this.mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            this.mr.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mr.start();
        this.isRec = true;
        return  audioFile;
    }
    public void stop(){
        if (this.isRec){
            this.mr.stop();
            this.mr.release();
        }
    }
}
