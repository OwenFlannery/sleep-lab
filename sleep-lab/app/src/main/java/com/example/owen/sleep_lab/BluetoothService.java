package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Created by owen on 19/04/2018.
 */

public class BluetoothService
{
	private Handler mHandler;
	private String TAG = "BLuetoothService";

	public interface MessageConstants
	{
		int MESSAGE_READ = 0;
		int MESSAGE_WRITE = 1;
		int MESSAGE_TOAST = 2;

	}

	private class ConnectedThread extends Thread
	{
		private final BluetoothSocket mSocket;
		private final InputStream input;
		private final OutputStream output;
		private byte[] mBuffer;

		public ConnectedThread(BluetoothSocket socket)
		{
			mSocket = socket;
			InputStream tempIn = null;
			OutputStream tempOut = null;

			try
			{
				tempIn = socket.getInputStream();
			}
			catch(IOException e)
			{
				Log.d(TAG, "problem with input stream");
				e.printStackTrace();
			}
			try
			{
				tempOut = socket.getOutputStream();
			}
			catch(IOException e)
			{
				Log.d(TAG, "problem with output stream");
				e.printStackTrace();
			}

			input = tempIn;
			output = tempOut;
		}

		public void run()
		{
			mBuffer = new byte[1024];
			int numBytes;

			while(true)
			{
				try
				{
					numBytes = input.read(mBuffer);
					Message readMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_READ, numBytes, -1, mBuffer);
					readMsg.sendToTarget();
				}
				catch(IOException e)
				{
					Log.d(TAG, "input stream interrupted");
					e.printStackTrace();
					break;
				}
			}
		}

		public void write(byte[] bytes)
		{
			try
			{
				output.write(bytes);
				Message writtenMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, mBuffer);
				writtenMsg.sendToTarget();
			}
			catch(IOException e)
			{
				Log.d(TAG, "output stream interrupted");
				e.printStackTrace();
				// Send a failure message back to the activity.
				Message writeErrorMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
				Bundle bundle = new Bundle();
				bundle.putString("toast", "Couldn't send data to the other device");
				writeErrorMsg.setData(bundle);
				mHandler.sendMessage(writeErrorMsg);
			}
		}

		public void cancel()
		{
			try
			{
				mSocket.close();
			}
			catch(IOException e)
			{
				Log.d(TAG, "connectedThread mSocket.close failed");
			}
		}
	}
}//end of BluetoothService11