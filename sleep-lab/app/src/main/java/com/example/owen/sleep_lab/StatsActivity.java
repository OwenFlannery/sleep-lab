package com.example.owen.sleep_lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
//import com.jjoe64.graphview.GraphView;

public class StatsActivity extends AppCompatActivity
{
	private String TAG = "StatsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Scanner scanner;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		try
		{
			scanner = new Scanner(new File("app\\sampledata\\11-04-2018.txt"));
		}
		catch(IOException e)
		{
			Log.d(TAG, "file not found ");

		}
		int[] tall = new int[100];
		int i = 0;
		while(scanner.hasNextInt())
		{
			tall[i++] = scanner.nextInt();
		}

	}
}
