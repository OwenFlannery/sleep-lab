package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;
/**
 * Created by owen on 19/04/2018.
 */


public class ConnectThread extends Thread
{
	private BluetoothSocket mSocket;
	private BluetoothDevice mDevice;
	private String TAG ="ConnectThread";
	private UUID MY_UUID = UUID.randomUUID();

	public ConnectThread(BluetoothDevice device)
	{
		BluetoothSocket temp = null;
		mDevice = device;

		try
		{
			temp = device.createRfcommSocketToServiceRecord(MY_UUID);
		}
		catch(IOException e)
		{
			Log.d(TAG, "connect thread rfcomm failed");
			e.printStackTrace();
		}
		mSocket = temp;
	}

	public void run()
	{
		//mBluetoothAdapter.cancelDiscovery();
		try
		{
			Log.d(TAG, "connectThread mSocket.connect called");
			mSocket.connect();
		}
		catch(IOException e1)
		{
			Log.d(TAG, "connectThread mSocket.connect failed");
			e1.printStackTrace();
		}
		return;
	}

	//ManageConnectThread(socket);

	public void cancel()
	{
		try
		{
			mSocket.close();
		}
		catch(IOException e)
		{
			Log.d(TAG, "mSocket.close failed");
			e.printStackTrace();
		}
	}
}//end of ConnectThread