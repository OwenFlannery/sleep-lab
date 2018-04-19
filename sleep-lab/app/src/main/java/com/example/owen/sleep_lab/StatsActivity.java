package com.example.owen.sleep_lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;
//import com.jjoe64.graphview.GraphView;

public class StatsActivity extends AppCompatActivity
{
	private String TAG = "StatsActivity";
	GraphView graph1, graph2, graph3;
	TextView textView;
	int x, y, i;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Scanner scanner=new Scanner(System.in);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		graph1 = (GraphView) findViewById(R.id.graph1);
		textView = (TextView) findViewById(R.id.textView);

		LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();


String text="";
		try
		{
			InputStream in = getAssets().open("app\\sampledata\\11-04-2018.txt");
			int size= in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			text = new String(buffer);

			textView.setText(text);



			//scanner = new Scanner(new File("app\\sampledata\\11-04-2018.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		/*int[] fileOne = new int[20000];

		int j = 0;
		for(i=0; i < 200||j<2; i++)
		{
				fileOne[i+1] = scanner.nextInt();

			Toast.makeText(this, "value"+fileOne[i], Toast.LENGTH_SHORT).show();
			x = i;
			y = fileOne[i];
			series.appendData(new DataPoint(x, y), true, 200);

			if(scanner.hasNextInt())
			{
				j = 0;
			}
			else
			{
				j = 5;
			}
		}
		graph1.addSeries(series);
	*/}
}
