package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
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
import java.util.UUID;

public class RecordActivity extends AppCompatActivity
{
    //blurtooth
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mGatt;
    private Button startRecordingBtn, stopRecordingBtn;
    int REQUEST_ENABLE_BT =1;
    String deviceName,deviceMacAddress;
    public BluetoothServerSocket mServerSocket;
    public ArrayList deviceItemList;
    private static final String TAG = RecordActivity.class.getSimpleName();

    //UUIDs
    private UUID HEART_RATE_SERVICE_UUID;
    private UUID HEART_RATE_MEASUREMENT_CHAR_UUID;
    private UUID HEART_RATE_CONTROL_POINT_CHAR_UUID;

    protected void onCreate(Bundle savedInstanceState)
    {
        /*
        HEART_RATE_SERVICE_UUID = ConvertFromInt(0x180D);
        HEART_RATE_MEASUREMENT_CHAR_UUID = ConvertFromInt(0x2A37);
        HEART_RATE_CONTROL_POINT_CHAR_UUID = ConvertFromInt(0x2A3);
        */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //tutorial way not using right now
        final BluetoothManager mbluetoothManager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //mBluetoothAdapter =mbluetoothManager.getAdapter();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        startRecordingBtn =(Button)findViewById(R.id.statBtn);
        //stopRecordingBtn =(Button)findViewById(R.id.stopBtn);

        if (mBluetoothAdapter == null)
        {
            Log.d(TAG,"device not compatible with bluetooth");
            Toast.makeText(this, "Bluetooth is not available on this device", Toast.LENGTH_SHORT).show();
        }

        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            IntentFilter enIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mReceiver,enIntent);
        }
        discoverable();
        mBluetoothAdapter.cancelDiscovery();//use after discovery finished to reserve battery power

        /*
        final BluetoothAdapter.LeScanCallback scanCallback= new BluetoothAdapter.LeScanCallback()
        {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes)
            {
                if(bluetoothDevice.getAddress()== HR_SENSOR_ADDRESS)
                {
                    myDevice = device;
                }
            }
        };

        BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
            @Override
            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
            }
        };
        gatt = myDevice.connectGatt(this,true,gattCallback);
        */

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
            if(action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED))
            {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);
                switch(state)
                {
                    case BluetoothAdapter.STATE_OFF:
                    Log.d(TAG,"state off");
                        Toast.makeText(context, "bluetooth off", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG,"state ON");
                        Toast.makeText(context, "bluetooth ON", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG,"state turning off");
                        Toast.makeText(context, "bluetooth turning off", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG,"state turning on");
                        Toast.makeText(context, "bluetooth turning on ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
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
    /*
    public UUID ConvertFromInt(int i)
    {
        final long MSB =0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }
    */

}
