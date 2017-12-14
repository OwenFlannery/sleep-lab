package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.renderscript.Element;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import java.util.List;

public class RecordActivity extends AppCompatActivity
{

    private Button startRecordingBtn, stopRecordingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        startRecordingBtn =(Button)findViewById(R.id.statBtn);
        //stopRecordingBtn =(Button)findViewById(R.id.stopBtn);
    }

    public void ScanForDevices()
    {
        //scan for devices sensor.api
    }

    public void startRecording()
    {
        //what actions to do while recording use recording.api
    }

    public void recordingStopped()
    {
        //recording stopped send file to firebase and save values to history.api
    }

}
