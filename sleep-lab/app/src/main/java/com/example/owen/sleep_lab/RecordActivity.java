package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;

public class RecordActivity extends AppCompatActivity
{
    private BluetoothAdapter mBluetoothAdapter;
    private Button startRecordingBtn, stopRecordingBtn;
    int REQUEST_ENABLE_BT =1;
    String deviceName,deviceMacAddress;
    public BluetoothServerSocket mServerSocket;
    public ArrayList deviceItemList;

    protected void onCreate(Bundle savedInstanceState)
    {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        startRecordingBtn =(Button)findViewById(R.id.statBtn);
        //stopRecordingBtn =(Button)findViewById(R.id.stopBtn);

        if (mBluetoothAdapter == null)
        {
            Toast.makeText(this, "Bluetooth is not available on this device", Toast.LENGTH_SHORT).show();
        }

        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
       /* discoverable();
        mBluetoothAdapter.cancelDiscovery();//use after discovery finished to reserve battery power
    */
        Log.d("devicelistr", "super called for deviceListFragment oCreate\n");

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if(pairedDevices.size()>0)
        {
            for (BluetoothDevice device : pairedDevices)
            {

            }
        }
    }

    public void discoverable()
    {
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        startActivity(discoverableIntent);
    }

    private BroadcastReceiver mReceiver= new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceName = device.getName();
                deviceMacAddress = device.getAddress(); // MAC address
            }
        }
    };

    protected void onDestroy()
    {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }

    public void recordingStopped()
    {
        //recording stopped send file to firebase and save values to history.api
    }
}
