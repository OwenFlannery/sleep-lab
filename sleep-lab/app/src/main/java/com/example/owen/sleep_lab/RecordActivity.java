package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHealth;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.bluetooth.BluetoothHealthCallback;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class RecordActivity extends AppCompatActivity
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

		stopRecordBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

			}
		});

		startRecordBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

			}
		});

		scanBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				mBluetoothAdapter.startDiscovery();
				;
			}
		});
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

		final BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener()
		{
			@Override
			public void onServiceConnected(int i, BluetoothProfile bluetoothProfile)
			{
				Log.d(TAG, "onServiceConnected");
				if(i == BluetoothProfile.HEALTH)
				{
					mHeartRateMonitor = (BluetoothHealth) bluetoothProfile;
				}
			}

			@Override
			public void onServiceDisconnected(int i)
			{
				Log.d(TAG, "onServiceDisconnected");
				if(i == BluetoothProfile.HEALTH)
				{
					mHeartRateMonitor = null;
				}
			}
		};

		mBluetoothAdapter.getProfileProxy(this, mProfileListener, BluetoothProfile.HEALTH);

		BluetoothHealthCallback healthCallback = new BluetoothHealthCallback()
		{
			@Override
			public void onHealthAppConfigurationStatusChange(BluetoothHealthAppConfiguration config, int status)
			{
				super.onHealthAppConfigurationStatusChange(config, status);
			}
		};

		//mBluetoothAdapter.startDiscovery();//make phone visible
		//for pairing known devices
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		if(pairedDevices.size() > 0)
		{
			for(BluetoothDevice device : pairedDevices)
			{
				deviceName = device.getName();
				deviceMacAddress = device.getAddress();
			}

		}

		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
		startActivity(discoverableIntent);

		//for discovering ne devices

		//mBluetoothAdapter.cancelDiscovery();//stop searching for connections to save battery
		//mBluetoothAdapter.closeProfileProxy(mHeartRateMonitor);

	}//end of onCreate

	public class ManageConnectThread extends Thread
	{

		public ManageConnectThread()
		{
		}

		public void sendData(BluetoothSocket socket, int data) throws IOException
		{
			ByteArrayOutputStream output = new ByteArrayOutputStream(4);
			output.write(data);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(output.toByteArray());
		}

		public int receiveData(BluetoothSocket socket) throws IOException
		{
			byte[] buffer = new byte[4];
			ByteArrayInputStream input = new ByteArrayInputStream(buffer);
			InputStream inputStream = socket.getInputStream();
			inputStream.read(buffer);
			return input.read();
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
				deviceMacAddress = device.getAddress();
			}
		}
	};

	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
}
