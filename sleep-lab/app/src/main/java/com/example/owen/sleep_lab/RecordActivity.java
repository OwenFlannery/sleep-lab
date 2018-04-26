package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHealth;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener
{
	//bluetooth
	private BluetoothAdapter mBluetoothAdapter;
	private Button scanBtn, startRecordBtn, stopRecordBtn;
	int REQUEST_ENABLE_BT = 1;
	public BluetoothHealth mHeartRateMonitor;
	public BluetoothDevice mdevice;
	String deviceName, deviceMacAddress;


	private static final String TAG = RecordActivity.class.getSimpleName();

	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		startRecordBtn = (Button) findViewById(R.id.startRecordBtn);
		stopRecordBtn = (Button) findViewById(R.id.stopRecordBtn);
		scanBtn = (Button) findViewById(R.id.scanBtn);
		startRecordBtn.setOnClickListener(this);
		stopRecordBtn.setOnClickListener(this);
		scanBtn.setOnClickListener(this);

		//broadcast receiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter == null)
		{
			Log.d(TAG, "device not compatible with bluetooth");
			Toast.makeText(this, "Bluetooth is not available on this device", Toast.LENGTH_SHORT).show();
		}

		if(!mBluetoothAdapter.isEnabled())
		{
			Intent enableBtIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			IntentFilter enIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
			registerReceiver(mReceiver, enIntent);
		}

	}//end of onCreate

	@Override
	public void onClick(View v)
	{
		if (v == scanBtn)
		{
			startActivity(new Intent(this, DeviceScanActivity.class));
		}
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Toast.makeText(context, "mReceiver called", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "your device is now discoverable", Toast.LENGTH_SHORT).show();

			String action = intent.getAction();

			if(BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				deviceName = device.getName();
				Toast.makeText(RecordActivity.this, "device found"+device.getName(), Toast.LENGTH_SHORT).show();
				deviceMacAddress = device.getAddress();
			}
		}
	};
}
