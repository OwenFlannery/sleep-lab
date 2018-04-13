package com.example.owen.sleep_lab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;
import java.util.UUID;
import java.io.IOException;
import java.util.jar.Attributes;

import static android.content.ContentValues.TAG;
public class AcceptThread extends Thread
{
    private final BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter;
    final String NAME ="Mobile Device";
    final UUID MY_UUID = UUID.randomUUID();

    public AcceptThread() {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run()
    {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true)
        {
            try
            {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null)
            {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
               //write your own method  manageMyConnectedSocket(socket);*****************************
              try
              {
                  mmServerSocket.close();
              }
              catch(IOException e)
                {
                    Log.e(TAG, "Socket not closed!!",e);
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
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}