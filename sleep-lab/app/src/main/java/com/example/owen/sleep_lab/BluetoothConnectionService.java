package com.example.owen.sleep_lab;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by owen on 16/04/2018.
 */

public class BluetoothConnectionService
{
    private static final String TAG= "BluetoothConnSerClass";
    private static final String appName = "SleepLab";
    private static final UUID myUUID = UUID.fromString("Bce255c0-200a-11e0-ac64-0800200c9a66");


    private BluetoothAdapter mBluetoothAdapter;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private BluetoothDevice mBluetoothDevice;
    private UUID deviceUUID;
    ProgressDialog mprogress;
    Context mContext;
    private ConnectedThread mConnectedThread;


    public BluetoothConnectionService(Context context)
    {
        mContext = context;
        this.mBluetoothAdapter = mBluetoothAdapter.getDefaultAdapter();
        start();
    }

    public class AcceptThread extends Thread
    {
        private final BluetoothServerSocket mServerSocket;

        public AcceptThread()
        {
            BluetoothServerSocket temp =null;
            try
            {
                temp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(appName, myUUID);
            }
            catch(IOException e)
            {

            }
            mServerSocket = temp;

        }
        public void run()
        {
            Log.d(TAG,"run Accept Thread");
            BluetoothSocket socket = null;
            try
            {
                Log.d(TAG,"run server socket start");
                socket = mServerSocket.accept();
            }
            catch(IOException e)
            {
                Log.d(TAG,"Accept Thread exception");
            }


            if(socket!=null)
            {
                connected(socket,mDevice);
            }
            Log.d(TAG,"Accept Thread finished");
        }
        public void cancel()
        {
            Log.d(TAG,"cancel Accept Thread");
            try
            {
                mServerSocket.close();
            }
            catch(IOException e)
            {
                Log.d(TAG,"cancel Accept Thread failed" );
            }
        }
    }

    private class ConnectThread extends Thread
    {
        private BluetoothSocket mSocket;
        public ConnectThread(BluetoothDevice device,UUID uuid)
        {
            Log.d(TAG,"Conect thread started");
            mBluetoothDevice = device;
            deviceUUID = uuid;
        }

        public void run()
        {
            BluetoothSocket temp = null;
            Log.d(TAG,"run connect thread ");

            try
            {
                Log.d(TAG," connect thread creating RFcommSocket ");
                temp = mBluetoothDevice.createRfcommSocketToServiceRecord(deviceUUID);
            }
            catch(IOException e)
            {
                Log.d(TAG,"exception in run connect thread ");
                e.printStackTrace();
            }
            mSocket=temp;
            mBluetoothAdapter.cancelDiscovery();

            try
            {
                mSocket.connect();
                Log.d(TAG,"connected thread connected ");
            } catch (IOException e)
            {
                try
                {
                    mSocket.close();
                    Log.d(TAG,"socket closed because ailed connection");
                } catch (IOException e1)
                {
                    Log.d(TAG,"Unable to close socket");
                    e1.printStackTrace();
                }
                Log.d(TAG,"connect thread connect exception ");
                e.printStackTrace();
            }
            Log.d(TAG," connect thread unable to connect to UUID"+ deviceUUID);
        }

        //not done yet
        connected(mSocket ,mDevice);

        public void cancel()
        {
            try
            {
                mSocket.close();
                Log.d(TAG,"");
            }catch(IOException e)
            {
                Log.d(TAG, "cansel close of socket connected thread failed");
            }
        }
    }
    public synchronized void start()
    {
        Log.d(TAG,"start");

        if(mConnectThread!=null)
        {
            mConnectThread.cancel();
            mConnectThread= null;
        }

        if(mAcceptThread == null)
        {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
    }
    public void startClient(BluetoothDevice device , UUID uuid)
    {
        Log.d(TAG,"strt client");
        mprogress = ProgressDialog.show(mContext,"connecting Bluetooth",true);

        mConnectThread = new ConnectThread(device ,uuid);
        mConnectThread.start();
    }
    private class ConnectedThread
    {
        private final BluetoothSocket mSocket;
        private final InputStream mInputS;
        private final OutputStream mOutputS;

        public ConnectedThread(BluetoothSocket socket)
        {
            Log.d(TAG,"start connected thread ");
            mSocket =socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;
            mprogress.dismiss();
        }

    }

}
