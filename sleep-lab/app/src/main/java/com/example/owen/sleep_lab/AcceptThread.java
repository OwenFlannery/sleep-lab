package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;
/**
 * Created by owen on 19/04/2018.
 */


public class AcceptThread extends Thread
{

	private String TAG = getClass().getSimpleName();
	private final BluetoothServerSocket mServerSocket;
	UUID MY_UUID;
	private BluetoothAdapter mBluetoothAdapter;

	public AcceptThread()
	{
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		MY_UUID = UUID.randomUUID();

		// Use a temporary object that is later assigned to mmServerSocket
		// because mmServerSocket is final.
		BluetoothServerSocket tmp = null;
		try
		{
			// MY_UUID is the app's UUID string, also used by the client code.
			tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("sleepLab", MY_UUID);
		}
		catch(IOException e)
		{
			Log.e(TAG, "Socket's listen() method failed", e);
		}
		mServerSocket = tmp;
	}

	public void run()
	{
		BluetoothSocket socket = null;
		// Keep listening until exception occurs or a socket is returned.
		while(true)
		{
			try
			{
				socket = mServerSocket.accept();
			}
			catch(IOException e)
			{
				Log.e(TAG, "Socket's accept() method failed", e);
				break;
			}

			if(socket != null)
			{
				// A connection was accepted. Perform work associated with
				// the connection in a separate thread.

				//not done yet needs to be defined
				//ManageConnectThread(socket);***********************************************************
				try
				{
					mServerSocket.close();
					Log.d(TAG, "mServerSocket released ");
				}
				catch(IOException e)
				{
					Log.d(TAG, "failed to close mServerSocket");
					e.printStackTrace();
				}
				break;
			}
		}
	}

	// Closes the connect socket and causes the thread to finish.
	public void cancel()
	{
		try
		{
			mServerSocket.close();
		}
		catch(IOException e)
		{
			Log.e(TAG, "Could not close the connect socket", e);
		}
	}
}